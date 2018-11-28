package dao.impl.jdbc;

import dao.interfaces.JDBCRelations;
import utils.connectors.C3POConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCRelationsImpl extends AbstractJDBCHandler implements JDBCRelations {
    @Override
    public int askQuestion(String userName, String question) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = C3POConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            int userId = getUserId(userName, connection);
            int questionId = getQuestionId(question, connection);

            if (questionId == -1) {
                result += insertQuestion(question, connection);
            }
            if (selectRelation(userId, questionId, connection) == null) {
                insertRelation(userName, question, connection);
            }

            connection.commit();
            result++;
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            C3POConnector.closeConnection(connection);
        }
        return result;
    }

    @Override
    public int answerQuestion(String userName, String question, String answer) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = C3POConnector.getInstance().getConnection();
            connection.setAutoCommit(false);
            int questionId = getQuestionId(question, connection);
            int userId = getUserId(userName, connection);

            if (getAnswerId(answer, connection) == -1) {
                result += insertAnswer(answer, connection);
            }
            if (!updateRelation(questionId, answer, userId, connection) && result > 0) {
                connection.rollback();
            }

            connection.commit();
            result++;
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            C3POConnector.closeConnection(connection);
        }
        return result;
    }
}
