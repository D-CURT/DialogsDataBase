package utils.interceptors.interception.loaders;

import entities.users.Administrator;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = Administrator.class)
public class AdministratorLoadingInterceptor extends UserLoadingInterceptor {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
