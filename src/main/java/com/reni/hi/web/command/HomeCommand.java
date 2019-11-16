package com.reni.hi.web.command;

import com.reni.hi.web.PageData;

import javax.servlet.http.HttpServletRequest;

public class HomeCommand implements Command {
    @Override
    public PageData execute(HttpServletRequest request) {
        request.setAttribute("activeTab", "home");
        return new PageData(UrlConstants.HOME_PAGE);
    }
}