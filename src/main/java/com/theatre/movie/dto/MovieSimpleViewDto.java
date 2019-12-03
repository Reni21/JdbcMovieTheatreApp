package com.theatre.movie.dto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MovieSimpleViewDto {
    private Integer movieId;
    private String title;
    private Integer duration;
}
