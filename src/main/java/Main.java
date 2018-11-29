
import dao.impl.hibernate.HibernateQuestionImpl;
import dao.impl.hibernate.HibernateUserImpl;
import dao.impl.hibernate.HibernateRelationsImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        HibernateUserImpl user = new HibernateUserImpl();
        HibernateQuestionImpl question = new HibernateQuestionImpl();
        HibernateRelationsImpl relation = new HibernateRelationsImpl();
        //System.out.println(HibernateUserImpl.getUser(3).getQuestions());
//        System.out.println(user.getUser("peter"));
//        System.out.println(user.getUser("peter").getPassportKey());
        //System.out.println(HibernateUserImpl.getUser("wru3e").getId());
        user.addUser("baq", "4");
//        user.addUser("fill", "321");


//        relation.askQuestion("alex", "What is going on with you?");
//        question.removeQuestion("What is going on with you?");
//        user.removeUser("peter");
        user.removeUser("test");
//        user.getUsers().forEach();
//        System.out.println(user.getUser("aLEX"));
    }
}