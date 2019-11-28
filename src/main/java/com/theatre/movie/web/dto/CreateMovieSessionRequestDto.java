package com.theatre.movie.web.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CreateMovieSessionRequestDto {
    String movieTitle;
//    String hallName;
    String hours;
    String minutes;
}
