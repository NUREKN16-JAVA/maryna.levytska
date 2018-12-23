package ua.nure.kn16.levitskaya.usermanagement.db;

import org.dbunit.Assertion;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.XmlDataSet;

import org.junit.Before;
import org.junit.Test;
import ua.nure.kn16.levitskaya.usermanagement.User;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class HsqlDBUserDaoTest extends DatabaseTestCase {

	private User user;
	private User user2;
	private User user3;
	private UserDAO dao;
	private static final int ETALON_LIST_SIZE = 1;
	private static final Long ID = 0L;
	private ConnectionFactory connectionFactory;

	@Before
	public void setUp() throws Exception {
		getConnection();
		user = new User("Ivan", "Ivanov", new Date());
		user2 = new User("Vika", "Pupkina", java.sql.Date.valueOf("1968-04-26"));
		user3 = new User(1000L, "Vasya", "Pupkin", java.sql.Date.valueOf("1968-04-26"));
		dao = new HsqlDBUserDAO(connectionFactory);
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement",
				"sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

	/**
	 * Test method for creating new user
	 * 
	 * @throws DAOException
	 */
	@Test
	public void testCreate() throws DAOException {
		assertNull(user.getId());
		User userResult = dao.create(user);
		assertNotNull(userResult);
		assertNotNull(userResult.getId());
		assertEquals(user.getFirstName(), userResult.getFirstName());
		assertEquals(user.getLastName(), userResult.getLastName());
		assertEquals(user.getDateOfBirth(), userResult.getDateOfBirth());
	}

	/**
	 * Test method for getting list of all users from DB
	 * 
	 * @throws Exception
	 * @throws SQLException
	 */
	@Test
	public void testFindAll() throws SQLException, Exception {
		User resUser = dao.create(user);
		Collection collection = dao.findAll();
		assertNotNull(collection);
		assertEquals(ETALON_LIST_SIZE, collection.size());

	}

	/**
	 * Test for finding user by ID
	 * 
	 * @throws DAOException
	 */
	@Test
	public void testFind() throws DAOException {
		User testUser = dao.find(ID);
		assertNotNull(testUser);
		assertEquals(user.getFirstName(), testUser.getFirstName());
		assertEquals(user.getLastName(), testUser.getLastName());
	}

	/**
	 * Test for deleting user's data from DB
	 * 
	 * @throws DAOException
	 */
	@Test
	public void testDelete() throws DAOException {
		User resUser = dao.create(user2);
		assertNotNull(dao.find(resUser.getId()));
		dao.delete(user2);
		assertNull(dao.find(user2.getId()));
	}

	/**
	 * Test for updating user's data
	 * 
	 * @throws Exception
	 * @throws SQLException
	 */
	@Test
	public void testUpdate() throws SQLException, Exception {
		dao.update(user3);
		IDataSet databaseDataSet = getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("USERS");
		IDataSet expectedDataSet = new XmlDataSet(getClass().getResourceAsStream("/usersUpdateDataSet.xml"));
		ITable expectedTable = expectedDataSet.getTable("USERS");
		Assertion.assertEquals(expectedTable, actualTable);

	}

}