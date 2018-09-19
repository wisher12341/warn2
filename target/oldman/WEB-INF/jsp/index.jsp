<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<%@ page import="com.warn.entity.User"%>
<%
    User user = (User)request.getSession().getAttribute("USER");
%>


<script type="text/javascript">
    var pathJs = "${path}"; //在JS文件中使用  JS文件环境不能用EL表达式
    var message="${message}";
    var username="${username}";
    var user="${user}";
    var role="${role}";
</script>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>长者关怀</title>
    <link rel="shortcut icon" href="${path}/images/title.ico">
    <link rel="stylesheet" href="${path}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/css/index.css">
    <link href="${path}/easyUI_insdep/themes/insdep/easyui.css" rel="stylesheet" type="text/css">
    <link href="${path}/easyUI_insdep/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">

    <%--dwr--%>
    <script type='text/javascript' src="${path}/dwr/engine.js"></script>
    <script type='text/javascript' src="${path}/dwr/util.js"></script>
    <script type='text/javascript' src="${path}/dwr/interface/Remote.js"></script>

</head>
<body style="overflow: hidden">
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#" id="titleLogo">长者关怀</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active" id="index"><a href="/">首页</a></li>
                <%--<li><a href="#" class="loginD" style="display: none">信息查询</a></li>--%>
                <%--<li><a href="#" class="loginD" style="display: none">信息展示</a></li>--%>
                <%--<li><a href="#" class="loginD" style="display: none">日志记录</a></li>--%>
                <%--<li><a href="#" class="loginD" style="display: none">历史记录</a></li>--%>
            </ul>
            <form class="navbar-form navbar-right" action="${path}/login" method="post" role="search" id="login">
                <div class="form-group">
                    <input type="text" class="form-control" name="username" placeholder="username">
                    <input type="password" class="form-control" name="password" placeholder="password">
                </div>
                <button type="submit" class="btn btn-default" onclick="clearCookie()">登录</button>
            </form>
            <div id="welcome" style="display: none">
                <form action="${path}/main">
                    <%
                        if(user!=null){
                    %>
                    <div class="messageB loginD" style="display: none">
                    <span id="userTitle">欢迎，<%=user.getUsername()%></span>
                        <a href="#" class="easyui-menubutton" data-options="menu:'#mm',hasDownArrow:false">未读消息<span class="badge color-default tMessage"></span></a>
                        <div id="mm" class="theme-navigate-menu-panel" style="width:180px;">
                            <div id="noReadMessageWarn">预警消息<span class="badge color-important wMessage"></span></div>
                            <%--<div id="noReadMessageOut">出门消息<span class="badge color-success oMessage"></span></div>--%>
                            <div id="noReadMessageDown">故障消息<span class="badge color-important dMessage"></span></div>
                        </div>
                    </div>
                    <%
                        if(!request.getSession().getAttribute("role").equals("前台人员")){
                    %>
                    <input type="submit" class="btn btn-default" value="进入后台" id="sub" onclick="clearCookie()" />
                </form>
                <%
                     }
                    }
                %>
            </div>
            <div id="sys" style="display: none" class="loginD">
                <a href="#" class="easyui-menubutton theme-navigate-more-button" data-options="menu:'#more',hasDownArrow:false"></a>
                <div id="more" class="theme-navigate-more-panel">
                    <div onclick="reLogin();">重新登录</div>
                    <div onclick="logOut();">退出系统</div>
                </div>
            </div>
        </div>
    </div>
</nav>
<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner" role="listbox">
        <div class="item active">
            <img src="${path}/images/6.jpg" alt="bhb">
            <div class="carousel-caption">
                <%--<p style="color:black;font-size: xx-large">不叼</p>--%>
            </div>
        </div>
        <div class="item">
            <img src="${path}/images/5.jpg" alt="jkb">
            <div class="carousel-caption">
                <%--<p style="color:black;font-size:  xx-large">很好</p>--%>
            </div>
        </div>
        <div class="item">
            <img src="${path}/images/8.jpg" alt=" hv"/>
            <div class="carousel-caption">
                <%--<p style="color:black;font-size:  xx-large">星辰大海</p>--%>
            </div>
        </div>
    </div>
    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
<div id="toIframe" style="overflow: hidden">

</div>
<div id="footer" class="container">
    <%--<nav class="navbar navbar-default navbar-fixed-bottom">--%>
        <%--<div class="navbar-inner navbar-content-center">--%>
            <%--<p class="text-muted credit" style="padding: 10px;">--%>
                <%--Copyright © 2015 , 星辰大海, All Rights Reserved--%>
            <%--</p>--%>
        <%--</div>--%>
    <%--</nav>--%>
</div>

<script src="${path}/js/jquery.min.js"></script>
<script src="${path}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${path}/easyUI_insdep/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/easyUI_insdep/themes/insdep/jquery.insdep-extend.min.js"></script>
<script src="${path}/js/index.js"></script>
</body>
</html>