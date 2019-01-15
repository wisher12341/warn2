<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>

<html>
<head>
    <title>可视化</title>
    <link href="${path}/css/play.css" rel="stylesheet" type="text/css">
    <script src="${path}/js/echarts/echarts.common.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${path}/js/visual.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>


</head>
<body class="easyui-layout" fit="true">
<div region="west" border="true" style="overflow: hidden;width: 23%">
    <div id="table">
        <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/area/data/datagrid1" title="人员列表"
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
            <form id="search" method="post" action="${paht}/area/data/datagrid1" novalidate>
                <input class="easyui-searchbox" data-options="prompt:'ID'"  id="searchOid" name="oldId" /><br/>
                <%--<input class="easyui-searchbox" data-options="prompt:'姓名'" name="oldName" />--%>
            </form>
            <div id="buttonTool">
                <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search toolB"
                   plain="true" onclick="formSearch()" id="searchA"><span>查询</span></a>
                <a href="javascript:void(0);" class="easyui-linkbutton toolB fa fa-refresh"
                   plain="true" onclick="refresh();" id="refreshA"><span>刷新</span></a>
                <a href="javascript:void(0);" class="easyui-linkbutton dayButton block disabled"
                   plain="true" onclick="dayB()"><span>整体活动情况</span></a>
                <a href="javascript:void(0);" class="easyui-linkbutton roomsButton block disabled"
                   plain="true" onclick="roomsB()"><span>各个房间活动情况</span></a>
            </div>
        </div>
    </div>
</div>

<div region="center" border="true" singleSelect="true" style="overflow: hidden;" id="visual">
    <%--<div>--%>
    <%--<input type="button" value="全天活动情况" class="dayButton" disabled="true" onclick="dayB()">--%>
    <%--<input type="button" value="各个房间活动情况" class="roomsButton" onclick="roomsB()">--%>
    <%--</div>--%>
    <div id="mainDiv" class="tuMain">
        <div class="title">生活规律环图</div>
        <div id="main"  ></div>
    </div>
    <div id="mainLineDiv" class="tuLine">
        <div id="mainLine" ></div>
    </div>
    <div id="main1Div" class="tu">
        <div class="title"></div>
        <div id="main1"  ></div>
    </div>
    <div id="main2Div" class="tu">
        <div class="title"></div>
        <div id="main2"  ></div>
    </div>
    <div id="main3Div" class="tu">
        <div class="title"></div>
        <div id="main3" ></div>
    </div>
    <div id="main4Div" class="tu">
        <div class="title"></div>
        <div id="main4"  ></div>
    </div>
    <div id="main5Div" class="tu">
        <div class="title"></div>
        <div id="main5" ></div>
    </div>
    <div id="main6Div" class="tu">
        <div class="title"></div>
        <div id="main6"  ></div>
    </div>
    <%--图形设置一定要放在后面  固定最多6个环图--%>
    <script type="text/javascript">
        var myChart = echarts.init(document.getElementById('main'));
        var myChartLine = echarts.init(document.getElementById('mainLine'));
        var myChart1 = echarts.init(document.getElementById('main1'));
        var myChart2 = echarts.init(document.getElementById('main2'));
        var myChart3 = echarts.init(document.getElementById('main3'));
        var myChart4 = echarts.init(document.getElementById('main4'));
        var myChart5 = echarts.init(document.getElementById('main5'));
        var myChart6 = echarts.init(document.getElementById('main6'));
    </script>

</div>


</body>
</html>
