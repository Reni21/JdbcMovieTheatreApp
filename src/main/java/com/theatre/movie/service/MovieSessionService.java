package com.theatre.movie.service;

import com.theatre.movie.dao.BookingDao;
import com.theatre.movie.dao.MovieSessionDao;
import com.theatre.movie.dto.BookedSeatViewDto;
import com.theatre.movie.dto.MovieSessionViewDto;
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

    public MovieSessionViewDto getMovieSessionById(int id) {
        MovieSession movieSession = movieSessionDao.getById(id);
        Set<Integer> bookedSeats = bookingDao.getAllBookedSeatsIdByMovieSessionId(movieSession.getSessionId());
        Map<Integer, List<BookedSeatViewDto>> seats = mapBookedSeats(movieSession.getHall().getSeats(), bookedSeats);
        MovieSessionViewDto dto = new MovieSessionViewDto(movieSession, seats);
        dto.setBookedSeatsCount(bookedSeats.size());
        return dto;
    }

    private Map<Integer, List<BookedSeatViewDto>> mapBookedSeats(List<Seat> allSeats, Set<Integer> bookedSeats) {
        return allSeats.stream().map(seat -> {
            BookedSeatViewDto dto = new BookedSeatViewDto(seat.getSeatId(), seat.getRow(), seat.getPlace());
            if (bookedSeats.contains(seat.getSeatId())) {
                dto.setBooked(true);
            }
            return dto;
        }).collect(Collectors.groupingBy(BookedSeatViewDto::getRow));
    }
}
