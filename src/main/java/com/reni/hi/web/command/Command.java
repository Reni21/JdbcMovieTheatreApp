package com.reni.hi.web.command;

import com.reni.hi.web.PageData;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    PageData execute(HttpServletRequest request);
}
