package com.theatre.movie.service;

import com.theatre.movie.dto.MenuDateViewDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The {@code WeekScheduleDatesService} class provides method for manage information about
 * range of possible dates for movie session creations and available movie session schedule
 * of movie theatre
 *
 * @author Hlushchenko Renata
 */

public class WeekScheduleDatesService {

    /**
     * The method processes information and forms a set of dates for display as
     * second level menu on view page
     *
     * @param lookupDate - indicates the date selected in the schedule menu on view page.
     *                   Method will mark it like <tt>active</tt>
     * @return list of Dtos - stores data for displaying dates menu on view page
     */
    public List<MenuDateViewDto> getWeekScheduleDates(LocalDate lookupDate) {
        LocalDate now = LocalDate.now();
        return IntStream.range(0, 7)
                .mapToObj(i -> {
                    MenuDateViewDto dto = new MenuDateViewDto(now.plusDays(i));
                    if (dto.getDate().isEqual(lookupDate)) {
                        dto.setActive(true);
                    }
                    return dto;
                }).collect(Collectors.toList());
    }

}
