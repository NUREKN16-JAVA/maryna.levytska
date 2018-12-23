package ua.nure.kn16.levitskaya.usermanagement.db;

import java.util.Collection;

import ua.nure.kn16.levitskaya.usermanagement.User;

public interface UserDAO {

	/**
	 * Add user to DB table USER
	 * 
	 * @param user with null id field
	 * @return user modified record example with DB auto generated id field *
	 */
	public User create(final User user) throws DAOException;

	/**
	 * Get user from DB using user id
	 * 
	 * @param id
	 * @return user with such id
	 */
	public User find(final Long id) throws DAOException;

	/**
	 * Get list of all users from DB
	 * 
	 * @return Collection of all users
	 */
	public Collection<User> findAll() throws DAOException;

	/**
	 * Update user's data
	 * 
	 * @param user
	 * 
	 */
	public void update(final User user) throws DAOException;

	/**
	 * Delete user's data from DB
	 * 
	 * @param user
	 */
	public void delete(final User user) throws DAOException;

	/**
	 * Set connection factory
	 * 
	 * @param connectionFactory
	 */
	void setConnectionFactory(ConnectionFactory connectionFactory);

}