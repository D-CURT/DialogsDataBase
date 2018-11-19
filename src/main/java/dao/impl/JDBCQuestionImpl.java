package dao.impl;

import dao.interfaces.JDBCQuestion;
import utils.Connector;
import utils.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCQuestionImpl extends AbstractJDBCHandler implements JDBCQuestion {
    @Override
    public int addQuestion(String question) throws SQLException {
        PreparedStatement statement = null;
        try (Connection connection = Connector.connection()) {
            if (getQuestionId(question, connection) == -1) {
                statement = connection.prepareStatement(SQLSection.ADD_QUESTION.getSQL());
                statement.setString(FIRST_ARGUMENT, question);
                return statement.executeUpdate();
            }
            return 0;
        } finally {
            Connector.closeStatement(statement);
        }
    }

    @Override
    public int removeQuestion(String userName, String question) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = Connector.connection();
            connection.setAutoCommit(false);
            int userId = getUserId(userName, connection);
            int questionId = getQuestionId(question, connection);

            deleteRelation(userId, questionId, connection);
            result += deleteQuestion(questionId, connection);

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
