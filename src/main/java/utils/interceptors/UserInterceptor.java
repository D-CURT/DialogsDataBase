package utils.interceptors;

import entities.users.User;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Arrays;

public class UserInterceptor extends EmptyInterceptor {

    /*@Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            User user = (User) entity;
            if (user.getAge() == null) {
                user.setAge(6);
                Object[] newState = Arrays.copyOf(currentState, currentState.length);
                newState[0] = 6;
                currentState = newState;
                System.out.println(types);
                System.out.println(currentState);
                System.out.println(user);
                entity = user;
            }
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }*/

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof User) {
            User user = (User) entity;
            if (user.getAge() == null) {
                user.setAge(6);
                state[3] = 6;
                propertyNames[0] = "id";
                propertyNames[3] = "age";
                System.out.println(state);
                System.out.println(user);
                entity = user;
            }
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }
}
