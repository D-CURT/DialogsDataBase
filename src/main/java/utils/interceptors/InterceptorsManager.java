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
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        extractSet(entity).forEach(emptyInterceptor -> emptyInterceptor.onLoad(entity,id,state,propertyNames,types));
        return true;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        extractSet(entity).forEach(emptyInterceptor -> emptyInterceptor.onSave(entity,id,state,propertyNames,types));
        return true;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        extractSet(entity).forEach(emptyInterceptor -> emptyInterceptor.onDelete(entity,id,state,propertyNames,types));
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private Set<EmptyInterceptor> extractSet(Object entity) {
         return catcher.getInterceptors()
                .get(entity.getClass().getName());
    }
}
