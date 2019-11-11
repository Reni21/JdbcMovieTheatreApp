package com.reni.hi.command;

import com.reni.hi.dto.PageDto;

import javax.servlet.http.HttpServletRequest;

import static com.reni.hi.command.UrlConstants.NOT_FOUND_PAGE;

public class NotFoundCommand implements Command {

    @Override
    public PageDto execute(HttpServletRequest request){
        return new PageDto(NOT_FOUND_PAGE);
    }
}
