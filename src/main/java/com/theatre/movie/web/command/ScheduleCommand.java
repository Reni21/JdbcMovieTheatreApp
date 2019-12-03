package com.theatre.movie.web.command;

import com.google.gson.Gson;
import com.theatre.movie.dto.MenuDateViewDto;
import com.theatre.movie.dto.MovieSessionTimeViewDto;
import com.theatre.movie.dto.MovieSessionsScheduleViewDto;
import com.theatre.movie.entity.MovieSession;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.exception.InvalidScheduleDateException;
import com.theatre.movie.exception.MovieSessionCreationException;
import com.theatre.movie.service.HallService;
import com.theatre.movie.service.MovieSessionService;
import com.theatre.movie.service.WeekScheduleDatesService;
import com.theatre.movie.web.dto.CreateMovieSessionRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
public class ScheduleCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(ScheduleCommand.class);
    private MovieSessionService movieSessionService;
    private WeekScheduleDatesService weekScheduleDatesService;
    private HallService hallService;

    @Override
    protected PageResponse performGet(HttpServletRequest request) {
        LOG.info("Get for /schedule");
        String dateStr = request.getParameter("date");
        if (dateStr == null) {
            LOG.info("Date param is null. Send redirect.");
            return new PageResponse(
                    request.getContextPath() + "/schedule?date=" + LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                    true
            );
        }

        LocalDate date = LocalDate.parse(dateStr);
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
                return new PageResponse(UrlConstants.ADMIN_SCHEDULE_PAGE);
            }
            return new PageResponse(UrlConstants.SCHEDULE_PAGE);
        } catch (InvalidScheduleDateException e) {
            LOG.warn("Get movie schedule request failed: " + e.getMessage());
            return new PageResponse(request.getContextPath() + "/404-error", true);
        }
    }

    @Override
    protected CommandResponse performPost(HttpServletRequest request) {
        String movieId = request.getParameter("movieId");
        String hours = request.getParameter("hours");
        String minutes = request.getParameter("minutes");
        String date = request.getParameter("date");
        String price = request.getParameter("price");
        try {
            CreateMovieSessionRequestDto dto = new CreateMovieSessionRequestDto(movieId, "1", date, hours, minutes, price);
            MovieSession movieSession = movieSessionService.addMovieSession(dto);
            MovieSessionTimeViewDto movieSessionTime = new MovieSessionTimeViewDto(
                    movieSession.getSessionId(), movieSession.getStartAt().toLocalTime());

            Gson gson = new Gson();
            String json = gson.toJson(movieSessionTime);
            return new SuccessResponse(json);
        } catch (MovieSessionCreationException ex) {
            LOG.error("Failed to create new movie session:" + ex);
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
