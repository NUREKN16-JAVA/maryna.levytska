package ua.nure.kn16.levitskaya.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DAOFactory {

	public static final String DAO_FACTORY = "dao.factory";
	public static final String USER_DAO = "dao.ua.nure.kn16.levitskaya.usermanagement.db.UserDAO";
	protected static Properties properties;
	private static DAOFactory instance;

	static {
		properties = new Properties();
		try {
			properties.load(DAOFactory.class.getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create exactly one DAOFactory
	 * 
	 * @return instance of DAOFactory
	 */
	public static synchronized DAOFactory getInstance() {
		if (instance == null) {
			try {
				Class factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				instance = (DAOFactory) factoryClass.newInstance();

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	protected ConnectionFactory getConnectionFactory() {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
		return new ConnectionFactoryImpl(driver, url, user, password);
	}

	/**
	 * 
	 * @return UserDao
	 */
	public abstract UserDAO getUserDAO();

	public static void init(Properties prop) {
		properties = prop;
		instance = null;
	}

}