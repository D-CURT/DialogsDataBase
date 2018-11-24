package dao.impl.hibernate;

import entities.Question;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

import java.util.List;

public class QuestionDAO {
    public static void addQuestion(Question question) {
        if (getQuestion(question.getContent()) == null) {
            Session session = SessionFactoryManager.getFactory()
                                                   .openSession();
            Transaction transaction = session.beginTransaction();
            session.save(question);
            transaction.commit();
            session.close();
        }
    }

    public static Question getQuestion(int id) {
        return SessionFactoryManager.getFactory()
                                    .openSession()
                                    .get(Question.class, id);
    }

    public static Question getQuestion(String content) {
        Session session = SessionFactoryManager.getFactory()
                                               .openSession();
        Query query = session.createQuery("from Question where content=:content");
        query.setParameter("content", content);
        Question question = (Question) query.uniqueResult();
        session.close();
        return question;
    }

    public static List getQuestions() {
        Query query = SessionFactoryManager.getFactory()
                                           .openSession()
                                           .createQuery("from Question");
        return query.getResultList();
    }

    public static void removeQuestion(String content) {
        Question question = getQuestion(content);
        Session session = SessionFactoryManager.getFactory()
                                               .openSession();
        session.delete(question);
        session.close();
    }
}