package com.reni.hi.entity;

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
    private Integer number;
    @NonNull
    private List<Integer> seatsId = new ArrayList<>();

}
