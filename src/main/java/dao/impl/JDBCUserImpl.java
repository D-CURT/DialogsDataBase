package dao.impl;

import dao.interfaces.JDBCUser;
import utils.Connector;
import utils.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCUserImpl extends AbstractJDBCHandler implements JDBCUser {
    @Override
    public int addUser(String name) throws SQLException {
        PreparedStatement statement = null;
        try (Connection connection = Connector.connection()) {
            if (getUserId(name, connection) == -1) {
                statement = connection.prepareStatement(SQLSection.ADD_USER.getSQL());
                statement.setString(FIRST_ARGUMENT, name);
                return statement.executeUpdate();
            }
            return 0;
        } finally {
            Connector.closeStatement(statement);
        }
    }

    @Override
    public int removeUser(String name) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = Connector.connection();
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
            assert connection != null;
            connection.rollback();
        } finally {
            Connector.closeConnection(connection);
        }
        return result;
    }
}