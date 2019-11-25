package com.theatre.movie.web.command;

import com.theatre.movie.entity.User;
import com.theatre.movie.service.BookingService;
import com.theatre.movie.web.PageData;
import com.theatre.movie.web.dto.CreateBookingRequestDto;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
public class BookingCommand implements Command {
    private static final Logger LOG = Logger.getLogger(BookingCommand.class);
    private BookingService bookingService;

    @Override
    public PageData execute(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
        int movieSessionId = Integer.parseInt(request.getParameter("movieSessionId"));
        String[] bookedSeatsId = request.getParameterValues("bookedSeats");
        CreateBookingRequestDto createBookingRequest = new CreateBookingRequestDto(user.getId(),movieSessionId,bookedSeatsId);
        try {
            bookingService.createBooking(createBookingRequest);
        } catch (Exception ex){
            LOG.info("Booking creation failed: " + ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            return new PageData(UrlConstants.MOVIE_SESSION_PAGE);
        }
        request.setAttribute("activeTab", "account");
        return new PageData(request.getContextPath()+"/account/" + user.getLogin(), true);
    }
}
