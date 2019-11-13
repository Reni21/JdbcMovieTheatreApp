<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Cinema World</title>
    <base href="${pageContext.request.contextPath}/ui/">

	<link href="https://fonts.googleapis.com/css?family=Muli:300,300i,400,400i,600,600i,700,700i,800,800i&display=swap" rel="stylesheet">
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
							<li class="active"><a href="${pageContext.request.contextPath}/app/schedule">Schedule</a></li>
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
		<li><a href="#">MON 01.12</a></li>
		<li class="active"><a href="#">TU 02.12</a></li>
		<li><a href="#">WED 03.12</a></li>
		<li><a href="#">TH 04.12</a></li>
		<li><a href="#">FR 05.12</a></li>
		<li><a href="#">ST 06.12</a></li>
		<li><a href="#">SUN 07.12</a></li>
	</ul>
</div>
</header>

<main>
		<!--
		===============================================================================================
		Movie card
		===============================================================================================
	-->
	<div class="movie-card">
		<div class="movie-card__container">
			<a href="#"><div class="movie-cover">
				<img class="play-icon" src="img/play.png" alt="cover"/>
				<img class="cover-img" src="https://upload.wikimedia.org/wikipedia/ru/8/87/Ringstrilogyposter.jpg" alt="cover"/>
			</div></a>

			<div class="movie-card__background">
				<img class="background-img" src="https://s16.stc.all.kpcdn.net/share/i/12/10249794/inx960x640.jpg">
			</div> 

			<div class="movie-description"> 
				<div class="movie-title">The Battle of the Five Armies</div>
				<p class="movie-duration">Duration: 120min</p>  

				<a class="tag" href="seats-booking.html">9:00</a>
				<a class="tag" href="seats-booking.html">13:30</a>
				<a class="tag" href="seats-booking.html">16:00</a>
				<a class="tag" href="seats-booking.html">18:00</a>
				<a class="tag" href="seats-booking.html">20:30</a>
			</div> 
		</div>
	</div>
</main>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/script.js"></script>
</body>
</html>


