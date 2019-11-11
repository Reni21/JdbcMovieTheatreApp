package com.reni.hi.dao;

import com.reni.hi.entity.Movie;

import java.util.List;

public interface MovieDao extends EntityDao<Movie>{

    List<Movie> getAll();

    Movie getMovieById(Integer movieId);

}