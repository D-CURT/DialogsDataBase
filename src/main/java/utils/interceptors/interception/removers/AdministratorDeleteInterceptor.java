package utils.interceptors.interception.removers;

import entities.users.Administrator;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = Administrator.class)
public class AdministratorDeleteInterceptor extends UserDeleteInterceptor {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
