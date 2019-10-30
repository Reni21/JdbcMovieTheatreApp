package com.theatre.movie.service;

import com.theatre.movie.dto.MenuDateDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WeekScheduleDatesServiceTest {
    private WeekScheduleDatesService instance;

    @Before
    public void setUp() {
        instance = new WeekScheduleDatesService();
    }

    @Test
    public void shouldReturnProperScheduleDatesList() {
        List<MenuDateDto> weekScheduleDates = instance.getWeekScheduleDates(LocalDate.now());
        int res = weekScheduleDates.size();
        assertEquals(7, res);
        assertTrue(weekScheduleDates.get(0).isActive());
    }
}