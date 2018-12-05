package utils.interceptors;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.reflections.Reflections;
import utils.annotations.Interceptor;
import utils.interceptors.interfaces.DeletingInterceptor;
import utils.interceptors.interfaces.InitializationInterceptor;
import utils.interceptors.interfaces.LoadingInterceptor;
import utils.interceptors.interfaces.ValidationInterceptor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InterceptorsManager extends EmptyInterceptor {
    private static final Class<Interceptor> TARGET_ANNOTATION = Interceptor.class;
    private String packageName;
    private Map<Class, Set<Object>> interceptors;

    public InterceptorsManager() {
    }

    public InterceptorsManager(String packageName) {
        this.packageName = packageName;
        findInterceptors();
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Set<Object> set;
        if ((set = extractSet(entity)) != null) {
            set.forEach(o -> {
                if (o instanceof LoadingInterceptor) {
                    LoadingInterceptor interceptor = (LoadingInterceptor) o;
                    interceptor.load(entity, state, propertyNames);
                }
            });
           return true;
        }
        return false;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Set<Object> set;
        if ((set = extractSet(entity)) != null) {
            set.forEach(o -> {
                if (o instanceof InitializationInterceptor) {
                    InitializationInterceptor interceptor = (InitializationInterceptor) o;
                    interceptor.initialize(entity, state, propertyNames);
                }
                if (o instanceof ValidationInterceptor) {
                    ValidationInterceptor interceptor = (ValidationInterceptor) o;
                    interceptor.validate(entity, state, propertyNames);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Set<Object> set;
        if ((set = extractSet(entity)) != null) {
            set.forEach(o -> {
                if (o instanceof DeletingInterceptor) {
                    DeletingInterceptor interceptor = (DeletingInterceptor) o;
                    interceptor.delete(state, propertyNames);
                }
            });
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private Set<Object> extractSet(Object entity) {
        return interceptors.get(entity.getClass());
    }

    @SuppressWarnings("unchecked")
    private void findInterceptors() {
        interceptors = new HashMap<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TARGET_ANNOTATION);
        for (Class<?> c: annotated) {
            try {
                Class key = c.getAnnotation(TARGET_ANNOTATION).interceptedType();
                Object o = c.newInstance();
                c.getAnnotation(TARGET_ANNOTATION).applyFor().thisType(this, key, o);
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("*** Unknown interceptor ***");
            }
        }
    }

    void addingMap(Class key, Object o) {
        Set<Object> interceptorSet;
        if ((interceptorSet = interceptors.get(key)) == null) {
            interceptorSet = new HashSet<>();
        }
        interceptorSet.add(o);
        interceptors.put(key, interceptorSet);
    }
}