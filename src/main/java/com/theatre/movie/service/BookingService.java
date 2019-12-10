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

/**
 * The {@code BookingService} class provides methods for manage user bookings/tickets purchases
 * represented by {@link com.theatre.movie.entity.Booking} class
 * Properties: <b>bookingDao</b>, <b>movieSessionDao</b>, <b>transactionHandler</b>
 *
 * @author Hlushchenko Renata
 * @see com.theatre.movie.dao.BookingDao
 * @see com.theatre.movie.dao.MovieSessionDao
 * @see com.theatre.movie.persistence.transaction.TransactionHandler
 */

@AllArgsConstructor
public class BookingService {
    private static final Logger LOG = Logger.getLogger(BookingService.class);
    private BookingDao bookingDao;
    private MovieSessionDao movieSessionDao;
    private TransactionHandler transactionHandler;

    /**
     * @param dto - is used for data transfer for book tickets request
     *            represented by {@link com.theatre.movie.web.dto.CreateBookingRequestDto} class
     * @see TransactionHandler#runInTransaction(Runnable runnable)
     */
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

    /**
     * Return information about all users purchases, which has movie session
     * {@link com.theatre.movie.entity.MovieSession} with startAt property >= current time now
     *
     * @param userId - user id, is used for search data in db
     * @return list of Dtos - stores information about booking which will be displayed
     * for registered user on user-tickets.jsp
     */
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
