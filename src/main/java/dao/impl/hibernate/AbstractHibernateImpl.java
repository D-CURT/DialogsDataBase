package dao.impl.hibernate;

import utils.SessionFactoryManager;

import java.util.List;

abstract class AbstractHibernateImpl {
    final Integer countTableRows(final String TABLE_NAME) {
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
}

