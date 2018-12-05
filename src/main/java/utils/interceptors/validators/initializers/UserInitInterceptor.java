package utils.interceptors.validators.initializers;

import entities.users.User;
import utils.annotations.Interceptor;
import utils.interceptors.validators.interfaces.InitializationInterceptor;

/*@Interceptor(interceptedType = User.class)*/
public class UserInitInterceptor implements InitializationInterceptor {
    @Override
    public boolean initialize(Object entity, Object[] state, String[] propertyNames) {
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
}
