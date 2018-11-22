package dao.impl.jdbc;

import dao.interfaces.JDBCUser;
import utils.C3POConnector;
import utils.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCUserImpl extends AbstractJDBCHandler implements JDBCUser {
    @Override
    public int addUser(String name) throws SQLException {
        PreparedStatement statement = null;
        int result = 0;
        try (Connection connection = C3POConnector.getInstance().getConnection()) {
            if (getUserId(name, connection) == -1) {
                statement = connection.prepareStatement(SQLSection.ADD_USER.getSQL());
                statement.setString(FIRST_ARGUMENT, name);
                result += statement.executeUpdate();
            }
        } finally {
            C3POConnector.closeStatement(statement);
        }
        return result;
    }

    @Override
    public int removeUser(String name) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = C3POConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            int userId = getUserId(name, connection);
            List<Integer> relations;
            if ((relations = selectRelation(userId, connection)) != null) {
                for (int id: relations) {
                    deleteRelation(id, connection);
                }
            }
            result += deleteUser(userId, connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            C3POConnector.closeConnection(connection);
        }
        return result;
    }
}