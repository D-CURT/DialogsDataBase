package dao.impl;

import dao.Connector;
import utils.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCImpl {
    private static final int FIRST_ARGUMENT = 1;
    private static final int SECOND_ARGUMENT = 2;

    public int addUser(String name) throws SQLException {
        try (Connection connection = Connector.connection();
             PreparedStatement statement = connection.prepareStatement(SQLSection.ADD_USER.getSQL())) {
            statement.setString(1, name);
            return statement.executeUpdate();
        }
    }

    public int addQuestion(String userName, String question) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = Connector.connection();
            connection.setAutoCommit(false);

            result += insertQuestion(question, connection);
            insertRelation(userName, question, connection);

            connection.commit();
        } catch (SQLException e) {
            assert connection != null;
            connection.rollback();
        } finally {
            Connector.closeConnection(connection);
        }
        return result;
    }

    public int addAnswer(String answer) throws SQLException {
        try (Connection connection = Connector.connection()) {
            return insertAnswer(answer, connection);
        }
    }

    public int addAnswer(String question, String answer) throws SQLException {
        Connection connection = null;
        int result = 0;
        try {
            connection = Connector.connection();
            connection.setAutoCommit(false);

            result += insertAnswer(answer, connection);
            updateRelation(question, answer, connection);

            connection.commit();
        } catch (SQLException e) {
            assert connection != null;
            connection.rollback();
        }
        return result;
    }

    public int removeUser(String name) throws SQLException {
        try (Connection connection = Connector.connection();
             PreparedStatement statement = connection.prepareStatement(SQLSection.REMOVE_USER.getSQL())) {
            statement.setString(FIRST_ARGUMENT, name);
            return statement.executeUpdate();
        }
    }

    private int getUserId(String name, Connection connection) throws SQLException {
        return selectEntityId(name, connection, SQLSection.GET_USER.getSQL());
    }

    private int getQuestionId(String question, Connection connection) throws SQLException {
        return selectEntityId(question, connection, SQLSection.GET_QUESTION.getSQL());
    }

    private int getAnswerId(String answer, Connection connection) throws SQLException {
        return selectEntityId(answer, connection, SQLSection.GET_ANSWER.getSQL());
    }

    private int selectEntityId(String question, Connection connection, String sql) throws SQLException {
        ResultSet set = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setString(FIRST_ARGUMENT, question);
            if ((set = statement.executeQuery()).next())
                return set.getInt("id");
        } finally {
            Connector.closeResultSet(set);
        }
        return -1;
    }

    private int insertQuestion(String question, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.ADD_QUESTION.getSQL())) {
            statement.setString(FIRST_ARGUMENT, question);
            return statement.executeUpdate();
        }
    }

    private int insertAnswer(String answer, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.ADD_ANSWER.getSQL())) {
            statement.setString(FIRST_ARGUMENT, answer);
            return statement.executeUpdate();
        }
    }

    private void insertRelation(String userName, String question, Connection connection) throws SQLException {
        int userId = getUserId(userName, connection);
        int questionId = getQuestionId(question, connection);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.ADD_RELATION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.setInt(SECOND_ARGUMENT, questionId);
            statement.execute();
        }
    }

    private void updateRelation(String question, String answer, Connection connection) throws SQLException {
        int questionId = getQuestionId(question, connection);
        int answerId = getAnswerId(answer, connection);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.UPDATE_RELATION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, answerId);
            statement.setInt(SECOND_ARGUMENT, questionId);
            statement.execute();
        }
    }
}
