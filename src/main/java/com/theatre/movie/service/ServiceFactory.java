package com.theatre.movie.service;

import com.theatre.movie.dao.DaoFactory;

/**
 * The {@code ServiceFactory} class provides methods for provide
 * services for working with DAO
 * Properties: <b>ServiceFactory.MOVIE_SESSION_SERVICE</b>, <b>ServiceFactory.WEEK_SCHEDULE_DATES_SERVICE</b>,
 * <b>ServiceFactory.USER_SERVICE</b>, <b>ServiceFactory.BOOKING_SERVICE</b>,
 * <b>ServiceFactory.HALL_SERVICE</b>, <b>ServiceFactory.MOVIE_SERVICE</b>
 *
 * @author Hlushchenko Renata
 * @see com.theatre.movie.service.MovieSessionService
 * @see com.theatre.movie.service.WeekScheduleDatesService
 * @see com.theatre.movie.service.UserService
 * @see com.theatre.movie.service.BookingService
 * @see com.theatre.movie.service.HallService
 * @see com.theatre.movie.service.MovieService
 */

public class ServiceFactory {

    private static final MovieSessionService MOVIE_SESSION_SERVICE = new MovieSessionService(
            DaoFactory.getMovieSessionDao(),
            DaoFactory.getBookingDao(),
            DaoFactory.getHallDao(),
            DaoFactory.getMovieDao(),
            TransactionHandlerFactory.getTransactionHandler()
    );
    private static final WeekScheduleDatesService WEEK_SCHEDULE_DATES_SERVICE = new WeekScheduleDatesService();
    private static final UserService USER_SERVICE = new UserService(
            DaoFactory.getUserDao());
    private static final BookingService BOOKING_SERVICE = new BookingService(
            DaoFactory.getBookingDao(),
            DaoFactory.getMovieSessionDao(),
            TransactionHandlerFactory.getTransactionHandler());
    private static final HallService HALL_SERVICE = new HallService(
            DaoFactory.getHallDao());
    private static final MovieService MOVIE_SERVICE = new MovieService(
            DaoFactory.getMovieDao(),
            DaoFactory.getBookingDao(),
            DaoFactory.getMovieSessionDao(),
            TransactionHandlerFactory.getTransactionHandler()
    );

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

    public static MovieService getMovieService() {
        return MOVIE_SERVICE;
    }
}
