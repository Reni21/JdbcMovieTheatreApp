package com.theatre.movie.web.command;

import com.theatre.movie.entity.User;
import com.theatre.movie.exception.UserAlreadyExistException;
import com.theatre.movie.service.UserService;
import com.theatre.movie.web.command.response.PageResponse;
import com.theatre.movie.web.dto.CreateUserRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
public class SignUpCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(SignUpCommand.class);
    private UserService userService;


    @Override
    protected PageResponse performGet(HttpServletRequest request) {
        request.setAttribute("activeTab", "account");
        return new PageResponse(UrlConstants.SIGN_UP_PAGE);
    }

    @Override
    protected PageResponse performPost(HttpServletRequest request) {
        request.setAttribute("activeTab", "account");

        CreateUserRequestDto userRequest = new CreateUserRequestDto(
                request.getParameter("username"),
                request.getParameter("password"),
                request.getParameter("email")
        );
        try {
            User user = userService.addUser(userRequest);
            if (user == null) {
                throw new IllegalArgumentException("Something wend wrong. Please try later.");
            }
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return new PageResponse(request.getContextPath() + "/account", true);
        } catch (UserAlreadyExistException | IllegalArgumentException ex) {
            LOG.warn("Sign up failed for new user:" + userRequest);
            LOG.warn("Error msg:" + ex.getMessage());
            LOG.info("Forward to: " + UrlConstants.SIGN_UP_PAGE);
            request.setAttribute("error", ex.getMessage());
            return new PageResponse(UrlConstants.SIGN_UP_PAGE);
        }
    }
}
