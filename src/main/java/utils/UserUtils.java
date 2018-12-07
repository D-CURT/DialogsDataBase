package utils;

import entities.users.User;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

public class UserUtils {
    public static final String USER_PARAM = "user";

    public static void storeLoginedUser(ServletRequest request, User user) {
        request.setAttribute(USER_PARAM, user);
    }

    public static User getLoginedUser(ServletRequest request) {
        return (User) request.getAttribute(USER_PARAM);
    }
}
