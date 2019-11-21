package com.theatre.movie.dao.impl;

import com.theatre.movie.dao.EntityMapper;
import com.theatre.movie.entity.*;

import static com.theatre.movie.dao.impl.DbTablesConstants.*;

public class EntityMapperProvider {

    public static final EntityMapper<MovieSession> MOVIE_SESSION_SIMPLE_MAPPER = rs -> {
        Movie movie = new Movie(
                rs.getString(DbTablesConstants.MovieTable.TITLE),
                rs.getString(DbTablesConstants.MovieTable.DIRECTED_BY),
                rs.getInt(DbTablesConstants.MovieTable.DURATION_MIN));
        movie.setMovieId(rs.getInt(MovieTable.MOVIE_ID));
        movie.setDescription(rs.getString(DbTablesConstants.MovieTable.DESCRIPTION));
        movie.setTrailerUrl(rs.getString(DbTablesConstants.MovieTable.TRAILER_URL));
        movie.setBackgroundImgUrl(rs.getString(DbTablesConstants.MovieTable.BACKGROUND_IMG_URL));
        movie.setCoverImgUrl(rs.getString(DbTablesConstants.MovieTable.COVER_IMG_URL));

        MovieSession session = new MovieSession(
                movie,
                null,
                rs.getTimestamp(DbTablesConstants.MovieSessionTable.START_AT).toLocalDateTime(),
                rs.getDouble(DbTablesConstants.MovieSessionTable.PRICE));
        session.setSessionId(rs.getInt(DbTablesConstants.MovieSessionTable.SESSION_ID));
        return session;
    };

    public static final EntityMapper<Seat> SEAT_ENTITY_MAPPER = rs -> {
        Seat seat = new Seat(
                rs.getInt(SeatTable.SEAT_ROW),
                rs.getInt(SeatTable.PLACE));
        seat.setSeatId(rs.getInt(SeatTable.SEAT_ID));
        return seat;
    };

    public static final EntityMapper<Hall> HALL_ENTITY_MAPPER = rs -> {
        Hall hall = new Hall(
                rs.getString(HallTable.NAME));
        hall.setHallId(rs.getInt(HallTable.HALL_ID));
        return hall;
    };

    public static final EntityMapper<Movie> MOVIE_ENTITY_MAPPER = rs -> {
        Movie movie = new Movie(
                rs.getString(MovieTable.TITLE),
                rs.getString(MovieTable.DIRECTED_BY),
                rs.getInt(MovieTable.DURATION_MIN));
        movie.setMovieId(rs.getInt(MovieTable.MOVIE_ID));
        movie.setDescription(rs.getString(MovieTable.DESCRIPTION));
        movie.setTrailerUrl(rs.getString(MovieTable.TRAILER_URL));
        movie.setBackgroundImgUrl(rs.getString(MovieTable.BACKGROUND_IMG_URL));
        movie.setCoverImgUrl(rs.getString(MovieTable.COVER_IMG_URL));
        return movie;
    };

    public static final EntityMapper<User> USER_ENTITY_MAPPER = rs -> {
        User user = new User(
                rs.getString(UserTable.LOGIN),
                rs.getString(UserTable.PASSWORD),
                rs.getString(UserTable.EMAIL),
                Role.valueOf(rs.getString(UserTable.ROLE)));
        user.setId(rs.getInt(UserTable.USER_ID));
        return user;
    };

    public static final EntityMapper<Booking> BOOKING_ENTITY_MAPPER = rs -> {
        Booking booking = new Booking(rs.getTimestamp(BookingTable.CREATED_AT).toLocalDateTime(),
                rs.getInt(BookingTable.USER_ID),
                rs.getInt(BookingTable.SEAT_ID),
                rs.getInt(BookingTable.MOVIE_SESSION_ID));

        booking.setBookingId(rs.getInt(BookingTable.BOOKING_ID));
        booking.setBookingStatus(BookingStatus.valueOf(rs.getString(BookingTable.STATUS)));
        return booking;
    };
}
