package dao;

import java.sql.*;

public class Connector {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "780161");
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
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
