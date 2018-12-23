package ua.nure.kn16.levitskaya.usermanagement.db;

import org.junit.Test;

import static org.junit.Assert.*;

public class DAOFactoryTest {

	@Test
	public void testGetUserDao() {

		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			assertNotNull("DaoFactory instance is null", daoFactory);
			UserDAO userDao = daoFactory.getUserDAO();
			assertNotNull("UserDao instance is null", userDao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}