package com.theatre.movie.dao;

import com.theatre.movie.dao.impl.*;
import lombok.Getter;

@Getter
public class DaoFactory {
    private static final MovieDao MOVIE_DAO = new MovieDaoImpl();
    private static final MovieSessionDao MOVIE_SESSION_DAO = new MovieSessionDaoImpl();
    private static final UserDao USER_DAO = new UserDaoImpl();
    private static final BookingDao BOOKING_DAO = new BookingDaoImpl();
    private static final HallDao HALL_DAO = new HallDaoImpl();

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
