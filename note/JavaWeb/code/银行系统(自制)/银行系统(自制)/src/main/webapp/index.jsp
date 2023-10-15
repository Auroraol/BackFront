<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>网上银行</title>
    <!-- Bootstrap Css -->
    <link href="plugins/bootstrap-5.1.3/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/index.css">
    <script src="plugins/bootstrap-5.1.3/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<br>
<div class="container">
    <div class="alert alert-success alert-dismissible fade show">
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        <strong>欢迎用户【${user.username}】使用网上银行系统</strong>
        <a href="balance" class="alert-link">查看余额</a>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
