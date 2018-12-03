package utils.reflection;

import org.hibernate.EmptyInterceptor;
import org.reflections.Reflections;
import utils.annotations.Interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InterceptorCatcher {
    private static InterceptorCatcher instance;
    private static final Class<Interceptor> TARGET_ANNOTATION = Interceptor.class;
    private String packageName;
    private Map<String, EmptyInterceptor> interceptors;

    private InterceptorCatcher() {
    }

    private InterceptorCatcher(String packageName) {
        this.packageName = packageName;
        findInterceptors();
    }

    public static InterceptorCatcher getInstance(String packageName) {
        if (instance == null) {
            instance = new InterceptorCatcher(packageName);
        }
        return instance;
    }

    public Map<String, EmptyInterceptor> getInterceptors() {
        return interceptors;
    }

    private void findInterceptors() {
        interceptors = new HashMap<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TARGET_ANNOTATION);
        annotated.forEach(aClass -> {
            String key =
                    aClass.getAnnotation(TARGET_ANNOTATION).interceptedType().getName();
            try {
                interceptors.put(key, (EmptyInterceptor) aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("*** Unknown interceptor ***");
            }
        });
    }
}
