package utils.interceptors.validators;

import entities.users.Administrator;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import utils.annotations.Interceptor;

import java.io.Serializable;

@Interceptor(interceptedType = Administrator.class)
public class AdministratorInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        if (entity instanceof Administrator) {
            Administrator user = (Administrator) entity;
            if (user.getAge() == null) {
                for (int i = 0; i < propertyNames.length; i++) {
                    if (propertyNames[i].equals("age")) {
                        state[i] = "6";
                    }
                }
            }
            if
            (      user.getName() == null
                    || user.getPassportKey() == null
                    || user.getPassword() == null
                    || user.getName().isEmpty()
                    || user.getPassportKey().isEmpty()
                    || user.getPassword().isEmpty()
            )
            {
                throw new IllegalArgumentException("Unexpected values of the user properties");
            }
            return true;
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }
}
