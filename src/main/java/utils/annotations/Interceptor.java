package utils.annotations;

import dao.interfaces.HibernateDBImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Interceptor {
    Class<? extends HibernateDBImpl> interceptedType();
}
