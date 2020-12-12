<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%--<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<html>
<head>
    <title>统计区域阈值</title>
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
    <script type="text/javascript" src="${path}/js/threshold_statistic.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div region="west" border="true" style="overflow: hidden;width: 28%">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/Statistic/datagrid" title="人员列表"
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
            <th data-options="field:'oldId',width:fixWidth(0.08),align:'center'" >对应人员ID</th>
            <%--<th data-options="field:'oid',width:fixWidth(0.3),align:'center'" >ID</th>--%>
            <th data-options="field:'oldName',width:fixWidth(0.3),align:'center'" >姓名</th>
            <th data-options="field:'rid',width:fixWidth(0.08),align:'center',hidden:true">房间ID</th>
            <th data-options="field:'roomName',width:fixWidth(0.12),align:'center'" >房间名</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar">
        <form id="search" method="post" action="${path}/Statistic/datagrid" novalidate>
            <input class="easyui-searchbox" data-options="prompt:'ID'"  id="searchOid" name="oldId" /><br/>
            <%--<input class="easyui-searchbox" data-options="prompt:'姓名'" name="oldName" />--%>
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
    <table id="statistic" class="easyui-datagrid" fitColumns="true" singleSelect="true" url="${path}/threshold/getThresholdS" title="偏离阈值" striped="true">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true" >ID</th>
            <th data-options="field:'area',hidden:true" >area</th>
            <th data-options="field:'areaInfo',width:fixHtmlWidth(0.2),align:'center'">区域</th>
            <th data-options="field:'sThreshold',width:fixHtmlWidth(0.6),align:'center',editor:'numberspinner'" >偏离阈值</th>
            <th data-options="field:'action',width:fixHtmlWidth(0.25),align:'center'" formatter="formatAction"></th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>
