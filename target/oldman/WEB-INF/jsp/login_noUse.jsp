<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%--<html>--%>
<head>
    <title>管理系统</title>
    <script type="text/javascript">
        var message="${message}";
    </script>

    <script src="<%=request.getContextPath()%>/js/jquery.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/login.js" type="text/javascript"></script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/login" method="post" id="loginForm">
        <label>用户名：</label>
        <input type="text" name="username" /><br/>
        <label>密　码：</label>
        <input type="password" name="password" /><br/>
        &nbsp;<span id="error"></span>
        <input type="submit" value="登录" id="loginBtn" onClick="login()"/>
        <div class="login_info">
            <input type="checkbox" id="autologin" name="autologin" class="autologin" value=""/>
            &nbsp;一周内自动登录
            <input type="hidden" id="autologinch" name="autologinch"  class="autologinch" value=""/>
        </div>
    </form>
</body>
</html>
