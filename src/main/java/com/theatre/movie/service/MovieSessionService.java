package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.dto.BookedSeatDto;
import com.theatre.movie.dto.MovieSessionDto;
import com.theatre.movie.entity.MovieSession;
import com.theatre.movie.entity.Seat;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MovieSessionService {
    private MovieSessionDao movieSessionDao;
    private BookingDao bookingDao;

    public MovieSessionDto getMovieSessionById(int id) {
        MovieSession movieSession = movieSessionDao.getById(id);
        Set<Integer> bookedSeats = bookingDao.getAllBookedSeatsIdByMovieSessionId(movieSession.getSessionId());
        Map<Integer, List<BookedSeatDto>> seats = mapBookedSeats(movieSession.getHall().getSeats(), bookedSeats);
        return new MovieSessionDto(movieSession, seats);
    }

    private Map<Integer, List<BookedSeatDto>> mapBookedSeats(List<Seat> allSeats, Set<Integer> bookedSeats) {
        return allSeats.stream().map(seat -> {
            BookedSeatDto dto = new BookedSeatDto(seat.getSeatId(), seat.getRow(), seat.getPlace());
            if (bookedSeats.contains(seat.getSeatId())) {
                dto.setBooked(true);
            }
            return dto;
        }).collect(Collectors.groupingBy(BookedSeatDto::getRow));
    }
}
