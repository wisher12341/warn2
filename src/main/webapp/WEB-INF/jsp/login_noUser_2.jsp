<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<script type="text/javascript">
	var pathJs = "${path}"; //在JS文件中使用  JS环境不能用EL表达式
	var message="${message}";
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
    <head>
        <title>长者关怀系统</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="Expand, contract, animate forms with jQuery wihtout leaving the page" />
        <meta name="keywords" content="expand, form, css3, jquery, animate, width, height, adapt, unobtrusive javascript"/>
		<%--<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon"/>--%>
        <link rel="stylesheet" type="text/css" href="${path}/css/style.css" />
		<script src="${path}/js/jquery.min.js" type="text/javascript"></script>
		<%--<script src="#{path}/js/login.js" type="text/javascript"></script>--%>
		<script src="${path}/js/cufon-yui.js" type="text/javascript"></script>
		<script src="${path}/js/ChunkFive_400.font.js" type="text/javascript"></script>
		<script type="text/javascript">
			Cufon.replace('h1',{ textShadow: '1px 1px #fff'});
			Cufon.replace('h2',{ textShadow: '1px 1px #fff'});
			Cufon.replace('h3',{ textShadow: '1px 1px #000'});
			Cufon.replace('.back');
		</script>
    </head>
    <body>
		<div class="wrapper">
			<div class="content">
				<div id="form_wrapper" class="form_wrapper">
					<form class="login active" action="${path}/login" method="post">
						<h3>Login</h3>
						<div>
							<label>Username:</label>
							<input type="text" name="username"/>
						</div>
						<div>
							<label>Password:</label>
							<input type="password" name="password"/>
							<span id="error"></span>
						</div>
						<div class="bottom">
							<div class="remember"><input type="checkbox" name="autologin" id="autologin"/><span>Keep me logged in</span></div>
							<input type="submit" value="Login"/>
							<div class="clear"></div>
						</div>
					</form>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		

		<!-- The JavaScript -->

		<script type="text/javascript">
			$(function() {
				//错误信息
				if(message!=null){
					$("#error").html(message);
				}
			});

//			function login(){
//				if($(".autologin").is(":checked")){
//					$(".autologinch").val("Y");
//				}
//				$("#loginForm").submit();
//			}
        </script>
    </body>
</html>