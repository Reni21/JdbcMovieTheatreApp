package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.PaginatedData;
import com.theatre.movie.exception.CanNotRemoveMovieException;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.util.List;

@AllArgsConstructor
public class MovieService {
    private static final Logger LOG = Logger.getLogger(MovieService.class);
    private MovieDao movieDao;
    private BookingDao bookingDao;

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
        int id = movieDao.create(movie);

        Movie createdMovie = new Movie(movie.getTitle(), movie.getDirectedBy(), movie.getDurationMinutes());
        createdMovie.setCoverImgUrl(movie.getCoverImgUrl());
        createdMovie.setBackgroundImgUrl(movie.getBackgroundImgUrl());
        createdMovie.setTrailerUrl(movie.getTrailerUrl());
        createdMovie.setMovieId(id);
        return createdMovie;
    }

    private void validateMovie(Movie movie) {
        // todo: throw Ex
    }

    public void deleteMovieById(int movieId) throws CanNotRemoveMovieException {
        boolean res = bookingDao.isBookingForMovieSessionExist(movieId);
        if (res) {
            throw new CanNotRemoveMovieException("Movie can not be deleted. Still exist booking for movie sessions with this movie.");
        }
        movieDao.remove(movieId);
    }
}
