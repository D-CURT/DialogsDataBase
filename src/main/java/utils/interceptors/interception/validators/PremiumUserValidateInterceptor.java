package utils.interceptors.interception.validators;

import entities.users.PremiumUser;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = PremiumUser.class)
public class PremiumUserValidateInterceptor extends UserValidateInterceptor {

    @Override
    public boolean validate(Object entity, Object[] state, String[] propertyNames) {
        if (entity instanceof PremiumUser) {
            PremiumUser user = (PremiumUser) entity;
            if (user.getCreditCard() == null || user.getCreditCard().isEmpty())
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
