package dao.interfaces;

import java.sql.SQLException;

public interface JDBCUser {
    int addUser(String name) throws SQLException;

    int removeUser(String name) throws SQLException;
}
