package com.theatre.movie.web.command;

import javax.servlet.http.HttpServletRequest;

public abstract class MultipleMethodCommand implements Command {

    @Override
    public CommandResponse execute(HttpServletRequest request) {
        String type = request.getMethod();

        return "GET".equals(type)
                ? performGet(request)
                : performPost(request);
    }

    protected abstract CommandResponse performGet(HttpServletRequest request);

    protected abstract CommandResponse performPost(HttpServletRequest request);

}
