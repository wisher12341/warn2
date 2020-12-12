<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
    <title>传感器信息2</title>
    <script type="text/javascript" src="${path}/js/sensor.js"></script>
    <link href="${path}/css/warn.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${path}/js/common.js"></script>
    <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">

<div region="center" border="false" style="overflow: hidden;">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/raw/sensorSec/datagrid"
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
            <th data-options="field:'gatewayID',width:fixWidth(0.1),align:'center'">网关</th>
            <th data-options="field:'segment',width:fixWidth(0.1),align:'center',formatter:formatActionSegmentRaw">网段标识</th>
            <th data-options="field:'sensorId',width:fixWidth(0.1),align:'center',formatter:formatActionSensorRaw">传感器ID</th>
            <th data-options="field:'eName',width:fixWidth(0.1),align:'center',
      formatter: function(value,row,index){
                if (row.equipment.eName){
                    return row.equipment.eName;
                } else {
                    return '';
                }
           }">名称</th>
            <th data-options="field:'sensorType',width:fixWidth(0.08),align:'center'">种类</th>
            <th data-options="field:'sensorData',width:fixWidth(0.06),align:'center'">数据</th>
            <th data-options="field:'time',width:fixWidth(0.18),align:'center'">时间</th>
            <th data-options="field:'oldMan',width:fixWidth(0.17),align:'center',
      formatter: function(value,row,index){
                if (row.oldMan.oid&&row.oldMan.oldName){
                    return 'ID：'+row.oldMan.oid+'&nbsp;&nbsp;&nbsp;姓名：'+row.oldMan.oldName;
                } else {
                    return '';
                }
           }">人员信息</th>
            <th data-options="field:'room',width:fixWidth(0.19),align:'center',
      formatter: function(value,row,index){
                if (row.room.rid&&row.room.roomName){
                    return 'ID：'+row.room.rid+'&nbsp;&nbsp;&nbsp;名称：'+row.room.roomName;
                } else {
                    return '';
                }
           }">房间信息</th>
        </thead>
    </table>

    <!-- 总控按钮 -->
    <div id="toolbar">
        <div id="buttonTool">
            <%--<a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-download"--%>
            <%--plain="true" onclick="exportfile();"><span>导出</span></a>--%>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
               plain="true" onclick="refresh();"><span>刷新</span></a>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
               plain="true" onclick="stopRefresh(event);"><span>开启实时刷新</span></a>
        </div>
        <form id="search" method="post" action="${path}/raw/sensor/datagrid" novalidate>
            <input class="easyui-searchbox" data-options="prompt:'网关',validType:'length[1,4]'" name="oldMan.gatewayID" />
            <input class="easyui-searchbox" data-options="prompt:'网段标识',validType:'length[1,4]'"  name="oldMan.segment" />
            <input class="easyui-searchbox" data-options="prompt:'传感器ID',validType:'length[1,4]'"  name="sensorId" />
            <input class="easyui-searchbox" data-options="prompt:'传感器种类'" name="sensorType"/>
            <input class="easyui-searchbox" data-options="prompt:'人员ID'" name="oldMan.oid" />
            <input data-options="prompt:'日期'" name="time" class="easyui-datebox" />
            <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
               plain="true" onclick="formSearch()"><span>查询</span></a>
        </form>
    </div>



</div>


<style type="text/css">
    /*设置datagrid左侧序列栏 宽度*/
    .datagrid-header-rownumber, .datagrid-cell-rownumber{
        width: 60px;
    }

    /*设置分页栏  显示页数的宽度*/
    /*.pagination-num{*/
    /*width: 100px;;*/
    /*}*/
</style>
</body>
</html>
