package com.theatre.movie.web.command;

import javax.servlet.http.HttpSession;

import com.theatre.movie.dto.BookingViewDto;
import com.theatre.movie.entity.Role;
import com.theatre.movie.entity.User;
import com.theatre.movie.service.BookingService;
import com.theatre.movie.service.ServiceFactory;
import com.theatre.movie.web.PageData;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@AllArgsConstructor
public class AccountCommand implements Command {
    private static final Logger LOG = Logger.getLogger(AccountCommand.class);
    private BookingService bookingService = ServiceFactory.getBookingService();

    @Override
    public PageData execute(HttpServletRequest request) {
        request.setAttribute("activeTab", "account");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Role role = user.getRole();
        LOG.info("User role=" + role);
        if (Role.ROLE_ADMIN.equals(role)) {
            return new PageData(UrlConstants.ADMIN_ACCOUNT_PAGE);
        } else if (Role.ROLE_USER.equals(role)) {
            List<BookingViewDto> bookings = bookingService.getActualUsersBookingById(user.getId());
            LOG.info("Extracted bookings:\n" + bookings);
            request.setAttribute("bookings" , bookings);
            return new PageData(UrlConstants.USER_ACCOUNT_PAGE);
        }
        return new PageData(request.getContextPath() + "/403-error", true);
    }
}
