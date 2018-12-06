package utils.context;

import entities.users.User;

public class RequestContext {
    private static RequestContext instance;
    private static ThreadLocal<User> users;
    private final static Object EMPTY_OBJECT = new Object();

    private RequestContext() {
        users = new ThreadLocal<>();
    }

    public static RequestContext getInstance() {
        synchronized (EMPTY_OBJECT) {
            if (instance == null) {
                instance = new RequestContext();
            }
        }
        return instance;
    }

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }
}
