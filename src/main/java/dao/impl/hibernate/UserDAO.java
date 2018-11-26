package dao.impl.hibernate;

import entities.Relations;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

import java.util.List;

public class UserDAO {
    public static User getUser(int id) {
        return SessionFactoryManager.getInstance().getSession().get(User.class, id);
    }

    public static void addUser(User user) {
        if (getUser(user.getName()) == null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
        }
    }

    public static User getUser(String name) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Query query = session.createQuery("from User where name=:name");
        query.setParameter("name", name);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }

    public static List getUsers() {
        Query query = SessionFactoryManager.getInstance()
                                           .getSession()
                                           .createQuery("from User");
        return query.getResultList();
    }

    public static void removeUser(String name) {
        User user;
        if ((user = UserDAO.getUser(name)) != null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Relations where user =: user");
            query.setParameter("user", user);
            session.delete(user);
            transaction.commit();
            session.close();
        }
    }
}
