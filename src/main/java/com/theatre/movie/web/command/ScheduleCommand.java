package com.theatre.movie.web.command;

import com.theatre.movie.dto.MenuDateViewDto;
import com.theatre.movie.dto.MovieSessionsScheduleViewDto;
import com.theatre.movie.entity.Hall;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.exception.InvalidScheduleDateException;
import com.theatre.movie.service.HallService;
import com.theatre.movie.service.MovieSessionService;
import com.theatre.movie.service.WeekScheduleDatesService;
import com.theatre.movie.web.PageData;
import com.theatre.movie.web.dto.CreateMovieSessionRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class ScheduleCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(ScheduleCommand.class);
    private MovieSessionService movieSessionService;
    private WeekScheduleDatesService weekScheduleDatesService;
    private HallService hallService;

    @Override
    protected PageData performGet(HttpServletRequest request) {
        String dateStr = request.getParameter("date");
        LocalDate now = LocalDate.now();
        LocalDate date = dateStr == null ? now : LocalDate.parse(dateStr);
        try {
            List<MovieSessionsScheduleViewDto> currentDaySessions = movieSessionService.getMovieSessionsScheduleForDate(date);
            List<MenuDateViewDto> menuDates = weekScheduleDatesService.getWeekScheduleDates(date);

            LOG.info("Current day sessions number: " + currentDaySessions.size() + "\n" + currentDaySessions);
            request.setAttribute("menuDates", menuDates);
            request.setAttribute("sessions", currentDaySessions);
            request.setAttribute("activeTab", "schedule");

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null && Role.ROLE_ADMIN.equals(user.getRole())) {
                return new PageData(UrlConstants.ADMIN_SCHEDULE_PAGE);
            }
            return new PageData(UrlConstants.SCHEDULE_PAGE);
        } catch (InvalidScheduleDateException e) {
            LOG.warn("Get movie schedule request failed: " + e.getMessage());
            return new PageData(request.getContextPath() + "/404-error", true);
        }
    }

    @Override
    protected PageData performPost(HttpServletRequest request) {
        String movieTitle =  request.getParameter("movieTitle");
        String hours = request.getParameter("hours");
        String minutes = request.getParameter("minutes");

        try {
            if (!movieSessionService.createMovieSession(new CreateMovieSessionRequestDto(movieTitle, hours, minutes))) {
                request.setAttribute("error", "Something went wrong.");
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }
        request.setAttribute("activeTab", "schedule");
        return new PageData(request.getContextPath() + "/schedule", true);
    }
}
