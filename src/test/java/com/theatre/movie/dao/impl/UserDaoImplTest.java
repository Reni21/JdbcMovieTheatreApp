package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.UserDao;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.persistence.DataSourceConnectionFactoryWithPool;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoImplTest {
    private UserDao instance;

    @Before
    public void setUp() {
        instance = new UserDaoImpl(DataSourceConnectionFactoryWithPool.getInstance());
    }

    @Test
    public void shouldReturnNotNullUser() {
        User res = instance.getById(1);
        assertNotNull(res);
    }

    @Test
    public void shouldReturnNotNullUserByCredentials() {
//        User res = instance.getByCredential("admin", "admin");
//        assertNotNull(res);
    }

    @Test
    public void shouldReturnNotNullUserWithRoleAdmin() {
        User user = instance.getById(1);
        Role res = user.getRole();
        assertEquals(Role.ROLE_ADMIN, res);
    }

    @Test
    public void shouldReturnNullIfRequiredUserIsNotExist() {
        User user = instance.getById(1000000000);
        assertNull(user);
    }

//    @Test
//    public void shouldReturnIdForNewUser() {
//        User user = new User("junit", "junit", Role.USER);
//        int res = instance.create(user);
//        System.out.println(res);
//        assertNotNull(res);
//    }
}