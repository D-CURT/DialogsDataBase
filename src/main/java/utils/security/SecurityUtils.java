package utils.security;

import com.sun.istack.internal.Nullable;
import entities.users.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public class SecurityUtils {
    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        Set<SecurityConfig.Roles> allRoles = SecurityConfig.getAllRoles();

        return hasNecessaryUrlPattern(allRoles, urlPattern);
    }

    public static boolean hasPermission(HttpServletRequest request) {
        User user = UserUtils.getLoginedUser(request);

        SecurityConfig.Roles role;
        if (user != null) {
            role = SecurityConfig.Roles.valueOf(user.getRole());
        } else {
            return false;
        }
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        Set<SecurityConfig.Roles> allRoles = SecurityConfig.getAllRoles();

        if (!allRoles.contains(role)) {
            return false;
        }
        return role.getUrlPatterns().contains(urlPattern);
    }

    private static boolean hasNecessaryUrlPattern(Set<SecurityConfig.Roles> allRoles, String urlPattern) {
        return allRoles.stream()
                       .anyMatch(roles -> {
                    List<String> urlPatternForRole = SecurityConfig.getUrlPatternForRole(roles.name());
                    return urlPatternForRole != null && urlPatternForRole.contains(urlPattern);
                });
    }
}
