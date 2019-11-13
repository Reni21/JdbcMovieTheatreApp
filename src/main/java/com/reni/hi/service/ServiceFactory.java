package com.reni.hi.service;

import com.reni.hi.dao.DaoFactory;


public class ServiceFactory {

    private static final MovieSessionService movieSessionService = new MovieSessionService(
            DaoFactory.getMovieSessionDao(), DaoFactory.getMovieDao()
    );
    private static final WeekScheduleDatesService weekScheduleDatesService = new WeekScheduleDatesService();


    public static MovieSessionService getMovieSessionService() {
        return movieSessionService;
    }
    public static WeekScheduleDatesService getWeekScheduleDatesService() {
        return weekScheduleDatesService;
    }

}
