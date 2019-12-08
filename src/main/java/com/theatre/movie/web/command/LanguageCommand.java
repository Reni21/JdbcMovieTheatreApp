package com.theatre.movie.web.command;


import com.theatre.movie.web.command.response.PageResponse;

import javax.servlet.http.HttpServletRequest;


public class LanguageCommand implements Command {

    private static final String LOCALE = "locale";

    @Override
    public PageResponse execute(HttpServletRequest request) {
        String locale = request.getParameter(LOCALE);
        String originUrl = request.getHeader("referer");
        request.getSession().setAttribute(LOCALE, locale);

        return new PageResponse(originUrl, true);
    }
}
