package com.reni.hi.command;

import com.reni.hi.dto.SessionPreviewDto;
import com.reni.hi.dto.PageDto;
import com.reni.hi.service.MovieSessionService;
import com.reni.hi.service.WeekScheduleDatesService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScheduleCommand implements Command {
    private MovieSessionService sessionService;
    private WeekScheduleDatesService weekScheduleDatesService;

    public ScheduleCommand(MovieSessionService sessionService, WeekScheduleDatesService weekScheduleDatesService) {
        this.sessionService = sessionService;
        this.weekScheduleDatesService = weekScheduleDatesService;
    }

    @Override
    public PageDto execute(HttpServletRequest request) {
        String dateStr = request.getParameter("date");
        LocalDate date = dateStr == null ? LocalDate.now() : LocalDate.parse(dateStr);
        weekScheduleDatesService.isDateInValidRange(date);
        LocalDateTime searchFrom = date.atTime(LocalTime.now());
        LocalDateTime searchTo = date.atTime(23, 59, 59);

        List<SessionPreviewDto> currentDaySessions = sessionService.getSessionsInRange(searchFrom, searchTo);
        List<LocalDate> menuDates = weekScheduleDatesService.getWeekScheduleDates();

        request.setAttribute("menuDates", menuDates);
        request.setAttribute("sessions", currentDaySessions);
        return new PageDto(UrlConstants.SCHEDULE_PAGE, date.format(DateTimeFormatter.ISO_DATE));
    }
}
