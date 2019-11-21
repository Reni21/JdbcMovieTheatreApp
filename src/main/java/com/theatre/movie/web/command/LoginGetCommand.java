package com.theatre.movie.web.command;

import com.theatre.movie.web.PageData;

import javax.servlet.http.HttpServletRequest;

public class LoginGetCommand implements Command {
    @Override
    public PageData execute(HttpServletRequest request) {
        request.setAttribute("activeTab", "account");
        return new PageData(UrlConstants.LOGIN_PAGE);
    }
}
