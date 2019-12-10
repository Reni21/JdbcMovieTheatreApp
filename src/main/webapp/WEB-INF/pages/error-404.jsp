<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>

<!doctype html>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<html lang="en">
    <head>
        <title>Error</title>
        <c:import url="head-data.jsp"/>
        <!-- Unique css -->
        <link rel="stylesheet" href="static/css/login-styles.css">
    </head>
    <body>
        <div id="js-particles" style="position: fixed;width: 100%;height: auto;z-index: 0;"></div>
        <header>
        </header>
        <main>
            <div style="z-index: 1;">
                <div style="margin: 0 auto;width: 960px;height: auto;position: relative;">
                    <div class="page-title" style="font-size: 125px;margin-bottom: 35px">404</div>
                    <div class="page-title">SORRY! NOT FOUND</div>
                    <div class="form__container">
                        <p class="error-msg" style="font-size: 18px;font-weight: 600;color: grey;text-align: center;">
                            Error message for 404 page is here
                        </p>
                        <a href="">
                            <button class="signinbutton">Home page</button>
                        </a>
                    </div>
                </div>
            </div>
        </main>
        <script src='static/js/particlesmin.js'></script>
        <script type="text/javascript" src="static/js/error-particles.js"></script>
    </body>
</html>


