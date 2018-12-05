package utils.interceptors.validators.validators;

import entities.users.User;
import utils.annotations.Interceptor;
import utils.interceptors.validators.interfaces.ValidationInterceptor;

/*@Interceptor(interceptedType = User.class)*/
public class UserValidateInterceptor implements ValidationInterceptor {
    @Override
    public boolean validate(Object entity, Object[] state, String[] propertyNames) {
        boolean flag = false;
        if (entity instanceof User) {
            User user = (User) entity;
            if
            (       user.getName() == null
                    || user.getPassportKey() == null
                    || user.getName().isEmpty()
                    || user.getPassportKey().isEmpty()
            )
            {
                throw new IllegalArgumentException("Unexpected values of the user properties");
            }
            flag = true;
        }
        return flag;
    }
}
