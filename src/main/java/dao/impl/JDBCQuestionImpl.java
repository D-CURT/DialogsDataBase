package dao.impl;

import dao.interfaces.JDBCQuestion;
import utils.C3POConnector;
import utils.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCQuestionImpl extends AbstractJDBCHandler implements JDBCQuestion {
    @Override
    public int addQuestion(String question) throws SQLException {
        PreparedStatement statement = null;
        try (Connection connection = C3POConnector.getInstance().getConnection()) {
            if (getQuestionId(question, connection) == -1) {
                statement = connection.prepareStatement(SQLSection.ADD_QUESTION.getSQL());
                statement.setString(FIRST_ARGUMENT, question);
                return statement.executeUpdate();
            }
            return 0;
        } finally {
            C3POConnector.closeStatement(statement);
        }
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
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            connection.rollback();
        }
        return result;
    }
}
