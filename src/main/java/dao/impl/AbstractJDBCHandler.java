package dao.impl;

import utils.C3POConnector;
import utils.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractJDBCHandler {
    static final int FIRST_ARGUMENT = 1;
    static final int SECOND_ARGUMENT = 2;
    static final int THIRD_ARGUMENT = 3;

    String getFullData() throws SQLException {
        ResultSet set = null;
        StringBuilder builder = new StringBuilder();
        try (Connection connection = C3POConnector.getInstance().getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQLSection.GET_FULL_DATA.getSQL())) {
            set = statement.executeQuery();
            while (set.next()) {
                builder.append(set.getString(FIRST_ARGUMENT)).append(" ");
                builder.append(set.getString(SECOND_ARGUMENT)).append(" ");
                builder.append(set.getString(THIRD_ARGUMENT)).append("\n");
            }
        } finally {
            C3POConnector.closeResultSet(set);
        }
        return builder.toString();
    }

    int getUserId(String name, Connection connection) throws SQLException {
        return selectEntityId(name, connection, SQLSection.GET_USER.getSQL());
    }

    int getQuestionId(String question, Connection connection) throws SQLException {
        return selectEntityId(question, connection, SQLSection.GET_QUESTION.getSQL());
    }

    int getAnswerId(String answer, Connection connection) throws SQLException {
        return selectEntityId(answer, connection, SQLSection.GET_ANSWER.getSQL());
    }

    List<Integer> selectRelation(int userId, Connection connection) throws SQLException {
        ResultSet set = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.GET_RELATION_BY_USER.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            set = statement.executeQuery();
            List<Integer> identifiers = new ArrayList<>();
            while (set.next()) {
                identifiers.add(set.getInt("id_user"));
            }
            if (identifiers.size() > 0) return identifiers;
        } finally {
            C3POConnector.closeResultSet(set);
        }
        return null;
    }

    List<Integer> selectRelation(int userId, int questionId, Connection connection) throws SQLException {
        ResultSet set = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.GET_RELATION_BY_USER_AND_QUESTION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.setInt(SECOND_ARGUMENT, questionId);
            set = statement.executeQuery();
            List<Integer> identifiers = new ArrayList<>();
            while (set.next()) {
                identifiers.add(set.getInt("id_user"));
            }
            if (identifiers.size() > 0) return identifiers;
        } finally {
            C3POConnector.closeResultSet(set);
        }
        return null;
    }

    int insertQuestion(String question, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.ADD_QUESTION.getSQL())) {
            statement.setString(FIRST_ARGUMENT, question);
            System.out.println(statement);
            return statement.executeUpdate();
        }
    }

    int insertAnswer(String answer, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.ADD_ANSWER.getSQL())) {
            statement.setString(FIRST_ARGUMENT, answer);
            return statement.executeUpdate();
        }
    }

    void insertRelation(String userName, String question, Connection connection) throws SQLException {
        int userId = getUserId(userName, connection);
        int questionId = getQuestionId(question, connection);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.ADD_RELATION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.setInt(SECOND_ARGUMENT, questionId);
            statement.executeUpdate();
        }
    }

    boolean updateRelation(int questionId, String answer, int userId, Connection connection) throws SQLException {
        int answerId = getAnswerId(answer, connection);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.UPDATE_RELATION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, answerId);
            statement.setInt(SECOND_ARGUMENT, userId);
            statement.setInt(THIRD_ARGUMENT, questionId);
            return statement.executeUpdate() > 0;
        }
    }

    int deleteUser(int userId, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.REMOVE_USER.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            return statement.executeUpdate();
        }
    }

    int deleteQuestion(int questionId, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.REMOVE_QUESTION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, questionId);
            return statement.executeUpdate();
        }
    }

    void deleteRelation(int userId, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.REMOVE_RELATION_BY_USER.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.execute();
        }
    }

    void deleteRelation(int userId, int questionId, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             SQLSection.REMOVE_RELATION_BY_USER_AND_QUESTION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.setInt(SECOND_ARGUMENT, questionId);
            statement.execute();
        }
    }

    private int selectEntityId(String value, Connection connection,
                               String sql) throws SQLException {
        ResultSet set = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setString(FIRST_ARGUMENT, value);
            if ((set = statement.executeQuery()).next())
                return set.getInt("id");
        } finally {
            C3POConnector.closeResultSet(set);
        }
        return -1;
    }
}