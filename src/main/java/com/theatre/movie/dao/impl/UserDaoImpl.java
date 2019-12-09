package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.UserDao;
import com.theatre.movie.entity.User;
import com.theatre.movie.persistence.ConnectionFactory;
import org.apache.log4j.Logger;

import java.util.List;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);

    public UserDaoImpl(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public User create(User entity) {
        LOG.debug("Create user: + " + entity);

        String query = "INSERT INTO `user` ("
                + DbTablesConstants.UserTable.USERNAME + ", " + DbTablesConstants.UserTable.PASSWORD + ", "
                + DbTablesConstants.UserTable.ROLE + ", " + DbTablesConstants.UserTable.EMAIL
                + ") VALUE (?, ?, ?, ?)";
        int id = super.create(query, ps -> {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getRole().toString());
            ps.setString(4, entity.getEmail());
        });
        entity.setId(id);
        return entity;
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * FROM `user` WHERE " + DbTablesConstants.UserTable.USER_ID + " = ?";
        return super.getById(query,
                ps -> ps.setInt(1, id),
                EntityMapperProvider.USER_ENTITY_MAPPER);
    }

    @Override
    public boolean isUserExists(String username) {
        String query = "SELECT 1 FROM `user` WHERE " + DbTablesConstants.UserTable.USERNAME + " = ?";
        return super.checkIfDataExists(query,
                ps -> ps.setString(1, username));
    }

    @Override
    public boolean isEmailExist(String email) {
        String query = "SELECT 1 FROM `user` WHERE " + DbTablesConstants.UserTable.EMAIL + " = ?";
        return super.checkIfDataExists(query,
                ps -> ps.setString(1, email));
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM `user` WHERE " + DbTablesConstants.UserTable.USERNAME + " = ?";
        List<User> users = super.getAll(query,
                ps -> ps.setString(1, username),
                EntityMapperProvider.USER_ENTITY_MAPPER);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
