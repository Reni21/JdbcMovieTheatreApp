package com.theatre.movie.web.command;

import com.theatre.movie.web.command.response.PageResponse;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public PageResponse execute(HttpServletRequest request) {
        String originUrl = request.getHeader("referer");
        request.getSession().removeAttribute("user");
        if (originUrl.contains("account")) {
            return new PageResponse(request.getContextPath() + "/login", true);
        }
        return new PageResponse(originUrl, true);
    }
}
