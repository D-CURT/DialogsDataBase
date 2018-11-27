package controllers;

import dao.impl.hibernate.HibernateUserImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserControllerTest extends Mockito {
    private static final int ONE = 1;
    private static final String PARAM_NAME = "userName";
    private static final String USER_NAME = "Test";
    private UserController testController;
    private HibernateUserImpl testHandler;

    @Before
    public void setUp() throws Exception {
        testController = new UserController();
        testController.init();
        testHandler = testController.getHandler();
    }

    @Test
    public void check_whether_add_user_using_doGet() throws ServletException, IOException {
        testHandler.addUser(USER_NAME);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        when(mockRequest.getParameter(PARAM_NAME)).thenReturn(USER_NAME);
        assertNotNull(testHandler.getUser(USER_NAME));

        final int BEFORE = testHandler.countRows();
        testController.doGet(mockRequest, mockResponse);
        final int AFTER = testHandler.countRows();
        int actual = AFTER - BEFORE;
        int expected = -1;

        verify(mockRequest, atLeast(ONE)).getParameter(PARAM_NAME);
        assertThat(expected, is(equalTo(actual)));
    }

    @Test
    public void check_whether_remove_user_using_doPost() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter(PARAM_NAME)).thenReturn(USER_NAME);
        assertNull(testHandler.getUser(USER_NAME));

        final int BEFORE = testHandler.countRows();
        testController.doPost(request, response);
        final int AFTER = testHandler.countRows();
        int actual = AFTER - BEFORE;
        int expected = 1;

        verify(request, atLeast(ONE)).getParameter(PARAM_NAME);
        assertThat(expected, is(equalTo(actual)));

        testHandler.removeUser(USER_NAME);
    }
}