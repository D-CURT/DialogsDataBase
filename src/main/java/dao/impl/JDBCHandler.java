package dao.impl;

import utils.Connector;
import utils.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCHandler extends AbstractJDBCHandler {
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

    public int askQuestion(String userName, String question) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = Connector.connection();
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
        } catch (SQLException e) {
            System.out.println("exception");
            assert connection != null;
            connection.rollback();
        } finally {
            Connector.closeConnection(connection);
        }
        return result;
    }

    public int answerQuestion(String userName, String question, String answer) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = Connector.connection();
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
        } catch (SQLException e) {
            assert connection != null;
            connection.rollback();
        } finally {
            Connector.closeConnection(connection);
        }
        return result;
    }

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

    public String getFullData() throws SQLException {
        ResultSet set = null;
        StringBuilder builder = new StringBuilder();
        try (Connection connection = Connector.connection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLSection.GET_FULL_DATA.getSQL())) {
            set = statement.executeQuery();
            while (set.next()) {
                builder.append(set.getString(FIRST_ARGUMENT)).append(" ");
                builder.append(set.getString(SECOND_ARGUMENT)).append(" ");
                builder.append(set.getString(THIRD_ARGUMENT)).append("\n");
            }
        } finally {
            Connector.closeResultSet(set);
        }
        return builder.toString();
    }
}