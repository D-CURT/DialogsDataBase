package utils.interceptors.interception.loaders;

import entities.users.PremiumUser;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = PremiumUser.class)
public class PremiumUserLoadInterceptor extends UserLoadingInterceptor {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
