<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<jsp:useBean id="sessions" scope="request" type="java.util.List"/>

<html lang="en">
    <head>
        <title>Movies schedule</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" type="text/css" href="static/css/schedule-admin-styles.css">
        <link rel="stylesheet" type="text/css" href="static/css/modal-styles.css">
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
            <!-- Button -->
            <div class="wrapper btn">
                <button type="submit" class="myBtn add-movie">Add movie</button>
            </div>

            <!-- The Modal for movies-->
            <div id="myModal" class="modal">
                <div class="content">
                    <div class="page-title">Login</div>
                    <!-- Modal content -->
                    <div class="modal-content">

                        <form id="selectedMovies" method="post" action="movie">
                            <label class="container">One
                                <input class="remember" type='checkbox' name='movie_ids[]' value='1' id='checkbox_1'/>
                                <span class="checkmark"></span>
                            </label>
                            <label class="container">Two
                                <input class="remember" type='checkbox' name='movie_ids[]' value='2' id='checkbox_2'/>
                                <span class="checkmark"></span>
                            </label>
                            <label class="container">Three
                                <input class="remember" type='checkbox' name='movie_ids[]' value='3' id='checkbox_3'/>
                                <span class="checkmark"></span>
                            </label>
                        </form>
                    </div>

                    <button id="btnCheckout" name="confirm" class="signinbutton"
                            type="submit" form="selectedMovies" onclick="submit_form();">Confirm
                    </button>
                    <button class="close">Cancel and close</button>
                </div>
            </div>


            <c:forEach items="${sessions}" var="session">
                <div class="movie-card">
                    <div class="movie-card__container">
                        <div class="movie-cover"/>
                        <button class="btn delete">Remove movie</button>
                    </div>

                    <div id="movie_${session.movieId}" class="movie-description">
                        <div class="movie-title">${session.title}</div>
                        <p class="movie-duration">Duration: ${session.duration}min</p>
                        <form action="schedule<c:if test="${param.get('date') != null}">?date=${param.get('date')}</c:if>"
                              class="session-form" method="post">
                            <input id="movieId_${session.movieId}" type="hidden" name="movieId" value="${session.movieId}"/>
                            <input id="hours_${session.movieId}" class="session-field" type="number" name="hours" placeholder="hh" maxlength="2"
                                   min="9" max="22" style="padding: 8px 5px">
                            <input id="minutes_${session.movieId}"class="session-field" type="number" name="minutes" placeholder="mm" maxlength="2"
                                   min="0" max="60" style="padding: 8px 5px">
                            <input class="session-field" type="number" name="price" placeholder="Price 0.0" style="padding: 8px 5px">
                            <input type="submit" value="Add session" class="add">
                        </form>
                        <c:forEach items="${session.movieSessionTimes}" var="time">
                            <a class="tag" href="movie-session/${time.getMovieSessionId()}">${time.getTimeView()}</a>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </main>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script type="text/javascript" src="static/js/modal-script.js"></script>
    </body>
</html>


