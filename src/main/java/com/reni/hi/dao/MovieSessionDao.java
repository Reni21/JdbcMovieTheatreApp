package com.reni.hi.dao;

import com.reni.hi.entity.MovieSession;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieSessionDao {

    int create(MovieSession entity);

    List<MovieSession> getAllInRange(LocalDateTime from, LocalDateTime to);

    MovieSession getById(int id);

    boolean remove(int it);
}
