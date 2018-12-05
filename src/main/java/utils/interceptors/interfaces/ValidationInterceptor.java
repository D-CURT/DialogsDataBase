package utils.interceptors.interfaces;

public interface ValidationInterceptor {
    boolean validate(Object entity, Object[] state, String[] propertyNames);
}
