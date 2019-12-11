<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html lang="en">
    <head>
        <title><fmt:message key="login.label" /></title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" href="static/css/login-styles.css">
    </head>
    <body>
        <header>
            <!-- Common menu -->
            <c:import url="main-menu.jsp"/>
            <!-- Second level menu -->
            <ul class="nav__second-lvl">
                <li class="active"><a href="login"><fmt:message key="second.menu.login" /></a></li>
                <li><a href="sign-up"><fmt:message key="second.menu.signup" /></a></li>
            </ul>
        </header>
        <main>
            <div class="content-card">
                <div class="content-card__container">
                    <div class="page-title"><fmt:message key="login.label" /></div>
                    <div class="form__container">
                        <form class="form__http-properties" name="loginForm" action="login" method="POST" onsubmit="return validateLoginForm()">
                            <!-- Fields -->
                            <p class="field-title"><fmt:message key="login.username.title" />*</p>
                            <input type="text" name="username" class="input" placeholder="" maxlength="15" required=""/>


                            <p class="field-title"><fmt:message key="login.password.title" />*</p>
                            <input type="password" name="password" class="input" placeholder="" maxlength="15" required=""/>

                            <c:if test="${not empty error}">
                                <h4 style="text-align: center; color: red">${error}</h4>
                            </c:if>

                            <!-- Button -->
                            <button type="submit" class="signinbutton" value="login"><fmt:message key="login.label" /></button>
                        </form>
                    </div>
                </div>
            </div>
        </main>
        <script type="text/javascript" src="static/js/validate-forms-script.js"></script>
    </body>
</html>


