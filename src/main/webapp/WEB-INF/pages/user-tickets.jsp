<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>


<html lang="en">
    <head>
        <title><fmt:message key="tickets.label" />s</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" type="text/css" href="static/css/schedule-admin-styles.css">
    </head>
    <body>
        <header>
            <!-- Common menu -->
            <c:import url="main-menu.jsp"/>
        </header>
        <main>
            <c:set value="${requestScope['bookings']}" var="bookings"/>
            <c:if test="${bookings == null || bookings.isEmpty()}">
                <h2 style="text-align: center; color: gray; padding-top: 150px;">
                    <fmt:message key="tickets.empty" /></h2>
            </c:if>
            <c:if test="${bookings!= null && !bookings.isEmpty()}">
                <c:forEach items="${bookings}" var="booking">
                    <div class="movie-card">
                        <div class="movie-card__container">
                            <div class="movie-description">
                                <div class="movie-cover"/>
                                <h2 class="booking_number">№${booking.bookingId}</h2>
                            </div>
                            <div class="movie-title">${booking.movieName}</div>
                            <p class="movie-duration"><fmt:message key="tickets.duration" />: ${booking.movieDuration}<fmt:message key="schedule.min" /></p>
                            <h3>| <fmt:message key="tickets.date" />: ${booking.getFormattedDate()} &nbsp;&nbsp;&nbsp;&nbsp;| <fmt:message key="tickets.start" />: ${booking.getTimeView()} &nbsp;&nbsp;&nbsp;&nbsp;| <fmt:message key="tickets.hall" />: ${booking.hallName} &nbsp;&nbsp;&nbsp;&nbsp;| <fmt:message key="tickets.row" />: ${booking.row}&nbsp;&nbsp;&nbsp;&nbsp;| <fmt:message key="tickets.seat" />: ${booking.place}</h3>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </main>
    </body>
</html>


