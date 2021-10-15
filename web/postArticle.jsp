<%-- 
    Document   : postArticle
    Created on : Oct 6, 2021, 5:00:08 AM
    Author     : Shang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Artical Page</title>
        <script src="lib/jquery-3.4.1.js" type="text/javascript"></script>
        <script src="lib/jquery.validate.js" type="text/javascript"></script>
        <script src="lib/additional-methods.js" type="text/javascript"></script>
        <script type="text/javascript">
            // jQuery Validator method for required not blank.
            $.validator.addMethod('requiredNotBlank', function (value, element) {
                return $.validator.methods.required.call(this, $.trim(value), element);
            }, "Field should not be blank!");

            $().ready(function () {
                $("#postArticalForm").validate({
                    rules: {
                        title: {
                            required: true,
                            requiredNotBlank: true,
                            rangelength: [2, 200]
                        },
                        description: {
                            required: true,
                            requiredNotBlank: true,
                            rangelength: [2, 500]
                        },
                        content: {
                            required: true,
                            requiredNotBlank: true,
                        },
                    },
                    messages: {
                        title: {
                            required: "Field must not be blank",
                            rangelength: "Title length must be from 2 to 200 characters"
                        },
                        description: {
                            required: "Field must not be blank",
                            rangelength: "Description length must be from 2 to 500 characters"
                        },
                        content: {
                            required: "Field must not be blank",
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
            .areaMsg {
                width: 96%;
                padding: 15px;
                margin: 5px 0 10px 0;
                border: 1;
                background: aquamarine;
                resize: none;
                min-height: 100px;
            }
            input{
                width: 100%;
            }
            .error{
                color:red;
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
                <button type="button" onclick="location.href = 'searchAction'">Home</button>
            </c:if>
            <c:if test="${!isLogin}">
                <button type="button" onclick="location.href = 'searchAction'">Home</button>
                <button type="button" onclick="location.href = 'registerPage'">Register</button>
                <button type="button" onclick="location.href = 'loginPage'">Login</button>
            </c:if>
        </nav>
        <br/>
        <form action="postArticleAction" id="postArticalForm" method="POST">
            <table style="width:40%" >
                <tbody>
                    <tr>
                        <td><b>Title:</b></td>
                        <td>
                            <input name="title" id="title"/>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Short Description:</b></td>
                        <td>
                            <input name="description" id="description"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <textarea class="areaMsg" placeholder="Type comment..." name="content" id="content"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" style="height: 50px" value="Post"/>
                        </td>
                    </tr>

                </tbody>
            </table>



        </form>


    </body>
</html>
