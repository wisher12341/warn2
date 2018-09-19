<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>

<html>
<head>
    <title>可视化</title>
    <script src="${path}/js/echarts/echarts.common.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${path}/js/visual.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
    <link href="${path}/css/play.css" rel="stylesheet" type="text/css">


</head>
<body class="easyui-layout" fit="true">
<div region="west" border="true" style="overflow: hidden;width: 20%">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/data/datagrid"
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
        <th data-options="field:'oid',width:fixWidth(0.3),align:'center'" >网关ID</th>
        <th data-options="field:'oldName',width:fixWidth(0.8),align:'center'" >姓名</th>
    </tr>
    </thead>
  </table>
    <div id="toolbar">
        <form id="search" method="post" action="${paht}/data/datagrid" novalidate>
            <input class="easyui-searchbox" data-options="prompt:'ID'" name="oid" />
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

<div region="center" border="true" singleSelect="true" style="overflow: hidden;" id="visual">
    <div>
        <input type="button" value="全天活动情况" class="dayButton" disabled="true" onclick="dayB()">
        <input type="button" value="各个房间活动情况" class="roomsButton" onclick="roomsB()">
    </div>
    <div id="main"  ></div>
    <div id="mainLine" ></div>
    <div id="main1" class="visual" ></div>
    <div id="main2" class="visual" ></div>
    <div id="main3" class="visual"></div>
    <div id="main4" class="visual"></div>
    <div id="main5" class="visual"></div>
    <div id="main6" class="visual"></div>
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
