<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>


<html lang="en">
    <head>
        <title>Login Movies World</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" type="text/css" href="ui/css/schedule-admin-styles.css">
    </head>
    <body>
        <header>
            <!-- Common menu -->
            <c:import url="main-menu.jsp"/>
            <!-- Second level menu -->
            <ul class="nav__second-lvl">
                <li class="active"><a href="#">MY TICKETS</a></li>
            </ul>
        </header>
        <main>

            <%--            <c:if test="${allPages ne null}">--%>
            <%--                <c:forEach var="i" begin="1" end="${allPages}">--%>
            <%--                    <li><a href="?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>--%>
            <%--                </c:forEach>--%>
            <%--            </c:if>--%>
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


