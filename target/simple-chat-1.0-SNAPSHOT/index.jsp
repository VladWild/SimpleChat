<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div id="page">
    <form name="loginForm" method="post" action="./login">
        <div class="rectangle">
            <div class="field-input">
                <div class="username">
                    <span>Username:</span>
                    <input type="text" name="username" value="1${requestScope.userName}">
                    <div class="warn">${requestScope.userNameError}</div>
                </div>
                <div class="password">
                    <span>Password:</span>
                    <input type="password" name="password" value="1">
                    <div class="warn">${requestScope.passwordError}</div>
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