package com.reni.hi.entity;

import lombok.*;


@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Seat {
    private Integer seatId;
    @NonNull
    private Integer row;
    @NonNull
    private Integer place;

}
