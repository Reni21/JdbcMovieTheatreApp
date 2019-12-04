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
import com.theatre.movie.exception.CanNotRemoveMovieScheduleException;
import com.theatre.movie.exception.InvalidScheduleDateException;
import com.theatre.movie.exception.MovieSessionCreationException;
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
import java.util.stream.Collectors;

@AllArgsConstructor
public class MovieSessionService {
    private static final Logger LOG = Logger.getLogger(MovieSessionService.class);

    private MovieSessionDao movieSessionDao;
    private BookingDao bookingDao;
    private HallDao hallDao;
    private MovieDao movieDao;

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

    private void checkScheduleDateInValidRange(LocalDate date) throws InvalidScheduleDateException {
        LocalDate now = LocalDate.now();
        if (date.isBefore(now) || date.isAfter(now.plusDays(7))) {
            throw new InvalidScheduleDateException(
                    "Date " + date.format(DateTimeFormatter.ISO_DATE) + " is in wrong range");
        }
    }

    private List<MovieSessionTimeViewDto> mapMovieSessionTimeDtos(List<MovieSession> movieSessions) {
        return movieSessions.stream()
                .map(session -> new MovieSessionTimeViewDto(
                        session.getSessionId(),
                        session.getStartAt().toLocalTime()))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<BookedSeatViewDto>> mapBookedSeats(List<Seat> allSeats, Set<Integer> bookedSeats) {
        return allSeats.stream().map(seat -> {
            BookedSeatViewDto dto = new BookedSeatViewDto(seat.getSeatId(), seat.getRow(), seat.getPlace());
            if (bookedSeats.contains(seat.getSeatId())) {
                dto.setBooked(true);
            }
            return dto;
        }).collect(Collectors.groupingBy(BookedSeatViewDto::getRow));
    }

    public MovieSession addMovieSession(CreateMovieSessionRequestDto movieSessionDto) throws MovieSessionCreationException {
        LOG.info("Create new movie session for data: " + movieSessionDto);
        validateMovieSessionRequestDto(movieSessionDto);


        LocalDate date = LocalDate.parse(movieSessionDto.getDate(), DateTimeFormatter.ISO_DATE);
        LocalTime time = LocalTime.of(Integer.parseInt(movieSessionDto.getHours()), Integer.parseInt(movieSessionDto.getMinutes()));
        LocalDateTime startAt = LocalDateTime.of(date, time);
        MovieSession movieSession = new MovieSession(
                Integer.parseInt(movieSessionDto.getMovieId()),
                Integer.parseInt(movieSessionDto.getHallId()),
                startAt,
                Double.parseDouble(movieSessionDto.getPrice()));
        LOG.info("Create movieSession with id=" + movieSession.getSessionId());
        return movieSessionDao.create(movieSession);
    }

    private void validateMovieSessionRequestDto(CreateMovieSessionRequestDto movieSessionDto) throws MovieSessionCreationException {
        //throw new MovieSessionCreationException("Oopd");
    }

    // transactional
    public void deleteMovieSessionById(List<Integer> sessionsIds) throws CanNotRemoveMovieScheduleException {
        for (Integer id : sessionsIds) {
            boolean res = bookingDao.isBookingForMovieSessionExist(id);
            if(res){
                throw new CanNotRemoveMovieScheduleException("Movie card can not be deleted. Some sessions already have reservations.");
            }
        }
        sessionsIds.forEach(movieSessionDao::remove);
    }
}
