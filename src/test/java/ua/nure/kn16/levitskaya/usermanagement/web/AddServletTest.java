package ua.nure.kn16.levitskaya.usermanagement.web;

import ua.nure.kn16.levitskaya.usermanagement.User;

import java.text.DateFormat;
import java.util.Date;

public class AddServletTest extends MockServletTestCase {
    private static final Long USER_ID = 1000L;
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final Date USER_DATE_OF_BIRTH = new Date();

    private static final String OK_OPTION = "Ok";
    private static final String ATTR_ERROR = "error";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    public void testAdd() {
        User newUser = new User(
                USER_FIRST_NAME,
                USER_LAST_NAME,
                USER_DATE_OF_BIRTH
        );
        User user = new User(
                USER_ID,
                USER_FIRST_NAME,
                USER_LAST_NAME,
                USER_DATE_OF_BIRTH
        );

        getMockUserDao().expectAndReturn("create", newUser, user);

        addRequestParameter("firstName", USER_FIRST_NAME);
        addRequestParameter("lastName", USER_LAST_NAME);
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(USER_DATE_OF_BIRTH));
        addRequestParameter("okButton", OK_OPTION);
        doPost();
    }

    public void testAddEmptyFirstName() {
        addRequestParameter("lastName", USER_LAST_NAME);
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(USER_DATE_OF_BIRTH));
        addRequestParameter("okButton", OK_OPTION);
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }

    public void testAddEmptyLastName() {
        addRequestParameter("firstName", USER_FIRST_NAME);
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(USER_DATE_OF_BIRTH));
        addRequestParameter("okButton", OK_OPTION);
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }

    public void testAddEmptyDateOfBirth() {
        addRequestParameter("firstName", USER_FIRST_NAME);
        addRequestParameter("lastName", USER_LAST_NAME);
        addRequestParameter("okButton", OK_OPTION);
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }

    public void testAddInvalidDate() {
        addRequestParameter("firstName", USER_FIRST_NAME);
        addRequestParameter("lastName", USER_LAST_NAME);
        addRequestParameter("dateOfBirth", "#####");
        addRequestParameter("okButton", OK_OPTION);
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }
}
