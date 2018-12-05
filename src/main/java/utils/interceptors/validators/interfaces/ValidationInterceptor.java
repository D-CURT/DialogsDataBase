package utils.interceptors.validators.interfaces;

public interface ValidationInterceptor extends EntityInterceptor {
    boolean validate(Object entity, Object[] state, String[] propertyNames);
}
