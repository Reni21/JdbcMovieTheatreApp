package com.reni.hi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekScheduleDatesService {

    public List<LocalDate> getWeekScheduleDates() {
        List<LocalDate> menuDates = new ArrayList<>();
        LocalDate date = LocalDate.now();
        menuDates.add(date);
        for (int i = 0; i < 6; i++) {
            menuDates.add(date.plusDays(i+1));
        }
        return menuDates;
    }

    public boolean isDateInValidRange(LocalDate date) {
        LocalDate now = LocalDate.now();
        return date.isBefore(now.plusDays(7)) && date.isAfter(now.minusDays(1));
    }
}
