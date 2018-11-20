package utils;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.*;

public class C3POConnector {
    private static C3POConnector instance;
    private ComboPooledDataSource comboPooledDataSource;

    private C3POConnector() {
        try {
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass("org.postgresql.Driver");
            comboPooledDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            comboPooledDataSource.setUser("postgres");
            comboPooledDataSource.setPassword("780161");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static C3POConnector getInstance() {
        if (instance == null) {
            instance = new C3POConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void closeStatement(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    public static void closeResultSet(ResultSet set) throws SQLException {
        if (set != null) {
            set.close();
        }
    }

}
