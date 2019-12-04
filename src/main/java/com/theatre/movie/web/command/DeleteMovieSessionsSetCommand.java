package com.theatre.movie.web.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theatre.movie.exception.CanNotRemoveMovieScheduleException;
import com.theatre.movie.service.MovieSessionService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@AllArgsConstructor
public class DeleteMovieSessionsSetCommand implements Command {
    private static final Logger LOG = Logger.getLogger(DeleteMovieSessionsSetCommand.class);
    private static final Gson GSON = new Gson();
    private MovieSessionService movieSessionService;

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        String sessionsIdsJson = request.getParameter("sessionsIds");
        LOG.info("Movie sessions ids for delete: " + sessionsIdsJson);
        Type type = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> sessionsIds = GSON.fromJson(sessionsIdsJson, type);


        try {
            movieSessionService.deleteMovieSessionById(sessionsIds);
            String json = GSON.toJson("OK");
            return new SuccessResponse(json);
        } catch (CanNotRemoveMovieScheduleException ex) {
            LOG.error("Failed to delete movie sessions set:" + ex);
            return new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
