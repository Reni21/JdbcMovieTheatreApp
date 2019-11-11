package com.reni.hi.service;

import com.reni.hi.dao.MovieDao;
import com.reni.hi.dao.MovieSessionDaoImpl;
import com.reni.hi.dto.MovieSessionPreviewDto;
import com.reni.hi.dto.MovieSessionTimeDto;
import com.reni.hi.entity.Movie;
import com.reni.hi.entity.MovieSession;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieSessionService {
    private final MovieSessionDaoImpl movieSessionDaoImpl;
    private final MovieDao movieDao;

    public MovieSessionService(MovieSessionDaoImpl movieSessionDaoImpl, MovieDao movieDao) {
        this.movieSessionDaoImpl = movieSessionDaoImpl;
        this.movieDao = movieDao;
    }

    public List<MovieSessionPreviewDto> getMovieSessionsInRange(LocalDateTime searchFrom, LocalDateTime searchTo) {
        List<MovieSession> sessions = movieSessionDaoImpl.getAllSessionsInRange(searchFrom, searchTo);
        Map<Integer, Movie> movies = new HashMap<>();
        sessions.forEach(session -> {
            int movieId = session.getMovieId();
            Movie movie = movieDao.getMovieById(movieId);
            if (movie != null && !movies.containsKey(movieId)) {
                movies.put(movieId, movie);
            }
        });
        return createMovieSessionPreviewDto(sessions, movies);
    }

    private List<MovieSessionPreviewDto> createMovieSessionPreviewDto(List<MovieSession> sessions, Map<Integer, Movie> movies) {
        List<MovieSessionPreviewDto> sessionPreviewDtos = new ArrayList<>();
        sessions.forEach(session -> {
            Movie movie = movies.get(session.getMovieId());
            MovieSessionPreviewDto sessionPreview = new MovieSessionPreviewDto(movie.getTitle(), movie.getDurationMinutes());
            sessionPreview.setTrailerUrl(movie.getTrailerUrl());
            sessionPreview.setCoverImgPath(movie.getCoverImgPath());
            sessionPreview.setBackgroundImgPath(movie.getBackgroundImgPath());

            LocalTime startAt = session.getStartAt().toLocalTime();
            MovieSessionTimeDto timeDto = new MovieSessionTimeDto(session.getSessionId(), startAt);
            sessionPreview.addMovieSessionTimeDto(timeDto);
            sessionPreviewDtos.add(sessionPreview);
        });
        return sessionPreviewDtos;
    }
}
