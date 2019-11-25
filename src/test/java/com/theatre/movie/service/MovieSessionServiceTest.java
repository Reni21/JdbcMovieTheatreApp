package com.theatre.movie.service;

import com.theatre.movie.dao.impl.MovieSessionDaoImpl;
import com.theatre.movie.dto.MovieSessionsScheduleViewDto;
import com.theatre.movie.exception.InvalidScheduleDateException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.System.out;

public class MovieSessionServiceTest {
    private MovieSessionService instance;

//    @Before
//    public void setUp() throws Exception {
//        instance = new MovieSessionServiceTest(new MovieSessionDaoImpl(), movieDao);
//    }
//
//    @Test
//    public void shouldReturnListOfSessionPreviewDtos() throws InvalidScheduleDateException {
//        LocalDateTime searchFrom = LocalDateTime.parse("2019-11-11T00:14:30.266");
//        LocalDateTime searchTo = LocalDateTime.parse("2019-11-11T23:59:59.0");
//
//        List<MovieSessionsScheduleViewDto> res = instance.getMovieSessionsScheduleForDate(LocalDate.now());
//        res.forEach(out::println);
//    }
}