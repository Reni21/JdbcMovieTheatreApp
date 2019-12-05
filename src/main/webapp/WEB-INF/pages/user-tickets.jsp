<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>


<html lang="en">
    <head>
        <title>My tickets</title>
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
            <c:if test="${bookings!= null && !bookings.isEmpty()}">
                <c:forEach items="${bookings}" var="booking">
                    <div class="movie-card">
                        <div class="movie-card__container">
                            <div class="movie-description">
                                <div class="movie-cover"/>
                                <h2 class="booking_number">№${booking.bookingId}</h2>
                            </div>
                            <div class="movie-title">${booking.movieName}</div>
                            <p class="movie-duration">Duration: ${booking.movieDuration}min</p>
                            <h3>| Date: ${booking.getFormattedDate()} &nbsp;&nbsp;&nbsp;&nbsp;| Start at: ${booking.getTimeView()} &nbsp;&nbsp;&nbsp;&nbsp;| Hall: ${booking.hallName} &nbsp;&nbsp;&nbsp;&nbsp;|Row: ${booking.row}&nbsp;&nbsp;&nbsp;&nbsp;| Seat: ${booking.place}</h3>
                        </div>
                    </div>
                </c:forEach>
            </c:if>

        </main>
    </body>
</html>


