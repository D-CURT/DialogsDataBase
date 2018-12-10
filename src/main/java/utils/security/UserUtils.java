package utils.security;

import entities.users.User;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

public class UserUtils {
    public static final String USER_PARAM = "user";
    private static int REDIRECT_ID = 0;

    private static final Map<Integer, String> ID_URI = new HashMap<>();
    private static final Map<String, Integer> URI_ID = new HashMap<>();

    public static void storeLoginedUser(ServletRequest request, User user) {
        request.setAttribute(USER_PARAM, user);
    }

    public static User getLoginedUser(ServletRequest request) {
        Object o = request.getAttribute(USER_PARAM);
        return o != null ? (User) o : null;
    }

    public static int storeRedirectAfterLoginUrl(String requestUri) {
        Integer id = URI_ID.get(requestUri);

        if (id == null) {
            id = REDIRECT_ID++;

            URI_ID.put(requestUri, id);
            ID_URI.put(id, requestUri);
            return id;
        }

        return id;
    }

    public static String getRedirectAfterLoginUrl(int redirectId) {
        String url = ID_URI.get(redirectId);
        if (url != null) {
            return url;
        }
        return null;
    }
}
