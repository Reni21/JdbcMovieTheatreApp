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
            <div class="movie-card">
                <div class="movie-card__container">
                    <div class="movie-description">
                        <div class="movie-cover"/>
                        <div><h2><p class="booking_number">№23123</p></h2></div>
                    </div>
                    <div class="movie-title">The Battle of the Five Armies</div>
                    <p class="movie-duration">Duration: 120min</p>
                    <h3>| Date: 21.10 &nbsp;&nbsp;&nbsp;&nbsp;| Start at: 9:00 &nbsp;&nbsp;&nbsp;&nbsp;|Seat: 12 &nbsp;&nbsp;&nbsp;&nbsp;| Row:2</h3>
                </div>
            </div>
        </main>
    </body>
</html>


