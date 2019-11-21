package com.theatre.movie.dao;

import com.theatre.movie.dao.impl.MovieDaoImpl;
import com.theatre.movie.entity.Movie;
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
        assertNotNull(res);
    }
}