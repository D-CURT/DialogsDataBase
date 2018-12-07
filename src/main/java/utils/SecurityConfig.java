package utils;

import java.util.*;

public class SecurityConfig {

    public enum Roles {
        USER {
            @Override
            List<String> getUrlPatterns() {
                List<String> list = new ArrayList<>();
                list.add("/index.jsp");
                return list;
            }
        },
        PREMIUM_USER {
            @Override
            List<String> getUrlPatterns() {
                List<String> list = new ArrayList<>();
                list.add("/index.jsp");
                return list;
            }
        },
        ADMINISTRATOR {
            @Override
            List<String> getUrlPatterns() {
                List<String> list = new ArrayList<>();
                list.add("/index.jsp");
                return list;
            }
        };

        abstract List<String> getUrlPatterns();
    }

    private static final Map<Roles, List<String>> CONFIGS = new HashMap<>();
    
    static {
        init();
    }

    private static void init() {
        Arrays.stream(Roles.values()).forEach(roles -> CONFIGS.put(roles, roles.getUrlPatterns()));
    }

    public static Set<Roles> getAllRoles() {
        return CONFIGS.keySet();
    }

    public static List<String> getUrlPatternForRole(String role) {
        return CONFIGS.get(Roles.valueOf(role));
    }
}
