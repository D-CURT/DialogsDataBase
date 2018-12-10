package utils.security;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

public class UrlPatternUtils {
    public static String getUrlPattern(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        String servletPath = request.getServletPath();
        String servletPathInfo = request.getPathInfo();

        String pattern;
        if (servletPathInfo != null) {
            return servletPath + "/*";
        }

        pattern = servletPath;
        boolean has = hasUrlPattern(context, pattern);

        if (has) {
            return pattern;
        }

        int index = servletPath.lastIndexOf('.');
        if (index != -1) {
            String ext = servletPath.substring(index + 1);
            pattern = "*." + ext;

            has = hasUrlPattern(context, pattern);

            if (has) return pattern;
        }
        return "/";
    }

    private static boolean hasUrlPattern(ServletContext context, String pattern) {
        Map<String, ? extends ServletRegistration> registrations = context.getServletRegistrations();

        return registrations.keySet()
                            .stream()
                            .anyMatch(s -> {
             ServletRegistration registration = registrations.get(s);
             Collection<String> mappings = registration.getMappings();
             return mappings.contains(pattern);
        });
    }
}
