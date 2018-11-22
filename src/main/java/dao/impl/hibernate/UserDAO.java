package dao.impl.hibernate;

import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

public class UserDAO {
    public static User getUser(int id) {
        return SessionFactoryManager.getInstance().getSession().get(User.class, id);
    }

    public static void addUser(User user) {
        /*String userName = "Fred";
        if ()
        user.setName(");
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();*/
    }

    public static User getUser(String name) {
        Query query = SessionFactoryManager.getInstance().getSession().
                createQuery("from User where name=:name");
        query.setParameter("name", name);
        return  (User) query.uniqueResult();
    }

}
