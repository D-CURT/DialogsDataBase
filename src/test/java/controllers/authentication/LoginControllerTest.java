package controllers.authentication;

import dao.impl.hibernate.HibernateUserImpl;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

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
        dbHandler.addUser(TEST_VALUE, TEST_VALUE, TEST_VALUE, TEST_VALUE, null, null);
        HttpUriRequest httpRequest = new HttpPost("http://localhost:8080/login?login=Test&password=Test");
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpRequest);
        String expectedStatus = "HTTP/1.1 200 ";
        String actualStatus = httpResponse.getStatusLine().toString();

        assertThat(expectedStatus, is(equalTo(actualStatus)));

        dbHandler.removeUser(TEST_VALUE);
    }
}