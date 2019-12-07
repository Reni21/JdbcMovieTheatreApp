package com.theatre.movie.dao;

import com.theatre.movie.entity.User;

public interface UserDao {

    User create(User entity);

    User getById(int id);

    boolean isUserExists(String username);

    boolean isEmailExist(String email);

    User getUserByUsername(String username);

}
