package controllers.authentication;

import dao.impl.hibernate.HibernateUserImpl;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import java.util.Base64;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import utils.security.SecurityConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LoginControllerTest {
    private final String TEST_VALUE = "Test";
    private HibernateUserImpl dbHandler;

    @Before
    public void setUp() throws Exception {
         dbHandler = new HibernateUserImpl();
    }

    @Test
    public void check_weather_user_login_using_HttpClient() throws IOException {

        String expectedStatus = "HTTP/1.1 200 ";
        CloseableHttpResponse httpResponse = getStatusCodeAfterLogin(TEST_VALUE, TEST_VALUE);
        String actualStatus = httpResponse.getStatusLine().toString();

        assertThat(actualStatus, is(equalTo(expectedStatus)));

        dbHandler.removeUser(TEST_VALUE);
    }

    @Test
    public void check_of_login_negative_result() throws IOException {
        String suffix = "111";
        String expectedStatus = "HTTP/1.1 401 ";
        CloseableHttpResponse httpResponse = getStatusCodeAfterLogin(TEST_VALUE + suffix, TEST_VALUE + suffix);
        String actualStatus = httpResponse.getStatusLine().toString();

        assertThat(actualStatus, is(equalTo(expectedStatus)));

        dbHandler.removeUser(TEST_VALUE);
    }

    private CloseableHttpResponse getStatusCodeAfterLogin(String login, String password) throws IOException {
        SecurityConfig.Roles role = SecurityConfig.Roles.USER;
        String roleName = role.name();
        dbHandler.addUser(TEST_VALUE, TEST_VALUE, TEST_VALUE, TEST_VALUE, null, roleName);
        String encoding = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());

        HttpUriRequest httpRequest = new HttpPost("http://localhost:8080/login");
        httpRequest.setHeader("Authorization", "Basic " + encoding);
        return HttpClientBuilder.create().build().execute(httpRequest);
    }
}