package dao.interfaces;

import java.sql.SQLException;

public interface JDBCRelations {
    int askQuestion(String userName, String question) throws SQLException;

    int answerQuestion(String userName, String question, String answer) throws SQLException;

    String getFullData() throws SQLException;
}
