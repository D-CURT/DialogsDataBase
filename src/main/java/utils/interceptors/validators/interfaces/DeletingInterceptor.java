package utils.interceptors.validators.interfaces;

public interface DeletingInterceptor extends EntityInterceptor {
    void delete(Object[] state, String[] propertyNames);
}
