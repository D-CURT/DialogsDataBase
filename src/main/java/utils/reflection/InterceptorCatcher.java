package utils.reflection;

import dao.interfaces.HibernateDBImpl;
import org.hibernate.EmptyInterceptor;
import org.reflections.Reflections;
import utils.annotations.Interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InterceptorCatcher {
    private static final Class<Interceptor> TARGET_ANNOTATION = Interceptor.class;
    private String packageName;
    private Map<Class<? extends HibernateDBImpl>, EmptyInterceptor> interceptors;

    public InterceptorCatcher() {
    }

    public InterceptorCatcher(String packageName) {
        this.packageName = packageName;
        findInterceptors();
    }

    public Map<Class<? extends HibernateDBImpl>, EmptyInterceptor> getInterceptors() {
        return interceptors;
    }

    private void findInterceptors() {
        interceptors = new HashMap<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TARGET_ANNOTATION);
        annotated.forEach(aClass -> {
            Class<? extends HibernateDBImpl> key =
                    aClass.getAnnotation(TARGET_ANNOTATION).interceptedType();
            try {
                interceptors.put(key, (EmptyInterceptor) aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("*** Unknown interceptor ***");
            }
        });
    }
}
