<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="sessions" scope="request" type="java.util.List"/>
<jsp:useBean id="menuDates" scope="request" type="java.util.List"/>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Cinema World</title>
        <%--        <base href="${pageContext.request.contextPath}">--%>

        <link href="https://fonts.googleapis.com/css?family=Muli:300,300i,400,400i,600,600i,700,700i,800,800i&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/schedule-styles.css">
        <!-- main css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/ui/css/index-styles.css">
    </head>
    <body>
        <header>
            <!--
            ===============================================================================================
            Common menu
            ===============================================================================================
        -->
            <nav class="nav__first-lvl">
                <div class="wrapper">

                    <table class="first-lvl__bar-table">
                        <tr>
                            <td>
                                <ul class="first-lvl__main-menu">
                                    <li class="active"><a href="${pageContext.request.contextPath}/app/schedule${menuDates.get(0).getIsoDate()}">Schedule</a>
                                    </li>
                                    <li><a href="${pageContext.request.contextPath}/app/login">Login</a></li>
                                </ul>
                            </td>

                            <td class="lang__switcher">
                                <ul class="first-lvl__lang-menu">
                                    <li class="active">
                                        <a href="#">EN</a>
                                    </li>
                                    <li>
                                        <a href="#">RU</a>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                    </table>
                </div>
            </nav>
            <!--
            ===============================================================================================
            Extra menu
            ===============================================================================================
        -->
            <ul class="nav__second-lvl">
                <c:forEach items="${menuDates}" var="menuDate">
                    <li <c:if test="${menuDate.isActive()}">class="active"</c:if>>
                        <a href="${pageContext.request.contextPath}/app/schedule${menuDate.getIsoDate()}">${menuDate.getDateOfWeek()}</a></li>
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
                                <img class="play-icon" src="${pageContext.request.contextPath}/ui/img/play.png" alt="cover"/>
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
                                <a class="tag" href="seats-booking.html">${time.getTimeView()}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </main>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/ui/js/script.js"></script>
    </body>
</html>


