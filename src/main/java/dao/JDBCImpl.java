package dao;

import java.sql.SQLException;

public interface JDBCImpl {
    int addUser(String name) throws SQLException;

    int addQuestion(String question) throws SQLException;

    int askQuestion(String userName, String question) throws SQLException;

    int answerQuestion(String userName, String question, String answer) throws SQLException;

    int removeUser(String name) throws SQLException;

    int removeQuestion(String userName, String question) throws SQLException;

    String getFullData() throws SQLException;
}
