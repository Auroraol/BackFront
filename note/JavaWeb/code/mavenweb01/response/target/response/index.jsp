<html>

<body>
<h2>Hello World!</h2>
<!--中文-->
<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>

<!--如果跳转失败证明jsp页面还不支持el表达式，最简单的方法是在jsp上面加上-->
<%@ page isELIgnored="false"%>
<!--表单-->
<%--这里的路径，需要项目的路径+url路径
${ pageContext.request.contextPath} 代表当前项目
--%>
<form action="${pageContext.request.contextPath}/login" method="get">
    <p>名字: <input type="text" name="username" ></p>
    <p>密码: <input type="password" name="password" id="password"></p>
    <p> <input type="submit"></p>
</form>

</body>
</html>
