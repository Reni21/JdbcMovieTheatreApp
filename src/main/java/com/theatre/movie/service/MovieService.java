package com.theatre.movie.service;

import com.theatre.movie.dao.MovieDao;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import java.util.List;

@AllArgsConstructor
public class MovieService {
    private static final Logger LOG = Logger.getLogger(MovieService.class);
    private MovieDao movieDao;

    public List<MovieSimpleViewDto> getAllSimpleView(){
        LOG.info("Extract simple movie view data");
        return movieDao.getAllPreview();
    }

    public List<Movie> getAll(){
        LOG.info("Extract all movies");
        return movieDao.getAll();
    }
}
