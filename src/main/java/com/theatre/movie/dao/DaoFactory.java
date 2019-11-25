package com.theatre.movie.dao;

import com.theatre.movie.dao.impl.*;
import com.theatre.movie.persistence.ConnectionFactory;
import com.theatre.movie.persistence.DataSourceConnectionFactoryWithPool;
import lombok.Getter;

@Getter
public class DaoFactory {
    private static final ConnectionFactory CONNECTION_FACTORY = DataSourceConnectionFactoryWithPool.getInstance();


    private static final MovieDao MOVIE_DAO = new MovieDaoImpl(CONNECTION_FACTORY);
    private static final MovieSessionDao MOVIE_SESSION_DAO = new MovieSessionDaoImpl(CONNECTION_FACTORY);
    private static final UserDao USER_DAO = new UserDaoImpl(CONNECTION_FACTORY);
    private static final BookingDao BOOKING_DAO = new BookingDaoImpl(CONNECTION_FACTORY);
    private static final HallDao HALL_DAO = new HallDaoImpl(CONNECTION_FACTORY);

    public static MovieDao getMovieDao() {
        return MOVIE_DAO;
    }

    public static MovieSessionDao getMovieSessionDao() {
        return MOVIE_SESSION_DAO;
    }

    public static UserDao getUserDao() {
        return USER_DAO;
    }

    public static BookingDao getBookingDao() {
        return BOOKING_DAO;
    }

    public static HallDao getHallDao() {
        return HALL_DAO;
    }
}
