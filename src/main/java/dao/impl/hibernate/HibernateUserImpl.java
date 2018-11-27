package dao.impl.hibernate;

import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

import java.util.List;

@SuppressWarnings("JpaQlInspection")
public class HibernateUserImpl extends AbstractHibernateImpl{
    public User getUser(int id) {
        return SessionFactoryManager.getInstance().getSession().get(User.class, id);
    }

    public void addUser(String name) {
        if (getUser(name) == null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            session.save(new User(name));
            session.close();
        }
    }

    public User getUser(String name) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Query query = session.createQuery("from User where name=:name");
        query.setParameter("name", name);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }

    public List getUsers() {
        return getTable("User");
    }

    public void removeUser(String name) {
        User user;
        if ((user = getUser(name)) != null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Relations where user =: user");
            query.setParameter("user", user);
            query.executeUpdate();
            session.delete(user);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public int countRows() {
        return countTableRows("User");
    }
}
