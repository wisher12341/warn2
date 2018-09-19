<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>传感器信息</title>
  <script type="text/javascript" src="${path}/js/raw_user.js"></script>
    <link href="${path}/css/raw_user.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">

<div region="center" border="false" style="overflow: hidden;">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/raw/user/datagrid"
         toolbar="#toolbar"
         pagination="true"
         fitColumns="true"
         singleSelect="true"
         rownumbers="true"
         striped="true"
         border="false"
         nowrap="false" style="overflow: hidden;">
    <thead>
    <tr>
      <th data-options="field:'name',width:fixWidth(0.1),align:'center'">用户名</th>
      <th data-options="field:'password',width:fixWidth(0.1),align:'center'">密码</th>
      <th data-options="field:'address',width:fixWidth(0.1),align:'center'">地址</th>
      <th data-options="field:'userPhone',width:fixWidth(0.1),align:'center'">手机</th>
        <th data-options="field:'guardianPhone',width:fixWidth(0.1),align:'center'">联系人手机</th>
        <th data-options="field:'mac',width:fixWidth(0.1),align:'center'">Mac</th>
        <th data-options="field:'gatewayID',width:fixWidth(0.05),align:'center'">网关</th>
        <th data-options="field:'roomInfoArray',width:fixWidth(0.35),align:'center'" formatter="formatAction">房间</th>
  </table>

  <!-- 总控按钮 -->
  <div id="toolbar">
      <div id="buttonTool">
          <%--<a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-download"--%>
             <%--plain="true" onclick="exportfile();"><span>导出</span></a>--%>
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
             plain="true" onclick="refresh();"><span>刷新</span></a>
              <%--<a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"--%>
                 <%--plain="true" onclick="stopRefresh(event);"><span>开启实时刷新</span></a>--%>
      </div>
    <form id="search" method="post" action="${path}/raw/sensor/datagrid" novalidate>
        <input class="easyui-searchbox" data-options="prompt:'用户名'" name="name" />
        <input class="easyui-searchbox" data-options="prompt:'网关ID'" name="gatewayID" />
        <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
           plain="true" onclick="formSearch()"><span>查询</span></a>
    </form>
  </div>



</div>


<%--<style type="text/css">--%>
    <%--/*设置datagrid左侧序列栏 宽度*/--%>
    <%--.datagrid-header-rownumber, .datagrid-cell-rownumber{--%>
    <%--width: 60px;--%>
    <%--}--%>

    <%--/*设置分页栏  显示页数的宽度*/--%>
    <%--/*.pagination-num{*/--%>
        <%--/*width: 100px;;*/--%>
    <%--/*}*/--%>
<%--</style>--%>
</body>
</html>
