<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>


<html lang="en">
    <head>
        <title>My account</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" href="static/css/login-styles.css">
    </head>
    <body>
        <header>
            <!-- Common menu -->
            <c:import url="main-menu.jsp"/>
            <!-- Second level menu -->
        </header>
        <main>
            <!--
            ===============================================================================================
            Форма для заполения
            ===============================================================================================
        -->
            <div class="content-card">
                <div class="content-card__container">
                    <div class="page-title">Hello ${sessionScope['user'].username}</div>
                    <div class="form__container">
                    </div>
                </div>
            </div>
        </main>
    </body>
</html>


