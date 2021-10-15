<%-- 
    Document   : admin
    Created on : Oct 2, 2021, 2:21:54 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
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
        <script>
            function changeSelect() {
                var x = document.querySelector('input[name="SearchOpt"]:checked').value;
                if (x === 'SearchByArticleName') {
                    document.getElementById('txtSearchArticleName').disabled = false;
                    document.getElementById('txtSearchContent').disabled = true;
                    document.getElementById('txtSearchContent').value = null;
                } else if (x === 'SearchByContent') {
                    document.getElementById('txtSearchContent').disabled = false;
                    document.getElementById('txtSearchArticleName').disabled = true;
                    document.getElementById('txtSearchArticleName').value = null;
                }
            }
        </script>
    </head>
    <body onload="changeSelect()">
        <div class="Title">
            <h1>Simple Blog</h1>
        </div>
        <br/>
        <h1>ADMIN</h1>
        <nav>
            <c:if test="${sessionScope.ACCOUNT != null}" var="isLogin">
                <label>Hello ${sessionScope.ACCOUNT.name}</label>
                <button type="button" onclick="location.href = 'logoutAction'">Logout</button>
            </c:if>
            <c:if test="${!isLogin}">
                <button type="button" onclick="location.href = 'registerPage'">Register</button>
                <button type="button" onclick="location.href = 'loginPage'">Login</button>
            </c:if>
        </nav><br/>

        <form action="adminSearchAction" method="GET">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <input type="radio" name="SearchOpt" value="SearchByArticleName" onclick="changeSelect()" <c:if test="${sessionScope.OPTSEARCH eq 'SearchByArticleName'}">checked</c:if>>
                            </td>
                            <td>Search by article name:</td>
                            <td>
                                <input name="txtSearchArticleName" id="txtSearchArticleName" value="${sessionScope.ADMINSEARCHARTICLE}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="radio" name="SearchOpt" value="SearchByContent" onclick="changeSelect()" <c:if test="${sessionScope.OPTSEARCH eq 'SearchByContent'}">checked</c:if>>
                            </td>
                            <td>Search by content:</td>
                            <td>
                                <input name="txtSearchContent" id="txtSearchContent" value="${sessionScope.ADMINSEARCHCONTENT}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Status:</td>
                        <td>
                            <select name="status" id="status">
                                <c:if test="${sessionScope.SELECTEDSTATUS eq '1'}" var="selectedStatus">
                                    <option selected value="1">New</option>
                                </c:if>
                                <c:if test="${!selectedStatus}">
                                    <option value="1">New</option>
                                </c:if>
                                <c:if test="${sessionScope.SELECTEDSTATUS eq '2'}" var="selectedStatus">
                                    <option selected value="2">Active</option>
                                </c:if>
                                <c:if test="${!selectedStatus}">
                                    <option value="2">Active</option>
                                </c:if>
                                <c:if test="${sessionScope.SELECTEDSTATUS eq '3'}" var="selectedStatus">
                                    <option selected value="3">Deleted</option>
                                </c:if>
                                <c:if test="${!selectedStatus}">
                                    <option value="3">Deleted</option>
                                </c:if>
                            </select>
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
                        <c:url value="adminViewDetailAction" var="viewDetail">
                            <c:param name="articleId" value="${article.id}"></c:param>
                        </c:url>
                        <div class="column" style="background-color:#aaa;">
                            <h2><a href="${viewDetail}">${article.title}</a></h2>
                            <i>Author: ${article.author}</i>
                            <p>${article.description}</p>
                            <p>Date: ${article.postingDate}</p>
                            <div>
                                <c:url value="adminApproveAction" var="approveArticle">
                                    <c:param name="articleId" value="${article.id}" ></c:param>
                                </c:url>
                                <c:url value="adminDeleteAction" var="deleteArticle">
                                    <c:param name="articleId" value="${article.id}" ></c:param>
                                </c:url>
                                <script>
                                    function confirmDelete() {
                                        var z = confirm("Are you sure to delete this article?");
                                        if (z === true) {
                                            return location.href = "${deleteArticle}"
                                        }
                                    }
                                </script>
                                <c:if test="${article.status == 1}">
                                    <button style="color: green" onclick="location.href = '${approveArticle}'">Approve</button>
                                </c:if>
                                <c:if test="${article.status != 3}">
                                    <button style="color: red" onclick="return confirmDelete()">Delete</button>
                                </c:if>
                            </div>
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
