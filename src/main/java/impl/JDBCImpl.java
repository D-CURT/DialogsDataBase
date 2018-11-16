package impl;

import dao.Connector;

import java.sql.SQLException;

public class JDBCImpl {
    public static void test() throws SQLException {
        Connector.connection();
        System.out.println("true");
    }
}
