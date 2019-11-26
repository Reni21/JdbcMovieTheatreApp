<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<jsp:useBean id="menuDates" scope="request" type="java.util.List"/>
<jsp:useBean id="movieSession" scope="request" type="com.theatre.movie.dto.MovieSessionViewDto"/>


<html lang="en">
    <head>
        <title>${movieSession.movieTitle}</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" type="text/css" href="ui/css/booking-styles.css">

    </head>
    <body>
        <header>
            <!-- Common menu -->
            <c:import url="main-menu.jsp"/>
            <!-- Second level menu -->

            <!-- Second level menu -->
            <ul class="nav__second-lvl">
                <c:forEach items="${menuDates}" var="menuDate">
                    <li <c:if
                            test="${menuDate.isActive() && !requestScope['javax.servlet.forward.request_uri'].contains('movie-session')}">
                        class="active"</c:if>>
                        <a href="schedule${menuDate.getIsoDate()}">
                            <fmt:message key="week.day.${menuDate.getDayOfWeek()}"/>
                                ${menuDate.getFormattedDate()}</a></li>
                </c:forEach>
            </ul>
        </header>

        <main>
            <!-- Booking seat block -->
            <div class="content-card">
                <div class="content-card__container">
                    <c:set value="${movieSession}" var="session"/>
                    <div class="page-title">${session.movieTitle}</div>
                    <div class="hall-title">${session.getFormattedDate()} ${session.getFormattedTime()} <img
                            class="clock"
                            aria-hidden="true"
                            src="ui/img/clock.png"
                            alt="Italian"
                            height="20"> ${session.movieDuration}
                        <fmt:message key="schedule.min"/></div>
                    <div class="seatSelection col-lg-12">
                        <div class="seatsChart col-lg-6">
                            <div class="screen"><p>SCREEN</p></div>
                            <c:set value="${session.bookedSeats}" var="bookedSeats"/>
                            <div id="session_price" value="${session.price}"></div>
                            <form id="selectedSeats" method="post" action="booking">
                                <input type="hidden" name="movieSessionId" value="${session.sessionId}">
                                <c:forEach items="${bookedSeats.entrySet()}" var="entrySetSeats">
                                    <div class="seatRow">
                                        <div class="seatRowNumber">Row ${entrySetSeats.getKey()}</div>
                                        <c:forEach items="${entrySetSeats.getValue()}" var="seat">
                                        <div id="${seat.row}_${seat.place}"
                                             role="checkbox"
                                             aria-checked="false" focusable="true"
                                             tabindex="-1"
                                             class="seatNumber <c:if test="${seat.isBooked()}">seatUnavailable"</c:if>">
                                                ${seat.place}
                                        </div>
                                        <label class="checkbox">
                                            <input type="checkbox" id="checkbox_${seat.row}_${seat.place}" class="booked_seats_checkbox"
                                                   name="bookedSeats" value="${seat.getSeatId()}" style="opacity:0; position:absolute; left:9999px;">
                                        </label>
                                            </c:forEach>
                                    </div>
                                </c:forEach>
                            </form>
                        </div>

                        <div class="seatsReceipt col-lg-2">
                            <div class="clear-container">
                                <c:set var="user" value="${sessionScope['user']}"/>
                                <c:choose>
                                    <c:when test="${user == null || user.getRole().toString() == 'USER'}">
                                        <p>Hall ${session.hallName} | Selected: <span class="seatsAmount">0 </span>
                                            <button id="btnClear" class="btn btn-disabled">CLEAR ALL &ensp; <strong
                                                    class="btn-disabled">X</strong></button>
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Hall ${session.hallName} | Booked seats:
                                            <span class="seatsAmount">${session.getBookedSeatsCount()}</span></p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <ul id="seatsList" class="nav nav-stacked"></ul>

                        </div>
                    </div>

                    <div class="checkout col-lg-offset-6">
                        <c:if test="${user == null || user.getRole().toString() == 'USER'}">
                            <span>Total price: </span><span class="txtSubTotal">0.00</span><br/>
                        </c:if>
                        <c:choose>
                            <c:when test="${user == null}">
                                <h3 style="text-align: center; color: gray; margin: 0; padding-top: 15px;">
                                    Log in to book tickets</h3>
                            </c:when>
                            <c:when test="${user != null && user.getRole().toString() == 'ADMIN'}">
                                <button id="btnCheckout" name="btnCheckout" class="btn btn-primary primary-active"
                                        onclick="location.href='movie-session'">Delete movie session
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button id="btnCheckout" name="btnCheckout" class="btn btn-primary primary-active"
                                        type="submit" form="selectedSeats">
                                    Book tickets
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>

                </div>
            </div>
        </main>
        <c:if test="${user == null || user.getRole().toString() == 'USER'}">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
            <script type="text/javascript" src="ui/js/script.js"></script>
        </c:if>
    </body>
</html>


