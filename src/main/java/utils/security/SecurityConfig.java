package utils.security;

import java.util.*;

public class SecurityConfig {

    public enum Roles {
        USER("/userInfo.jsp"){
            @Override
            List<String> getUrlPatterns() {
                List<String> list = new ArrayList<>();
                storeInitialMapping(list);
                list.add(this.getInfoPage());
                return list;
            }
        },
        PREMIUM_USER("/premiumUserInfo.jsp") {
            @Override
            List<String> getUrlPatterns() {
                List<String> list = new ArrayList<>();
                storeInitialMapping(list);
                list.add(this.getInfoPage());
                return list;
            }
        },
        ADMINISTRATOR("/administratorInfo.jsp") {
            @Override
            List<String> getUrlPatterns() {
                List<String> list = new ArrayList<>();
                storeInitialMapping(list);
                list.add(this.getInfoPage());
                return list;
            }
        };

        private String infoPage;

        Roles(String infoPage) {
            this.infoPage = infoPage;
        }

        abstract List<String> getUrlPatterns();

        public String getInfoPage() {
            return infoPage;
        }
    }

    private static final Map<Roles, List<String>> CONFIGS = new HashMap<>();
    
    static {
        init();
    }

    private static void init() {
        Arrays.stream(Roles.values()).forEach(roles -> CONFIGS.put(roles, roles.getUrlPatterns()));
    }

    private static void storeInitialMapping(List<String> urls) {
        urls.add("/index.jsp");
        urls.add("/main");
    }

    public static Set<Roles> getAllRoles() {
        return CONFIGS.keySet();
    }

    public static List<String> getUrlPatternForRole(String role) {
        return CONFIGS.get(Roles.valueOf(role));
    }
}
