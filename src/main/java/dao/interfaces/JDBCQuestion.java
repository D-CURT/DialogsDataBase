package dao.interfaces;

import java.sql.SQLException;

public interface JDBCQuestion {
    int addQuestion(String question) throws SQLException;

    int removeQuestion(String userName, String question) throws SQLException;
}
