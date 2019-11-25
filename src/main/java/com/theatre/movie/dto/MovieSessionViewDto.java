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
public class MovieSessionViewDto {
    @NonNull
    private Integer sessionId;
    private String movieTitle;
    private Integer movieDuration;
    private String hallName;
    private Map<Integer, List<BookedSeatViewDto>> bookedSeats = new HashMap<>();
    private Integer bookedSeatsCount;
    private LocalDateTime startAt;
    private Double price;

    public MovieSessionViewDto(MovieSession movieSession,
                               String movieTitle,
                               Integer movieDuration,
                               String hallName,
                               Map<Integer, List<BookedSeatViewDto>> bookedSeats) {
        this.sessionId = movieSession.getSessionId();
        this.movieTitle = movieTitle;
        this.movieDuration = movieDuration;
        this.hallName = hallName;
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

    public void addSeatDto(Integer row, BookedSeatViewDto seat) {
        if (bookedSeats.containsKey(row)){
            bookedSeats.get(row).add(seat);
        } else {
            List<BookedSeatViewDto> seats = new ArrayList<>();
            seats.add(seat);
            bookedSeats.put(row, seats);
        }

    }
}
