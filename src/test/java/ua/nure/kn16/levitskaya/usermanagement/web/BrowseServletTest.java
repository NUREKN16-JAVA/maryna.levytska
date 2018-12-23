package ua.nure.kn16.levitskaya.usermanagement.web;

import ua.nure.kn16.levitskaya.usermanagement.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BrowseServletTest extends MockServletTestCase {

	private static final Long USER_ID = 1000L;
	private static final String STRING_USER_ID = "1000";
	private static final String USER_FIRST_NAME = "John";
	private static final String USER_LAST_NAME = "Doe";
	private static final Date USER_DATE_OF_BIRTH = new Date();

	private static final String DETAILS_OPTION = "Details";
	private static final String EDIT_OPTION = "Edit";
	private static final String DELETE_OPTION = "Delete";

	private static final String ATTR_USERS = "users";
	private static final String ATTR_USER = "user";
	private static final String ATTR_ERROR = "error";

	@Override
	public void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}

	public void testBrowse() {
		User user = new User(USER_ID, USER_FIRST_NAME, USER_LAST_NAME, USER_DATE_OF_BIRTH);

		List<User> listOfUsers = Collections.singletonList(user);

		getMockUserDao().expectAndReturn("findAll", listOfUsers);

		doGet();

		Collection<User> collectionOfUsers = (Collection<User>) getWebMockObjectFactory().getMockSession()
				.getAttribute(ATTR_USERS);
		assertNotNull("Could not find list of users in sessions", collectionOfUsers);
		assertSame(listOfUsers, collectionOfUsers);
	}

	public void testEdit() {
		User user = new User(USER_ID, USER_FIRST_NAME, USER_LAST_NAME, USER_DATE_OF_BIRTH);

		getMockUserDao().expectAndReturn("find", USER_ID, user);

		addRequestParameter("editButton", EDIT_OPTION);
		addRequestParameter("id", STRING_USER_ID);

		doPost();

		User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute(ATTR_USER);
		assertNotNull("Could not find user in session", userInSession);
		assertSame(user, userInSession);
	}

	public void testEditWithEmptyId() {
		addRequestParameter("editButton", EDIT_OPTION);

		doPost();

		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
		assertNotNull("The session scope must have a error message", errorMessage);
	}

	public void testDetails() {
		User user = new User(USER_ID, USER_FIRST_NAME, USER_LAST_NAME, USER_DATE_OF_BIRTH);

		getMockUserDao().expectAndReturn("find", USER_ID, user);

		addRequestParameter("detailsButton", DETAILS_OPTION);
		addRequestParameter("id", STRING_USER_ID);

		doPost();

		User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute(ATTR_USER);
		assertNotNull("Could not find user in session", userInSession);
		assertSame(user, userInSession);
	}

	public void testDetailsWithEmptyId() {
		addRequestParameter("detailsButton", DETAILS_OPTION);

		doPost();

		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
		assertNotNull("The session scope must have a error message", errorMessage);
	}

	public void testDelete() {
		User user = new User(USER_ID, USER_FIRST_NAME, USER_LAST_NAME, USER_DATE_OF_BIRTH);

		getMockUserDao().expectAndReturn("find", USER_ID, user);
		getMockUserDao().expect("delete", user);

		addRequestParameter("deleteButton", DELETE_OPTION);
		addRequestParameter("id", STRING_USER_ID);

		doPost();

		User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute(ATTR_USER);
		assertNull("The user must not exists in session scope", userInSession);
	}

	public void testDeleteWithEmptyId() {
		addRequestParameter("deleteButton", DELETE_OPTION);

		doPost();

		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
		assertNotNull("The session scope must have a error message", errorMessage);
	}
}
