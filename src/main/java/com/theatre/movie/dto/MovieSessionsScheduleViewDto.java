package com.theatre.movie.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MovieSessionsScheduleViewDto {
    private Integer movieId;
    @NonNull
    private String title;
    @NonNull
    private Integer duration;
    private List<MovieSessionTimeViewDto> movieSessionTimes = new ArrayList<>();
    private String trailerUrl;
    private String backgroundImgPath;
    private String coverImgPath;

    public void addMovieSessionTimeDto(MovieSessionTimeViewDto timeDto){
        movieSessionTimes.add(timeDto);
    }

}
