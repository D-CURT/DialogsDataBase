package controllers;

import dao.impl.hibernate.HibernateQuestionImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

public class QuestionControllerTest extends Mockito {
    private static final int ONE = 1;
    private static final String PARAM_NAME = "question";
    private static final String QUESTION = "Test";
    private QuestionController testController;
    private HibernateQuestionImpl testHandler;

    @Before
    public void setUp() throws Exception {
        testController = new QuestionController();
        testController.init();
        testHandler = testController.getHandler();
    }

    @Test
    public void check_whether_remove_question_using_doGet() throws ServletException, IOException {
        testHandler.addQuestion(QUESTION);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter(PARAM_NAME)).thenReturn(QUESTION);

        assertNotNull(testHandler.getQuestion(QUESTION));

        final int BEFORE = testHandler.countRows();
        testController.doGet(request, response);
        final int AFTER = testHandler.countRows();

        verify(request,atLeast(ONE)).getParameter(PARAM_NAME);
        assertThat(BEFORE, greaterThan(AFTER));
    }

    @Test
    public void check_whether_add_question_using_doPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter(PARAM_NAME)).thenReturn(QUESTION);

        assertNull(testHandler.getQuestion(QUESTION));

        final int BEFORE = testHandler.countRows();
        testController.doPost(request, response);
        final int AFTER = testHandler.countRows();

        verify(request, atLeast(ONE)).getParameter(PARAM_NAME);
        assertThat(BEFORE, lessThan(AFTER));

        testHandler.removeQuestion(QUESTION);
    }
}