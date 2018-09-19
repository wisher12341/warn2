<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>设备信息</title>
  <link rel="stylesheet" type="text/css" href="${path}/css/table_equip.css"/>
  <%--<script type="text/javascript" src="${path}/js/equip_js.js"></script>--%>
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">
<div id="table">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/equip/datagrid" title="设备信息"
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
        <th data-options="field:'gatewayID',width:fixWidth(0.13),align:'center',formatter:formatActionGateway">网关</th>
        <th data-options="field:'segment',width:fixWidth(0.13),align:'center',formatter:formatActionSegment">网段标识</th>
        <th data-options="field:'eid',width:fixWidth(0.13),align:'center',formatter:formatActionSensor_Eid">设备ID</th>
        <th data-options="field:'eName',width:fixWidth(0.13),align:'center'" >设备名</th>
        <th data-options="field:'oldId',width:fixWidth(0.13),align:'center'" >对应人员ID</th>
        <th data-options="field:'roomId',width:fixWidth(0.13),align:'center'" >对应房间ID</th>
        <th data-options="field:'eRegtime',width:fixWidth(0.3),align:'center'">注册时间</th>
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
    <form id="search" method="post" action="${paht}/equip/datagrid" novalidate>
        <input class="easyui-searchbox" data-options="prompt:'设备ID',validType:'length[1,4]'"  style="width:10%" name="eid" />
        <input class="easyui-searchbox" data-options="prompt:'设备名'"  style="width:10%" name="eName" />
        <input class="easyui-searchbox" data-options="prompt:'对应人员ID'"  style="width:10%" name="oldId" />
        <input class="easyui-searchbox" data-options="prompt:'对应房间ID'"  style="width:10%" name="roomId"/>
        <input data-options="prompt:'注册日期'"  style="width:10%" name="eRegtime" class="easyui-datebox" />
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
