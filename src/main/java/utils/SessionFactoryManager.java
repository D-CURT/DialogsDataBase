package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager {
    private static SessionFactoryManager instance;
    private static SessionFactory factory;

    private SessionFactoryManager() {
        try {
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable e) {
            System.out.println("connection failed");
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
