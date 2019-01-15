<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%--<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<html>
<head>
    <title>阈值信息</title>
  <script type="text/javascript">
    var pathJs = "${path}"; //在JS文件中使用  JS环境不能用EL表达式
  </script>

    <link rel="stylesheet" type="text/css" href="${path}/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${path}/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${path}/easyui/demo/demo.css">

    <%--第三方图标--%>
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="${path}/css/threshold.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${path}/easyui/jquery.min.js"></script>
  <script type="text/javascript" src="${path}/js/jquery.easyui.min.js"></script> <%--不能用项目中easyui、easyui_insdep文件夹的jquery.easyui.min.js版本, 会出现 行内编辑器BUG  --%>
  <script type="text/javascript" src="${path}/easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="${path}/js/threshold_js.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div region="west" border="true" style="overflow: hidden;width: 28%">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/data/datagrid" title="人员列表"
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
      <th data-options="field:'oid',width:fixWidth(0.3),align:'center'" >ID</th>
      <th data-options="field:'oldName',width:fixWidth(0.8),align:'center'" >姓名</th>
    </tr>
    </thead>
  </table>
    <div id="toolbar">
        <form id="search" method="post" action="${paht}/data/datagrid" novalidate>
            <input class="easyui-searchbox" data-options="prompt:'ID'"  id="searchOid" name="oid" /><br/>
            <input class="easyui-searchbox" data-options="prompt:'姓名'" name="oldName" />
        </form>
        <div id="buttonTool">
            <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
               plain="true" onclick="formSearch()"><span>查询</span></a>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
               plain="true" onclick="refresh();"><span>刷新</span></a>
        </div>
    </div>
  </div>

<div region="center" border="true" singleSelect="true">
  <table id="tt" class="easyui-datagrid" fitColumns="true"  singleSelect="true" url="${path}/threshold/getThreshold" title="行为阈值" striped="true"
         >
    <thead>
    <tr>
      <th data-options="field:'tid',hidden:true" rowspan="2">ID</th>
      <th data-options="field:'room',width:fixHtmlWidth(0.2),align:'center',formatter: function(value,row,index){ if (value.roomName){return value.roomName;} else {return ''; }}," rowspan="2">房间</th>
      <th colspan="2">活动时（分钟）</th>
      <th colspan="2">休息时（分钟）</th>
      <th colspan="2">不在房间规律时段时（分钟）</th>
      <th data-options="field:'action',width:fixHtmlWidth(0.25),align:'center'"  formatter="formatAction" rowspan="2"></th>
    </tr>
    <tr>
        <th data-options="field:'a1Threshold',width:fixHtmlWidth(0.1),align:'center',editor:'numberspinner'" >一级</th>
        <th data-options="field:'a2Threshold',width:fixHtmlWidth(0.1),align:'center',editor:'numberspinner'" >二级</th>
        <th data-options="field:'r1Threshold',width:fixHtmlWidth(0.1),align:'center',editor:'numberspinner'" >一级</th>
        <th data-options="field:'r2Threshold',width:fixHtmlWidth(0.1),align:'center',editor:'numberspinner'" >二级</th>
        <th data-options="field:'n1Threshold',width:fixHtmlWidth(0.15),align:'center',editor:'numberspinner'" >一级</th>
        <th data-options="field:'n2Threshold',width:fixHtmlWidth(0.15),align:'center',editor:'numberspinner'" >二级</th>
    </tr>
    </thead>
  </table>


   <table id="wendu" class="easyui-datagrid" fitColumns="true" singleSelect="true" url="${path}/threshold/getThreshold_w" title="温度阈值" striped="true"
        >
        <thead>
        <tr>
            <th data-options="field:'wid',hidden:true">ID</th>
            <th data-options="field:'room',width:fixHtmlWidth(0.2),align:'center',formatter: function(value,row,index){ if (value.roomName){return value.roomName;} else {return ''; }}">房间</th>
            <th data-options="field:'wThreshold',width:fixHtmlWidth(0.6),align:'center',editor:'numberspinner'" >温度阈值（单位：摄氏度）</th>
            <th data-options="field:'action',width:fixHtmlWidth(0.25),align:'center'" formatter="formatAction_w"></th>
        </tr>
        </thead>
    </table>

    <table id="light" class="easyui-datagrid" fitColumns="true" singleSelect="true" url="${path}/threshold/getThreshold_l" title="光强阈值" striped="true"
           >
        <thead>
        <tr>
            <th data-options="field:'lid',hidden:true">ID</th>
            <th data-options="field:'room',width:fixHtmlWidth(0.2),align:'center',formatter: function(value,row,index){ if (value.roomName){return value.roomName;} else {return ''; }}">房间</th>
            <th data-options="field:'lThreshold',width:fixHtmlWidth(0.2),align:'center',editor:'numberspinner'" >光强阈值</th>
            <th data-options="field:'times',width:fixHtmlWidth(0.2),align:'center',editor:'text'" >时间段</th>
            <th data-options="field:'continueTime',width:fixHtmlWidth(0.21),align:'center',editor:'numberspinner'" >持续时间（分钟）</th>
            <th data-options="field:'action',width:fixHtmlWidth(0.25),align:'center'" formatter="formatAction_l"></th>
        </tr>
        </thead>
    </table>

    <table id="door" class="easyui-datagrid" fitColumns="true" singleSelect="true" url="${path}/threshold/getThreshold_d" title="出门阈值" striped="true"
           >
        <thead>
        <tr>
            <th data-options="field:'outId',hidden:true">ID</th>
            <th data-options="field:'outThreshold',width:fixHtmlWidth(0.4),align:'center',editor:'numberspinner'" >出门阈值（单位：分钟）</th>
            <th data-options="field:'noComeThreshold',width:fixHtmlWidth(0.4),align:'center',editor:'numberspinner'" >未归阈值（单位：分钟）</th>
            <th data-options="field:'action',width:fixHtmlWidth(0.25),align:'center'" formatter="formatAction_d"></th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>
