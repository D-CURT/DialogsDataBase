package dao.impl;

import dao.Connector;
import utils.SQLSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCImpl {
    private static final int FIRST_ARGUMENT = 1;
    private static final int SECOND_ARGUMENT = 2;
    private static final int THIRD_ARGUMENT = 3;

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

    public int addAnswer(String answer) throws SQLException {
        try (Connection connection = Connector.connection()) {
            return insertAnswer(answer, connection);
        }
    }

    public int answerQuestion(String question, String answer, String userName) throws SQLException {
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
            updateRelation(questionId, answer, userId, connection);

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

    public int removeQuestion(String question, String userName) throws SQLException {
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
            System.out.println("exception");
            assert connection != null;
            connection.rollback();
        } finally {
            Connector.closeConnection(connection);
        }
        return result;
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

    private int selectEntityId(String value, Connection connection,
                                         String sql) throws SQLException {
        ResultSet set = null;
        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setString(FIRST_ARGUMENT, value);
            if ((set = statement.executeQuery()).next())
                return set.getInt("id");
        } finally {
            Connector.closeResultSet(set);
        }
        return -1;
    }

    private List<Integer> selectRelation(int userId, Connection connection) throws SQLException {
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
            Connector.closeResultSet(set);
        }
        return null;
    }

    private List<Integer> selectRelation(int userId, int questionId, Connection connection) throws SQLException {
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
            Connector.closeResultSet(set);
        }
        return null;
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

    private void updateRelation(int questionId, String answer, int userId, Connection connection) throws SQLException {
        int answerId = getAnswerId(answer, connection);
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.UPDATE_RELATION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, answerId);
            statement.setInt(SECOND_ARGUMENT, userId);
            statement.setInt(THIRD_ARGUMENT, questionId);
            statement.execute();
        }
    }

    private int deleteUser(int userId, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.REMOVE_USER.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            return statement.executeUpdate();
        }
    }

    private int deleteQuestion(int questionId, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.REMOVE_QUESTION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, questionId);
            return statement.executeUpdate();
        }
    }

    private void deleteRelation(int userId, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLSection.REMOVE_RELATION_BY_USER.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.execute();
        }
    }

    private void deleteRelation(int userId, int questionId, Connection connection) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(
                             SQLSection.REMOVE_RELATION_BY_USER_AND_QUESTION.getSQL())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.setInt(SECOND_ARGUMENT, questionId);
            statement.execute();
        }
    }
}
