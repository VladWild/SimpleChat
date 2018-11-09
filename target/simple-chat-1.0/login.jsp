<%@ page import="datalayer.data.loginerror.TypeLoginError" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div id="page">
    <form name="loginForm" method="post" action="login">
        <div class="rectangle">
            <div class="field-input">
                <div class="username">
                    <span>Username:</span>
                    <input type="text" name="username" value="1${requestScope.userName}">
                    <div class="warn">
                        <c:set var="typeError" value="<%=TypeLoginError.USERNAME_ERROR%>"/>
                        <c:forEach var="error" items='${requestScope["errors"]}'>
                            <c:if test = "${error.getTypeLoginError() == typeError}">
                                <c:out value = "${error.getMessage()}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
                <div class="password">
                    <span>Password:</span>
                    <input type="password" name="password" value="1">
                    <div class="warn">
                    <c:set var="typeError" value="<%=TypeLoginError.PASSWORD_ERROR%>"/>
                    <c:forEach var="error" items='${requestScope["errors"]}'>
                        <c:if test = "${error.getTypeLoginError() == typeError}">
                            <c:out value = "${error.getMessage()}"/>
                        </c:if>
                    </c:forEach>
                    </div>
                </div>
            </div>
            <button class="login">
                <span>Login</span>
            </button>
        </div>
    </form>
</div>
</body>
</html>