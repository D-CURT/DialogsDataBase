package utils.interceptors.interception.removers;

import entities.users.PremiumUser;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = PremiumUser.class)
public class PremiumUserDeleteInterceptor extends UserDeleteInterceptor {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
