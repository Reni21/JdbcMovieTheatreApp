package com.theatre.movie.web.command;

import com.theatre.movie.dto.MenuDateViewDto;
import com.theatre.movie.dto.MovieSessionsScheduleViewDto;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.exception.InvalidScheduleDateException;
import com.theatre.movie.service.MovieSessionService;
import com.theatre.movie.service.WeekScheduleDatesService;
import com.theatre.movie.web.PageData;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class ScheduleCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ScheduleCommand.class);
    private MovieSessionService sessionService;
    private WeekScheduleDatesService weekScheduleDatesService;

    @Override
    public PageData execute(HttpServletRequest request) {
        String dateStr = request.getParameter("date");
        LocalDate now = LocalDate.now();
        LocalDate date = dateStr == null ? now : LocalDate.parse(dateStr);
        try {
            List<MovieSessionsScheduleViewDto> currentDaySessions = sessionService.getMovieSessionsScheduleForDate(date);
            List<MenuDateViewDto> menuDates = weekScheduleDatesService.getWeekScheduleDates(date);

            LOG.info("Current day sessions number: " + currentDaySessions.size() + "\n" + currentDaySessions);
            request.setAttribute("menuDates", menuDates);
            request.setAttribute("sessions", currentDaySessions);
            request.setAttribute("activeTab", "schedule");

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null && Role.ADMIN.equals(user.getRole())) {
                return new PageData(UrlConstants.ADMIN_SCHEDULE_PAGE);
            }
            return new PageData(UrlConstants.SCHEDULE_PAGE);
        } catch (InvalidScheduleDateException e) {
            LOG.warn("Get movie schedule request failed: " + e.getMessage());
            return new PageData(UrlConstants.NOT_FOUND_PAGE);
        }

    }
}
