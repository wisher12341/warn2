<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>

<html>
<head>
    <title>可视化</title>
    <link href="${path}/css/play.css" rel="stylesheet" type="text/css">
    <script src="${path}/js/echarts/echarts.common.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${path}/js/area_Visual.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>


</head>
<body class="easyui-layout" fit="true">
<div region="west" border="true" style="overflow: hidden;width: 23%">
    <div id="table">
        <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/Statistic/datagrid" title="房间列表"
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
                <th data-options="field:'oldName',width:fixWidth(0.08),align:'center'" >姓名</th>
                <th data-options="field:'rid',width:fixWidth(0.08),align:'center',hidden:true">房间ID</th>
                <th data-options="field:'roomName',width:fixWidth(0.08),align:'center'" >房间名</th>
            </tr>
            </thead>
        </table>
        <div id="toolbar">
            <form id="search" method="post" novalidate>
                <input data-options="prompt:'日期'" style="width:40%" name="time" class="easyui-datebox " id="timeS" />
                <input type="hidden" name="oldId" class="easyui-textbox"/>
                <input type="hidden" name="rid" class="easyui-textbox"/>
                <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
                   plain="true" onclick="timeSearch()"><span>查询</span></a>
                <%--<input class="easyui-searchbox" data-options="prompt:'姓名'" name="oldName" />--%>
            </form>
            <%--<div id="buttonTool">--%>
                <%--<a href="javascript:void(0);" class="easyui-linkbutton fa fa-search toolB"--%>
                   <%--plain="true" onclick="formSearch()" id="searchA"><span>查询</span></a>--%>
                <%--<a href="javascript:void(0);" class="easyui-linkbutton toolB fa fa-refresh"--%>
                   <%--plain="true" onclick="refresh();" id="refreshA"><span>刷新</span></a>--%>
                <%--<a href="javascript:void(0);" class="easyui-linkbutton dayButton block disabled"--%>
                   <%--plain="true" onclick="dayB()"><span>整体活动情况</span></a>--%>
                <%--<a href="javascript:void(0);" class="easyui-linkbutton roomsButton block disabled"--%>
                   <%--plain="true" onclick="roomsB()"><span>各个房间活动情况</span></a>--%>
            <%--</div>--%>
        </div>
    </div>
</div>

<div region="center" border="true" singleSelect="true" style="overflow: hidden;" id="visual">
    <%--<div>--%>
    <%--<input type="button" value="全天活动情况" class="dayButton" disabled="true" onclick="dayB()">--%>
    <%--<input type="button" value="各个房间活动情况" class="roomsButton" onclick="roomsB()">--%>
    <%--</div>--%>
    <div id="mainDiv" style="width:1400px;" class="tuMain">
        <%--<div class="title">生活规律环图</div>--%>
        <div id="main"  ></div>
    </div>
    <div id="mainLineDiv" style="width:1400px;" class="tuMainLine">
        <div id="mainLine"  style = "left:150px;"></div>
    </div>
    <%--图形设置一定要放在后面  固定最多6个环图--%>
    <script type="text/javascript">
        var myChart = echarts.init(document.getElementById('main'));
        var myChartLine = echarts.init(document.getElementById('mainLine'));
    </script>

</div>


</body>
</html>
