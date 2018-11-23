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
            Session session = SessionFactoryManager.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.save(question);
            transaction.commit();
            session.close();
        }
    }

    public static Question getQuestion(int id) {
        return SessionFactoryManager.getInstance()
                                    .getSession()
                                    .get(Question.class, id);
    }

    public static Question getQuestion(String content) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Query query = session.createQuery("from Question where content=:content");
        query.setParameter("content", content);
        Question question = (Question) query.uniqueResult();
        session.close();
        return question;
    }

    public static List getQuestions() {
        Query query = SessionFactoryManager.getInstance()
                                           .getSession()
                                           .createQuery("from Question");
        return query.getResultList();
    }

    public static void removeQuestion(String content) {
        Question question = getQuestion(content);
        Session session = SessionFactoryManager.getInstance().getSession();
        session.delete(question);
        session.close();
    }
}