package com.theatre.movie.service;

import com.theatre.movie.dao.DaoFactory;
import com.theatre.movie.persistence.DataSourceConnectionFactoryWithPool;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import com.theatre.movie.persistence.transaction.TransactionManager;


public class ServiceFactory {
    private static final TransactionManager TRANSACTION_MANAGER = new TransactionManager(
            DataSourceConnectionFactoryWithPool.getInstance()
    );
    private static final TransactionHandler TRANSACTION_HANDLER = new TransactionHandler(TRANSACTION_MANAGER);

    private static final MovieSessionService MOVIE_SESSION_SERVICE = new MovieSessionService(
            DaoFactory.getMovieSessionDao(),
            DaoFactory.getBookingDao(),
            DaoFactory.getHallDao(),
            DaoFactory.getMovieDao(),
            TRANSACTION_HANDLER
    );
    private static final WeekScheduleDatesService WEEK_SCHEDULE_DATES_SERVICE = new WeekScheduleDatesService();
    private static final UserService USER_SERVICE = new UserService(DaoFactory.getUserDao());
    private static final BookingService BOOKING_SERVICE = new BookingService(DaoFactory.getBookingDao(), TRANSACTION_HANDLER);
    private static final HallService HALL_SERVICE = new HallService(DaoFactory.getHallDao());
    private static final MovieService MOVIE_SERVICE = new MovieService(
            DaoFactory.getMovieDao(),
            DaoFactory.getBookingDao(),
            DaoFactory.getMovieSessionDao(),
            TRANSACTION_HANDLER
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
