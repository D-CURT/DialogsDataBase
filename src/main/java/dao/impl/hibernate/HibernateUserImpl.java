package dao.impl.hibernate;

import entities.users.Administrator;
import entities.users.PremiumUser;
import entities.users.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.connectors.SessionFactoryManager;

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
            Session session = SessionFactoryManager.getInstance().getSession();
            session.save(new User(name, passportKey));
            session.close();
        }
    }

    public void addPremiumUser(String name, String passportKey, String creditCard) {
        if (getUser(name) == null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            session.save(new PremiumUser(name, passportKey, creditCard));
            session.close();
        }
    }

    public void addAdministrator(String name, String passportKey, String password) {
        if (getUser(name) == null) {
            Session session = SessionFactoryManager.getInstance().getSession();
            session.save(new Administrator(name, passportKey, password));
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
            session.delete(user);
            session.close();
        }
    }

    @Override
    public int countRows() {
        return countTableRows(TABLE_NAME);
    }
}
