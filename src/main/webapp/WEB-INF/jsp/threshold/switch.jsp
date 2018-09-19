<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%--<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>--%>
<html>
<head>
  <title>预警开关</title>
  <link href="${path}/css/switch.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="${path}/js/switch_js.js"></script>
  <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/timer/datagrid"
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
      <th data-options="field:'oid',width:fixWidth(0.25),align:'center',formatter:function(value,row,index){if (row.oldMan.oid){return row.oldMan.oid;} else {return '';}}">ID</th>
      <th data-options="field:'oldName',width:fixWidth(0.35),align:'center',formatter:function(value,row,index){if (row.oldMan.oldName){return row.oldMan.oldName;} else {return '';}}">姓名</th>
      <th data-options="field:'timerSwitch',width:fixWidth(0.4),align:'center',formatter:formatAction">开关</th>
    </tr>
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
      <form id="search" method="post" action="${paht}/timer/datagrid" novalidate>
        <input class="easyui-searchbox" data-options="prompt:'ID'" name="oid" />
        <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
           plain="true" onclick="formSearch()"><span>查询</span></a>
    </form>
  </div>


</div>

</body>
</html>
