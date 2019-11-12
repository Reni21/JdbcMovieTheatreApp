package com.reni.hi.service;

import com.reni.hi.dao.DaoFactory;


public class ServiceFactory {

    private static final MovieSessionService movieSessionService = new MovieSessionService(
            DaoFactory.getMovieSessionDao(), DaoFactory.getMovieDao()
    );

    public static MovieSessionService getMovieSessionService() {
        return movieSessionService;
    }

}
