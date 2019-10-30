package com.theatre.movie.web.command;

import com.theatre.movie.entity.User;
import com.theatre.movie.service.UserService;
import com.theatre.movie.web.PageData;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
public class LoginPostCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoginPostCommand.class);
    private UserService userService;

    @Override
    public PageData execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        request.setAttribute("activeTab", "account");

        try {
            User user = userService.getUserByCredentials(login, password);
            if (user == null) {
                LOG.info("Login failed. Invalid credentials: login=" + login + " password=" + password);
                request.setAttribute("error", "Login or password invalid!");
                LOG.info("Forward to: " + UrlConstants.LOGIN_PAGE);
                return new PageData(UrlConstants.LOGIN_PAGE);
            }
            HttpSession session = request.getSession(true);
            LOG.info("Login successful for user: " + user);
            session.setAttribute("user", user);
            return new PageData(request.getContextPath()+"/account/" + user.getLogin(), true);

        } catch (IllegalArgumentException ex){
            LOG.info("Login failed: " + ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            LOG.info("Forward to" + UrlConstants.LOGIN_PAGE);
            return new PageData(UrlConstants.LOGIN_PAGE);
        }
    }
}
