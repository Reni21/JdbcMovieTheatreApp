package com.theatre.movie.web.command;

import com.theatre.movie.web.PageData;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public PageData execute(HttpServletRequest request) {
        String originUrl = request.getHeader("referer");
        request.getSession().removeAttribute("user");
        if (originUrl.contains("account")) {
            return new PageData(request.getContextPath() + "/login", true);
        }
        return new PageData(originUrl, true);
    }
}
