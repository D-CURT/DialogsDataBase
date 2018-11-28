package dao.impl.jdbc;

import dao.interfaces.JDBCQuestion;
import utils.connectors.C3POConnector;
import utils.queries.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCQuestionImpl extends AbstractJDBCHandler implements JDBCQuestion {
    @Override
    public int addQuestion(String question) throws SQLException {
        PreparedStatement statement = null;
        int result = 0;
        try (Connection connection = C3POConnector.getInstance().getConnection()) {
            if (getQuestionId(question, connection) == -1) {
                statement = connection.prepareStatement(SQLSection.ADD_QUESTION.getSQL());
                statement.setString(FIRST_ARGUMENT, question);
                result += statement.executeUpdate();
            }
        } finally {
            C3POConnector.closeStatement(statement);
        }
        return result;
    }

    @Override
    public int removeQuestion(String userName, String question) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = C3POConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            int userId = getUserId(userName, connection);
            int questionId = getQuestionId(question, connection);

            deleteRelation(userId, questionId, connection);
            result += deleteQuestion(questionId, connection);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            C3POConnector.closeConnection(connection);
        }
        return result;
    }
}