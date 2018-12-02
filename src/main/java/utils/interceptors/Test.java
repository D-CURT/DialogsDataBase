package utils.interceptors;

import dao.impl.hibernate.HibernateQuestionImpl;
import org.hibernate.EmptyInterceptor;
import utils.annotations.Interceptor;

@Interceptor(interceptedType = HibernateQuestionImpl.class)
public class Test extends EmptyInterceptor {
    @Override
    public String toString() {
        return "InterceptorType: " + getClass();
    }
}
