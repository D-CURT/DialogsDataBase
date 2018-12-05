package utils.interceptors.interception.initializers;

import entities.users.Administrator;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = Administrator.class)
public class AdministratorInitInterceptor extends UserInitInterceptor {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
