package com.reni.hi.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class MovieSessionTimeDto {
    private final Integer movieSessionId;
    private final LocalTime startAt;
}
