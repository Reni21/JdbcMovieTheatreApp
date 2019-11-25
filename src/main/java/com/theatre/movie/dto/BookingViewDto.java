package com.theatre.movie.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class BookingViewDto {
    private Integer bookingId;
    private String movieName;
    private Integer movieDuration;
    private LocalDateTime startAt;
    private String hallName;
    private Integer row;
    private Integer place;

    public String getTimeView() {
        return startAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getFormattedDate() {
        return startAt.format(DateTimeFormatter.ofPattern("dd.MM"));
    }
}

