<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>权限信息</title>

    <link rel="stylesheet" type="text/css" href="${path}/css/authority_css.css"/>

  <script type="text/javascript" src="${path}/js/authority_js.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style=" width: 90%;height: 100%">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/authority/datagrid"
         toolbar="#toolbar"
         fitColumns="true"
         singleSelect="true"
         striped="true"
         border="false"
         nowrap="false">
    <thead>
    <tr>
      <th data-options="field:'id',width:fixWidth(0.3),align:'center'"  formatter="formatActionFunction">功能</th>
      <th data-options="field:'superManager',width:fixWidth(0.22),align:'center'" formatter="formatActionS">超级管理人员</th>
      <th data-options="field:'manager',width:fixWidth(0.22),align:'center'" formatter="formatActionM">管理人员</th>
      <th data-options="field:'user',width:fixWidth(0.22),align:'center'" formatter="formatActionU">前台人员</th>

    </thead>
  </table>

  <!-- 总控按钮 -->
  <div id="toolbar">
      <div id="buttonTool">
    <a href="javascript:void(0);" class="easyui-linkbutton admin toolB fa fa-plus-square"
        plain="true" onclick="save();"><span>保存</span></a>
    <a href="javascript:void(0);" class="easyui-linkbutton admin toolB fa fa-pencil"
       plain="true" onclick="refresh();"><span>重置</span></a>
        <a href="javascript:void(0);" class="easyui-linkbutton admin toolB fa fa-pencil"
           plain="true" onclick="recover();"><span>恢复最初设置</span></a>
  </div>
  </div>


</div>

</body>
</html>
