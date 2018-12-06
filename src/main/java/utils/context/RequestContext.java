package utils.context;

import entities.users.User;

public class RequestContext {
    private static RequestContext instance;
    private static ThreadLocal<User> users;

    private RequestContext(ThreadLocal<User> users) {}

    public static RequestContext getInstance() {
        synchronized (instance) {
            if (instance == null) {
                instance = new RequestContext(new ThreadLocal<>());
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
