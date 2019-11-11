package com.reni.hi.dao;

import com.reni.hi.entity.MovieSession;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class MovieSessionDaoImplTest {
    private MovieSessionDaoImpl instance;

    @Before
    public void setUp() throws Exception {
        instance = new MovieSessionDaoImpl();
    }

    @Test
    public void shouldReturnListWithMovieSession() {
        LocalDateTime searchFrom = LocalDateTime.parse("2019-11-11T00:14:30.266");
        LocalDateTime searchTo = LocalDateTime.parse("2019-11-11T23:59:59.0");
        List<MovieSession> sessions = instance.getAllSessionsInRange(searchFrom,searchTo);
        System.out.println(sessions);
        assertEquals(2, sessions.size());
    }
}