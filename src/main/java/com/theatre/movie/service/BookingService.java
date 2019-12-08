package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.dto.BookingViewDto;
import com.theatre.movie.entity.Booking;
import com.theatre.movie.entity.MovieSession;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import com.theatre.movie.web.dto.CreateBookingRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class BookingService {
    private static final Logger LOG = Logger.getLogger(BookingService.class);
    private BookingDao bookingDao;
    private MovieSessionDao movieSessionDao;
    private TransactionHandler transactionHandler;

    public void createBooking(CreateBookingRequestDto dto) {
        validateCreateBookingRequest(dto);
        transactionHandler.runInTransaction(() -> {
            for (String seatId : dto.getBookedSeatsId()) {
                Booking booking = new Booking(
                        LocalDateTime.now(),
                        dto.getUserId(),
                        Integer.parseInt(seatId),
                        Integer.parseInt(dto.getMovieSessionId())
                );
                Booking createdBooking = bookingDao.create(booking);
                LOG.info("Created new booking: \n" + createdBooking);
            }
        });
    }

    public List<BookingViewDto> getActualUsersBookingById(int userId) {
        LOG.info("Get actual booking for user id=" + userId);
        return bookingDao.getAllActualBookingByUserId(userId);
    }

    private void validateCreateBookingRequest(CreateBookingRequestDto dto) {
        if (dto.getMovieSessionId() == null || dto.getBookedSeatsId() == null || dto.getBookedSeatsId().length == 0) {
            throw new IllegalArgumentException("Booked seats is empty");
        }
        MovieSession movieSession = movieSessionDao.getById(Integer.parseInt(dto.getMovieSessionId()));
        if (movieSession == null) {
            throw new IllegalArgumentException("Movie session with id " + dto.getMovieSessionId() + " does not exist");
        }
    }
}
