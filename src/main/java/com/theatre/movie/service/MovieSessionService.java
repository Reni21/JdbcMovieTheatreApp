package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.HallDao;
import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.dto.BookedSeatViewDto;
import com.theatre.movie.dto.MovieSessionTimeViewDto;
import com.theatre.movie.dto.MovieSessionViewDto;
import com.theatre.movie.dto.MovieSessionsScheduleViewDto;
import com.theatre.movie.entity.Hall;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.MovieSession;
import com.theatre.movie.entity.Seat;
import com.theatre.movie.exception.InvalidScheduleDateException;
import com.theatre.movie.exception.MovieScheduleRemovalException;
import com.theatre.movie.exception.MovieSessionCreationException;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import com.theatre.movie.web.dto.CreateMovieSessionRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * The {@code MovieSessionService} class provides methods for manage information about all movies sessions
 * represented by {@link com.theatre.movie.entity.MovieSession} class
 * Properties: <b>bookingDao</b>, <b>hallDao</b>,
 * <b>movieDao</b>, <b>transactionHandler</b>, <b>MovieSessionService.BREAK_DURATION</b>
 *
 * @author Hlushchenko Renata
 * @see com.theatre.movie.dao.MovieDao
 * @see com.theatre.movie.dao.BookingDao
 * @see com.theatre.movie.dao.MovieSessionDao
 * @see com.theatre.movie.persistence.transaction.TransactionHandler
 */

@AllArgsConstructor
public class MovieSessionService {
    private static final Logger LOG = Logger.getLogger(MovieSessionService.class);
    /**
     * Stores break interval between movie sessions
     */
    private static final int BREAK_DURATION = 15;

    private MovieSessionDao movieSessionDao;
    private BookingDao bookingDao;
    private HallDao hallDao;
    private MovieDao movieDao;
    private TransactionHandler transactionHandler;

    public MovieSessionViewDto getMovieSessionById(int id) {
        MovieSession movieSession = movieSessionDao.getById(id);
        Set<Integer> bookedSeats = bookingDao.getAllBookedSeatsIdByMovieSessionId(movieSession.getSessionId());
        Hall hall = hallDao.getHallById(movieSession.getHallId());
        Movie movie = movieDao.getById(movieSession.getMovieId());
        Map<Integer, List<BookedSeatViewDto>> seats = mapBookedSeats(hall.getSeats(), bookedSeats);
        MovieSessionViewDto dto = new MovieSessionViewDto(
                movieSession,
                movie.getTitle(),
                movie.getDurationMinutes(),
                hall.getHallName(), seats
        );
        dto.setBookedSeatsCount(bookedSeats.size());
        return dto;
    }

    /**
     * The method collect and return movie sessions for required day
     *
     * @param date - use for determine the limit for search in db, indicates day for matching with
     *             <tt>startAt</tt> property in {@link com.theatre.movie.entity.MovieSession} class
     * @return list of Dtos - stores part of information about all movie sessions for required day
     * @throws InvalidScheduleDateException from {@link #checkScheduleDateInValidRange(LocalDate date)}
     */
    public List<MovieSessionsScheduleViewDto> getMovieSessionsScheduleForDate(LocalDate date)
            throws InvalidScheduleDateException {

        checkScheduleDateInValidRange(date);

        LocalDateTime searchFrom = LocalDate.now().equals(date) ? date.atTime(LocalTime.now()) : date.atStartOfDay();
        LocalDateTime searchTo = date.atTime(LocalTime.MAX);

        LOG.info("Sessions search start from: " + searchFrom + " to: " + searchTo);
        List<MovieSession> sessions = movieSessionDao.getAllInRange(searchFrom, searchTo);
        Map<Integer, List<MovieSession>> sessionsByMovie = sessions.stream()
                .collect(Collectors.groupingBy(MovieSession::getMovieId));

        return sessionsByMovie.entrySet().stream()
                .map(movieListEntry -> {
                    Integer movieId = movieListEntry.getKey();
                    Movie movie = movieDao.getById(movieId);
                    MovieSessionsScheduleViewDto scheduleDto = new MovieSessionsScheduleViewDto(movie.getTitle(), movie.getDurationMinutes());
                    scheduleDto.setMovieId(movieId);
                    scheduleDto.setTrailerUrl(movie.getTrailerUrl());
                    scheduleDto.setBackgroundImgPath(movie.getBackgroundImgUrl());
                    scheduleDto.setCoverImgPath(movie.getCoverImgUrl());
                    List<MovieSessionTimeViewDto> timeDtos = mapMovieSessionTimeDtos(movieListEntry.getValue());
                    scheduleDto.setMovieSessionTimes(timeDtos);
                    return scheduleDto;
                }).collect(Collectors.toList());
    }

    public MovieSession addMovieSession(CreateMovieSessionRequestDto movieSessionDto) throws MovieSessionCreationException {
        LOG.info("Create new movie session for data: " + movieSessionDto);
        validateMovieSessionRequest(movieSessionDto);

        LocalDateTime startAt = getSessionStartAt(movieSessionDto);
        if (startAt.isBefore(LocalDateTime.now())) {
            throw new MovieSessionCreationException("Required time already passed.");
        }

        MovieSession movieSession = new MovieSession(
                Integer.parseInt(movieSessionDto.getMovieId()),
                Integer.parseInt(movieSessionDto.getHallId()),
                startAt,
                Double.parseDouble(movieSessionDto.getPrice()));
        LOG.info("Create movieSession with id=" + movieSession.getSessionId());
        return movieSessionDao.create(movieSession);
    }

