package com.theatre.movie.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateBookingRequestDto {
    int userId;
    String movieSessionId;
    String[] bookedSeatsId;
}
