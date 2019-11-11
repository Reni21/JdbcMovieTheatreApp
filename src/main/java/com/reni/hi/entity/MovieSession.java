package com.reni.hi.entity;

import lombok.*;

import java.time.LocalDateTime;

@NonNull
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
