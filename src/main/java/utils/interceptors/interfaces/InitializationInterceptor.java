package utils.interceptors.interfaces;

public interface InitializationInterceptor {
    boolean initialize(Object entity, Object[] state, String[] propertyNames);
}
