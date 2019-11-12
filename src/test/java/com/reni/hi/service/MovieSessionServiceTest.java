package com.reni.hi.service;

import com.reni.hi.dao.MovieSessionDaoImpl;
import com.reni.hi.dao.temp1.MovieDaoImpl;
import com.reni.hi.dto.MovieSessionPreviewDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.System.out;

public class MovieSessionServiceTest {
    private MovieSessionService instance;

    @Before
    public void setUp() throws Exception {
        instance = new MovieSessionService(new MovieSessionDaoImpl(), new MovieDaoImpl());
    }

    @Test
    public void shouldReturnListOfSessionPreviewDtos() {
        LocalDateTime searchFrom = LocalDateTime.parse("2019-11-11T00:14:30.266");
        LocalDateTime searchTo = LocalDateTime.parse("2019-11-11T23:59:59.0");

        List<MovieSessionPreviewDto> res = instance.getMovieSessionsInRange(searchFrom, searchTo);
        res.forEach(out::println);



    }
}