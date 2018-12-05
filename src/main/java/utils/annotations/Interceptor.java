package utils.annotations;

import utils.interceptors.EntitiesScope;

import java.lang.annotation.*;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Interceptor {
    Class interceptedType();
    EntitiesScope applyFor() default EntitiesScope.THIS;
}