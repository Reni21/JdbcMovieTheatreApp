package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.entity.Booking;
import com.theatre.movie.entity.User;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

@AllArgsConstructor
public class BookingService {
    private static final Logger LOG = Logger.getLogger(BookingService.class);
    private BookingDao bookingDao;

    public void createBooking(User user, int movieSessionId, String[] bookedSeatsId) {

        for (String seatId : bookedSeatsId) {
            Booking dto = new Booking(LocalDateTime.now(), user.getId(), Integer.parseInt(seatId), movieSessionId);
            int bookingId = bookingDao.create(dto);
            dto.setBookingId(bookingId);
            LOG.info("Created new booking: \n" + dto);
        }
    }
}
