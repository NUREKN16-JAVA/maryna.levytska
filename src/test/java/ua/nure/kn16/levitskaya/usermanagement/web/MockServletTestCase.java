package ua.nure.kn16.levitskaya.usermanagement.web;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import ua.nure.kn16.levitskaya.usermanagement.db.DAOFactory;
import ua.nure.kn16.levitskaya.usermanagement.db.MockDAOFactory;

import java.util.Properties;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

    private Mock mockUserDao;

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDAOFactory.class.getName());
        DAOFactory.init(properties);
        setMockUserDao(((MockDAOFactory)DAOFactory.getInstance()).getMockUserDAO());
    }

    @Override
    public void tearDown() throws Exception {
        getMockUserDao().verify();
        super.tearDown();
    }

}
