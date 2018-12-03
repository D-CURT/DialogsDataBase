package utils.connectors;

import dao.interfaces.HibernateDBImpl;
import entities.Answer;
import entities.Question;
import entities.Relations;
import entities.users.Administrator;
import entities.users.PremiumUser;
import entities.users.User;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Map;

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

    public Session getSessionWithInterceptor(Interceptor interceptor) {
        return factory.withOptions().interceptor(interceptor).openSession();
    }

    public Session getSessionWithInterceptor(List<EmptyInterceptor> interceptors) {
        SessionBuilder builder = factory.withOptions();
        interceptors.forEach(builder::interceptor);
        return builder.openSession();
    }

    public Session getSessionWithInterceptor(Map<String, EmptyInterceptor> interceptors, String expectedType) {
        SessionBuilder builder = factory.withOptions();
        interceptors.forEach((s, emptyInterceptor) -> {
            if (s.equals(expectedType)) {
                builder.interceptor(emptyInterceptor);
            }
        });
        return builder.openSession();
    }
}
