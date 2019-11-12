package com.reni.hi.dao.temp1;

import com.reni.hi.entity.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll();

    Movie getMovieById(Integer movieId);

}