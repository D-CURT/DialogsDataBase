package utils.reflection;

import org.hibernate.EmptyInterceptor;
import org.reflections.Reflections;
import utils.annotations.Interceptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InterceptorCatcher {
    private static final Class<Interceptor> TARGET_ANNOTATION = Interceptor.class;
    private String packageName;
    private Map<String, Set<EmptyInterceptor>> interceptors;

    public InterceptorCatcher() {
    }

    public InterceptorCatcher(String packageName) {
        this.packageName = packageName;
        findInterceptors();
    }

    public Map<String, Set<EmptyInterceptor>> getInterceptors() {
        return interceptors;
    }

    private void findInterceptors() {
        interceptors = new HashMap<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(TARGET_ANNOTATION);
        Set<EmptyInterceptor> interceptorSet;
        for (Class<?> c: annotated) {
            String key =
                    c.getAnnotation(TARGET_ANNOTATION).interceptedType().getName();
            try {
                EmptyInterceptor interceptor = (EmptyInterceptor) c.newInstance();
                interceptorSet =
                        interceptors.get(key) != null ? interceptors.get(key)
                                                      : new HashSet<>();
                interceptorSet.add(interceptor);
                interceptors.put(key, interceptorSet);
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("*** Unknown interceptor ***");
            }
        }
    }
}
