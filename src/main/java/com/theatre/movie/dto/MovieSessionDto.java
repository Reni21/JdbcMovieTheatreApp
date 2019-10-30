package com.theatre.movie.dto;

import com.theatre.movie.entity.MovieSession;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MovieSessionDto {
    @NonNull
    private Integer sessionId;
    private String movieTitle;
    private Integer movieDuration;
    private String hallName;
    private Map<Integer, List<BookedSeatDto>> bookedSeats = new HashMap<>();
    private LocalDateTime startAt;
    private Double price;

    public MovieSessionDto(MovieSession movieSession, Map<Integer, List<BookedSeatDto>> bookedSeats) {
        this.sessionId = movieSession.getSessionId();
        this.movieTitle = movieSession.getMovie().getTitle();
        this.movieDuration = movieSession.getMovie().getDurationMinutes();
        this.hallName = movieSession.getHall().getHallName();
        this.bookedSeats = bookedSeats;
        this.startAt = movieSession.getStartAt();
        this.price = movieSession.getPrice();

    }

    public String getFormattedTime() {
        return startAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getFormattedDate() {
        return startAt.format(DateTimeFormatter.ofPattern("dd.MM"));
    }

    public void addSeatDto(Integer row, BookedSeatDto seat) {
        if (bookedSeats.containsKey(row)){
            bookedSeats.get(row).add(seat);
        } else {
            List<BookedSeatDto> seats = new ArrayList<>();
            seats.add(seat);
            bookedSeats.put(row, seats);
        }

    }
}
