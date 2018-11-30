package utils.interceptors;

import entities.users.User;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

public class UserInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            User user = (User) entity;
            if (user.getAge() == null) {
                for (int i = 0; i < propertyNames.length; i++) {
                    if (propertyNames[i].equals("age")) {
                        state[i] = "6";
                    }
                }
            }
            if
               (       user.getName() == null
                    || user.getPassportKey() == null
                    || user.getName().isEmpty()
                    || user.getPassportKey().isEmpty()
               )
            {
                throw new IllegalArgumentException("Unexpected values of the user properties");
            }
            return true;
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }
}