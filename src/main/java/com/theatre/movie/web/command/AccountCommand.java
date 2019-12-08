package com.theatre.movie.web.command;

import com.theatre.movie.entity.User;
import com.theatre.movie.web.command.response.PageResponse;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
public class AccountCommand implements Command {
    private static final Logger LOG = Logger.getLogger(AccountCommand.class);

    @Override
    public PageResponse execute(HttpServletRequest request) {
        request.setAttribute("activeTab", "account");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        LOG.info("Open account for user: " + user);
        return new PageResponse(UrlConstants.ACCOUNT_PAGE);
    }
}
