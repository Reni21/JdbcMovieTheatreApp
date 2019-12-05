package com.theatre.movie.web.command;

import com.google.gson.Gson;
import com.theatre.movie.dto.MovieSessionTimeViewDto;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.MovieSession;
import com.theatre.movie.exception.MovieSessionCreationException;
import com.theatre.movie.service.MovieService;
import com.theatre.movie.web.dto.CreateMovieSessionRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@AllArgsConstructor
public class MovieCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(MovieCommand.class);
    private MovieService movieService;

    @Override
    protected CommandResponse performGet(HttpServletRequest request) {
        LOG.info("Start GET");
        String ajax = request.getParameter("ajax");
        if(ajax != null) {
            return performAjax();
        }

        request.setAttribute("activeTab", "movies");
        List<Movie> movies = movieService.getAll();
        request.setAttribute("movies", movies);
        return new PageResponse(UrlConstants.ADMIN_MOVIES_PAGE);
    }

    @Override
    protected CommandResponse performPost(HttpServletRequest request) {
        return null;
    }

    private CommandResponse performAjax() {
        try {
            List<MovieSimpleViewDto> simpleViewDtos = movieService.getAllSimpleView();
            Gson gson = new Gson();
            String json = gson.toJson(simpleViewDtos);
            return new SuccessResponse(json);
        } catch (Exception ex) {
            LOG.error("Failed to extract movie preview dtos:" + ex);
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
