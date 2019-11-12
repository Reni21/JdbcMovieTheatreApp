package com.reni.hi.service;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class WeekScheduleDatesServiceTest {
    private WeekScheduleDatesService instance;

    @Before
    public void setUp() {
        instance = new WeekScheduleDatesService();
    }

    @Test
    public void shouldReturnTrueIfDateIsInRange() {
        LocalDate date = LocalDate.now();
        boolean res = instance.isDateInValidRange(date);
        assertTrue(res);
    }

    @Test
    public void shouldReturnTrueIfDateIsNotInRange() {
        LocalDate date = LocalDate.now().plusDays(8);
        boolean res = instance.isDateInValidRange(date);
        assertFalse(res);
    }

    @Test
    public void shouldReturnScheduleForAWeek() {
        instance.getWeekScheduleDates();
    }
}