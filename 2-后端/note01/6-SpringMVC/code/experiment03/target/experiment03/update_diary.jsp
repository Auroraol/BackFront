<%--
  Created by IntelliJ IDEA.
  User: 29070
  Date: 2024/1/6
  Time: 8:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加页面</title>
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
        input{
            width: 260px;
            height: 30px;
            outline: none;
        }
        input:focus{
            box-shadow: 0 0 10px rgb(0, 95, 227);
            border: 1px solid deepskyblue;
        }
        textarea{
            width: 260px;
            height: 100px;
            resize: none;
        }
        button{
            width: 260px;
            height: 30px;
            background-color: deepskyblue;
            color: white;
            border: none;
        }
        button:hover{
            background-color: #0145c1;
        }
    </style>
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");
    String id = request.getParameter("id");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
%>
<%--页眉--%>
<div class="box1">
    <h1>欢迎来到你的日记</h1>
</div>
<%--主体--%>
<div class="box2">
    <form action="AddDiary" method="post">
        <input type="hidden" value="<%=id%>">
        <p><input type="text" name="title" value="<%=title%>"></p>
        <p><textarea name="content"><%=content%></textarea></p>
        <p><button type="submit">修改</button></p>
    </form>
</div>

<div class="box3">
    <p>2024 你的日记</p>
</div>
</body>
</html>
