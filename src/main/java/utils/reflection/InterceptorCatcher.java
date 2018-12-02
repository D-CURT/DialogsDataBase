package utils.reflection;

import dao.interfaces.HibernateDBImpl;
import org.hibernate.EmptyInterceptor;
import org.reflections.Reflections;
import utils.annotations.Interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InterceptorCatcher {
    private static final String TARGET_PACKAGE = "utils.interceptors";
    private static final Class<Interceptor> TARGET_ANNOTATION = Interceptor.class;
    private static InterceptorCatcher instance;
    private Map<Class<? extends HibernateDBImpl>, EmptyInterceptor> interceptors;

    private InterceptorCatcher() {
        findInterceptors();
    }

    public static InterceptorCatcher getInstance() {
        if (instance == null) {
            instance = new InterceptorCatcher();
        }
        return instance;
    }

    public Map<Class<? extends HibernateDBImpl>, EmptyInterceptor> getInterceptors() {
        return interceptors;
    }

    private void findInterceptors() {
        interceptors = new HashMap<>();
        Reflections reflections = new Reflections(TARGET_PACKAGE);
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
