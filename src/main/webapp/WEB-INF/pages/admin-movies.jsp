<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<jsp:useBean id="movies" scope="request" type="java.util.List"/>

<html lang="en">
<head>
    <title><fmt:message key="admin.movies.label" /></title>
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

    <c:set value="${requestScope['currentPage']}" var="currentPage"/>
    <c:set value="${requestScope['pagesCount']}" var="pagesCount"/>

    <!-- Second level menu -->
    <ul class="nav__second-lvl">

        <c:forEach begin="1" end="${pagesCount}" var="i">
            <li <c:if test="${currentPage == i}" > class="active"</c:if>>
                <a href="movies?page=${i}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</header>

<main>
    <!-- Button -->
    <div class="wrapper btn">
        <button type="submit" class="myBtn add-movie openModalBtn"><fmt:message key="admin.movies.modal.button" /></button>
    </div>
    <!-- The Modal for movies-->
    <div id="myModal" class="modal" style="overflow-y: auto !important; padding: 0 !important;">
        <div style="height: 50px"></div>
        <div class="content" style="height: 880px !important; width: 600px">
            <div class="page-title"><fmt:message key="admin.movies.modal.title" /></div>
            <!-- Modal content -->
            <div class="modal-content" style="margin-right: 30px;">
                <div class="form__container" style="border: 0;">
                    <form class="form__http-properties" action="movies" method="POST">
                        <!-- Fields -->

                        <p id="name_title" class="field-title"><fmt:message key="admin.movies.form.title.field" /></p>
                        <p id="err_title" class="errors" style="color: red;"></p>
                        <input type="text" name="title" class="input" data-error="title"/>

                        <p id="name_directed" class="field-title"><fmt:message key="admin.movies.form.directed.field" /></p>
                        <p id="err_directed" class="errors" style="color: red;"></p>
                        <input type="text" name="directed" class="input" data-error="directed"/>

                        <p id="name_duration" class="field-title"><fmt:message key="admin.movies.form.duration.field" /></p>
                        <p id="err_duration" class="errors" style="color: red;"></p>
                        <input type="number" name="duration" class="input" data-error="duration"/>

                        <p id="name_cover" class="field-title"><fmt:message key="admin.movies.form.cover.field" />* <i style="font-weight: normal; font-size: 13px">(<fmt:message key="admin.movies.link" /> http)</i></p>
                        <p id="err_cover" class="errors" style="color: red;"></p>
                        <input type="link" name="cover_link" class="input" data-error="cover"/>

                        <p id="name_bg" class="field-title"><fmt:message key="admin.movies.form.bg.field" />* <i style="font-weight: normal; font-size: 13px">(<fmt:message key="admin.movies.link" /> http)</i></p>
                        <p id="err_bg" class="errors" style="color: red;"></p>
                        <input type="link" name="bg_link" class="input" data-error="bg"/>

                        <p id="name_trailer" class="field-title"><fmt:message key="admin.movies.form.trailer.field" />* <i style="font-weight: normal; font-size: 13px">(<fmt:message key="admin.movies.link" /> http)</i></p>
                        <p id="err_trailer" class="errors" style="color: red;"></p>
                        <input type="link" name="trailer_link" class="input" data-error="trailer"/>

                        <!-- Buttons -->
                        <button type="submit" class="signinbutton"
                                style="cursor: pointer;margin-right: 0 !important; margin-left: 0 !important;"><fmt:message key="admin.movies.modal.save" />
                        </button>
                        <button type="button" class="close"
                                style="width: 452px; margin-right: 0 !important; margin-left: 0 !important;"><fmt:message key="admin.movies.modal.close" />
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <div style="height: 50px"></div>
    </div>

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
                            ${movie.durationMinutes}
                        <fmt:message key="schedule.min"/>
                    </p>
                    <a class="tag" onclick="deleteMovieHandler(${movie.movieId})"
                       style="border-radius: 25px;padding: 10px 25px;font-size: 18px;line-height: 28px;float: right;"><fmt:message key="admin.movies.remove.movie" /></a>
                </div>
            </div>
        </div>
    </c:forEach>

</main>
<script src="static/js/jquery/jquery-3.4.1.min.js"></script>
<script src="static/js/jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="static/js/movie-creation-modal.js"></script>
    <script type="text/javascript">
        var msgDictionary = new Map([
            ["remove", "<fmt:message key="admin.movies.remove.movie" />"],
            ["min", "<fmt:message key="schedule.min"/>"],
            ["duration", "<fmt:message key="schedule.duration"/>"],
            ["empty", "<fmt:message key="admin.movies.modal.empty"/>"]
        ]);
    </script>
</body>
</html>


