package com.theatre.movie.web.command;

import com.theatre.movie.web.PageData;

import javax.servlet.http.HttpServletRequest;

import static com.theatre.movie.web.command.UrlConstants.NOT_FOUND_PAGE;

public class NotFoundCommand implements Command {

    @Override
    public PageData execute(HttpServletRequest request){
        return new PageData(NOT_FOUND_PAGE);
    }
}
