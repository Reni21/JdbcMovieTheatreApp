<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title><fmt:message key="index.label"/></title>
        <base href="${pageContext.request.contextPath}/"/>

        <link href="https://fonts.googleapis.com/css?family=Muli:300,300i,400,400i,600,600i,700,700i,800,800i&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="static/css/index-styles.css">
    </head>
    <body onload="textEffect('fly-in-out');">
        <img class="bg" src="https://i.pinimg.com/originals/23/97/91/239791aa7eaee867743b065dc3d3ffb7.jpg">
        <header>
            <!-- Common menu -->
            <c:import url="main-menu.jsp"/>
        </header>
        <main class="title">
            <p class=""><fmt:message key="main.animation"/></p>
        </main>
        <script type="text/javascript" src="static/js/index-script.js"></script>
    </body>
</html>
