package com.reni.hi.web.command;


import com.reni.hi.web.PageData;

import javax.servlet.http.HttpServletRequest;


public class LanguageCommand implements Command {

    private static final String LOCALE = "locale";

    @Override
    public PageData execute(HttpServletRequest request) {
        String locale = request.getParameter(LOCALE);
        String originUrl = request.getHeader("referer");
        request.getSession().setAttribute(LOCALE, locale);

        return new PageData(originUrl, true);
    }
}
