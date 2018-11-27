package dao.impl.hibernate;

import entities.Question;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

import java.util.List;

public class HibernateQuestionImpl {
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
        Session session = SessionFactoryManager.getInstance()
                                               .getSession();
        Query query = session.createQuery("from Question where content =: content");
        query.setParameter("content", content);
        Question question = (Question) query.uniqueResult();
        session.close();
        return question;
    }

    public List getQuestions() {
        Query query = SessionFactoryManager.getInstance()
                                           .getSession()
                                           .createQuery("from Question");
        return query.getResultList();
    }

    public void removeQuestion(String username,String content) {
        Question question;
        if ((question = getQuestion(content)) != null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Relations where userName =: userName and question =: question");
            query.setParameter("userName", username);
            query.setParameter("question", question);
            query.executeUpdate();
            session.delete(question);
            transaction.commit();
            session.close();
        }
    }
}