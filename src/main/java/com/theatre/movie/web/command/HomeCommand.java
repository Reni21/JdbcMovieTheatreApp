package com.theatre.movie.web.command;

import com.theatre.movie.web.PageData;

import javax.servlet.http.HttpServletRequest;

public class HomeCommand implements Command {
    @Override
    public PageData execute(HttpServletRequest request) {
        request.setAttribute("activeTab", "main");
        return new PageData(UrlConstants.HOME_PAGE);
    }
}
