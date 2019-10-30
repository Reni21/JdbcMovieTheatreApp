package com.theatre.movie.entity;

import lombok.*;

@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Movie {
    private Integer movieId;
    @NonNull
    private String title;
    @NonNull
    private String directedBy;
    private String description;
    @NonNull
    private Integer durationMinutes;
    private String trailerUrl;
    private String backgroundImgUrl;
    private String coverImgUrl;
}
