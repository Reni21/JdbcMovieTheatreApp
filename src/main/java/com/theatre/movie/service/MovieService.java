package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.PaginatedData;
import com.theatre.movie.exception.MovieCreationException;
import com.theatre.movie.exception.MovieRemovalException;
import com.theatre.movie.persistence.transaction.TransactionHandler;
import com.theatre.movie.web.dto.CreateMovieRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * The {@code MovieService} class provides methods for manage information about all  movies
 * represented by {@link com.theatre.movie.entity.Movie} class
 * Properties: <b>movieDao</b>, <b>bookingDao</b>,
 * <b>movieSessionDao</b>, <b>transactionHandler</b>
 *
 * @author Hlushchenko Renata
 * @see com.theatre.movie.dao.MovieDao
 * @see com.theatre.movie.dao.BookingDao
 * @see com.theatre.movie.dao.MovieSessionDao
 * @see com.theatre.movie.persistence.transaction.TransactionHandler
 */

@AllArgsConstructor
public class MovieService {
    private static final Logger LOG = Logger.getLogger(MovieService.class);
    private MovieDao movieDao;
    private BookingDao bookingDao;
    private MovieSessionDao movieSessionDao;
    private TransactionHandler transactionHandler;

    /**
     * Return part of data about movie. Will be displaying
     * for admin-user on admin-movies.jsp
     *
     * @return list of Dtos - stores part of information about all movies
     */
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

    /**
     * @param dto - is used for data transfer for create new movie request
     *            represented by {@link com.theatre.movie.web.dto.CreateMovieRequestDto} class
     * @return {@link com.theatre.movie.entity.Movie}
     */
    public Movie createMovie(CreateMovieRequestDto dto) throws MovieCreationException {
        validateCreateMovieRequest(dto);

        Movie movie = new Movie(dto.getTitle(), dto.getDirectedBy(), Integer.parseInt(dto.getDurationMinutes()));
        movie.setTrailerUrl(dto.getTrailerUrl());
        movie.setCoverImgUrl(dto.getCoverImgUrl());
        movie.setBackgroundImgUrl(dto.getBackgroundImgUrl());
        return movieDao.create(movie);
    }

    /**
     * The method delete required movie and all movie sessions which mapped on it
     *
     * @param movieId - movie id, is used for search data in db
     * @throws MovieRemovalException if some actual movie sessions which has mapped for
     *                               required movie has booking
     */
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

    /**
     * @param dto - is used for data transfer for create new movie request
     *            represented by {@link com.theatre.movie.web.dto.CreateMovieRequestDto} class
     * @throws MovieCreationException if the binding fields is <tt>null</tt>
     */
    private void validateCreateMovieRequest(CreateMovieRequestDto dto) throws MovieCreationException {
        if (dto.getTitle() == null || dto.getDirectedBy() == null || dto.getDurationMinutes() == null) {
            throw new MovieCreationException("Required fields are empty");
        }
    }
}
