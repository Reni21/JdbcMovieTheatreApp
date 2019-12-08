package com.theatre.movie.web.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CreateMovieRequestDto {
    private String title;
    private String directedBy;
    private String durationMinutes;
    private String trailerUrl;
    private String backgroundImgUrl;
    private String coverImgUrl;
}
