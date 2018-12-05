package utils.interceptors.interception.validators.users;

import entities.users.User;
import utils.annotations.Interceptor;
import utils.interceptors.interfaces.ValidationInterceptor;

@Interceptor(interceptedType = User.class)
public class UserValidateInterceptor implements ValidationInterceptor {
    @Override
    public boolean validate(Object entity, Object[] state, String[] propertyNames) {
        System.out.println("*** Validating the user ***");
        boolean flag = false;
        if (entity instanceof User) {
            User user = (User) entity;
            String name = user.getName();
            String passportKey = user.getPassportKey();
            if
            (          name == null
                    || passportKey == null
                    || name.isEmpty()
                    || passportKey.isEmpty()
            )
            {
                throw new IllegalArgumentException("Unexpected values of the user properties");
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
