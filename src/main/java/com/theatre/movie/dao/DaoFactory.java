package com.theatre.movie.dao;

import com.theatre.movie.dao.impl.BookingDaoImpl;
import com.theatre.movie.dao.impl.MovieDaoImpl;
import com.theatre.movie.dao.impl.MovieSessionDaoImpl;
import com.theatre.movie.dao.impl.UserDaoImpl;
import lombok.Getter;

@Getter
public class DaoFactory {
    private static final MovieDao MOVIE_DAO = new MovieDaoImpl();
    private static final MovieSessionDao MOVIE_SESSION_DAO = new MovieSessionDaoImpl();
    private static final UserDao USER_DAO = new UserDaoImpl();
    private static final BookingDao BOOKING_DAO = new BookingDaoImpl();

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
}
