
import dao.impl.hibernate.HibernateQuestionImpl;
import dao.impl.hibernate.HibernateUserImpl;
import dao.impl.hibernate.HibernateRelationsImpl;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import utils.interceptors.UserInterceptor;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        HibernateUserImpl user = new HibernateUserImpl();
        HibernateQuestionImpl question = new HibernateQuestionImpl();
        HibernateRelationsImpl relation = new HibernateRelationsImpl();
        //System.out.println(HibernateUserImpl.getUser(3).getQuestions());
        //user.addPremiumUser("baq", "4", null, "123");
//        user.addUser("fill", "321");


//        relation.askQuestion("alex", "What is going on with you?");
//        question.removeQuestion("What is going on with you?");
//        user.removeUser("peter");
//        user.getUsers().forEach();
//        System.out.println(user.getUser("aLEX"));
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .filterInputsBy(new FilterBuilder().include("c:\\Users\\Админ\\Documents\\GitHub\\QuestionAnswerBase\\src\\main\\java\\utils\\"))
                .setUrls(ClasspathHelper.forClassLoader())
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner().filterResultsBy()));
        System.out.println(reflections.getSubTypesOf(UserInterceptor.class));
    }
}