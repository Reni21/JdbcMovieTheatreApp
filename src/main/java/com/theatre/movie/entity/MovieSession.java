package com.theatre.movie.entity;

import lombok.*;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MovieSession {
    private Integer sessionId;
    @NonNull
    private Integer movieId;
    @NonNull
    private Integer hallId;
    @NonNull
    private LocalDateTime startAt;
    @NonNull
    private Double price;
}
