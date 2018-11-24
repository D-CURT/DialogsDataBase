package dao.impl.hibernate;

import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

import java.util.List;

public class UserDAO {
    public static void addUser(User user) {
        if (getUser(user.getName()) == null) {
            Session session = SessionFactoryManager.getFactory()
                                                   .openSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            session.close();
        }
    }

    public static User getUser(int id) {
        return SessionFactoryManager.getFactory()
                                    .openSession()
                                    .get(User.class, id);
    }

    public static User getUser(String name) {
        Session session = SessionFactoryManager.getFactory()
                                               .openSession();
        Query query = session.createQuery("from User where name=:name");
        query.setParameter("name", name);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }

    public static List getUsers() {
        Query query = SessionFactoryManager.getFactory()
                                           .openSession()
                                           .createQuery("from User");
        return query.getResultList();
    }

    public static void removeUser(String name) {
        User user = UserDAO.getUser(name);
        Session session = SessionFactoryManager.getFactory()
                                               .openSession();
        session.delete(user);
        session.close();
    }
}
