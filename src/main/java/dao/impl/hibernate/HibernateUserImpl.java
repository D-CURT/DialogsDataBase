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

    public void addUser(String login, String password, String name, String passportKey, String age, String role) {
        insertUser(new User(login, password, name, passportKey, age, role));
    }

    public void addPremiumUser(String login, String password, String name, String passportKey, String creditCard) {
        insertUser(new PremiumUser(login, password, name, passportKey, creditCard));
    }

    public void addPremiumUser(String login, String password, String name, String passportKey, String age, String role, String creditCard) {
        insertUser(new PremiumUser(login, password,name, passportKey, age, role, creditCard));
    }

    public void addAdministrator(String login, String password, String name, String passportKey, String adminPassword) {
        insertUser(new Administrator(login, password, name, passportKey, adminPassword));
    }

    public void addAdministrator(String login, String password, String name, String passportKey, String age, String role, String adminPassword) {
        insertUser(new Administrator(login, password, name, passportKey, age, role, adminPassword));
    }

    public User getUser(String name) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Query query = session.createQuery(SELECT_USER_BY_NAME.getHql());
        User user = selectUserByName(name, query);
        session.close();
        return user;
    }

    public User getUser(String login, String password) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Query query = session.createQuery(SELECT_USER_BY_LOGIN_AND_PASS.getHql());
        User user = selectUserByLogin(login, password, query);
        session.close();
        return user;
    }

    public List getUsers() {
        return getTable(TABLE_NAME);
    }

    public void removeUser(String name) {
        User user;
        if ((user = getUser(name)) != null) {
            deleteEntity(user);
        }
    }

    @Override
    public int countRows() {
        return countTableRows(TABLE_NAME);
    }

    private User selectUserByLogin(String login, String password, Query query) {
        String screen = "%";
        String loginField = "login";
        String passwordField = "password";
        query.setParameter(loginField, screen + login + screen);
        query.setParameter(passwordField, screen + password + screen);
        return  (User) query.uniqueResult();
    }

    private User selectUserByName(String name, Query query) {
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
