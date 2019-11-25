<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html lang="en">
    <head>
        <title>Error</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" href="ui/css/login-styles.css">
    </head>
    <body>
        <header>
        </header>
        <main>
            <div class="content-card">
                <div class="content-card__container">
                    <div class="page-title">${requestScope["javax.servlet.error.status_code"]} NOT FOUND</div>
                    <div class="form__container">
                        <p class="error-msg" style="font-size: 18px;font-weight: 600;color: grey;text-align: center;">
                            <c:if test="${not empty error}">${error}</c:if>
                        </p>
                        <a href="">
                            <button class="signinbutton">Home page</button>
                        </a>
                    </div>
                </div>
            </div>
        </main>
    </body>
</html>


