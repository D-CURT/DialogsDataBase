package utils.interceptors.interception.validators.questions;

import entities.Question;
import utils.annotations.Interceptor;
import utils.interceptors.interfaces.ValidationInterceptor;

@Interceptor(interceptedType = Question.class)
public class QuestionValidateInterceptors implements ValidationInterceptor {
    @Override
    public boolean validate(Object entity, Object[] state, String[] propertyNames) {
        if (entity instanceof Question) {
            System.out.println("*** Validating the question ***");
            Question question = (Question) entity;
            String content = question.getContent();
            if (content == null || content.isEmpty())
            {
                throw new IllegalArgumentException("Unexpected values of the question properties");
            }
        }
        return true;
    }
}