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
                <button type="submit" class="myBtn add-movie"><fmt:message key="admin.pin.movie"/></button>
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
                <div class="wrapper">
                <div id="errors_${session.movieId}" class="errors" style="font-size: 15px;color: red;margin: 0 auto;position: relative;"></div>
                </div>
                <div class="movie-card">
                    <div class="movie-card__container" style="margin-bottom: 10px;">
                        <div class="movie-cover"/>
                        <button class="btn delete">Remove movie</button>
                    </div>

                    <div id="movie_${session.movieId}" class="movie-description">
                        <div class="movie-title">${session.title}</div>
                        <p class="movie-duration"><fmt:message key="schedule.duration"/>: ${session.duration}<fmt:message key="schedule.min"/></p>
                        <form action="schedule<c:if test="${param.get('date') != null}">?date=${param.get('date')}</c:if>"
                              class="session-form" name="session-form" id="session-form_${session.movieId}" method="post">
                            <input id="movieId_${session.movieId}" type="hidden" name="movieId" value="${session.movieId}"/>
                            <input id="hours_${session.movieId}" class="session-field" type="number" name="hours" placeholder="<fmt:message key="admin.input.hh"/>" min="9" max="22" style="padding: 8px 5px">
                            <input id="minutes_${session.movieId}"class="session-field" type="number" name="minutes" placeholder="<fmt:message key="admin.input.mm"/>" min="0" max="59" style="padding: 8px 5px">
                            <input class="session-field" type="number" name="price" placeholder="<fmt:message key="admin.input.price"/> 0.0" style="padding: 8px 5px">
                            <input type="submit" value="<fmt:message key="admin.add.session"/>" class="add">
                        </form>
                        <c:forEach items="${session.movieSessionTimes}" var="time">
                            <a class="tag" href="movie-session/${time.getMovieSessionId()}">${time.getTimeView()}</a>
                        </c:forEach>

                    </div>
                </div>
            </c:forEach>
        </main>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>
        <script type="text/javascript" src="static/js/modal-script.js"></script>
        <script type="text/javascript">
            var errorsDictionary = new Map([
                ["hoursRequired" , "<fmt:message key='error.hours.required'/>"],
                ["hoursRange" , "<fmt:message key='error.hours.range'/>"],
                ["hoursDigits" , "<fmt:message key='error.hours.digits'/>"],
                ["hoursMaxlength" , "<fmt:message key='error.hours.maxlength'/>"],
                ["minutesRequired" , "<fmt:message key='error.minutes.required'/>"],
                ["minutesRange" , "<fmt:message key='error.minutes.range'/>"],
                ["minutesDigits" , "<fmt:message key='error.minutes.digits'/>"],
                ["minutesMaxlength" , "<fmt:message key='error.minutes.maxlength'/>"],
                ["priceRequired" , "<fmt:message key='error.price.required'/>"],
                ["priceNumber" , "<fmt:message key='error.price.number'/>"],
                ["priceMin" , "<fmt:message key='error.price.min'/>"]
            ]);
        </script>
    </body>
</html>


