package com.theatre.movie.service;

import com.theatre.movie.dao.UserDao;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.exception.UserAlreadyExistException;
import com.theatre.movie.util.Passwords;
import com.theatre.movie.web.dto.CreateUserRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;


@AllArgsConstructor
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);
    private UserDao userDao;

    public User getUserByCredentials(String username, String password) {
        if (isEmpty(username) || isEmpty(password)) {
            throw new IllegalArgumentException("Please fill all required fields!");
        }

        User user = userDao.getUserByUsername(username);
        if (user != null && Passwords.verifyHash(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User addUser(CreateUserRequestDto userRequest) throws UserAlreadyExistException {
        validateCreateUserRequest(userRequest);
        String hashedPass = Passwords.hash(userRequest.getPassword());
        User user = new User(userRequest.getUsername(), hashedPass, userRequest.getEmail(), Role.ROLE_USER);
        return userDao.create(user);
    }

    private void validateCreateUserRequest(CreateUserRequestDto userRequest) throws UserAlreadyExistException {
        if (isEmpty(userRequest.getUsername()) || isEmpty(userRequest.getPassword()) || isEmpty(userRequest.getEmail())) {
            throw new IllegalArgumentException("Required data is empty.");
        }

        Passwords.ValidationResult validationResult = Passwords.validatePassword(userRequest.getPassword());
        if (!validationResult.valid) {
            throw new IllegalArgumentException(validationResult.errorReasons());
        }

        String username = userRequest.getUsername();
        if (userDao.isUserExists(username)) {
            LOG.warn("Find extra match for username=" + username);
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
