
import dao.impl.hibernate.HibernateQuestionImpl;
import dao.impl.hibernate.HibernateUserImpl;
import dao.impl.hibernate.HibernateRelationsImpl;
import dao.impl.hibernate.HibernateUserProfileImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        HibernateUserImpl user = new HibernateUserImpl();
        HibernateQuestionImpl question = new HibernateQuestionImpl();
        HibernateRelationsImpl relation = new HibernateRelationsImpl();
        HibernateUserProfileImpl profile = new HibernateUserProfileImpl();
        //System.out.println(HibernateUserImpl.getUser(3).getQuestions());
        //user.addPremiumUser("baq", "4", null, "123");

//        relation.askQuestion("alex", "What is going on with you?");
//        question.removeQuestion("What is going on with you?");
//        user.removeUser("peter");
//        user.getUsers().forEach();
//        System.out.println(user.getUser("aLEX"));
        profile.addUserProfile("testProfile", "test");
    }
}