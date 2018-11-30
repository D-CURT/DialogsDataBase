package dao.impl.hibernate;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import utils.connectors.SessionFactoryManager;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HibernateUserImplTest {
    private static final String USER_NAME = "test";
    private static final String KEY = "123";
    private static final String NAME_FIELD = "name";
    private SessionFactoryManager factory;
    private HibernateUserImpl testHandler;

    @Before
    public void setUp() throws Exception {
        factory = SessionFactoryManager.getInstance();
        testHandler = new HibernateUserImpl();
    }


    @Test
    public void check_of_adding_user_type_entity() {

        final String EXPECTED_TYPE = "U";
        testHandler.addUser(USER_NAME, KEY);
        final String ACTUAL_TYPE = selectUserType();

        assertThat(EXPECTED_TYPE, is(equalTo(ACTUAL_TYPE)));

        testHandler.removeUser(USER_NAME);
    }

    @Test
    public void check_of_adding_premium_user_type_entity() {

        final String EXPECTED_TYPE = "PU";
        testHandler.addPremiumUser(USER_NAME, KEY, KEY);
        final String ACTUAL_TYPE = selectUserType();

        assertThat(EXPECTED_TYPE, is(equalTo(ACTUAL_TYPE)));

        testHandler.removeUser(USER_NAME);
    }

    @Test
    public void check_of_users_interception_and_age_value_correction() {
        final String NULL_AGE = null;
        final String EXPECTED_AGE = "6";

        testHandler.addPremiumUser(USER_NAME, KEY, NULL_AGE, KEY);

        final String ACTUAL_AGE = testHandler.getUser(USER_NAME).getAge();

        assertThat(EXPECTED_AGE, is(equalTo(ACTUAL_AGE)));

        testHandler.removeUser(USER_NAME);
    }

    @SuppressWarnings("JpaQlInspection")
    private String selectUserType() {
        final String QUERY = "SELECT user_type FROM users WHERE name = :name";
        Session session = factory.getSession();
        String result = (String) session
                .createSQLQuery(QUERY)
                .setParameter(NAME_FIELD, USER_NAME)
                .uniqueResult();
        session.close();
        return result;
    }
}