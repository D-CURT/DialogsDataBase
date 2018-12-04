package utils.connectors;

import entities.Answer;
import entities.Question;
import entities.Relations;
import entities.profiles.Profile;
import entities.profiles.UserProfile;
import entities.users.Administrator;
import entities.users.PremiumUser;
import entities.users.User;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import utils.interceptors.InterceptorsManager;

public class SessionFactoryManager {
    private static SessionFactoryManager instance;
    private static SessionFactory factory;

    private SessionFactoryManager() {
        try {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(Question.class);
            config.addAnnotatedClass(Answer.class);
            config.addAnnotatedClass(Relations.class);
            config.addAnnotatedClass(PremiumUser.class);
            config.addAnnotatedClass(Administrator.class);
            config.addAnnotatedClass(Profile.class);
            config.addAnnotatedClass(UserProfile.class);
            config.setInterceptor(new InterceptorsManager("utils.interceptors.*"));
            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            factory = config.buildSessionFactory(builder.build());
        } catch (Throwable e) {
            System.out.println("Connection failed");
        }
    }

    public static SessionFactoryManager getInstance() {
        if (instance == null) {
            instance = new SessionFactoryManager();
        }
        return instance;
    }

    public Session getSession() {
        return factory.openSession();
    }
}
