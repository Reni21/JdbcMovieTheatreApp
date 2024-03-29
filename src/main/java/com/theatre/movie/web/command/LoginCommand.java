package com.theatre.movie.web.command;

import com.theatre.movie.entity.User;
import com.theatre.movie.service.UserService;
import com.theatre.movie.web.command.response.PageResponse;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
public class LoginCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    private UserService userService;

    @Override
    protected PageResponse performGet(HttpServletRequest request) {
        request.setAttribute("activeTab", "account");
        return new PageResponse(UrlConstants.LOGIN_PAGE);
    }

    @Override
    protected PageResponse performPost(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        request.setAttribute("activeTab", "account");

        try {
            User user = userService.getUserByCredentials(username, password);
            if (user == null) {
                LOG.info("Login failed. Invalid credentials");
                request.setAttribute("error", "Login or password invalid!");
                LOG.info("Forward to: " + UrlConstants.LOGIN_PAGE);
                return new PageResponse(UrlConstants.LOGIN_PAGE);
            }
            HttpSession session = request.getSession(true);
            LOG.info("Login successful for user: " + user);
            session.setAttribute("user", user);
            return new PageResponse(request.getContextPath() + "/account/" + user.getUsername(), true);

        } catch (IllegalArgumentException ex) {
            LOG.info("Login failed: " + ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            LOG.info("Forward to" + UrlConstants.LOGIN_PAGE);
            return new PageResponse(UrlConstants.LOGIN_PAGE);
        }
    }
}
