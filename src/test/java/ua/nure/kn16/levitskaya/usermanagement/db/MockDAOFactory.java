package ua.nure.kn16.levitskaya.usermanagement.db;

import com.mockobjects.dynamic.Mock;

import ua.nure.kn16.levitskaya.usermanagement.db.DAOFactory;
import ua.nure.kn16.levitskaya.usermanagement.db.UserDAO;

public class MockDAOFactory extends DAOFactory {
	
	private Mock mockUserDAO;

	public MockDAOFactory() {
		mockUserDAO = new Mock(UserDAO.class);
	}
	
	public Mock getMockUserDAO() {
	return mockUserDAO;
	}

	@Override
	public UserDAO getUserDAO() {
		return (UserDAO)mockUserDAO.proxy();
	}
}
