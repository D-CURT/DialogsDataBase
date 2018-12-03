package utils.interceptors.validators;

import entities.users.PremiumUser;
import org.hibernate.type.Type;
import utils.annotations.Interceptor;

import java.io.Serializable;

@Interceptor(interceptedType = PremiumUser.class)
public class PremiumUserInterceptor extends UserInterceptor {

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof PremiumUser) {
            PremiumUser user = (PremiumUser) entity;
            if (user.getCreditCard() == null || user.getCreditCard().isEmpty())
            {
                throw new IllegalArgumentException("Unexpected values of the user properties");
            }
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        super.onDelete(entity, id, state, propertyNames, types);
    }
}
