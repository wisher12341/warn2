<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
    <title>房间信息</title>
    <link rel="stylesheet" type="text/css" href="${path}/css/table_room.css"/>
    <%--<script type="text/javascript" src="${path}/js/room_js.js"></script>--%>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div id="table">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/room/area/datagrid" title="区域信息"
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
            <th data-options="field:'rid',width:fixWidth(0.08),align:'center'">房间ID</th>
            <th data-options="field:'roomName',width:fixWidth(0.12),align:'center'" >房间名</th>
            <th data-options="field:'collectId',width:fixWidth(0.12),align:'center',formatter:formatActionSensor" >对应设备ID</th>
            <th data-options="field:'oldId',width:fixWidth(0.08),align:'center'" >对应人员ID</th>
            <th data-options="field:'numOne',width:fixWidth(0.05),align:'center'">区域1</th>
            <th data-options="field:'numTwo',width:fixWidth(0.05),align:'center'">区域2</th>
            <th data-options="field:'numThree',width:fixWidth(0.05),align:'center'">区域3</th>
            <th data-options="field:'numFour',width:fixWidth(0.05),align:'center'">区域4</th>
            <th data-options="field:'numFive',width:fixWidth(0.05),align:'center'">区域5</th>
            <th data-options="field:'numSix',width:fixWidth(0.05),align:'center'">区域6</th>
            <th data-options="field:'numSeven',width:fixWidth(0.05),align:'center'">区域7</th>
            <th data-options="field:'numEight',width:fixWidth(0.05),align:'center'">区域8</th>
            <th data-options="field:'numNine',width:fixWidth(0.05),align:'center'">区域9</th>
            <th data-options="field:'rRegtime',width:fixWidth(0.16),align:'center'">注册时间</th>
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
        <form id="search" method="post" action="${paht}/room/area/datagrid" novalidate>
            <input class="easyui-searchbox" data-options="prompt:'房间ID'"  style="width:10%" name="rid" />
            <input class="easyui-searchbox" data-options="prompt:'房间名'"  style="width:10%" name="roomName" />
            <input class="easyui-searchbox" data-options="prompt:'对应设备ID'"  style="width:10%" name="collectId" />
            <input class="easyui-searchbox" data-options="prompt:'对应人员ID'"  style="width:10%" name="oldId"/>
            <%--<input class="easyui-searchbox" data-options="prompt:'相邻房间'"  style="width:15%" name="nerRoom"/>--%>
            <input data-options="prompt:'注册日期'" style="width:10%" name="rRegtime" class="easyui-datebox" />
            <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
               plain="true" onclick="formSearch()"><span>查询</span></a>
        </form>

    </div>



</div>

</body>
<script type="text/javascript">
    $(function(){
        $(".active",parent.document).removeClass("active");
        $("#index + li",parent.document).addClass("active");
    });
</script>
</html>
