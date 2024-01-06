<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: 29070
  Date: 2024/1/6
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>展示日记</title>
    <style>
        .box1,.box3{
            background-color: #333333;
            color: white;
            padding: 10px;
            text-align: center;
        }
        .box2{
            height: 80%;
            text-align: center;
        }
        .box2-1{
            display: flex;
            justify-content: center;
        }
        .diary{
            border-bottom: 2px solid black;
            padding: 10px;
        }
        .title,.date,.name{
            width: 100px;
        }
        .content{
            width: 300px;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
        }
        .i{
            width: 20px;
        }
        #msg{
            font-size: 20px;
            color: red;
        }
        .update{
            background-color: #01b301;
            border: none;
        }
        .update a{
            color: white;
            text-decoration: none;
        }
        .del{
            background-color: red;
            border: none;
            margin-left: 20px;
        }
        .del a{
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
<%
    //java域 写java代码
    Object object = request.getAttribute("list");
    List<Map<String,Object>> list = (List<Map<String,Object>>)object;
    String msg;
    if (list.isEmpty()){
        //空的
        msg = "您查询的数据为空";
    }else {
        //不为空
        msg = "";
    }
%>
<%--页眉--%>
<div class="box1">
    <h1>欢迎来到你的日记</h1>
    <form action="QueryDiary" method="post"><input type="text" name="query" placeholder="搜索日记"></form>
</div>
<%--主体--%>
<div class="box2">
    <p id="msg"><%=msg%></p>
    <%
        int i = 1;
        for (Map<String,Object> map:list){
    %>
    <div class="box2-1">
        <div class="diary i"><%=i++%>、</div>
        <div class="diary title"><%=map.get("title")%></div>
        <div class="diary date"><%=map.get("date")%></div>
        <div class="diary content"><%=map.get("content")%></div>
        <div class="diary name"><%=map.get("name")%></div>
        <div class="diary"><button class="update"><a href="update_diary.jsp?id=<%=map.get("id")%>&title=<%=map.get("title")%>&content=<%=map.get("content")%>">修改</a></button></div>
        <div class="diary"><button class="del" onclick="return del()"><a href="DelDiary?id=<%=map.get("id")%>">删除</a></button></div>
    </div>
    <%
        }
    %>
</div>
<div class="box3">
    <p>2024 你的日记</p>
</div>
<script>
    function del() {
        let  boolean = window.confirm("是否确认删除")
        if (boolean){
            return true
        }else {
            return false
        }
    }
</script>
</body>
</html>
