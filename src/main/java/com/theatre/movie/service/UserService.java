package com.theatre.movie.service;

import com.theatre.movie.dao.UserDao;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.exception.UserAlreadyExistException;
import com.theatre.movie.util.Passwords;
import com.theatre.movie.web.dto.CreateUserRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.util.StringJoiner;

/**
 * The {@code UserService} class provides methods for manage information about users
 * represented by {@link com.theatre.movie.entity.User} class
 * Properties: <b>userDao</b>, <b>UserService.ALPHA_NUMERIC</b>,
 *
 * @author Hlushchenko Renata
 * @see com.theatre.movie.dao.UserDao
 */

@AllArgsConstructor
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class);
    /**
     * Regex for validation <tt>username</tt> from {@link com.theatre.movie.web.dto.CreateUserRequestDto}
     * It should contains only english alphabets or numbers
     */
    private static final String ALPHA_NUMERIC_REGEX = "^(?!\\d+$)[a-zA-Z0-9]+$";

    /**
     * Regex for validation <tt>password</tt> from {@link com.theatre.movie.web.dto.CreateUserRequestDto}
     * It should contains at least one uppercase letter
     */
    private static final String UPPERCASE_CHAR_REGEX_ = "(.*[A-Z].*)";

    /**
     * Regex for validation <tt>password</tt> from {@link com.theatre.movie.web.dto.CreateUserRequestDto}
     * It should contains at least one number
     */
    private static final String NUMBER_REGEX = "(.*[0-9].*)";
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
        String hashedPass = Passwords.hash(userRequest.getPassword().trim());
        User user = new User(userRequest.getUsername().trim(), hashedPass, userRequest.getEmail(), Role.ROLE_USER);
        return userDao.create(user);
    }

    /**
     * The method validate all required data for new user creation
     * represented by {@link com.theatre.movie.web.dto.CreateUserRequestDto} class
     *
     * @param userRequest - is used for data transfer for create new user request
     * @throws UserAlreadyExistException if any of required field are invalid
     */
    private void validateCreateUserRequest(CreateUserRequestDto userRequest) throws UserAlreadyExistException {
        if (isEmpty(userRequest.getUsername()) || isEmpty(userRequest.getPassword()) || isEmpty(userRequest.getEmail())) {
            throw new IllegalArgumentException("Required data is empty.");
        }

        if (!userRequest.getUsername().matches(ALPHA_NUMERIC_REGEX)) {
            throw new IllegalArgumentException("Username must be alphanumeric");
        }

        validatePassword(userRequest.getPassword());

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

    private void validatePassword(String password) {

        StringJoiner errors = new StringJoiner(";\n");

        if (password.length() < 5) {
            errors.add("Password length must be greater than 5 characters");
        }

        if (!password.matches(UPPERCASE_CHAR_REGEX_)) {
            errors.add("Password should contain at least one upper case alphabet");
        }

        if (!password.matches(NUMBER_REGEX)) {
            errors.add("Password should contain at least one number");
        }

        if (errors.length() != 0) {
            throw new IllegalArgumentException(errors.toString());
        }
    }

    private boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

}
