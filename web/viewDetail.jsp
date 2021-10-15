<%-- 
    Document   : viewDetail
    Created on : Oct 5, 2021, 8:31:41 PM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Detail Page</title>
        <script src="lib/jquery-3.4.1.js" type="text/javascript"></script>
        <script src="lib/jquery.validate.js" type="text/javascript"></script>
        <script src="lib/additional-methods.js" type="text/javascript"></script>
        <script type="text/javascript">
            $.validator.addMethod('requiredNotBlank', function (value, element) {
                return $.validator.methods.required.call(this, $.trim(value), element);
            }, "Field should not be blank!");
            $().ready(function () {
                $("#commentForm").validate({
                    rules: {
                        comment: {
                            required: true,
                            requiredNotBlank: true
                        },

                    },
                    messages: {
                        comment: {
                            required: "Please enter your comment before summit",
                        },

                    }
                });
            });
        </script>
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
            .form-container {
                max-width: 300px;
                padding: 10px;
                background-color: white;
            }
            .areaMsg {
                width: 50%;
                padding: 15px;
                margin: 5px 0 10px 0;
                border: none;
                background: beige;
                resize: none;
                min-height: 100px;
            }
            .error{
                color:red;
            }
            .content{
                white-space:pre-wrap;
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
                Hello ${sessionScope.ACCOUNT.name}
                <button type="button" onclick="location.href = 'logoutAction'">Logout</button>
                <button type="button" onclick="location.href = 'postArticlePage'">Post an article</button>
                <button type="button" onclick="location.href = 'searchAction'">Home</button>
            </c:if>
            <c:if test="${!isLogin}">
                <button type="button" onclick="location.href = 'searchAction'">Home</button>
                <button type="button" onclick="location.href = 'registerPage'">Register</button>
                <button type="button" onclick="location.href = 'loginPage'">Login</button>
            </c:if>
        </nav>

        <c:if test="${requestScope.ARTICLE != null}" var="isAvailable">
            <div class="card">
                <h2>${requestScope.ARTICLE.title}</h2>
                <h5>Author: ${requestScope.ARTICLE.author}</h5>
                <h5>Posting date: ${requestScope.ARTICLE.postingDate}</h5>
                <h5>${requestScope.ARTICLE.description}</h5>
                <p class="content">${requestScope.ARTICLE.content}</p>
            </div>

            <form action="postCommentAction" id="commentForm" method="POST">
                <label for="msg"><b>Comment</b></label><br/>
                <input hidden="" name="articleId" value="${requestScope.ARTICLE.id}"/>
                <textarea class="areaMsg" placeholder="Type comment..." name="comment" id="comment" <c:if test="${sessionScope.ACCOUNT == null}">disabled=""</c:if>></textarea><br/>
                <button type="submit" <c:if test="${sessionScope.ACCOUNT == null}">disabled="" </c:if>>Send</button>
                <c:if test="${sessionScope.ACCOUNT == null}">
                    <i style="color: red">
                        Login is required if you want to join to communication
                    </i>
                </c:if>
            </form><br/>
            <form action="viewDetailAction">
                <input hidden="" name="articleId" value="${requestScope.ARTICLE.id}"/>
                <c:if test="${PAGES > 0}">
                    <label>Pages:</label>
                    <c:forEach begin="0" end="${PAGES - 1}" var="index" varStatus="counter">
                        <input type="submit" name="indexPageComment" value="${counter.count}" class="${(sessionScope.INDEXPAGECOMMENT - 1) eq index ? "red" : ""}"/>
                    </c:forEach>
                </c:if>
            </form>
            <c:forEach items="${requestScope.LISTCOMMENT}" var="comment" varStatus="counter">
                <div class="column" style="background-color:#aaa;">
                    <h4>${comment.commenter}</h4>
                    <p class="content">${comment.content}</p>
                    <input type="datetime" id="meeting-time"
                           name="meeting-time" value="${comment.postingDate}"
                           disabled=""/>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${!isAvailable}">
            Article not found
        </c:if>
    </body>
</html>
