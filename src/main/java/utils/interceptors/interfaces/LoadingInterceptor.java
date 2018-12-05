package utils.interceptors.interfaces;

public interface LoadingInterceptor {
    boolean load(Object entity, Object[] state, String[] propertyNames);
}
