package com.theatre.movie.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Hall {
    private Integer hallId;
    @NonNull
    private String hallName;
    private List<Seat> seats = new ArrayList<>();

    public void addSeat(Seat seat){
        seats.add(seat);
    }

}
