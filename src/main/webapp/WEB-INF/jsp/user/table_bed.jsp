<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
    <title>床位信息</title>
    <link rel="stylesheet" type="text/css" href="${path}/css/table_room.css"/>
    <%--<script type="text/javascript" src="${path}/js/room_js.js"></script>--%>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div id="table">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/bed/datagrid" title="床位信息"
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
            <th data-options="field:'id',width:fixWidth(0.08),align:'center'">床位ID</th>
            <th data-options="field:'number',width:fixWidth(0.18),align:'center'" >床位编号</th>
            <th data-options="field:'voiceId',width:fixWidth(0.18),align:'center'" >声音识别ID</th>
            <th data-options="field:'gravityId',width:fixWidth(0.1),align:'center'" >重力感应设备ID</th>
            <th data-options="field:'status',width:fixWidth(0.3),align:'center'">状态</th>
            <th data-options="field:'time',width:fixWidth(0.16),align:'center'">更新时间</th>
        </tr>
        </thead>
    </table>

    <!-- 总控按钮 -->
    <%--<div id="toolbar">--%>
        <%--<div id="buttonTool">--%>
            <%--<a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-download"--%>
               <%--plain="true" onclick="exportfile();"><span>导出</span></a>--%>
            <%--<a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"--%>
               <%--plain="true" onclick="refresh();"><span>刷新</span></a>--%>
        <%--</div>--%>
        <%--<form id="search" method="post" action="${paht}/room/datagrid" novalidate>--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'房间ID'"  style="width:10%" name="rid" />--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'房间名'"  style="width:10%" name="roomName" />--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'对应设备ID'"  style="width:10%" name="collectId" />--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'对应人员ID'"  style="width:10%" name="oldId"/>--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'相邻房间'"  style="width:15%" name="nerRoom"/>--%>
            <%--<input data-options="prompt:'注册日期'" style="width:10%" name="rRegtime" class="easyui-datebox" />--%>
            <%--<a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"--%>
               <%--plain="true" onclick="formSearch()"><span>查询</span></a>--%>
        <%--</form>--%>

    <%--</div>--%>



</div>

</body>
<script type="text/javascript">
    $(function(){
        $(".active",parent.document).removeClass("active");
        $("#index + li",parent.document).addClass("active");
    });
</script>
</html>
