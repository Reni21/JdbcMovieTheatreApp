package com.theatre.movie.dao;

import com.theatre.movie.entity.Booking;

import java.util.Set;

public interface BookingDao {

//    List<Booking> getAllByMovieSessionId(int movieSessionId);

    Set<Integer> getAllBookedSeatsIdByMovieSessionId(int movieSessionId);

//    List<Booking> getAllActualByUserId(int userId);

    int create(Booking booking);
}
