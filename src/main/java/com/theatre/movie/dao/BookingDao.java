package com.theatre.movie.dao;

import com.theatre.movie.dto.BookingViewDto;
import com.theatre.movie.entity.Booking;

import java.util.List;
import java.util.Set;

public interface BookingDao {

    Set<Integer> getAllBookedSeatsIdByMovieSessionId(int movieSessionId);

    List<BookingViewDto> getAllActualBookingByUserId(int userId);

    Booking create(Booking booking);

    boolean isBookingForMovieSessionExist(Integer movieSessionId);
}
