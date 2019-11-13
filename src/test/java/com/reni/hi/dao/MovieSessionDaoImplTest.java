package com.reni.hi.dao;

import com.reni.hi.dao.impl.MovieSessionDaoImpl;
import com.reni.hi.entity.MovieSession;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class MovieSessionDaoImplTest {
    private MovieSessionDao instance;

    @Before
    public void setUp() throws Exception {
        instance = new MovieSessionDaoImpl();
    }

    @Test
    public void shouldReturnListWithMovieSession() {
        LocalDateTime searchFrom = LocalDateTime.parse("2019-11-13T00:14:30.266");
        LocalDateTime searchTo = LocalDateTime.parse("2019-11-13T23:59:59.0");
        List<MovieSession> sessions = instance.getAllInRange(searchFrom, searchTo);
        int res = sessions.size();
        assertEquals(4, res);
    }

    @Test
    public void shouldReturnMovieSession() {
        MovieSession res = instance.getById(2);
        assertNotNull(res);
    }

    @Test
    public void shouldRemoveMovieSession() {
//        boolean res = instance.remove(37);
//        assertTrue(res);
    }

    @Test
    public void shouldReturnIdForCreatedMovieSession() {
//        LocalDateTime startAt = LocalDateTime.now();
//        int res = instance.create(new MovieSession(2, 1, startAt, 100.0));
//        assertEquals(39, res);
    }
}