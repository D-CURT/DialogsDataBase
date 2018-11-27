package dao.impl.hibernate;

import entities.Answer;
import entities.Question;
import entities.Relations;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

import java.util.ArrayList;
import java.util.List;

public class HibernateRelationsImpl {
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
        Query query = SessionFactoryManager.getInstance()
                .getSession()
                .createQuery("from Relations");
        query.getResultList().forEach(o -> relations.add((Relations) o));
        return relations;
    }

    public void removeRelation(Relations relation, Session session) {
        session.delete(relation);
        session.close();
    }

    private Relations getRelation(User user, Question question, Session session) {
        Query query = session.createQuery(
                "from Relations where user =: user and question =: question and answer is null ");
        query.setParameter("user", user);
        query.setParameter("question", question);

        return (Relations) query.uniqueResult();
    }
}
