package com.theatre.movie.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MovieSession {
    private Integer sessionId;
    private Integer movieId;
    private Integer hallId;
    private LocalDateTime startAt;
    private Double price;

    public MovieSession(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public MovieSession(Integer movieId, Integer hallId, LocalDateTime startAt, Double price) {
        this.movieId = movieId;
        this.hallId = hallId;
        this.startAt = startAt;
        this.price = price;
    }
}
