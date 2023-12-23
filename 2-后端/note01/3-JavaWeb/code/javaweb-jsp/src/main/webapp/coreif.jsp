<%--
  Created by IntelliJ IDEA.
  User: 16658
  Date: 2023/3/23
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="coreif.jsp" method="get">
    <%--
       EL表达式获取表单中的数据
       ${param.参数名}
  --%>
    <input type="text" name="username" value="${}">
</form>
</body>
</html>
