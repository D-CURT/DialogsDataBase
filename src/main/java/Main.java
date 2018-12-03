
import dao.impl.hibernate.HibernateQuestionImpl;
import dao.impl.hibernate.HibernateUserImpl;
import dao.impl.hibernate.HibernateRelationsImpl;
import org.hibernate.EmptyInterceptor;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import utils.annotations.Interceptor;
import utils.interceptors.UserInterceptor;
import utils.reflection.InterceptorCatcher;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        HibernateUserImpl user = new HibernateUserImpl();
        HibernateQuestionImpl question = new HibernateQuestionImpl();
        HibernateRelationsImpl relation = new HibernateRelationsImpl();
        //System.out.println(HibernateUserImpl.getUser(3).getQuestions());
        //user.addPremiumUser("baq", "4", null, "123");


//        relation.askQuestion("alex", "What is going on with you?");
//        question.removeQuestion("What is going on with you?");
//        user.removeUser("peter");
//        user.getUsers().forEach();
//        System.out.println(user.getUser("aLEX"));
        System.out.println(InterceptorCatcher.getInstance("utils.interceptors").getInterceptors().values());
    }
}