package dao.impl.hibernate;

import entities.Answer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

import java.util.List;

public class AnswerDAO {
    public static void addAnswer(Answer answer) {
        if (getAnswer(answer.getContent()) == null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.save(answer);
            transaction.commit();
            session.close();
        }
    }

    public static Answer getAnswer(int id) {
        return SessionFactoryManager.getInstance()
                                    .getSession()
                                    .get(Answer.class, id);
    }

    public static Answer getAnswer(String content) {
        Query query = SessionFactoryManager.getInstance()
                                           .getSession()
                                           .createQuery("from Answer where content=:content");
        query.setParameter("content", content);
        return (Answer) query.uniqueResult();
    }

    public static List getAnswers() {
        Query query = SessionFactoryManager.getInstance()
                                           .getSession()
                                           .createQuery("from Answer");
        return query.getResultList();
    }
}
