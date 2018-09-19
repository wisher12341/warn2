<%@page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>

<html>
<head>
  <title>管理系统</title>
  <script type="text/javascript" src="${path}/js/main_old_noUse.js"></script>
  <script type='text/javascript' src="${path}/dwr/engine.js"></script>
  <script type='text/javascript' src="${path}/dwr/util.js"></script>
  <script type='text/javascript' src="${path}/dwr/interface/Remote.js"></script>
</head>
<body class="easyui-layout">

<%--<input id="usercode" type="text" value="123"/>--%>
<!-- 正上方panel -->
<div data-options="region:'north',border:false" style="height:80px;padding:0px">
  <div class="header_left" style="position: absolute;left: 5px;top:10px;"><h1>&nbsp;&nbsp;管理系统</h1></div>

  <div id="userInfo" style="position: absolute;right: 5px;top:10px;">
    <strong><span>欢迎你, <%=user.getUsername()%></span></strong>
  </div>
  <div style="position: absolute; right: 0px; bottom: 0px; ">
    <a href="javascript:void(0);" class="easyui-menubutton"
       data-options="menu:'#layout_north_zxMenu',iconCls:'icon-back'">注销</a>
  </div>

  <div id="layout_north_zxMenu" style="width: 100px; display: none;">
    <div onclick="reLogin();">重新登录</div>
    <div class="menu-sep"></div>
    <div onclick="logOut();">退出系统</div>
  </div>

</div>

<!-- 左侧菜单 -->
<div data-options="region:'west',href:''" title="导航菜单" style="width: 200px; padding: 0px;">
  <ul id="mainMenu"></ul>
</div>

<!-- 正中间panel -->
<!-- <div region="center" title="功能区" >   -->
<div region="center">
  <div class="easyui-tabs" id="mainTabs" fit="true" border="false">
    <div title="首页" style="padding:20px;">
      <div style="margin-top:20px; float:left;min-width:600px;widht: 600px; height: 90%; ">
        <h1 style="font-size:24px;">欢迎使用！</h1>
      </div>
    </div>
  </div>
</div>

</body>
</html>