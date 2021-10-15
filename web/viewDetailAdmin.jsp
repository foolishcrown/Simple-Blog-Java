<%-- 
    Document   : viewDetailAdmin
    Created on : Oct 6, 2021, 4:06:08 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin View Detail Page</title>
        <style>
            .Title{
                padding: 30px;
                font-size: 40px;
                text-align: center;
                background: bisque;
            }
            .card {
                background-color: white;
                padding: 20px;
                margin-top: 20px;
            }
            .column {
                width: 48%;
                padding: 10px;
                margin: 1%
            }
            .content{
                white-space:pre-wrap;
            }
            div button{
                height: 50px;
                width: 150px;
            }

        </style>
    </head>
    <body>
        <div class="Title">
            <h1>Simple Blog</h1>
        </div>
        <br/>
        <h1>ADMIN</h1>
        <nav>
            <c:if test="${sessionScope.ACCOUNT != null}" var="isLogin">
                <label>Hello ${sessionScope.ACCOUNT.name}</label>
                <button type="button" onclick="location.href = 'logoutAction'">Logout</button>
                <button type="button" onclick="location.href = 'adminSearchAction'">Admin Home</button>
            </c:if>
            <c:if test="${!isLogin}">
                <button type="button" onclick="location.href = 'registerPage'">Register</button>
                <button type="button" onclick="location.href = 'loginPage'">Login</button>
            </c:if>
        </nav><br/>
        <c:if test="${requestScope.ARTICLE != null}" var="isAvailable">
            <div class="card">
                <div>
                    <c:url value="adminApproveAction" var="approveArticle">
                        <c:param name="articleId" value="${requestScope.ARTICLE.id}" ></c:param>
                    </c:url>
                    <c:url value="adminDeleteAction" var="deleteArticle">
                        <c:param name="articleId" value="${requestScope.ARTICLE.id}" ></c:param>
                    </c:url>
                    <script>
                        function confirmDelete() {
                            var z = confirm("Are you sure to delete this article?");
                            if (z === true) {
                                return location.href = "${deleteArticle}"
                            }
                        }
                    </script>

                    <c:if test="${requestScope.ARTICLE.status == 1}">
                        <button style="color: green" onclick="location.href = '${approveArticle}'">Approve</button>
                    </c:if>
                    <c:if test="${requestScope.ARTICLE.status != 3}">
                        <button style="color: red" onclick="return confirmDelete()">Delete</button>
                    </c:if>
                </div>
                <h2>${requestScope.ARTICLE.title}</h2>
                <h5>Author: ${requestScope.ARTICLE.author}</h5>
                <h5>Posting date: ${requestScope.ARTICLE.postingDate}</h5>
                <h5>${requestScope.ARTICLE.description}</h5>
                <p class="content">${requestScope.ARTICLE.content}</p>
            </div>

        </c:if>
        <c:if test="${!isAvailable}">
            <h3>Article not found</h3>
        </c:if>
    </body>
</html>
