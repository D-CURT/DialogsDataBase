package utils.interceptors.interception.validators.users;

import entities.users.Administrator;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = Administrator.class)
public class AdministratorValidateInterceptor extends UserValidateInterceptor {

    @Override
    public boolean validate(Object entity, Object[] state, String[] propertyNames) {
        if (entity instanceof Administrator) {
            Administrator user = (Administrator) entity;
            if (user.getPassword() == null || user.getPassword().isEmpty())
            {
                throw new IllegalArgumentException("Unexpected values of the user properties");
            }
        }
        return super.validate(entity, state, propertyNames);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
