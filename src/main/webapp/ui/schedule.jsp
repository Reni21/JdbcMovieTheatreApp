<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
            <!--
            ===============================================================================================
            Common menu
            ===============================================================================================
        -->
            <title>Movies schedule</title>
            <jsp:include page="../WEB-INF/main-menu.jspf" />
            <!--
            ===============================================================================================
            Second level menu
            ===============================================================================================
        -->
            <ul class="nav__second-lvl">
                <c:forEach items="${menuDates}" var="menuDate">
                    <li <c:if test="${menuDate.isActive()}">class="active"</c:if>>
                        <a href="app/schedule${menuDate.getIsoDate()}">${menuDate.getDateOfWeek()}</a></li>
                </c:forEach>
            </ul>
            </div>
        </header>

        <main>
            <!--
            ===============================================================================================
            Movie card
            ===============================================================================================
        -->
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
                            <p class="movie-duration">Duration: ${session.duration}min</p>

                            <c:forEach items="${session.movieSessionTimes}" var="time">
                                <a class="tag" href="ui/seats-booking.html">${time.getTimeView()}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </main>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="ui/js/script.js"></script>
    </body>
</html>


