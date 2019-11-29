package com.theatre.movie.web.command;

import javax.servlet.http.HttpServletRequest;

import static com.theatre.movie.web.command.UrlConstants.NOT_FOUND_PAGE;

public class NotFoundCommand implements Command {

    @Override
    public PageResponse execute(HttpServletRequest request){
        return new PageResponse(NOT_FOUND_PAGE);
    }
}
