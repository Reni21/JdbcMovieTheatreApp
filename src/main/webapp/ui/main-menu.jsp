<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<jsp:useBean id="activeTab" scope="request" type="java.lang.String"/>

<html lang="en">
    <head>
    </head>
    <body>
        <nav class="nav__first-lvl">
            <div class="wrapper">
                <table class="first-lvl__bar-table">
                    <tr>
                        <td>
                            <ul class="first-lvl__main-menu">
                                <c:set var="name" value='${activeTab}'/>
                                <li <c:if test="${name == 'main'}">class="active"</c:if>><a href=""><fmt:message
                                        key="main.menu.home"/></a>
                                </li>
                                <li <c:if test="${name == 'schedule'}">class="active"</c:if>><a
                                        href="schedule"><fmt:message key="main.menu.schedule"/></a>
                                </li>
                                <li <c:if test="${name == 'login'}">class="active"</c:if>><a href="login"><fmt:message
                                        key="main.menu.login"/></a></li>
                            </ul>
                        </td>

                        <td class="lang__switcher">
                            <ul class="first-lvl__lang-menu">
                                <li <c:if test="${sessionScope['locale'] == 'en'}">class="active"</c:if>>
                                    <a href="javascript:document.getElementById('form1').submit()">EN</a>
                                    <form action="<c:url value="/locale"/>" method="post" id="form1">
                                        <input type="hidden" name="locale" value="en"/>
                                    </form>
                                </li>
                                <li <c:if test="${sessionScope['locale'] == 'ru'}">class="active"</c:if>>
                                    <a href="javascript:document.getElementById('form2').submit()">RU</a>
                                    <form action="<c:url value="/locale"/>" method="post" id="form2">
                                        <input type="hidden" name="locale" value="ru"/>
                                    </form>
                                </li>
                            </ul>
                        </td>
                    </tr>
                </table>
            </div>
        </nav>
    </body>
</html>