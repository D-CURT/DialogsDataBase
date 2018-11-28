
import dao.impl.hibernate.HibernateUserImpl;
import dao.impl.hibernate.HibernateRelationsImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        HibernateUserImpl user = new HibernateUserImpl();
        HibernateRelationsImpl relation = new HibernateRelationsImpl();
        //System.out.println(HibernateUserImpl.getUser(3).getQuestions());
        System.out.println(user.getUser("john"));
        //System.out.println(HibernateUserImpl.getUser("wru3e").getId());
        relation.askQuestion("john", "What is going on with you?");
////        user.removeUser("Fred");
        user.addUser("jOHN", "321");
        user.removeUser("fRED");
//        //System.out.println(user.getUsers());
//        System.out.println(user.getUser("aLEX"));


    }
}