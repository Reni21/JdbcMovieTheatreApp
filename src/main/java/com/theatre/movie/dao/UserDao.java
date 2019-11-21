package com.theatre.movie.dao;

import com.theatre.movie.entity.User;

public interface UserDao {

    User create(User entity);

    User getById(int id);

    boolean isLoginExist(String login);

    boolean isEmailExist(String email);

    User getUserByCredential(String login, String password);

}
