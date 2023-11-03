<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>login</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap Css -->
    <link href="plugins/bootstrap-5.1.3/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="css/style.default.css" id="theme-stylesheet">
  </head>
  <body>
    <div class="page login-page">
      <div class="container d-flex align-items-center">
        <div class="form-holder has-shadow">
          <div class="row">
            <!-- Logo & Information Panel-->
            <div class="col-lg-6">
              <div class="info d-flex align-items-center">
                <div class="content">
                  <div class="logo">
                    <h1>欢迎登录</h1>
                  </div>
                  <p>银行网银系统</p>
                </div>
              </div>
            </div>
            <!-- Form Panel    -->
            <div class="col-lg-6 bg-white">
<%--              <c:if test="${not empty failMsg && not empty fn:trim(failMsg)}">--%>
<%--                    <div class="alert alert-danger" role="alert">${failMsg }</div>--%>
<%--              </c:if>--%>
            <%
                String failMsg = (String) request.getAttribute("failMsg");
                if (Objects.nonNull(failMsg) && !failMsg.trim().isEmpty()) {
            %>
            <div class="alert alert-danger" role="alert">
                <%= failMsg %>
            </div>
            <%
                }
            %>
              <div class="form d-flex align-items-center">
                <div class="content">
                  <form action="<%= request.getContextPath()%>/account?method=login" class="form-validate" id="loginFrom" method="post">
                    <div class="form-group">
<%--                      <span style="color: red;font-size: 13px;margin-left: -17px;">  ${userNameError}</span>--%>
                      <input id="login-username" type="text" name="userName"  required data-msg="请输入用户名" placeholder="用户名"
                              class="input-material">
                    </div>
                    <div class="form-group">
<%--                      <span style="color: red;font-size: 13px;margin-left: -17px;">  ${passWordError}</span>--%>
                      <input id="login-password" type="password" name="passWord"  required data-msg="请输入密码" placeholder="密码"
                               class="input-material">
                    </div>
                      <div class="form-group">
                      <label class="radio-inline">
                          <input type="radio" name="type" checked value="admin" class="radio-inline" checked> 管理员
                      </label>
                      <label class="radio-inline">
                          <input type="radio" name="type" value="user" class="radio-inline" > 用户
                      </label>
                  </div>
                    <button id="login" type="submit" class="btn btn-primary">登录</button>
                    <div style="margin-top: -40px;">
                    	<div class="custom-control custom-checkbox " style="float: right;">
                                <input type="checkbox" class="custom-control-input" id="check2" >
                                <label class="custom-control-label" for="check2">自动登录</label>
                        </div>
                        <div class="custom-control custom-checkbox " style="float: right;">
                            <input type="checkbox" class="custom-control-input" id="check1" >
                            <label class="custom-control-label" for="check1">记住密码&nbsp;&nbsp;</label>
                        </div>
                    </div>
                  </form>
                  <br />
                  <small>没有账号?</small><a href="register.jsp" class="signup">&nbsp;注册</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- JavaScript files-->
    <script src="https://www.jq22.com/jquery/jquery-1.10.2.js"></script>
    <script src="plugins/bootstrap-5.1.3/js/bootstrap.min.js"></script>
    <script src="vendor/jquery-validation/jquery.validate.min.js"></script><!--表单验证-->
    <!-- Main File-->
    <script src="js/front.js"></script>
    <script>
    	$(function(){
    		/*判断上次是否勾选记住密码和自动登录*/
    		var check1s=localStorage.getItem("check1");
    		var check2s=localStorage.getItem("check2");
    		var oldName=localStorage.getItem("userName");
    		var oldPass=localStorage.getItem("passWord");
    		if(check1s=="true"){
    			 $("#login-username").val(oldName);
    			 $("#login-password").val(oldPass);
    			 $("#check1").prop('checked',true);
    		}else{
    			 $("#login-username").val('');
    			 $("#login-password").val('');
    			 $("#check1").prop('checked',false);
    		}
    		if(check2s=="true"){
    			$("#check2").prop('checked',true);
    			$("#loginFrom").submit();
    			//location="https://www.baidu.com?userName="+oldName+"&passWord="+oldPass;//添加退出当前账号功能
    		}else{
    			$("#check2").prop('checked',false);
    		}
    		/*拿到刚刚注册的账号*/
    		/*if(localStorage.getItem("name")!=null){
    			$("#login-username").val(localStorage.getItem("name"));
    		}*/
    		/*登录*/
    		$("#login").click(function(){
    			var userName=$("#login-username").val();
    			var passWord=$("#login-password").val();
    			/*获取当前输入的账号密码*/
    			localStorage.setItem("userName",userName)
    			localStorage.setItem("passWord",passWord)
    			/*获取记住密码  自动登录的 checkbox的值*/
    			var check1 = $("#check1").prop('checked');
	    		var check2 = $('#check2').prop('checked');
	    		localStorage.setItem("check1",check1);
	    		localStorage.setItem("check2",check2);
    		})
    		
    		/*$("#check2").click(function(){
    			var flag=$('#check2').prop('checked');
    			if(flag){
    				var userName=$("#login-username").val();
	    			var passWord=$("#login-password").val();
	    			$.ajax({
	    				type:"post",
	    				url:"http://localhost:8080/powers/pow/regUsers",
	    				data:{"userName":userName,"passWord":passWord},
	    				async:true,
	    				success:function(res){
	    					alert(res);
	    				}
	    			});
    			}
    		})*/
    	})
    </script>
  </body>
</html>