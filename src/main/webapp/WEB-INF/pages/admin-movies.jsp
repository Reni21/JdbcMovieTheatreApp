<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<jsp:useBean id="movies" scope="request" type="java.util.List"/>

<html lang="en">
<head>
    <title>Movies</title>
    <c:import url="head-data.jsp"/>
    <!-- Unique css -->
    <link rel="stylesheet" type="text/css" href="static/css/movies-styles.css">
    <link rel="stylesheet" type="text/css" href="static/css/modal-styles.css">
    <link rel="stylesheet" type="text/css" href="static/css/login-styles.css">
    <link rel="stylesheet" type="text/css" href="static/css/schedule-styles.css">
</head>
<body>

<header>
	<!-- Common menu -->
	<c:import url="main-menu.jsp"/>

	<!-- Second level menu -->
    <ul class="nav__second-lvl">
        <li><a href="#">FIRST</a></li>
        <li><a href="#">PREV</a></li>
        <li><a href="#">1</a></li>
        <li class="active"><a href="#">2</a></li>
        <li><a href="#">3</a></li>
        <li><a href="#">NEXT</a></li>
        <li><a href="#">LAST</a></li>
    </ul>
</header>

<main>
    <!-- Button -->
    <div class="wrapper btn">
        <button type="submit" class="myBtn add-movie openModalBtn">Create new movie</button>
    </div>
    <!-- The Modal for movies-->
    <div id="myModal" class="modal" style="overflow-y: auto !important; padding: 0 !important;">
        <div style="height: 50px"></div>
        <div class="content" style="height: 880px !important; width: 600px">
            <div class="page-title">Create movie</div>
            <!-- Modal content -->
            <div class="modal-content" style="margin-right: 30px;">
                <div class="form__container" style="border: 0;">
                    <form class="form__http-properties" action="movies" method="POST">
                        <!-- Fields -->

                        <p id="name_title" class="field-title">Movie Title</p>
                        <p id="err_title" class="errors" style="color: red;"></p>
                        <input type="text" name="title" class="input" data-error="title"/>

                        <p id="name_directed" class="field-title">Directed by</p>
                        <p id="err_directed" class="errors" style="color: red;"></p>
                        <input type="text" name="directed" class="input" data-error="directed"/>

                        <p id="name_duration" class="field-title">Duration</p>
                        <p id="err_duration" class="errors" style="color: red;"></p>
                        <input type="number" name="duration" class="input"data-error="duration"/>

                        <p id="name_cover" class="field-title">Cover link</p>
                        <p id="err_cover" class="errors" style="color: red;"></p>
                        <input type="link" name="cover_link" class="input"data-error="cover"/>

                        <p id="name_bg" class="field-title">Background link</p>
                        <p id="err_bg" class="errors" style="color: red;"></p>
                        <input type="link" name="bg_link" class="input"data-error="bg"/>

                        <p id="name_trailer" class="field-title">Trailer link</p>
                        <p id="err_trailer" class="errors" style="color: red;"></p>
                        <input type="link" name="trailer_link" class="input" data-error="trailer"/>

                        <!-- Buttons -->
                        <button type="submit" class="signinbutton" style="cursor: pointer;margin-right: 0 !important; margin-left: 0 !important;">Create</button>
                        <button type="button" class="close" style="width: 452px; margin-right: 0 !important; margin-left: 0 !important;">Cancel and Close</button>
                    </form>
                </div>
            </div>
        </div>
        <div style="height: 50px"></div>
    </div>

    <!--
    ===============================================================================================
    1я карточка с фильмами
    ===============================================================================================
-->
    <c:forEach items="${movies}" var="movie">
        <div id="card_${movie.movieId}" class="movie-card">
            <div class="movie-card__container">
                <a href="${movie.trailerUrl}" target="_blank">
                    <div class="movie-cover">
                        <img class="play-icon" src="static/img/play.png" alt="cover"/>
                        <img class="cover-img" src="${movie.coverImgUrl}" alt="cover"/>
                    </div>
                </a>

                <div class="movie-card__background">
                    <img class="background-img"
                         src="${movie.backgroundImgUrl}">
                </div>

                <div class="movie-description">
                    <div class="movie-title">${movie.title}</div>
                    <p class="movie-duration">
                        <fmt:message key="schedule.duration"/>:
                            ${movie.durationMinutes}<fmt:message key="schedule.min"/>
                    </p>
                    <a class="tag" onclick="deleteMovieHandler(${movie.movieId})"
                       style="border-radius: 25px;padding: 10px 25px;font-size: 18px;line-height: 28px;float: right;">Remove movie</a>
                </div>
            </div>
        </div>
    </c:forEach>

</main>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="static/js/movies-modal.js"></script>
</body>
</html>


