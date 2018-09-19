<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>故障历史信息</title>
  <script type="text/javascript" src="${path}/js/down.js"></script>
    <link href="${path}/css/out.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">

<div region="center" border="false" style="overflow: hidden;">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/downHistory/datagrid"
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
      <th data-options="field:'downid',hidden:true,align:'center'">ID</th>
        <th data-options="field:'oid',width:fixWidth(0.1),align:'center'">人员ID</th>
      <th data-options="field:'oldName',width:fixWidth(0.2),align:'center'">姓名</th>
      <th data-options="field:'typeDown',width:fixWidth(0.1),align:'center'">类型</th>
      <th data-options="field:'dataDown',width:fixWidth(0.3),align:'center'" formatter="formatActionData">详情</th>
      <th data-options="field:'timeDown',width:fixWidth(0.2),align:'center'">添加时间</th>
      <th data-options="field:'readDown',width:fixWidth(0.1),align:'center'" formatter="formatAction">是否已读</th>
    </thead>
  </table>

  <!-- 总控按钮 -->
  <div id="toolbar">
      <div id="buttonTool">
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-download"
             plain="true" onclick="exportfile();"><span>导出</span></a>
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
             plain="true" onclick="refresh();"><span>刷新</span></a>
      </div>
      <form id="search" method="post" action="${paht}/warnHistory/datagrid" novalidate>
          <input class="easyui-searchbox" data-options="prompt:'ID'" name="oid" />
          <input class="easyui-searchbox" data-options="prompt:'姓名'" name="oldName" />
          <input class="easyui-searchbox" data-options="prompt:'类型'" name="typeD" />
          <input class="easyui-searchbox" data-options="prompt:'是否已读'" name="readD"/>
          <input data-options="prompt:'添加日期'" style="width:10%" name="timeD" class="easyui-datebox" />
          <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
             plain="true" onclick="formSearch()"><span>查询</span></a>
      </form>
  </div>



</div>

</body>
</html>
