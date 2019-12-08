package com.theatre.movie.web.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theatre.movie.dto.MenuDateViewDto;
import com.theatre.movie.dto.MovieSessionViewDto;
import com.theatre.movie.exception.MovieScheduleRemovalException;
import com.theatre.movie.service.MovieSessionService;
import com.theatre.movie.service.WeekScheduleDatesService;
import com.theatre.movie.web.command.response.CommandResponse;
import com.theatre.movie.web.command.response.ErrorResponse;
import com.theatre.movie.web.command.response.PageResponse;
import com.theatre.movie.web.command.response.SuccessResponse;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class MovieSessionCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(MovieSessionCommand.class);
    private static final Gson GSON = new Gson();
    private WeekScheduleDatesService weekScheduleDatesService;
    private MovieSessionService movieSessionService;

    @Override
    protected PageResponse performGet(HttpServletRequest request) {
        request.setAttribute("activeTab", "null");
        List<MenuDateViewDto> menuDates = weekScheduleDatesService.getWeekScheduleDates(LocalDate.now());
        request.setAttribute("menuDates", menuDates);

        String uri = request.getRequestURI();
        int id = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
        MovieSessionViewDto movieSession = movieSessionService.getMovieSessionById(id);
        request.setAttribute("movieSession", movieSession);
        LOG.info("Extracted movie session:\n" + movieSession);
        return new PageResponse(UrlConstants.MOVIE_SESSION_PAGE);
    }

    /**
     * Delete movie session
     */
    @Override
    protected CommandResponse performPost(HttpServletRequest request) {
        String sessionsIdsJson = request.getParameter("sessionsIds");
        LOG.info("Movie sessions ids for delete: " + sessionsIdsJson);
        Type type = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> sessionsIds = GSON.fromJson(sessionsIdsJson, type);

        try {
            movieSessionService.deleteMovieSessionByIds(sessionsIds);
            String json = GSON.toJson("OK");
            return new SuccessResponse(json);
        } catch (MovieScheduleRemovalException ex) {
            LOG.error("Failed to delete movie sessions set:" + ex);
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
