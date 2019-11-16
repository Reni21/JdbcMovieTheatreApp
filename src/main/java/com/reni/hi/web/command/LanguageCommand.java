//package com.reni.hi.web.command;
//
//
//import com.reni.hi.dto.PageDto;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//public class LanguageCommand implements Command {
//
//    public static final String LOCALE = "locale";
//
//    @Override
//    public PageDto execute(HttpServletRequest request) {
//        String locale = request.getParameter(LOCALE);
//        request.getSession().setAttribute(LOCALE, locale);
//
//        return new PageDto("/", true);
//    }
//}
