<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>设备信息</title>
  <script type="text/javascript" src="${path}/js/sensor.js"></script>
    <link href="${path}/css/raw_user.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">

<div region="center" border="false" style="overflow: hidden;">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/raw/sensorPoint/datagrid"
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
      <th data-options="field:'sensorPointID',width:fixWidth(0.5),align:'center'">设备ID</th>
      <th data-options="field:'gatewayID',width:fixWidth(0.5),align:'center'">网关ID</th>
  </table>

  <!-- 总控按钮 -->
  <div id="toolbar">
      <div id="buttonTool">
          <%--<a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-download"--%>
             <%--plain="true" onclick="exportfile();"><span>导出</span></a>--%>
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
             plain="true" onclick="refresh();"><span>刷新</span></a>
      </div>
    <form id="search" method="post" action="${path}/raw/sensor/datagrid" novalidate>
        <input class="easyui-searchbox" data-options="prompt:'设备ID'" name="sensorPointID" />
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
