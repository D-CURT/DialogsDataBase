
import dao.impl.hibernate.HibernateUserImpl;
import dao.impl.hibernate.HibernateRelationsImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*JDBCUserImpl jdbc = new JDBCUserImpl();
        System.out.println(jdbc.addUser("Alex"));
        //System.out.println(jdbc.removeUser("Alex"));
        //System.out.println(jdbc.removeQuestion("How are you?", "Alex"));
        //System.out.println(jdbc.getFullData());
        System.out.println(jdbc.askQuestion("Alex", "How are you?"));
        System.out.println(jdbc.askQuestion("Alex", "What are you doing?"));
        System.out.println(jdbc.answerQuestion("What are you doing?", "i`m going home", "Alex"));
        System.out.println(jdbc.answerQuestion("How are you?", "i`m fine, thanks", "Alex"));*/
        //System.out.println(SQLSection.ADD_QUESTION.name());
        HibernateUserImpl user = new HibernateUserImpl();
        HibernateRelationsImpl relation = new HibernateRelationsImpl();
        //System.out.println(HibernateUserImpl.getUser(3).getQuestions());
        System.out.println(user.getUser("alex"));
        //System.out.println(HibernateUserImpl.getUser("wru3e").getId());
        relation.askQuestion("john", "What is going on with you?");
        //relation.answerQuestion("piter", "Waths`up, bro?", "I`m great!");
////        user.removeUser("Fred");
        user.addUser("jOHN", "321");
        user.removeUser("fRED");
//        //System.out.println(user.getUsers());
//        System.out.println(user.getUser("aLEX"));


    }
}