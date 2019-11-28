package com.theatre.movie.web.command;

import com.theatre.movie.dto.MenuDateViewDto;
import com.theatre.movie.dto.MovieSessionViewDto;
import com.theatre.movie.service.MovieSessionService;
import com.theatre.movie.service.WeekScheduleDatesService;
import com.theatre.movie.web.PageData;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class MovieSessionCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(MovieSessionCommand.class);
    private WeekScheduleDatesService weekScheduleDatesService;
    private MovieSessionService movieSessionService;

    @Override
    protected PageData performGet(HttpServletRequest request) {
        request.setAttribute("activeTab", "null");
        List<MenuDateViewDto> menuDates = weekScheduleDatesService.getWeekScheduleDates(LocalDate.now());
        request.setAttribute("menuDates", menuDates);

        String uri = request.getRequestURI();
        int id = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
        MovieSessionViewDto movieSession = movieSessionService.getMovieSessionById(id);
        request.setAttribute("movieSession", movieSession);
        LOG.info("Extracted movie session:\n" + movieSession);
        return new PageData(UrlConstants.MOVIE_SESSION_PAGE);
    }

    @Override
    protected PageData performPost(HttpServletRequest request) {

        return null;
    }
}
