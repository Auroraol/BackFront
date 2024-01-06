<%--
  Created by IntelliJ IDEA.
  User: 29070
  Date: 2024/1/6
  Time: 8:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>登录页面</title>
    <style>
      body{
        background-color: gainsboro;
      }
      .box{
        width: 500px;
        height: 300px;
        margin: 200px auto;
        text-align: center;
        background-color: white;
        border-radius: 10px;
        box-shadow: 0 0 10px rgb(0, 0, 0 );
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
      button{
        width: 260px;
        height: 30px;
        background-color: #0066ff;
        color: white;
        border: none;
      }
      h2{
        padding: 20px;
      }
    </style>
  </head>
  <body>
  <div class="box">
    <form action="LoginServlet" method="post">
      <h2>登录</h2>
      <p><input type="text" name="id" placeholder="账号"></p>
      <p><input type="password" name="password" placeholder="密码"></p>
      <p><button type="submit">登录</button></p>
    </form>
  </div>
  </body>
</html>
