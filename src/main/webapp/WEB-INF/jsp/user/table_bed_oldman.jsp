<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
    <title>床位人员</title>

    <link rel="stylesheet" type="text/css" href="${path}/css/table_oldman.css"/>
    <script type="text/javascript" src="${path}/js/table_oldman.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true" style="overflow: hidden">
<div id="table">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/bed/oldman/datagrid" title="床位人员信息"
           toolbar="#toolbar"
           pagination="true"
           fitColumns="true"
           singleSelect="true"
           rownumbers="true"
           striped="true"
           border="false"
           nowrap="false">
        <thead>
        <tr>
            <th data-options="field:'id',width:fixWidth(0.15),align:'center'," rowspan="2">床位id</th>
            <th data-options="field:'oid',width:fixWidth(0.15),align:'center'" rowspan="2">人员ID</th>
            <%--<th data-options="field:'oldMan.oldName',width:fixWidth(0.05),align:'center'" rowspan="2">姓名</th>--%>
            <%--<th data-options="field:'segment',width:fixWidth(0.08),align:'center',formatter:formatActionSegment" rowspan="2">网段标识</th>--%>
            <%--<th data-options="field:'oldMan.oldPhone',width:fixWidth(0.08),align:'center'" rowspan="2">电话</th>--%>
            <%--<th data-options="field:'oldMan.oldAddress',width:fixWidth(0.11),align:'center'" rowspan="2">住址</th>--%>
            <%--<th data-options="field:'oldMan.oldRegtime',width:fixWidth(0.08),align:'center'" rowspan="2">注册时间</th>--%>
            <%--<th data-options="field:'rooms',width:fixWidth(0.052),align:'center',formatter:formatActionRoom" rowspan="2">房间</th>--%>
            <%--<th data-options="field:'equips',width:fixWidth(0.052),align:'center',formatter:formatActionEquip" rowspan="2">设备</th>--%>
            <%--<th data-options="field:'relid',hidden:true,formatter: function(value,row,index){if (row.relatives.relid){return row.relatives.relid;} else {return '';}}">紧急联系人ID</th>--%>
            <th colspan="6">老人信息</th>
        </tr>
        <tr>
            <th data-options="field:'oldPhone',width:fixWidth(0.15),align:'center',
      formatter: function(value,row,index){
                if (row.oldMan.oldPhone){
                    return row.oldMan.oldPhone;
                } else {
                    return '';
                }
           }">联系方式</th>
            <th data-options="field:'oldName',width:fixWidth(0.15),align:'center',
      formatter: function(value,row,index){
                if (row.oldMan.oldName){
                    return row.oldMan.oldName;
                } else {
                    return '';
                }
           }">姓名</th>
            <th data-options="field:'oldAddress',width:fixWidth(0.15),align:'center',
      formatter: function(value,row,index){
                if (row.oldMan.oldAddress){
                    return row.oldMan.oldAddress;
                } else {
                    return '';
                }
           }">住址</th>
            <th data-options="field:'oldRegtime',width:fixWidth(0.15),align:'center',
      formatter: function(value,row,index){
                if (row.oldMan.oldRegtime){
                    return row.oldMan.oldRegtime;
                } else {
                    return '';
                }
           }">入住时间</th>
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
        <%--<form id="search" method="post" action="${paht}/data/datagrid" novalidate>--%>
            <%--&lt;%&ndash;<label>老人ID：</label>&ndash;%&gt;--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'人员ID'"  style="width:10%" name="oid" />--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'姓名'" style="width:10%" name="oldName" />--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'网关',validType:'length[1,4]'"  style="width:10%" name="gatewayID" />--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'网段标识',validType:'length[1,4]'"  style="width:10%" name="segment" />--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'电话'" style="width:10%" name="oldPhone" />--%>
            <%--<input class="easyui-searchbox" data-options="prompt:'地址'" style="width:15%" name="oldAddress"/>--%>
            <%--<input  data-options="prompt:'注册日期'" style="width:10%" name="oldRegtime" class="easyui-datebox" />--%>
            <%--<a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"--%>
               <%--plain="true" id="searchB" onclick="formSearch()"><span>查询</span></a>--%>
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
