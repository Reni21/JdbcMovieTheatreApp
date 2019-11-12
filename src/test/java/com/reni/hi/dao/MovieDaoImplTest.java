package com.reni.hi.dao;

import com.reni.hi.dao.temp1.MovieDao;
import com.reni.hi.dao.temp1.MovieDaoImpl;
import com.reni.hi.entity.Movie;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieDaoImplTest {
    private MovieDao movieDao;

    @Before
    public void setUp() {
        movieDao = new MovieDaoImpl();
    }


    @Test
    public void shouldReturnMovie() {
        Movie res = movieDao.getMovieById(1);
        System.out.println(res);
        assertNotNull(res);
    }
}