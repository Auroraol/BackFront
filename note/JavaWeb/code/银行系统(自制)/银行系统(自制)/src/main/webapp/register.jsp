<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>register</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
    <link href="plugins/bootstrap-5.1.3/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,700">
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
                    <h1>欢迎注册</h1>
                  </div>
                  <p>银行网银系统</p>
                </div>
              </div>
            </div>
            <!-- Form Panel    -->
            <div class="col-lg-6 bg-white">
              <div class="form d-flex align-items-center">
                <div class="content">
                    <div class="form-group">
                      <input id="register-username" class="input-material" type="text" name="registerUsername" placeholder="请输入用户名/姓名" >
								      <div class="invalid-feedback">
								        	用户名必须在2~10位之间
								      </div>
                    </div>
                    <div class="form-group">
                      <input id="register-password" class="input-material" type="password" name="registerPassword" placeholder="请输入密码"   >
                    	<div class="invalid-feedback">
								        	密码必须在6~10位之间
								      </div>
                    </div>
                    <div class="form-group">
                      <input id="register-passwords" class="input-material" type="password" name="registerPasswords" placeholder="确认密码"   >
                    	<div class="invalid-feedback">
								        	两次密码必须相同 且在6~10位之间
								      </div>
                    </div>
                    <div class="form-group">
                      <button id="regbtn" type="button" name="registerSubmit" class="btn btn-primary">注册</button>
                    </div>
                  <small>已有账号?</small><a href="login.jsp" class="signup">&nbsp;登录</a>
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
    <script>
      /*
      Ajax：异步刷新 局部刷新
          1、Ajax具备发请求，提交数据的功能。提交数据有两种方式get post
          2、提交的数据需要我们手动的拼接键值对。需要我们手动设置要提交数据的name属性
          3、接触到一个新的数据格式：json串（实际上就是一组一组的键值对）。利用第三方的工具包，简化拼接json串的操作
      我们接下来要使用的Ajax，JavaScript支持的一种语法，原生Ajax；
      我们在这里使用的Ajax是JQuery提供的Ajax的支持。
   */

    	$(function(){
    		/*错误class  form-control is-invalid
    		正确class  form-control is-valid*/
    		var flagName=false;
    		var flagPas=false;
    		var flagPass=false;
    		/*验证用户名*/
    		var name,passWord,passWords;
    		$("#register-username").change(function(){
    			name=$("#register-username").val();
    			if(name.length<2||name.length>10){
    				$("#register-username").removeClass("form-control is-valid")
    				$("#register-username").addClass("form-control is-invalid");
    				flagName=false;
    			}else{
    				$("#register-username").removeClass("form-control is-invalid")
    				$("#register-username").addClass("form-control is-valid");
    				flagName=true;
    			}
    		})
    		/*验证密码*/
    		$("#register-password").change(function(){
    			passWord=$("#register-password").val();
    			if(passWord.length<6||passWord.length>18){
    				$("#register-password").removeClass("form-control is-valid")
    				$("#register-password").addClass("form-control is-invalid");
    				flagPas=false;
    			}else{
    				$("#register-password").removeClass("form-control is-invalid")
    				$("#register-password").addClass("form-control is-valid");
    				flagPas=true;
    			}
    		})
    		/*验证确认密码*/
    		$("#register-passwords").change(function(){
    			passWords=$("#register-passwords").val();
    			if((passWord!=passWords)||(passWords.length<6||passWords.length>18)){
    				$("#register-passwords").removeClass("form-control is-valid")
    				$("#register-passwords").addClass("form-control is-invalid");
    				flagPass=false;
    			}else{
    				$("#register-passwords").removeClass("form-control is-invalid")
    				$("#register-passwords").addClass("form-control is-valid");
    				flagPass=true;
    			}
    		})

    		//按键点击
    		$("#regbtn").click(function(){

    			if(flagName&&flagPas&&flagPass){
    				localStorage.setItem("name",name);
    				localStorage.setItem("passWord",passWord);
    				// location="login.jsp"  //跳转到login页面
    			} else{
    				if(!flagName){
    					$("#register-username").addClass("form-control is-invalid");
    				}
    				if(!flagPas){
    					$("#register-password").addClass("form-control is-invalid");
    				}
    				if(!flagPass){
    					$("#register-passwords").addClass("form-control is-invalid");
    				}
    			}
              //使用$.ajax()发送异步请求
              $.ajax({
                url:"register" , // 请求路径
                type:"POST" , //请求方式
                data:{"userName":name,"passWord":passWord},
                success:function (data) {
                  window.location.href = "login.jsp"; // 重定向到login.jsp页面
                },//响应成功后的回调函数
                error:function () {
                  alert("出错啦...")
                },//表示如果请求响应出现错误，会执行的回调函数
                dataType:"text"//设置接受到的响应数据的格式
              });

            })
    	})
    </script>
  </body>
</html>