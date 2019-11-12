package com.reni.hi.dao;

import com.reni.hi.dao.impl.MovieDaoImpl;
import com.reni.hi.dao.impl.MovieSessionDaoImpl;
import com.reni.hi.entity.Movie;

public class DaoFactory {
    private static final CrudDao<Movie> movieDao = new MovieDaoImpl();
    private static final MovieSessionDao movieSessionDao = new MovieSessionDaoImpl();

    public static CrudDao<Movie> getMovieDao() {
        return movieDao;
    }

    public static MovieSessionDao getMovieSessionDao() {
        return movieSessionDao;
    }
}
