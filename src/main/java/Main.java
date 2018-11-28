
import dao.impl.hibernate.HibernateUserImpl;
import dao.impl.hibernate.HibernateRelationsImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        HibernateUserImpl user = new HibernateUserImpl();
        HibernateRelationsImpl relation = new HibernateRelationsImpl();
        //System.out.println(HibernateUserImpl.getUser(3).getQuestions());
        System.out.println(user.getUser("peter"));
        System.out.println(user.getUser("peter").getPassportKey());
        //System.out.println(HibernateUserImpl.getUser("wru3e").getId());
//        user.addUser("peter", "321");
        //relation.askQuestion("peter", "What is going on with you?");
        user.removeUser("peter");
//        user.removeUser("fRED");
//        //System.out.println(user.getUsers());
//        System.out.println(user.getUser("aLEX"));
    }
}