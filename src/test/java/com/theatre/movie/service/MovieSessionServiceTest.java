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
import com.theatre.movie.exception.MovieSessionCreationException;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import com.theatre.movie.web.dto.CreateMovieSessionRequestDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class MovieSessionServiceTest {
    private MovieSessionService instance;
    private MovieSessionDao movieSessionDao = mock(MovieSessionDao.class);
    private BookingDao bookingDao = mock(BookingDao.class);
    private HallDao hallDao = mock(HallDao.class);
    private MovieDao movieDao = mock(MovieDao.class);
    private TransactionHandler transactionHandler = mock(TransactionHandler.class);

    @Before
    public void setUp() {
        instance = new MovieSessionService(movieSessionDao, bookingDao, hallDao, movieDao, transactionHandler);
    }

    @Test
    public void shouldGetMovieSessionById() {
        int movieSessionId = 3;

        LocalDateTime startAt = LocalDateTime.now();
        MovieSession movieSession = createMovieSession(movieSessionId, startAt);
        when(movieSessionDao.getById(movieSessionId)).thenReturn(movieSession);

        Set<Integer> bookedSeats = new HashSet<>();
        bookedSeats.add(18);

        when(bookingDao.getAllBookedSeatsIdByMovieSessionId(movieSessionId)).thenReturn(bookedSeats);

        Hall hall = new Hall("red");
        hall.setHallId(movieSession.getHallId());
        Seat seat = new Seat(7, 8);
        seat.setSeatId(18);
        hall.addSeat(seat);

        when(hallDao.getHallById(movieSession.getHallId())).thenReturn(hall);

        Movie movie = createMovie(movieSession.getMovieId());
        when(movieDao.getById(movieSession.getMovieId())).thenReturn(movie);

        MovieSessionViewDto actual = instance.getMovieSessionById(movieSessionId);
        MovieSessionViewDto expected = expectedMovieSessionViewDto(movieSession);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetMovieSessionsScheduleForDate() throws InvalidScheduleDateException {
        LocalDate date = LocalDate.of(2019, 12, 14);

        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.atTime(LocalTime.MAX);

        int movieId = 4;
        MovieSession movieSession = createMovieSession(3, from);
        List<MovieSession> movieSessions = Collections.singletonList(movieSession);

        when(movieSessionDao.getAllInRange(from, to)).thenReturn(movieSessions);

        Movie movie = createMovie(movieSession.getMovieId());
        when(movieDao.getById(movieId)).thenReturn(movie);

        List<MovieSessionsScheduleViewDto> actual = instance.getMovieSessionsScheduleForDate(date);

        List<MovieSessionsScheduleViewDto> expected = expectedMovieSessionScheduleDtos(movieId, movieSession, movie);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldFailsWhenGetMovieSessionsScheduleForWrongDate() {
        LocalDate wrongDate = LocalDate.of(2019, 1, 1);

        assertThatThrownBy(() -> instance.getMovieSessionsScheduleForDate(wrongDate))
                .isInstanceOf(InvalidScheduleDateException.class)
                .hasMessageContaining("Date 2019-01-01 is in wrong range");
    }

    @Test
    public void shouldAddMovieSession() throws MovieSessionCreationException {
        CreateMovieSessionRequestDto dto = new CreateMovieSessionRequestDto(
                "3", "4", "2019-12-14", "11", "12", "100"
        );

        when(movieDao.getById(3)).thenReturn(createMovie(3));

        instance.addMovieSession(dto);

        MovieSession expected = new MovieSession(3, 4,
                LocalDateTime.of(LocalDate.of(2019, 12, 14), LocalTime.of(11, 12)), 100.0);

        verify(movieSessionDao).create(expected);
    }

    @Test
    public void shouldFailAddMovieSessionOfNotExistingMovie() {
        CreateMovieSessionRequestDto dto = new CreateMovieSessionRequestDto(
                "3", "4", "2019-12-14", "11", "12", "100"
        );

        assertThatThrownBy(() -> instance.addMovieSession(dto))
                .isInstanceOf(MovieSessionCreationException.class)
                .hasMessageContaining("Movie with id 3 does not exist");
    }


    private MovieSessionViewDto expectedMovieSessionViewDto(MovieSession movieSession) {
        Map<Integer, List<BookedSeatViewDto>> bookedSeatsDto = new HashMap<>();
        BookedSeatViewDto bookedSeatViewDto = new BookedSeatViewDto(18, 7, 8);
        bookedSeatViewDto.setBooked(true);
        bookedSeatsDto.put(7, Collections.singletonList(bookedSeatViewDto));
        MovieSessionViewDto expected = new MovieSessionViewDto(movieSession, "Titanic", 120, "red", bookedSeatsDto);
        expected.setBookedSeatsCount(1);
        return expected;
    }

    private Movie createMovie(int movieId) {
        Movie movie = new Movie("Titanic", "Cameron", 120);
        movie.setMovieId(movieId);
        movie.setBackgroundImgUrl("backgroundImgUrl");
        movie.setCoverImgUrl("coverImgUrl");
        movie.setTrailerUrl("trailerUrl");
        movie.setDescription("description");
        return movie;
    }

    private MovieSession createMovieSession(int movieSessionId, LocalDateTime startAt) {
        MovieSession movieSession = new MovieSession(4, 5, startAt, 100.0);
        movieSession.setSessionId(movieSessionId);
        return movieSession;
    }

    private List<MovieSessionsScheduleViewDto> expectedMovieSessionScheduleDtos(int movieId, MovieSession movieSession, Movie movie) {
        MovieSessionsScheduleViewDto scheduleDto = new MovieSessionsScheduleViewDto(movie.getTitle(), movie.getDurationMinutes());
        scheduleDto.setMovieId(movieId);
        scheduleDto.setTrailerUrl(movie.getTrailerUrl());
        scheduleDto.setCoverImgPath(movie.getCoverImgUrl());
        scheduleDto.setBackgroundImgPath(movie.getBackgroundImgUrl());
        List<MovieSessionTimeViewDto> timeDtos = Collections.singletonList(
                new MovieSessionTimeViewDto(movieSession.getSessionId(), movieSession.getStartAt().toLocalTime())
        );
        scheduleDto.setMovieSessionTimes(timeDtos);
        return Collections.singletonList(scheduleDto);
    }
}