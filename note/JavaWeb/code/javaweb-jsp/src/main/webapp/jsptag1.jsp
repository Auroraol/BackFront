<%--
  Created by IntelliJ IDEA.
  User: 16658
  Date: 2023/3/23
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>1</h1>
<%--<jsp:include page 拼接--%>
<%--<jsp:include page="jsptag2.jsp"/>--%>

<%--<jsp:forward page= 转发--%>
<jsp:forward page="jsptag2.jsp">
    <jsp:param name="name" value="小明"/>
    <jsp:param name="age" value="12"/>
</jsp:forward>

</body>
</html>