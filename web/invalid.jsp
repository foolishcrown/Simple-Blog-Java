<%-- 
    Document   : invalid
    Created on : Oct 4, 2021, 6:34:38 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invalid Page</title>
        <style>
            .Title{
                padding: 30px;
                font-size: 40px;
                text-align: center;
                background: bisque;
            }
        </style>
    </head>
    <body>
        <div class="Title">
            <h1>Simple Blog</h1>
        </div>
        <br/>
        <nav>
            <c:if test="${sessionScope.ACCOUNT != null}" var="isLogin">
                <label>Hello ${sessionScope.ACCOUNT.name}</label>
                <c:if test="${sessionScope.ACCOUNT.role == 1}" var="isAdmin">
                    <button type="button" onclick="location.href = 'logoutAction'">Logout</button>
                    <button type="button" onclick="location.href = 'adminSearchAction'">Admin Home</button>
                </c:if>
                <c:if test="${sessionScope.ACCOUNT.role == 2}" var="isMember">
                    <button type="button" onclick="location.href = 'logoutAction'">Logout</button>
                    <button type="button" onclick="location.href = 'searchAction'">Home</button>
                </c:if>  
            </c:if>
            <c:if test="${!isLogin}">
                <button type="button" onclick="location.href = 'searchAction'">Home</button>
                <button type="button" onclick="location.href = 'registerPage'">Register</button>
                <button type="button" onclick="location.href = 'loginPage'">Login</button>
            </c:if>
        </nav><br/>
        <h1>Invalid page</h1>
        <c:if test="${requestScope.INVALID != null}">
            <h3 style="color: red">Error: ${requestScope.INVALID}</h3>
        </c:if>
    </body>
</html>
