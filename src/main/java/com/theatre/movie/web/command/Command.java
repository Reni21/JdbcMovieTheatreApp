package com.theatre.movie.web.command;

import com.theatre.movie.web.PageData;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    PageData execute(HttpServletRequest request);
}
