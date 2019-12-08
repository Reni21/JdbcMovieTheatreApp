package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.PaginatedData;
import com.theatre.movie.exception.MovieCreationException;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import com.theatre.movie.web.dto.CreateMovieRequestDto;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    private MovieService instance;
    private BookingDao bookingDao = mock(BookingDao.class);
    private MovieDao movieDao = mock(MovieDao.class);
    private MovieSessionDao movieSessionDao = mock(MovieSessionDao.class);
    private TransactionHandler transactionHandler = mock(TransactionHandler.class);

    @Before
    public void setUp() {
        instance = new MovieService(movieDao, bookingDao, movieSessionDao, transactionHandler);
    }

    @Test
    public void shouldGetAllMoviesWithPagination() {

        List<Movie> movies = Collections.singletonList(new Movie("Titanic", "Cameron", 120));
        when(movieDao.getAll(5, 5)).thenReturn(movies);
        when(movieDao.getMovieCount()).thenReturn(12L);

        PaginatedData<Movie> actual = instance.getAll(2, 5);

        PaginatedData<Movie> expected = new PaginatedData<>(movies, 3, 2);

        assertThat(actual.getData()).isEqualTo(expected.getData());
        assertThat(actual.getCurrentPage()).isEqualTo(expected.getCurrentPage());
        assertThat(actual.getPagesCount()).isEqualTo(expected.getPagesCount());
    }

    @Test
    public void shouldCreateMovie() throws MovieCreationException {

        CreateMovieRequestDto dto = new CreateMovieRequestDto("Titanic", "Cameron", "120",
                "trailerUrl", "backgroundImgUrl", "coverImgUrl");

        instance.createMovie(dto);

        Movie expected = new Movie(dto.getTitle(), dto.getDirectedBy(), Integer.parseInt(dto.getDurationMinutes()));
        expected.setCoverImgUrl(dto.getCoverImgUrl());
        expected.setTrailerUrl(dto.getTrailerUrl());
        expected.setBackgroundImgUrl(dto.getBackgroundImgUrl());

        verify(movieDao).create(expected);
    }

    @Test
    public void shouldFailCreateMovieWithNullRequiredFields() {

        CreateMovieRequestDto dto = new CreateMovieRequestDto(null, null, "120",
                "trailerUrl", "backgroundImgUrl", "coverImgUrl");

        assertThatThrownBy(() -> instance.createMovie(dto))
                .isInstanceOf(MovieCreationException.class)
                .hasMessageContaining("Required fields are empty");

    }

}