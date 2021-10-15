<%-- 
    Document   : index
    Created on : Sep 29, 2021, 7:39:27 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <style>
            .Title{
                padding: 30px;
                font-size: 40px;
                text-align: center;
                background: bisque;
            }
            * {
                box-sizing: border-box;
            }

            /* Create two equal columns that floats next to each other */
            .column {
                width: 48%;
                padding: 10px;
                margin: 1%
            }
            .red{
                color: red;
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
                <button type="button" onclick="location.href = 'logoutAction'">Logout</button>
                <button type="button" onclick="location.href = 'postArticlePage'">Post an article</button>
            </c:if>
            <c:if test="${!isLogin}">
                <button type="button" onclick="location.href = 'registerPage'">Register</button>
                <button type="button" onclick="location.href = 'loginPage'">Login</button>
            </c:if>
        </nav><br/>

        <form action="searchAction" method="GET">
            <table>
                <tbody>
                    <tr>
                        <td>Search by content:</td>
                        <td>
                            <input name="txtSearch" value="${sessionScope.SEARCH}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" value="Search"/>
                        </td>
                    </tr>
                </tbody>
            </table>
            <br/>
            <c:if test="${PAGES > 0}">
                Pages:
                <c:forEach begin="0" end="${PAGES - 1}" var="index" varStatus="counter">
                    <input type="submit" name="indexPage" value="${counter.count}" class="${(sessionScope.INDEXPAGE - 1) eq index ? "red" : ""}"/>
                </c:forEach>
            </c:if>
        </form>
        <c:if test="${requestScope.LIST != null}" var="isEmpty">
            <c:if test="${not empty requestScope.LIST}">
                <div class="row">
                    <c:forEach items="${requestScope.LIST}" var="article" varStatus="counter">
                        <c:url value="viewDetailAction" var="viewDetail">
                            <c:param name="articleId" value="${article.id}"></c:param>
                        </c:url>
                        <div class="column" style="background-color:#aaa;">
                            <h2><a href="${viewDetail}">${article.title}</a></h2>
                            <i>Author: ${article.author}</i>
                            <p>${article.description}</p>
                            <p>Date: ${article.postingDate}</p>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </c:if>
        <c:if test="${!isEmpty}">
            Article not found
        </c:if>
    </body>
</html>
