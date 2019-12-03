package com.theatre.movie.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Getter
@EqualsAndHashCode
@ToString
public class MovieSessionTimeViewDto {
    private final Integer movieSessionId;
    private final LocalTime startAt;
    private final String timeView;

    public MovieSessionTimeViewDto(Integer movieSessionId, LocalTime startAt) {
        this.movieSessionId = movieSessionId;
        this.startAt = startAt;
        this.timeView = startAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
