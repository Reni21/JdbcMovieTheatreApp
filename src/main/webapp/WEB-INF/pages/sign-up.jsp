<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html lang="en">
    <head>
        <title>Sign-up Cinema World</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" href="static/css/login-styles.css">
    <body>
        <header>
            <!-- Common menu -->
            <c:import url="main-menu.jsp"/>
            <!-- Second level menu -->
            <ul class="nav__second-lvl">
                <li><a href="login">LOGIN</a></li>
                <li class="active"><a href="sign-up">SIGN UP</a></li>
            </ul>
        </header>


        <main>
            <div class="content-card">
                <div class="content-card__container">
                    <div class="page-title">Sign up</div>


                    <div class="form__container">
                        <form class="form__http-properties" name="signUpForm" action="sign-up" method="POST" onsubmit="return validateSignUpForm()">

                            <!-- Fields -->

                            <p class="field-title">Username* <i style="font-weight: normal; font-size: 13px">(Only english letter or number)</i></p>
                            <input type="text" name="username" class="input" placeholder="" maxlength="15" required=""/>

                            <p class="field-title">Password* <i style="font-weight: normal; font-size: 13px">(At least one upper case letter and digit)</i></p>
                            <input type="password" name="password" class="input" placeholder="" maxlength="15" required=""/>

                            <p class="field-title">Email*</p>
                            <input type="email" name="email" class="input" placeholder="" maxlength="25" required=""/>

                            <c:if test="${not empty error}">
                                <h4 style="text-align: center; color: red">${error}</h4>
                            </c:if>

                            <!-- Button -->
                            <button type="submit" class="signinbutton" value="sign-up">Sign up</button>
                        </form>
                    </div>
                </div>
            </div>
        </main>
        <script type="text/javascript" src="static/js/validate-forms-script.js"></script>
    </body>
</html>


