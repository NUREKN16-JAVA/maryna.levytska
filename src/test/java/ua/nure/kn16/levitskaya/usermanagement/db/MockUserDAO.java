package ua.nure.kn16.levitskaya.usermanagement.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.kn16.levitskaya.usermanagement.User;

public class MockUserDAO implements UserDAO {
    private Long id = 0L;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) throws DAOException {
        Long currentId = ++id;
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    @Override
    public void update(User user) throws DAOException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    @Override
    public void delete(User user) throws DAOException {
        Long currentId = user.getId();
        users.remove(currentId);
    }

    @Override
    public User find(Long id) throws DAOException {
        return users.get(id);
    }

    @Override
    public Collection findAll() throws DAOException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }

}
