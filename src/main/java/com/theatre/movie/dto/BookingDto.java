package com.theatre.movie.dto;

import com.theatre.movie.entity.BookingStatus;
import com.theatre.movie.entity.MovieSession;
import com.theatre.movie.entity.Seat;
import com.theatre.movie.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class BookingDto {
    private Integer bookingId;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private User userId;
    @NonNull
    private Seat bookedSeat;

    private String movieName;


    @NonNull
    private MovieSession movieSession;

    private BookingStatus bookingStatus = BookingStatus.BOOKED;
}
