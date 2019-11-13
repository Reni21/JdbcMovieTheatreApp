package com.reni.hi.command;

import com.reni.hi.dto.MenuDateDto;
import com.reni.hi.dto.SessionPreviewDto;
import com.reni.hi.dto.PageDto;
import com.reni.hi.service.MovieSessionService;
import com.reni.hi.service.WeekScheduleDatesService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScheduleCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ScheduleCommand.class);
    private MovieSessionService sessionService;
    private WeekScheduleDatesService weekScheduleDatesService;

    public ScheduleCommand(MovieSessionService sessionService, WeekScheduleDatesService weekScheduleDatesService) {
        this.sessionService = sessionService;
        this.weekScheduleDatesService = weekScheduleDatesService;
    }

    @Override
    public PageDto execute(HttpServletRequest request) {
        String dateStr = request.getParameter("date");
        LocalDate now = LocalDate.now();
        LocalDate date = dateStr == null ? now : LocalDate.parse(dateStr);
        weekScheduleDatesService.isDateInValidRange(date);
        LocalDateTime searchFrom;
        if (now.equals(date)) {
            searchFrom = date.atTime(LocalTime.now());
        } else {
            searchFrom = date.atTime(0,0,0);
        }
        LocalDateTime searchTo = date.atTime(23, 59, 59);

        List<SessionPreviewDto> currentDaySessions = sessionService.getSessionsInRange(searchFrom, searchTo);
        List<MenuDateDto> menuDates = weekScheduleDatesService.getWeekScheduleDates();
        menuDates.stream().filter(dateDto -> date.isEqual(dateDto.getDate())).findFirst().get().setActive(true);

        LOG.info("Current day sessions number: " + currentDaySessions.size() + "\n" + currentDaySessions);
        request.setAttribute("menuDates", menuDates);
        request.setAttribute("sessions", currentDaySessions);
        return new PageDto(UrlConstants.SCHEDULE_PAGE, date.format(DateTimeFormatter.ISO_DATE));
    }
}
