package com.theatre.movie.web.command;

import com.google.gson.Gson;
import com.theatre.movie.dto.MovieSimpleViewDto;
import com.theatre.movie.entity.Movie;
import com.theatre.movie.service.MovieService;
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
        String id = request.getParameter("id");
        if (id == null) {
            return createMovie(request);
        }
        // todo: deleteMovie method
        return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "Something went wrong");
    }

    private CommandResponse createMovie(HttpServletRequest request) {
        String title = request.getParameter("title");
        String directed = request.getParameter("directed");
        String duration = request.getParameter("duration");
        String cover_link = request.getParameter("cover_link");
        String bg_link = request.getParameter("bg_link");
        String trailer_link = request.getParameter("trailer_link");
        try {
            Movie movie = new Movie(title, directed,Integer.parseInt(duration));
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
