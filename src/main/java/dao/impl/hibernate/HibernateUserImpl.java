package dao.impl.hibernate;

import entities.users.Administrator;
import entities.users.PremiumUser;
import entities.users.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.connectors.SessionFactoryManager;
import utils.interceptors.UserInterceptor;
import utils.queries.HQLSection;

import java.util.List;


import static utils.queries.HQLSection.SELECT_USER;

@SuppressWarnings("JpaQlInspection")
public class HibernateUserImpl extends AbstractHibernateImpl{
    private static final String TABLE_NAME = "User";

    public User getUser(int id) {
        return SessionFactoryManager.getInstance().getSession().get(User.class, id);
    }

    public void addUser(String name, String passportKey) {
        if (getUser(name) == null) {
            insertUser(new User(name, passportKey));
        }
    }

    public void addUser(String name, String passportKey, String age) {
        if (getUser(name) == null) {
            insertUser(new User(name, passportKey, age));
        }
    }

    private void insertUser(User user) {
        Session session = SessionFactoryManager.getInstance().getSessionWithInterceptor(new UserInterceptor());
        session.saveOrUpdate(user);
        session.close();
    }

    public void addPremiumUser(String name, String passportKey, String age, String creditCard) {
        if (getUser(name) == null) {
//            Session session = SessionFactoryManager.getInstance().getSession();
            insertUser(new PremiumUser(name, passportKey, age, creditCard));
//            session.close();
        }
    }

    public void addAdministrator(String name, String passportKey, String age, String password) {
        if (getUser(name) == null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            session.save(new Administrator(name, passportKey, age, password));
            session.close();
        }
    }

    public User getUser(String name) {
        final String NAME_FIELD = "name";
        final char SCREEN = '%';
        Session session = SessionFactoryManager.getInstance().getSession();
        Query query = session.createQuery(SELECT_USER.getHql());
        query.setParameter(NAME_FIELD, SCREEN + name.toLowerCase() + SCREEN);
        User user = (User) query.uniqueResult();
        session.close();
        return user;
    }

    public List getUsers() {
        return getTable(TABLE_NAME);
    }

    public void removeUser(String name) {
        User user;
        if ((user = getUser(name)) != null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery(HQLSection.DELETE_RELATION_BY_USER.getHql());
            query.setParameter(TABLE_NAME.toLowerCase(), user);
            query.executeUpdate();
            session.delete(user);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public int countRows() {
        return countTableRows(TABLE_NAME);
    }
}
