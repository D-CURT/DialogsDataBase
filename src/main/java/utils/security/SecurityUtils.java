package utils.security;

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

    /*public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        Set<SecurityConfig.Roles> allRoles = SecurityConfig.getAllRoles();

        for (SecurityConfig.Roles role: allRoles) {
            if (!request.isUserInRole(role.name())) {
                continue;
            }
            List<String> urlPatternForRole = SecurityConfig.getUrlPatternForRole(role.name());
            if (urlPatternForRole != null && urlPatternForRole.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }*/

    public static boolean hasPermission(HttpServletRequest request) {
        User user = UserUtils.getLoginedUser(request);
        if (user != null) {
            SecurityConfig.Roles role = SecurityConfig.Roles.valueOf(user.getRole());
            String urlPattern = UrlPatternUtils.getUrlPattern(request);

            Set<SecurityConfig.Roles> allRoles = SecurityConfig.getAllRoles();

            if (allRoles.contains(role)) {
                return role.getUrlPatterns().contains(urlPattern);
            }
        }
        return false;
    }

    private static boolean hasNecessaryUrlPattern(Set<SecurityConfig.Roles> allRoles, String urlPattern) {
        return allRoles.stream()
                       .anyMatch(roles -> {
                    List<String> urlPatternForRole = SecurityConfig.getUrlPatternForRole(roles.name());
                    return urlPatternForRole != null && urlPatternForRole.contains(urlPattern);
                });
    }
}
