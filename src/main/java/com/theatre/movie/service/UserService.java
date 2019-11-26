package com.theatre.movie.service;

import com.theatre.movie.dao.UserDao;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.exception.UserAlreadyExistException;
import com.theatre.movie.web.dto.CreateUserRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;


@AllArgsConstructor
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);
    private UserDao userDao;

    public User getUserByCredentials(String login, String password) {
        if (isEmpty(login) || isEmpty(password)) {
            throw new IllegalArgumentException("Please fill all required fields!");
        }
        return userDao.getUserByCredential(login, password);
    }

    public User addUser(CreateUserRequestDto userRequest) throws UserAlreadyExistException {
        validateUserRequest(userRequest);
        //todo: add pass hashing
        User user = new User(userRequest.getLogin(), userRequest.getPassword(), userRequest.getEmail(), Role.ROLE_USER);
        return userDao.create(user);
    }

    private void validateUserRequest(CreateUserRequestDto userRequest) throws UserAlreadyExistException {
        if (isEmpty(userRequest.getLogin()) || isEmpty(userRequest.getPassword()) || isEmpty(userRequest.getEmail())) {
            throw new IllegalArgumentException("Required data is empty.");
        }
        String login = userRequest.getLogin();
        if (userDao.isLoginExist(login)) {
            LOG.warn("Find extra match for login=" + login);
            throw new UserAlreadyExistException("This username is already occupied.");
        }
        String email = userRequest.getEmail();
        if (userDao.isEmailExist(email)) {
            LOG.warn("Find extra match for email=" + email);
            throw new UserAlreadyExistException("This email is already occupied.");
        }
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

}
