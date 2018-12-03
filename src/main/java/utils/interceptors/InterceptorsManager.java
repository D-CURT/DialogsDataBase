package utils.interceptors;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import utils.reflection.InterceptorCatcher;

import java.io.Serializable;
import java.util.Set;

public class InterceptorsManager extends EmptyInterceptor {
    private InterceptorCatcher catcher;
    private String packageName;

    public InterceptorsManager(String packageName) {
        this.packageName = packageName;
        catcher = new InterceptorCatcher(packageName);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Set<EmptyInterceptor> set = catcher.getInterceptors()
                                           .get(entity.getClass().getName());
        for (EmptyInterceptor interceptor: set) {
            interceptor.onSave(entity,id,state,propertyNames,types);
        }
        return true;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
