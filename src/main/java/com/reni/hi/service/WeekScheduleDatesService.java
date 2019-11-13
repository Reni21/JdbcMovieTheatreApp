package com.reni.hi.service;

import com.reni.hi.dto.MenuDateDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekScheduleDatesService {

    public List<MenuDateDto> getWeekScheduleDates() {
        List<MenuDateDto> menuDates = new ArrayList<>();
        LocalDate date = LocalDate.now();
        menuDates.add(new MenuDateDto(date));
        for (int i = 0; i < 6; i++) {
            menuDates.add(new MenuDateDto(date.plusDays(i+1)));
        }
        return menuDates;
    }

    public boolean isDateInValidRange(LocalDate date) {
        LocalDate now = LocalDate.now();
        return date.isBefore(now.plusDays(7)) && date.isAfter(now.minusDays(1));
    }
}
