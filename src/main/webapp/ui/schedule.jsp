<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<jsp:useBean id="sessions" scope="request" type="java.util.List"/>
<jsp:useBean id="menuDates" scope="request" type="java.util.List"/>

<html lang="en">
    <head>
        <title>Movies schedule</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" type="text/css" href="ui/css/schedule-styles.css">
    </head>
    <body>
        <header>
            <!-- Common menu -->
            <c:import url="main-menu.jsp"/>

            <!-- Second level menu -->
            <ul class="nav__second-lvl">
                <c:forEach items="${menuDates}" var="menuDate">
                    <li <c:if test="${menuDate.isActive()}">class="active"</c:if>>
                        <a href="schedule${menuDate.getIsoDate()}">
                            <fmt:message key="week.day.${menuDate.getDayOfWeek()}"/>
                                ${menuDate.getFormattedDate()}</a></li>
                </c:forEach>
            </ul>
        </header>

        <main>
            <!--
            ===============================================================================================
            Movie card
            ===============================================================================================
        -->
            <c:if test="${sessions.size() == 0}">
                <h2 style="text-align: center; color: gray; padding-top: 150px;">
                    No movies sessions in this date.<br> Check out it later.</h2>
            </c:if>
            <c:forEach items="${sessions}" var="session">
                <div class="movie-card">
                    <div class="movie-card__container">
                        <a href="${session.trailerUrl}" target="_blank">
                            <div class="movie-cover">
                                <img class="play-icon" src="ui/img/play.png" alt="cover"/>
                                <img class="cover-img"
                                     src="${session.coverImgPath}"
                                     alt="cover"/>
                            </div>
                        </a>

                        <div class="movie-card__background">
                            <img class="background-img"
                                 src="${session.backgroundImgPath}">
                        </div>

                        <div class="movie-description">
                            <div class="movie-title">${session.title}</div>
                            <p class="movie-duration">
                                <fmt:message key="schedule.duration"/>:
                                    ${session.duration}<fmt:message key="schedule.min"/>
                            </p>

                            <c:forEach items="${session.movieSessionTimes}" var="time">
                                <a class="tag" href="movie-session/${time.getMovieSessionId()}">${time.getTimeView()}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </main>
    </body>
</html>


