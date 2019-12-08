package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.PaginatedData;
import com.theatre.movie.exception.MovieRemovalException;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.util.List;

@AllArgsConstructor
public class MovieService {
    private static final Logger LOG = Logger.getLogger(MovieService.class);
    private MovieDao movieDao;
    private BookingDao bookingDao;
    private MovieSessionDao movieSessionDao;
    private TransactionHandler transactionHandler;

    public List<MovieSimpleViewDto> getAllSimpleView() {
        LOG.info("Extract simple movie view data");
        return movieDao.getAllPreview();
    }

    public PaginatedData<Movie> getAll(int page, int moviesPerPage) {
        LOG.info("Extract all movies");
        List<Movie> movies = movieDao.getAll((page - 1) * moviesPerPage, moviesPerPage);
        long moviesCount = movieDao.getMovieCount();
        int pagesCount = (int) Math.ceil(moviesCount * 1.0 / moviesPerPage);

        return new PaginatedData<>(movies, pagesCount, page);
    }

    public Movie createMovie(Movie movie) {
        validateMovie(movie);
        return movieDao.create(movie);
    }

    private void validateMovie(Movie movie) {
        // todo: throw Ex
    }

    public void deleteMovieAndSessions(int movieId) throws MovieRemovalException {
        transactionHandler.runInTransaction(() -> {
            boolean bookingExists = bookingDao.isBookingForMovieExist(movieId);
            if (bookingExists) {
                throw new MovieRemovalException("Movie can not be deleted. Still exist booking for movie sessions with this movie.");
            }
            movieSessionDao.removeAllByMovieId(movieId);
            movieDao.remove(movieId);
        });
    }
}
