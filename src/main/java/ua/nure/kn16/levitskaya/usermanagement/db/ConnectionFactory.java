package ua.nure.kn16.levitskaya.usermanagement.db;

import java.sql.Connection;  

public interface ConnectionFactory {
	/**
	 * Create database connection
	 * @return DB connection
	 * @throws DAOException
	 */
	Connection createConnection() throws DAOException;
}
