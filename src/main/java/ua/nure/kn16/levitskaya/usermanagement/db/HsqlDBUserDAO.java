package ua.nure.kn16.levitskaya.usermanagement.db;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn16.levitskaya.usermanagement.User;

class HsqlDBUserDAO implements UserDAO {

	private static final String INSERT_USER_QUERY = "insert into users (firstname,lastname,dateofbirth) values (?,?,?)";
	private static final String UPDATE_USER_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
	private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
	private static final String FIND_USER_BY_ID_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id = ?";
	private static final String FIND_ALL_USERS_QUERY = "SELECT * FROM users";
	private static final String FIND_USERS_BY_NAMES_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE firstname = ? AND lastname = ?";
	private ConnectionFactory connectionFactory;

	public HsqlDBUserDAO(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public HsqlDBUserDAO() {
	}

	/**
	 * @return the connectionFactory
	 */
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public User create(User user) throws DAOException {

		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_USER_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));

			int number = statement.executeUpdate();
			if (number != 1) {
				throw new DAOException("Number of inserted rows: " + number);
			}

			CallableStatement callableStatement = connection.prepareCall("call identity()");
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return user;
	}

	@Override
	public User find(Long id) throws DAOException {
		User user = null;
		try {

			Connection connection = connectionFactory.createConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			user = extractUser(resultSet);

			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return user;
	}

	private User extractUser(ResultSet resultSet) throws SQLException {
		User user = null;
		if (resultSet.next()) {
			user = new User();
			user.setId(new Long(resultSet.getLong("id")));
			user.setFirstName(resultSet.getString("firstname"));
			user.setLastName(resultSet.getString("lastname"));
			user.setDateOfBirth(resultSet.getDate("dateofbirth"));
		}
		return user;
	}

	@Override
	public Collection<User> findAll() throws DAOException {
		Collection<User> listOfAllUsers = new LinkedList<>();
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS_QUERY);

			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				listOfAllUsers.add(user);

				resultSet.close();
				statement.close();
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfAllUsers;
	}

	@Override
	public void update(User user) throws DAOException {
		Connection connection = connectionFactory.createConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			preparedStatement.setLong(4, user.getId());
			int number = preparedStatement.executeUpdate();
			if (number != 1) {
				throw new DAOException("Number of updated raws: " + number);
			}
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void delete(User user) throws DAOException {
		if (user.getId() == null)
			throw new DAOException();
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);
			preparedStatement.setLong(1, user.getId());
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	@Override
	public Collection<User> find(String firstName, String lastName) throws DAOException {
		Collection<User> result = new LinkedList<>();

		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_USERS_BY_NAMES_QUERY);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result;
	}
}
