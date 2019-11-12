package com.reni.hi.dao;

import com.reni.hi.dao.impl.MovieDaoImpl;
import com.reni.hi.entity.Movie;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieDaoImplTest {
    private CrudDao<Movie> movieDao;

    @Before
    public void setUp() {
        movieDao = new MovieDaoImpl();
    }


    @Test
    public void shouldReturnMovie() {
        Movie res = movieDao.getById(1);
        System.out.println(res);
        assertNotNull(res);
    }
}