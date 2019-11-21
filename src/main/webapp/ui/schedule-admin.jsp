<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html lang="en">
    <head>
        <title>Movies schedule</title>
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
                <button type="submit" class="add-movie">Add movie</button>
            </div>
            <!--
            ===============================================================================================
            карточка с фильмами
            ===============================================================================================
        -->
            <div class="movie-card">
                <div class="movie-card__container">
                    <div class="movie-cover"/>
                    <button class="btn delete">Remove movie</button>
                </div>

                <div class="movie-description">
                    <div class="movie-title">The Battle of the Five Armies</div>
                    <p class="movie-duration">Duration: 120min</p>

                    <a class="tag" href="movie-session">9:00</a>
                    <a class="tag" href="movie-session">13:30</a>
                    <a class="tag" href="movie-session">16:00</a>
                    <a class="tag" href="movie-session">18:00</a>
                    <a class="tag" href="movie-session">20:30</a>

                    <a class="add" href="#"><b>+</b> SESSION</a>
                </div>
            </div>
            </div>
            <!--
        ===============================================================================================
        карточка с фильмами
        ===============================================================================================
    -->
            <div class="movie-card">
                <div class="movie-card__container">
                    <div class="movie-cover"/>
                    <button class="btn delete">Remove movie</button>
                </div>

                <div class="movie-description">
                    <div class="movie-title">The Battle of the Five Armies</div>
                    <p class="movie-duration">Duration: 120min</p>

                    <a class="tag" href="movie-session">9:00</a>
                    <a class="tag" href="movie-session">13:30</a>
                    <a class="tag" href="movie-session">16:00</a>
                    <a class="tag" href="movie-session">18:00</a>
                    <a class="tag" href="movie-session">20:30</a>

                    <a class="add" href="#"><b>+</b> SESSION</a>
                </div>
            </div>
            </div>
            <!--
        ===============================================================================================
        карточка с фильмами
        ===============================================================================================
    -->
            <div class="movie-card">
                <div class="movie-card__container">
                    <div class="movie-cover"/>
                    <button class="btn delete">Remove movie</button>
                </div>

                <div class="movie-description">
                    <div class="movie-title">The Battle of the Five Armies</div>
                    <p class="movie-duration">Duration: 120min</p>

                    <a class="tag" href="movie-session">9:00</a>
                    <a class="tag" href="movie-session">13:30</a>
                    <a class="tag" href="movie-session">16:00</a>
                    <a class="tag" href="movie-session">18:00</a>
                    <a class="tag" href="movie-session">20:30</a>

                    <a class="add" href="#"><b>+</b> SESSION</a>
                </div>
            </div>
            </div>

            <!--
    ===============================================================================================
    карточка с фильмами
    ===============================================================================================
-->
            <div class="movie-card">
                <div class="movie-card__container">
                    <div class="movie-cover"/>
                    <button class="btn delete">Remove movie</button>
                </div>

                <div class="movie-description">
                    <div class="movie-title">The Battle of the Five Armies</div>
                    <p class="movie-duration">Duration: 120min</p>

                    <a class="tag" href="movie-session">9:00</a>
                    <a class="tag" href="movie-session">13:30</a>
                    <a class="tag" href="movie-session">16:00</a>
                    <a class="tag" href="movie-session">18:00</a>
                    <a class="tag" href="movie-session">20:30</a>

                    <a class="add" href="#"><b>+</b> SESSION</a>
                </div>
            </div>
            </div>
        </main>
    </body>
</html>


