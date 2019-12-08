package com.theatre.movie.web.command;

import com.theatre.movie.web.command.response.PageResponse;

import javax.servlet.http.HttpServletRequest;

public class HomeCommand implements Command {
    @Override
    public PageResponse execute(HttpServletRequest request) {
        request.setAttribute("activeTab", "main");
        return new PageResponse(UrlConstants.HOME_PAGE);
    }
}
