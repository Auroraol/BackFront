<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <!-- 引入 Bootstrap -->
  <script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
  <!-- 引入 font-awesome -->
  <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="application/javascript">
    function change(url,index){
      $(".list-group-item").removeClass("active");
      $(".list-group-item").eq(index).addClass("active");
      $("iframe").attr("src",url);
    }
  </script>
</head>
<body>
<nav class="navbar navbar-inverse" role="navigation">
  <div class="container-fluid">
    <ul class="nav navbar-nav navbar-left">
      <li>
        <a style="font-size: 26px;">银行网银系统-系统管理员</a>
      </li>
    </ul>
    <span style="color: #CCCCCC;font-size: 26px;position: relative;top: 5px;"></span>
    <ul class="nav navbar-nav navbar-right">
      <li>
        <a>欢迎用户【${admin.username}】</a>
      </li>
      <li>
        <a href="/netBank/account?method=logout">安全退出</a>
      </li>
    </ul>
  </div>
</nav>
<div class="container-fluid">
  <div class="row">
    <div class="col-sm-2">

      <a href="javascript:void(0)" class="list-group-item active" onclick="change('/netBank/accountEnquiry?method=list',0)">
						<span class="" aria-hidden="true">
							<i class="fa fa-user-circle-o fa-fw"></i>
						</span>账户查询
      </a>
      <a href="javascript:void(0)" class="list-group-item" onclick="change('/student?method=list',1)">
						<span class="" aria-hidden="true">
							<i class="fa fa-user-circle fa-fw"></i>
						</span>已冻结账户
      </a>
      <a href="javascript:void(0)" class="list-group-item" onclick="change('/building?method=list',2)">
						<span class="" aria-hidden="true">
							<i class="fa fa-home fa-fw"></i>
						</span>已启用账户
      </a>
<%--        /netBank/admin/add.jsp--%>
      <a href="javascript:void(0)" class="list-group-item" onclick="change('/netBank/accountEnquiry?method=add', 3)">
						<span class="" aria-hidden="true">
							<i class="fa fa-bed fa-fw"></i>
						</span>申请开户
      </a>
      <a href="javascript:void(0)" class="list-group-item" onclick="change('/moveout?method=list',4)">
						<span class="" aria-hidden="true">
							<i class="fa fa-address-card-o fa-fw"></i>
						</span>修改个人密码
      </a>
<%--      <a href="javascript:void(0)" class="list-group-item" onclick="change('/moveout?method=record',5)">--%>
<%--						<span class="" aria-hidden="true">--%>
<%--							<i class="fa fa-bookmark fa-fw"></i>--%>
<%--						</span>学生迁出记录--%>
<%--      </a>--%>
<%--      <a href="javascript:void(0)" class="list-group-item" onclick="change('/absent?method=list',6)">--%>
<%--						<span class="" aria-hidden="true">--%>
<%--							<i class="fa fa-bookmark-o fa-fw"></i>--%>
<%--						</span>学生缺寝记录--%>
<%--      </a>--%>

    </div>
    <!--右边内容-->
    <iframe style="width: 81%; height: 600px; border: 0px;" src="/netBank/accountEnquiry?method=list"></iframe>
  </div>
</div>
<div class="footer">
  <p class="text-center">
    Copyright© 2023 All Rights Reserved.
  </p>
</div>
</body>
</html>
