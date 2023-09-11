<html>
<body>
<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<h2>Hello World!</h2>

<%--内置对象--%>
<%
    pageContext.setAttribute("name1", "小明1");    // 保存的数据只在一个页面中有效
    request.setAttribute("name2", "小明2");            // 保存的数据一次请求中有效，请求转发会携带这个数据
	session.setAttribute("name3", "小明3");            // 保存的数据只在一次会话中有效, 从打开浏览器到关闭浏览器
    application.setAttribute("name4", "小明4");     // 保存的数据只在服务器中有效，从打开浏览器到关闭浏览器
%>

<%
    //从pageContext取出，通过寻找的方式来取
    String name1 =(String)pageContext.findAttribute("name1");
    String name2 =(String)pageContext.findAttribute("name2");
    String name3 =(String)pageContext.findAttribute("name3");
    String name4 =(String)pageContext.findAttribute("name4");
    //String name5 =(String)pageContext.findAttribute("name5");            //  不存在
    out.println(name1);
%>

</body>
</html>
