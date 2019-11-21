package com.theatre.movie.entity;

import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Booking {
    private Integer bookingId;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private Integer userId;
    @NonNull
    private Integer bookedSeatId;
    @NonNull
    private Integer movieSessionId;

    private BookingStatus bookingStatus = BookingStatus.BOOKED;
}
