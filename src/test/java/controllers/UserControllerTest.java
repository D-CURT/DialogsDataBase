package controllers;

import entities.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserControllerTest extends Mockito {
    private static final int ONE = 1;
    private UserController testController;

    @Before
    public void setUp() throws Exception {
        testController = new UserController();
    }

    @Test
    public void doGet() throws ServletException, IOException {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        final String USER_NAME = "Fred";

        when(mockRequest.getParameter("userName")).thenReturn(USER_NAME);
        testController.doGet(mockRequest, mockResponse);

        verify(mockRequest, atLeast(ONE)).getParameter("userName");

        User actual = new User(USER_NAME);
        User expected = testController.getHandler().getUser(USER_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        final String USER_NAME = "Katrine";

        when(mockRequest.getParameter("userName")).thenReturn(USER_NAME);
        testController.doPost(mockRequest, mockResponse);

        verify(mockRequest, atLeast(ONE)).getParameter("userName");

        User actual = new User(USER_NAME);
        User expected = testController.getHandler().getUser(USER_NAME);
        assertEquals(expected, actual);
    }
}