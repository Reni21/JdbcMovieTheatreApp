package com.theatre.movie.web.command;

import javax.servlet.http.HttpSession;

import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.web.PageData;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.theatre.movie.web.command.UrlConstants.NOT_FOUND_PAGE;

public class AccountCommand implements Command {
    private static final Logger LOG = Logger.getLogger(AccountCommand.class);

    @Override
    public PageData execute(HttpServletRequest request) {
        request.setAttribute("activeTab", "account");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Role role = user.getRole();
        LOG.info("User role=" + role);
        if (Role.ADMIN.equals(role)) {
            return new PageData(UrlConstants.ADMIN_ACCOUNT_PAGE);
        } else if(Role.USER.equals(role)){
            return new PageData(UrlConstants.USER_ACCOUNT_PAGE);
        }
        return new PageData(NOT_FOUND_PAGE); // todo: throw exception?
    }
}
