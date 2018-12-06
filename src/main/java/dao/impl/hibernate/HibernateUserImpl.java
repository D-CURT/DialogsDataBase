package dao.impl.hibernate;

import entities.users.Administrator;
import entities.users.PremiumUser;
import entities.users.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.connectors.SessionFactoryManager;
import utils.queries.HQLSection;

import java.util.List;

import static utils.queries.HQLSection.SELECT_USER_BY_LOGIN_AND_PASS;
import static utils.queries.HQLSection.SELECT_USER_BY_NAME;

@SuppressWarnings("JpaQlInspection")
public class HibernateUserImpl extends AbstractHibernateImpl{
    private static final String TABLE_NAME = "User";

    public User getUser(int id) {
        return SessionFactoryManager.getInstance().getSession().get(User.class, id);
    }

    public void addUser(String login, String password, String name, String passportKey, String age) {
        insertUser(new User(login, password, name, passportKey, age));
    }

    public void addPremiumUser(String login, String password, String name, String passportKey, String creditCard) {
        insertUser(new PremiumUser(login, password, name, passportKey, creditCard));
    }

    public void addPremiumUser(String login, String password, String name, String passportKey, String age, String creditCard) {
        insertUser(new PremiumUser(login, password,name, passportKey, age, creditCard));
    }

    public void addAdministrator(String login, String password, String name, String passportKey, String adminPassword) {
        insertUser(new Administrator(login, password, name, passportKey, adminPassword));
    }

    public void addAdministrator(String login, String password, String name, String passportKey, String age, String adminPassword) {
        insertUser(new Administrator(login, password, name, passportKey, age, adminPassword));
    }

    public User getUser(String name) {

        Session session = SessionFactoryManager.getInstance().getSession();
        Query query = session.createQuery(SELECT_USER_BY_NAME.getHql());
        User user = selectUserByName(name, query, session);
        session.close();
        return user;
    }

    public User getUser(String login, String password) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Query query = session.createQuery(SELECT_USER_BY_LOGIN_AND_PASS.getHql());
        User user = selectUserByLogin(login, password, query, session);
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

    private User selectUserByLogin(String login, String password, Query query, Session session) {
        String loginField = "login";
        String passwordField = "password";
        query.setParameter(loginField, login);
        query.setParameter(passwordField, password);
        return  (User) query.uniqueResult();
    }

    private User selectUserByName(String name, Query query, Session session) {
        String screen = "%";
        String nameField = "name";
        query.setParameter(nameField, screen + name + screen);
        return (User) query.uniqueResult();
    }

    private void insertUser(User user) {
        if (getUser(user.getName()) == null) {
            Session session = SessionFactoryManager.getInstance()
                             .getSession();
            session.save(user);
            session.close();
        }
    }
}
