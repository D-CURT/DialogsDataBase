package utils.interceptors.interception.removers;

import entities.users.User;
import utils.annotations.Interceptor;
import utils.interceptors.EntitiesScope;
import utils.interceptors.interfaces.DeletingInterceptor;

@Interceptor(interceptedType = User.class, applyFor = EntitiesScope.SUB_CLASSES)
public class UserDeleteInterceptor implements DeletingInterceptor {
    @Override
    public void delete(Object[] state, String[] propertyNames) {
        System.out.println("*** Deleting the user ***");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
