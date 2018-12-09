package dao.impl.hibernate;

import dao.interfaces.HibernateDBImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.connectors.SessionFactoryManager;

import java.util.List;

abstract class AbstractHibernateImpl implements HibernateDBImpl {
    final int countTableRows(final String TABLE_NAME) {
        return getTable(TABLE_NAME).size();

    }

    public abstract int countRows();

    @SuppressWarnings("unchecked")
    List<Object> getTable(final String TABLE_NAME) {
        String sql = "from " + TABLE_NAME;

        return SessionFactoryManager.getInstance()
                                    .getSession()
                                    .createQuery(sql)
                                    .getResultList();
    }

    void deleteEntity(Object o) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(o);
        transaction.commit();
        session.close();
    }
}

