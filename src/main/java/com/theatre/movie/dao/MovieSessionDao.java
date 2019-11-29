package com.theatre.movie.dao;

import com.theatre.movie.entity.MovieSession;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieSessionDao {

    List<MovieSession> getAllInRange(LocalDateTime from, LocalDateTime to);

    MovieSession getById(int id);

    MovieSession create(MovieSession movieSession);

}
