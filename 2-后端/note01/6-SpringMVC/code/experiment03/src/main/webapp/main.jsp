<%--
  Created by IntelliJ IDEA.
  User: 29070
  Date: 2024/1/6
  Time: 8:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页</title>
    <style>
        .box1,.box3{
            background-color: #333333;
            color: white;
            padding: 10px;
            text-align: center;
        }
        .box2{
            height: 80%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        a{
            padding: 10px 20px;
            border: 2px solid black;
            color: black;
            text-decoration: none;
            margin: 20px;
        }
    </style>
</head>
<body>
<%--页眉--%>
<div class="box1">
    <h1>欢迎来到你的日记</h1>
</div>
<%--主体--%>
<div class="box2">
    <a href="add_diary.jsp">添加日记</a>
    <a href="QueryDiary">查询日记</a>
</div>
<%--页脚--%>
<div class="box3">
    <p>2024 你的日记</p>
</div>
</body>
</html>
