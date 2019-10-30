package com.theatre.movie.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class MovieSessionsScheduleDto {
    @NonNull
    private String title;
    @NonNull
    private Integer duration;
    @Setter
    private List<SessionTimeDto> movieSessionTimes = new ArrayList<>();
    @Setter
    private String trailerUrl;
    @Setter
    private String backgroundImgPath;
    @Setter
    private String coverImgPath;

    public void addMovieSessionTimeDto(SessionTimeDto timeDto){
        movieSessionTimes.add(timeDto);
    }

}
