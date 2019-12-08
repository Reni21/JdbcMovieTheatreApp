package com.theatre.movie.web.command;

import com.theatre.movie.web.command.response.CommandResponse;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    CommandResponse execute(HttpServletRequest request);
}
