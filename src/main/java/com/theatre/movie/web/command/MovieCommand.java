package com.theatre.movie.web.command;

import com.google.gson.Gson;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.entity.PaginatedData;
import com.theatre.movie.exception.CanNotRemoveMovieException;
import com.theatre.movie.service.MovieService;
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
        int page = pageStr == null ? 1 : Integer.valueOf(pageStr);


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
            LOG.info("Try delete movie with id=" + id);
            movieService.deleteMovieById(Integer.parseInt(id));
            String json = GSON.toJson("OK");
            return new SuccessResponse(json);
        } catch (CanNotRemoveMovieException ex) {
            LOG.error("Failed to delete movie." + ex);
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }

    private CommandResponse createMovie(HttpServletRequest request) {
        String title = request.getParameter("title");
        String directed = request.getParameter("directed");
        String duration = request.getParameter("duration");
        String cover_link = request.getParameter("cover_link");
        String bg_link = request.getParameter("bg_link");
        String trailer_link = request.getParameter("trailer_link");
        try {
            Movie movie = new Movie(title, directed, Integer.parseInt(duration));
            movie.setCoverImgUrl(cover_link);
            movie.setBackgroundImgUrl(bg_link);
            movie.setTrailerUrl(trailer_link);

            Movie createdMovie = movieService.createMovie(movie);
            Gson gson = new Gson();
            String json = gson.toJson(createdMovie);
            return new SuccessResponse(json);
        } catch (Exception ex) {
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
