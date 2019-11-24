package com.theatre.movie.dto;

import lombok.*;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class BookedSeatViewDto {
    @NonNull
    private Integer seatId;
    @NonNull
    private Integer row;
    @NonNull
    private Integer place;
    @Setter
    private boolean isBooked;
}
