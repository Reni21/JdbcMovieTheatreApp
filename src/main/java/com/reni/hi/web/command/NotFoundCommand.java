package com.reni.hi.web.command;

import com.reni.hi.web.PageData;

import javax.servlet.http.HttpServletRequest;

import static com.reni.hi.web.command.UrlConstants.NOT_FOUND_PAGE;

public class NotFoundCommand implements Command {

    @Override
    public PageData execute(HttpServletRequest request){
        return new PageData(NOT_FOUND_PAGE);
    }
}
