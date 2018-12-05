package utils.interceptors.validators.interfaces;

public interface InitializationInterceptor extends EntityInterceptor {
    boolean initialize(Object entity, Object[] state, String[] propertyNames);
}
