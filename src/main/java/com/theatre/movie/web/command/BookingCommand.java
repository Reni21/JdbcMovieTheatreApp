package com.theatre.movie.web.command;

import com.theatre.movie.dto.BookingViewDto;
import com.theatre.movie.entity.User;
import com.theatre.movie.service.BookingService;
import com.theatre.movie.web.dto.CreateBookingRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@AllArgsConstructor
public class BookingCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(BookingCommand.class);
    private BookingService bookingService;

    @Override
    protected CommandResponse performGet(HttpServletRequest request) {
        request.setAttribute("activeTab", "tickets");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<BookingViewDto> bookings = bookingService.getActualUsersBookingById(user.getId());
        LOG.info("Extracted bookings:\n" + bookings);
        request.setAttribute("bookings", bookings);
        return new PageResponse(UrlConstants.USER_TICKETS_PAGE);
    }

    @Override
    protected CommandResponse performPost(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        int movieSessionId = Integer.parseInt(request.getParameter("movieSessionId"));
        String[] bookedSeatsId = request.getParameterValues("bookedSeats");
        CreateBookingRequestDto createBookingRequest = new CreateBookingRequestDto(user.getId(), movieSessionId, bookedSeatsId);
        try {
            bookingService.createBooking(createBookingRequest);
        } catch (Exception ex) {
            LOG.info("Booking creation failed: " + ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            return new PageResponse(UrlConstants.MOVIE_SESSION_PAGE);
        }
        request.setAttribute("activeTab", "account");
        return new PageResponse(request.getContextPath() + "/tickets", true);
    }
}
