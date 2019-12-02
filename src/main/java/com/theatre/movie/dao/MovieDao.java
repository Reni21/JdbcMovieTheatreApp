package com.theatre.movie.dao;

import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;

import java.util.List;

public interface MovieDao extends CrudDao<Movie> {

    public List<MovieSimpleViewDto> getAllPreview();
}
