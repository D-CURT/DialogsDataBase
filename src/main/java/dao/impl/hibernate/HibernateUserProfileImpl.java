package dao.impl.hibernate;

import entities.profiles.UserProfile;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.connectors.SessionFactoryManager;

public class HibernateUserProfileImpl extends AbstractHibernateImpl {
    private static final String TABLE_NAME = "UserProfile";

    public void addUserProfile(String owner, String name) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(new UserProfile(owner, name));
        transaction.commit();
        session.close();
    }

    @Override
    public int countRows() {
        return countTableRows(TABLE_NAME);
    }
}
