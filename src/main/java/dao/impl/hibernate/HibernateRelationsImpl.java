package dao.impl.hibernate;

import entities.Answer;
import entities.Question;
import entities.Relations;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.connectors.SessionFactoryManager;

import java.util.ArrayList;
import java.util.List;

import static utils.queries.HQLSection.SELECT_RELATION_WITHOUT_ANSWER;

@SuppressWarnings("JpaQlInspection")
public class HibernateRelationsImpl extends AbstractHibernateImpl{
    private static final String TABLE_NAME = "Relations";
    private HibernateUserImpl userHandler;
    private HibernateQuestionImpl questionHandler;
    private HibernateAnswerImpl answerHandler;

    public HibernateRelationsImpl() {
        userHandler = new HibernateUserImpl();
        questionHandler = new HibernateQuestionImpl();
        answerHandler = new HibernateAnswerImpl();
    }

    public void askQuestion(String username, String content) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user = userHandler.getUser(username);
        questionHandler.addQuestion(content);
        Question question = questionHandler.getQuestion(content);
        Relations relation;
        if (getRelation(user, question, session) != null) {
            transaction.rollback();
        } else {
            relation = new Relations(user, question);
            session.save(relation);
            transaction.commit();
        }
        session.close();
    }

    public void answerQuestion(String username, String questionContent, String answerContent) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user = userHandler.getUser(username);
        Question question = questionHandler.getQuestion(questionContent);
        answerHandler.addAnswer(new Answer(answerContent), session);
        Answer answer = answerHandler.getAnswer(answerContent, session);
        Relations relation;
        if ((relation = getRelation(user, question, session)).getAnswer() != null) {
            transaction.rollback();
        } else {
            relation.setAnswer(answer);
            session.update(relation);
            transaction.commit();
        }
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<Relations> getFullData() {
        List<Relations> relations = new ArrayList<>();
        getTable(TABLE_NAME).forEach(o -> relations.add((Relations) o));
        return relations;
    }

    public void removeRelation(Relations relation, Session session) {
        session.delete(relation);
        session.close();
    }

    private Relations getRelation(User user, Question question, Session session) {
        final String USER_FIELD = "user";
        final String QUESTION_FIELD = "question";
        Query query = session.createQuery(SELECT_RELATION_WITHOUT_ANSWER.getHql());
        query.setParameter(USER_FIELD, user);
        query.setParameter(QUESTION_FIELD, question);

        return (Relations) query.uniqueResult();
    }

    @Override
    public int countRows() {
        return countTableRows(TABLE_NAME);
    }
}
