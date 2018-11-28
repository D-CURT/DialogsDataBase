package dao.impl.hibernate;

import entities.Question;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.connectors.SessionFactoryManager;

import java.util.List;

import static utils.queries.HQLSection.SELECT_QUESTION_BY_CONTENT;

@SuppressWarnings("JpaQlInspection")
public class HibernateQuestionImpl extends AbstractHibernateImpl{
    private static final String TABLE_NAME = "Question";

    public void addQuestion(String content) {
        if (getQuestion(content) == null) {
            Session session = SessionFactoryManager.getInstance()
                                                   .getSession();
            Transaction transaction = session.beginTransaction();
            session.save(new Question(content));
            transaction.commit();
            session.close();
        }
    }

    public Question getQuestion(int id) {
        return SessionFactoryManager.getInstance()
                                    .getSession()
                                    .get(Question.class, id);
    }

    public Question getQuestion(String content) {
        final String CONTENT_FIELD = "content";
        Session session = SessionFactoryManager.getInstance()
                                               .getSession();
        Query query = session.createQuery(SELECT_QUESTION_BY_CONTENT.getHql());
        query.setParameter(CONTENT_FIELD, content);
        Question question = (Question) query.uniqueResult();
        session.close();
        return question;
    }

    public List getQuestions() {
        return getTable(TABLE_NAME);
    }

    public void removeQuestion(String content) {
        final String QUESTION_FIELD = "question";
        Question question;
        if ((question = getQuestion(content)) != null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
           /*Query query = session.createQuery(DELETE_RELATION_BY_QUESTION.getHql());
            query.setParameter(QUESTION_FIELD, question);
            query.executeUpdate();*/
            session.delete(question);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public int countRows() {
        return countTableRows(TABLE_NAME);
    }
}