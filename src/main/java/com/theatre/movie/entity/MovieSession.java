package com.theatre.movie.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MovieSession {
    private Integer sessionId;
    private Movie movie;
    private Hall hall;
    private LocalDateTime startAt;
    private Double price;

    public MovieSession(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public MovieSession(Movie movie, Hall hall, LocalDateTime startAt, Double price) {
        this.movie = movie;
        this.hall = hall;
        this.startAt = startAt;
        this.price = price;
    }
}
