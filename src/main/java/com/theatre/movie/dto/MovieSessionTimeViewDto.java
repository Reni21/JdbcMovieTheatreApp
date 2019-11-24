package com.theatre.movie.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class MovieSessionTimeViewDto {
    private final Integer movieSessionId;
    private final LocalTime startAt;

    public String getTimeView() {
        return startAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
