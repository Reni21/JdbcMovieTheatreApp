package com.theatre.movie.service;

import com.theatre.movie.dao.DaoFactory;


public class ServiceFactory {

    private static final MovieSessionService MOVIE_SESSION_SERVICE = new MovieSessionService(
            DaoFactory.getMovieSessionDao(),
            DaoFactory.getBookingDao(),
            DaoFactory.getHallDao(),
            DaoFactory.getMovieDao()
    );
    private static final WeekScheduleDatesService WEEK_SCHEDULE_DATES_SERVICE = new WeekScheduleDatesService();
    private static final UserService USER_SERVICE = new UserService(DaoFactory.getUserDao());
    private static final BookingService BOOKING_SERVICE = new BookingService(DaoFactory.getBookingDao());
    private static final HallService HALL_SERVICE = new HallService(DaoFactory.getHallDao());

    public static MovieSessionService getMovieSessionService() {
        return MOVIE_SESSION_SERVICE;
    }

    public static WeekScheduleDatesService getWeekScheduleDatesService() {
        return WEEK_SCHEDULE_DATES_SERVICE;
    }

    public static UserService getUserService() {
        return USER_SERVICE;
    }

    public static BookingService getBookingService() {
        return BOOKING_SERVICE;
    }

    public static HallService getHallService() {
        return HALL_SERVICE;
    }
}
