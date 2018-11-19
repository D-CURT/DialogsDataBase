import dao.impl.JDBCImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        JDBCImpl jdbc = new JDBCImpl();
        System.out.println(jdbc.addUser("Alex"));
        //System.out.println(jdbc.removeUser("Alex"));
        //System.out.println(jdbc.askQuestion("Alex", "How are you?"));
        //System.out.println(jdbc.answerQuestion("How are you?", "i`m fine", "Alex"));
        //System.out.println(jdbc.removeQuestion("How are you?", "Alex"));
        System.out.println(jdbc.getFullData());
    }
}
