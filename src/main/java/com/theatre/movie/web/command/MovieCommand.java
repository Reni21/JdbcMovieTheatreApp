package com.theatre.movie.web.command;

import com.google.gson.Gson;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.PaginatedData;
import com.theatre.movie.exception.MovieCreationException;
import com.theatre.movie.exception.MovieRemovalException;
import com.theatre.movie.service.MovieService;
import com.theatre.movie.web.command.response.CommandResponse;
import com.theatre.movie.web.command.response.ErrorResponse;
import com.theatre.movie.web.command.response.PageResponse;
import com.theatre.movie.web.command.response.SuccessResponse;
import com.theatre.movie.web.dto.CreateMovieRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@AllArgsConstructor
public class MovieCommand extends MultipleMethodCommand {
    private static final int MOVIES_PER_PAGE = 5;
    private static final Gson GSON = new Gson();
    private static final Logger LOG = Logger.getLogger(MovieCommand.class);
    private MovieService movieService;

    @Override
    protected CommandResponse performGet(HttpServletRequest request) {
        LOG.info("Start GET");
        String simpleView = request.getParameter("simpleView");
        if (simpleView != null) {
            return performAjax();
        }
        String pageStr = request.getParameter("page");
        int page = pageStr == null ? 1 : Integer.parseInt(pageStr);


        request.setAttribute("activeTab", "movies");
        PaginatedData<Movie> paginatedMovies = movieService.getAll(page, MOVIES_PER_PAGE);
        request.setAttribute("movies", paginatedMovies.getData());
        request.setAttribute("pagesCount", paginatedMovies.getPagesCount());
        request.setAttribute("currentPage", paginatedMovies.getCurrentPage());

        return new PageResponse(UrlConstants.ADMIN_MOVIES_PAGE);
    }

    @Override
    protected CommandResponse performPost(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id == null) {
            return createMovie(request);
        }
        try {
            LOG.info("Try delete movie with id=" + id + " and all its sessions");
            movieService.deleteMovieAndSessions(Integer.parseInt(id));
            String json = GSON.toJson("OK");
            return new SuccessResponse(json);
        } catch (MovieRemovalException ex) {
            LOG.error("Failed to delete movie." + ex);
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }

    private CommandResponse createMovie(HttpServletRequest request) {

        CreateMovieRequestDto dto = new CreateMovieRequestDto(
                request.getParameter("title"),
                request.getParameter("directed"),
                request.getParameter("duration"),
                request.getParameter("trailer_link"),
                request.getParameter("bg_link"),
                request.getParameter("cover_link")
        );

        try {
            Movie createdMovie = movieService.createMovie(dto);
            Gson gson = new Gson();
            String json = gson.toJson(createdMovie);
            return new SuccessResponse(json);
        } catch (MovieCreationException ex) {
            LOG.error("Failed to create new movie session:" + ex);
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
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
