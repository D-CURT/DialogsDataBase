package utils.interceptors.interception.initializers;

import entities.users.User;
import utils.annotations.Interceptor;
import utils.interceptors.EntitiesScope;
import utils.interceptors.interfaces.InitializationInterceptor;

@Interceptor(interceptedType = User.class, applyFor = EntitiesScope.SUB_CLASSES)
public class UserInitInterceptor implements InitializationInterceptor {
    @Override
    public boolean initialize(Object entity, Object[] state, String[] propertyNames) {
        System.out.println("*** Initializing the user ***");
        boolean flag = false;
        if (entity instanceof User) {
            User user = (User) entity;
            if (user.getAge() == null) {
                for (int i = 0; i < propertyNames.length; i++) {
                    if (propertyNames[i].equals("age")) {
                        state[i] = "6";
                    }
                }
            }
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
