package com.theatre.movie.web.command;

import com.theatre.movie.entity.User;
import com.theatre.movie.exception.UserAlreadyExistException;
import com.theatre.movie.service.UserService;
import com.theatre.movie.web.PageData;
import com.theatre.movie.web.dto.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
public class SignUpPostCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SignUpPostCommand.class);
    private UserService userService;

    @Override
    public PageData execute(HttpServletRequest request) {
        request.setAttribute("activeTab", "account");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        CreateUserRequest userRequest = new CreateUserRequest(login, password, email);
        try {
            User user = userService.addUser(userRequest);
            if (user == null) {
                throw new IllegalArgumentException("Something wend wrong. Please try later.");
            }
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return new PageData(request.getContextPath() + "/account", true);
        } catch (UserAlreadyExistException | IllegalArgumentException ex) {
            LOG.warn("Sign up failed for new user:" + userRequest);
            LOG.warn("Error msg:" + ex.getMessage());
            LOG.info("Forward to: " + UrlConstants.SIGN_UP_PAGE);
            request.setAttribute("error", ex.getMessage());
            return new PageData(UrlConstants.SIGN_UP_PAGE);
        }
    }
}
