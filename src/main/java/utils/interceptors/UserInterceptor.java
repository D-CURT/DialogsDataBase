package utils.interceptors;

import entities.users.User;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Arrays;

public class UserInterceptor extends EmptyInterceptor {



    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            User user = (User) entity;
            if (user.getAge() == null) {
                user.setAge(6);
                Object[] newState = Arrays.copyOf(state, state.length);
                newState[0] = 6;

                state = newState;
                System.out.println(state);
                System.out.println(user);
                entity = user;
            }
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }
}
