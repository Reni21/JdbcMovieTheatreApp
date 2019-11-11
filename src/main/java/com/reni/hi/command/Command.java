package com.reni.hi.command;

import com.reni.hi.dto.PageDto;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    PageDto execute(HttpServletRequest request);
}
