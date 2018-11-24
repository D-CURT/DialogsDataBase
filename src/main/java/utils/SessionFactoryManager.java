package utils;

import entities.Answer;
import entities.Question;
import entities.Relations;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

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
            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            factory = config.buildSessionFactory(builder.build());
        } catch (Throwable e) {
            System.out.println("Connection failed");
        }
    }

    public static SessionFactory getFactory() {
        if (instance == null) {
            instance = new SessionFactoryManager();
        }
        return instance.factory();
    }

    private SessionFactory factory() {
        return factory;
    }
}
