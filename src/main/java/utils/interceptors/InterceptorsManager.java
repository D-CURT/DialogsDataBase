package utils.interceptors;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.reflections.Reflections;
import utils.annotations.Interceptor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InterceptorsManager extends EmptyInterceptor {
    private static final Class<Interceptor> TARGET_ANNOTATION = Interceptor.class;
    private String packageName;
    private Map<Class, Set<EmptyInterceptor>> interceptors;

    public InterceptorsManager() {
    }

    public InterceptorsManager(String packageName) {
        this.packageName = packageName;
        findInterceptors();
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Set<EmptyInterceptor> set;
        if ((set = extractSet(entity)) != null) {
           set.forEach(emptyInterceptor -> emptyInterceptor.onLoad(entity,id,state,propertyNames,types));
           return true;
        }
        return false;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Set<EmptyInterceptor> set;
        if ((set = extractSet(entity)) != null) {
            set.forEach(emptyInterceptor -> emptyInterceptor.onSave(entity,id,state,propertyNames,types));
            return true;
        }
        return false;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Set<EmptyInterceptor> set;
        if ((set = extractSet(entity)) != null) {
            set.forEach(emptyInterceptor -> emptyInterceptor.onDelete(entity,id,state,propertyNames,types));
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private Set<EmptyInterceptor> extractSet(Object entity) {
        return interceptors.get(entity.getClass());
    }

    private void findInterceptors() {
        interceptors = new HashMap<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TARGET_ANNOTATION);
        Set<EmptyInterceptor> interceptorSet;
        for (Class<?> c: annotated) {
            Class key =
                    c.getAnnotation(TARGET_ANNOTATION).interceptedType();
            try {
                EmptyInterceptor interceptor = (EmptyInterceptor) c.newInstance();
                if ((interceptorSet = interceptors.get(key)) == null) {
                    interceptorSet = new HashSet<>();
                }
                interceptorSet.add(interceptor);
                interceptors.put(key, interceptorSet);
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("*** Unknown interceptor ***");
            }
        }
    }
}