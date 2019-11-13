package com.reni.hi.service;

import com.reni.hi.dao.CrudDao;
import com.reni.hi.dao.MovieSessionDao;
import com.reni.hi.dto.SessionPreviewDto;
import com.reni.hi.dto.SessionTimeDto;
import com.reni.hi.entity.Movie;
import com.reni.hi.entity.MovieSession;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class MovieSessionService {
    private static final Logger LOG = Logger.getLogger(MovieSessionService.class);
    private final MovieSessionDao movieSessionDao;
    private final CrudDao<Movie> movieDao;

    public MovieSessionService(MovieSessionDao movieSessionDao, CrudDao<Movie> movieDao) {
        this.movieSessionDao = movieSessionDao;
        this.movieDao = movieDao;
    }

    public List<SessionPreviewDto> getSessionsInRange(LocalDateTime from, LocalDateTime to) {
        LOG.info("Sessions search start from: " + from + " to: "+ to);
        List<MovieSession> sessions = movieSessionDao.getAllInRange(from, to);
        Map<Integer, Movie> movies = new HashMap<>();

        sessions.forEach(session -> {
            int movieId = session.getMovieId();
            Movie movie = movieDao.getById(movieId);
            if (movie != null && !movies.containsKey(movieId)) {
                movies.put(movieId, movie);
            }
        });
        return createSessionPreviewDto(sessions, movies);
    }

    private List<SessionPreviewDto> createSessionPreviewDto(List<MovieSession> sessions, Map<Integer, Movie> movies) {
        Map<Movie, SessionPreviewDto> sessionPreviewDtos = new HashMap<>();
        sessions.forEach(session -> {
            Movie movie = movies.get(session.getMovieId()); //  смотри что за фильм
            LocalTime startAt = session.getStartAt().toLocalTime();
            SessionTimeDto timeDto = new SessionTimeDto(session.getSessionId(), startAt);

            if(sessionPreviewDtos.containsKey(movie)){
                sessionPreviewDtos.get(movie).addMovieSessionTimeDto(timeDto);
            } else {
                SessionPreviewDto dto = new SessionPreviewDto(movie.getTitle(), movie.getDurationMinutes());
                dto.setTrailerUrl(movie.getTrailerUrl());
                dto.setCoverImgPath(movie.getCoverImgUrl());
                dto.setBackgroundImgPath(movie.getBackgroundImgUrl());
                dto.addMovieSessionTimeDto(timeDto);
                sessionPreviewDtos.put(movie,dto);
            }
        });
        return new ArrayList<SessionPreviewDto>(sessionPreviewDtos.values());
    }
}
