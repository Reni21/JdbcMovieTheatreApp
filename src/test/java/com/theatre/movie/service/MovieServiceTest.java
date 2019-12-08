package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.PaginatedData;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

}