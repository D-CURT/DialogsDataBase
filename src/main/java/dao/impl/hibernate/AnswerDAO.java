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
            Session session = SessionFactoryManager.getFactory()
                                                   .openSession();
            Transaction transaction = session.beginTransaction();
            session.save(answer);
            transaction.commit();
            session.close();
        }
    }

    public static Answer getAnswer(int id) {
        return SessionFactoryManager.getFactory()
                                    .openSession()
                                    .get(Answer.class, id);
    }

    public static Answer getAnswer(String content) {
        Session session = SessionFactoryManager.getFactory()
                                               .openSession();
        Query query = session.createQuery("from Answer where content =: content");
        query.setParameter("content", content);
        Answer answer = (Answer) query.uniqueResult();
        session.close();
        return answer;
    }

    public static List getAnswers() {
        Query query = SessionFactoryManager.getFactory()
                                           .openSession()
                                           .createQuery("from Answer");
        return query.getResultList();
    }
}
