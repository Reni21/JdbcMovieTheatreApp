package com.theatre.movie.web.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PinMovieCommand extends MultipleMethodCommand {
    private static final Logger LOG = Logger.getLogger(PinMovieCommand.class);

    @Override
    protected CommandResponse performGet(HttpServletRequest request) {
        return null;
    }

    @Override
    protected CommandResponse performPost(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
//        int movieSessionId = Integer.parseInt(request.getParameter("movieSessionId"));
//        String[] pinedMoviesId = request.getParameterValues("movie_ids");
//        CreateBookingRequestDto createBookingRequest = new CreateBookingRequestDto(user.getId(),movieSessionId,bookedSeatsId);
//        try {
//            movieSessionService.createBooking(createBookingRequest);
//        } catch (Exception ex){
//            LOG.info("Booking creation failed: " + ex.getMessage());
//            request.setAttribute("error", ex.getMessage());
//            return new PageResponse(UrlConstants.MOVIE_SESSION_PAGE);
//        }
//        request.setAttribute("activeTab", "account");
//        return new PageResponse(request.getContextPath()+"/account/" + user.getLogin(), true);

        return null;
    }
}