    public void deleteMovieSessionByIds(List<Integer> sessionsIds) throws MovieScheduleRemovalException {
        transactionHandler.runInTransaction(() -> {
            for (Integer id : sessionsIds) {
                boolean bookingExists = bookingDao.isBookingForMovieSessionExist(id);
                if (bookingExists) {
                    throw new MovieScheduleRemovalException("Movie card can not be deleted. Some sessions already have reservations.");
                }
            }
            sessionsIds.forEach(movieSessionDao::remove);
        });
    }

    /**
     * @param date - {@link #getMovieSessionsScheduleForDate(LocalDate date)}
     * @throws InvalidScheduleDateException when required day is out of range:
     *                                      it less then today and more then 7 days past from today
     */
    private void checkScheduleDateInValidRange(LocalDate date) throws InvalidScheduleDateException {
        LocalDate now = LocalDate.now();
        if (date.isBefore(now) || date.isAfter(now.plusDays(7))) {
            throw new InvalidScheduleDateException(
                    "Date " + date.format(DateTimeFormatter.ISO_DATE) + " is in wrong range");
        }
    }

    /**
     * The method create list of <tt>MovieSessionTimeViewDto</tt> which used on
     * schedule.jsp & schedule-admin.jsp view pages as set of tags link and attached to one movie card
     *
     * @param movieSessions - initial data from which only the necessary will be selected for displaying on view
     * @return list of dtos - stores all current session hours for one movie card
     */
    private List<MovieSessionTimeViewDto> mapMovieSessionTimeDtos(List<MovieSession> movieSessions) {
        return movieSessions.stream()
                .map(session -> new MovieSessionTimeViewDto(
                        session.getSessionId(),
                        session.getStartAt().toLocalTime()))
                .collect(Collectors.toList());
    }

    /**
     * Compares the data of all seats in the hall with the booked seats and combines them into
     * Map<Integer, List<BookedSeatViewDto>> for displaying on view page
     *
     * @param allSeats    - list of all seats of required hall
     * @param bookedSeats - list of seats of required hall that already booked
     * @return - data for displaying seats of hall on seats-booking.jsp view page
     */
    private Map<Integer, List<BookedSeatViewDto>> mapBookedSeats(List<Seat> allSeats, Set<Integer> bookedSeats) {
        return allSeats.stream().map(seat -> {
            BookedSeatViewDto dto = new BookedSeatViewDto(seat.getSeatId(), seat.getRow(), seat.getPlace());
            if (bookedSeats.contains(seat.getSeatId())) {
                dto.setBooked(true);
            }
            return dto;
        }).collect(Collectors.groupingBy(BookedSeatViewDto::getRow));
    }


    private void validateMovieSessionRequest(CreateMovieSessionRequestDto dto) throws MovieSessionCreationException {

        if (dto.getMovieId() == null
                || dto.getHallId() == null
                || dto.getDate() == null
                || dto.getHours() == null
                || dto.getMinutes() == null
                || dto.getPrice() == null) {
            throw new MovieSessionCreationException("Required fields are empty");
        }
        Movie movie = movieDao.getById(Integer.parseInt(dto.getMovieId()));
        if (movie == null) {
            throw new MovieSessionCreationException("Movie with id " + dto.getMovieId() + " does not exist");
        }


        validateMovieSessionTimeRange(dto, movie.getDurationMinutes());
    }

    private void validateMovieSessionTimeRange(CreateMovieSessionRequestDto dto, int newMovieDuration) throws MovieSessionCreationException {
        LocalDateTime newMovieStartAt = getSessionStartAt(dto);

        LocalDateTime from = newMovieStartAt.toLocalDate().atStartOfDay();
        LocalDateTime to = newMovieStartAt.plusMinutes(newMovieDuration + BREAK_DURATION);

        List<MovieSession> movieSessions = movieSessionDao.getAllInRange(from, to);

        StringJoiner errors = new StringJoiner(";");

        for (MovieSession movieSession : movieSessions) {
            int movieDuration = movieDao.getById(movieSession.getMovieId()).getDurationMinutes();
            LocalDateTime movieStartAt = movieSession.getStartAt();

            if (newMovieStartAt.isEqual(movieStartAt)
                    || (newMovieStartAt.isAfter(movieStartAt) && newMovieStartAt.isBefore(movieStartAt.plusMinutes(movieDuration + BREAK_DURATION)))
                    || (newMovieStartAt.isBefore(movieStartAt) && newMovieStartAt.plusMinutes(newMovieDuration + BREAK_DURATION).isAfter(movieStartAt))) {
                errors.add(String.format("%s duration %d", movieStartAt.format(DateTimeFormatter.ofPattern("HH:mm")), movieDuration));
            }
        }

        if (errors.length() != 0) {
            throw new MovieSessionCreationException("Conflict session times: " + errors.toString());
        }

    }

    /**
     * The method convert string data of field <tt>hours</tt> and <tt>minutes</tt>
     * from {@link com.theatre.movie.web.dto.CreateMovieSessionRequestDto}
     * to {@link java.time.LocalDateTime}
     */
    private LocalDateTime getSessionStartAt(CreateMovieSessionRequestDto dto) {
        LocalDate date = LocalDate.parse(dto.getDate(), DateTimeFormatter.ISO_DATE);
        LocalTime time = LocalTime.of(Integer.parseInt(dto.getHours()), Integer.parseInt(dto.getMinutes()));
        return LocalDateTime.of(date, time);
    }

}
