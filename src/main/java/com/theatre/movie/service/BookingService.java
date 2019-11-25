package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.entity.Booking;
import com.theatre.movie.web.dto.CreateBookingRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

@AllArgsConstructor
public class BookingService {
    private static final Logger LOG = Logger.getLogger(BookingService.class);
    private BookingDao bookingDao;

    public void createBooking(CreateBookingRequestDto createBookingRequest) {

        for (String seatId : createBookingRequest.getBookedSeatsId()) {
            Booking booking = new Booking(LocalDateTime.now(), createBookingRequest.getUserId(),
                    Integer.parseInt(seatId),
                    createBookingRequest.getMovieSessionId()
            );
            Booking createdBooking = bookingDao.create(booking);
            LOG.info("Created new booking: \n" + createdBooking);
        }
    }
}
