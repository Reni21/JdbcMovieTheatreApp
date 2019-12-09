package com.theatre.movie.service;

import com.theatre.movie.dao.UserDao;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.exception.UserAlreadyExistException;
import com.theatre.movie.util.Passwords;
import com.theatre.movie.web.dto.CreateUserRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService instance;
    private UserDao userDao = mock(UserDao.class);

    @Before
    public void setUp() {
        instance = new UserService(userDao);
    }

    @Test
    public void shouldAddValidUser() throws UserAlreadyExistException {
        CreateUserRequestDto createUserRequest = new CreateUserRequestDto("john", "John12345", "john@gmail.com");
        instance.addUser(createUserRequest);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).create(userCaptor.capture());
        User actualUser = userCaptor.getValue();

        assertThat(actualUser.getUsername()).isEqualTo("john");
        assertThat(actualUser.getEmail()).isEqualTo("john@gmail.com");
        assertThat(actualUser.getPassword()).isNotBlank();
        assertThat(actualUser.getRole()).isEqualTo(Role.ROLE_USER);

    }

    @Test
    public void shouldNotAddUserWithInvalidPassword() {
        CreateUserRequestDto createUserRequest = new CreateUserRequestDto("john", "john", "john@gmail.com");

        assertThatThrownBy(() -> instance.addUser(createUserRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password length must be greater than 8 characters;\n" +
                        "Password should contain at least one upper case alphabet;\n" +
                        "Password should contain at least one number");

        verifyZeroInteractions(userDao);
    }

    @Test
    public void shouldNotAddUserWithEmptyEmail() {
        CreateUserRequestDto createUserRequest = new CreateUserRequestDto("john", "John1234", "");

        assertThatThrownBy(() -> instance.addUser(createUserRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Required data is empty.");

        verifyZeroInteractions(userDao);
    }

    @Test
    public void shouldNotAddUserWithInvalidUsername() {
        CreateUserRequestDto createUserRequest = new CreateUserRequestDto("1111", "John1234", "john@gmail.com");

        assertThatThrownBy(() -> instance.addUser(createUserRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username must be alphanumeric");

        verifyZeroInteractions(userDao);
    }

    @Test
    public void shouldGetUserByCredentialsWithCorrectPassword() {

        String hashedPass = Passwords.hash("John1234");

        User user = new User("john", hashedPass, "john@gmail.com", Role.ROLE_USER);
        when(userDao.getUserByUsername("john")).thenReturn(user);

        User actualUser = instance.getUserByCredentials("john", "John1234");

        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void shouldNotReturnUserByCredentialsWithIncorrectPassword() {
        User user = new User("john", "wrongpass", "john@gmail.com", Role.ROLE_USER);
        when(userDao.getUserByUsername("john")).thenReturn(user);

        User actualUser = instance.getUserByCredentials("john", "John1234");
        assertThat(actualUser).isNull();
    }

}