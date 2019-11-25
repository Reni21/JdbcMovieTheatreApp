package com.theatre.movie.web.command;

import com.theatre.movie.web.PageData;

import javax.servlet.http.HttpServletRequest;

public abstract class MultipleMethodCommand implements Command {

    @Override
    public PageData execute(HttpServletRequest request) {
        String type = request.getMethod();

        return "GET".equals(type)
                ? performGet(request)
                : performPost(request);
    }

    protected abstract PageData performGet(HttpServletRequest request);

    protected abstract PageData performPost(HttpServletRequest request);

}
