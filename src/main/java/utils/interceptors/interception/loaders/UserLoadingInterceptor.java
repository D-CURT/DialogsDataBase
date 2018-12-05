package utils.interceptors.interception.loaders;

import entities.users.User;
import utils.annotations.Interceptor;
import utils.interceptors.EntitiesScope;
import utils.interceptors.interfaces.LoadingInterceptor;

@Interceptor(interceptedType = User.class, applyFor = EntitiesScope.SUB_CLASSES)
public class UserLoadingInterceptor implements LoadingInterceptor {
    @Override
    public boolean load(Object entity, Object[] state, String[] propertyNames) {
        System.out.println("*** Loading the user ***");
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
