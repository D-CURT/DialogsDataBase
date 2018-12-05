package utils.interceptors.interception.initializers;

import entities.users.PremiumUser;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = PremiumUser.class)
public class PremiumUserInitInterceptor extends UserInitInterceptor {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
