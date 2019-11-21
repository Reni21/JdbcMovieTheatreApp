package com.theatre.movie.service;

import com.theatre.movie.dto.MenuDateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WeekScheduleDatesService {

    public List<MenuDateDto> getWeekScheduleDates(LocalDate lookupDate) {
        LocalDate now = LocalDate.now();
        return IntStream.range(0, 7)
                .mapToObj(i -> {
                    MenuDateDto dto = new MenuDateDto(now.plusDays(i));
                    if (dto.getDate().isEqual(lookupDate)) {
                        dto.setActive(true);
                    }
                    return dto;
                }).collect(Collectors.toList());
    }

}
