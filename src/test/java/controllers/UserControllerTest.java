package controllers;

import dao.impl.hibernate.HibernateUserImpl;
import entities.User;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utils.connectors.SessionFactoryManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class UserControllerTest extends Mockito {
    private static final int ONE = 1;
    private static final String PARAM_NAME = "userName";
    private static final String USER_NAME = "Test";
    private static final String PASSPORT_KEY = "123";
    private UserController testController;
    private HibernateUserImpl testHandler;

    @Before
    public void setUp() {
        testController = new UserController();
        testController.init();
        testHandler = testController.getHandler();
    }

    @Test
    public void check_whether_remove_user_using_mock() throws ServletException, IOException {
        testHandler.addUser(USER_NAME, PASSPORT_KEY);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter(PARAM_NAME)).thenReturn(USER_NAME);

        assertNotNull(testHandler.getUser(USER_NAME));

        final int BEFORE = testHandler.countRows();
        testController.doGet(request, response);
        final int AFTER = testHandler.countRows();

        verify(request, atLeast(ONE)).getParameter(PARAM_NAME);
        assertThat(BEFORE, greaterThan(AFTER));
    }

    @Test
    public void check_whether_add_user_using_mock() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter(PARAM_NAME)).thenReturn(USER_NAME);
        assertNull(testHandler.getUser(USER_NAME));

        final int BEFORE = testHandler.countRows();
        testController.doPost(request, response);
        final int AFTER = testHandler.countRows();

        verify(request, atLeast(ONE)).getParameter(PARAM_NAME);
        assertThat(BEFORE, lessThan(AFTER));

        testHandler.removeUser(USER_NAME);
    }

    @Test
    public void check_whether_remove_user_using_HttpClient() throws IOException {
        HttpUriRequest httpRequest = new HttpGet("http://localhost:8080/user?" + PARAM_NAME + "=" + USER_NAME);
        testHandler.addUser(USER_NAME, PASSPORT_KEY);

        assertNotNull(testHandler.getUser(USER_NAME));

        final int BEFORE = testHandler.countRows();
        HttpClientBuilder.create().build().execute(httpRequest);
        final int AFTER = testHandler.countRows();

        assertThat(BEFORE, greaterThan(AFTER));
    }

    @Test
    public void check_whether_add_user_using_HttpClient() throws IOException {
        HttpUriRequest httpRequest = new HttpPost("http://localhost:8080/user?" + PARAM_NAME + "=" + USER_NAME);

        assertNull(testHandler.getUser(USER_NAME));

        final int BEFORE = testHandler.countRows();
        HttpClientBuilder.create().build().execute(httpRequest);
        final int AFTER = testHandler.countRows();

        assertThat(BEFORE, lessThan(AFTER));
    }

    @SuppressWarnings("JpaQlInspection")
    @Test
    public void check_of_column_name_conversion() {
        final String USER_NAME = "tEST";
        final String NAME_FIELD = "name";
        final String COLUMN_IN_DB = "select name from users where name = :name";

        testHandler.addUser(USER_NAME, PASSPORT_KEY);

        final String ACTUAL_IN_DB = (String)
                SessionFactoryManager.getInstance()
                                     .getSession()
                                     .createSQLQuery(COLUMN_IN_DB)
                                     .setParameter(NAME_FIELD, USER_NAME.toLowerCase()).uniqueResult();

        final String EXPECTED_IN_DB = USER_NAME.toLowerCase();

        assertThat(EXPECTED_IN_DB, is(equalTo(ACTUAL_IN_DB)));

        final String ACTUAL_OUT_DB = testHandler.getUser(USER_NAME).getName();
        final String EXPECTED_OUT_DB = Character.toUpperCase(USER_NAME.charAt(0))
                          + USER_NAME.substring(1).toLowerCase();

        assertThat(EXPECTED_OUT_DB, is(equalTo(ACTUAL_OUT_DB)));

        testHandler.removeUser(USER_NAME);
    }

    @SuppressWarnings("JpaQlInspection")
    @Test
    public void check_of_column_passport_key_transformation() {
        final String NAME_FIELD = "name";
        final String COLUMN_IN_DB = "select passport_key from users where name = :name";

        testHandler.addUser(USER_NAME, PASSPORT_KEY);

        final String EXPECTED_OUT_DB = testHandler.getUser(USER_NAME).getPassportKey();

        final String ACTUAL_IN_DB = (String)
                SessionFactoryManager.getInstance()
                                     .getSession()
                                     .createSQLQuery(COLUMN_IN_DB)
                                     .setParameter(NAME_FIELD, USER_NAME.toLowerCase())
                                     .uniqueResult();

        assertThat(EXPECTED_OUT_DB, is(not(equalTo(ACTUAL_IN_DB))));

        assertThat(EXPECTED_OUT_DB, is(equalTo(PASSPORT_KEY)));

        testHandler.removeUser(USER_NAME);
    }
}