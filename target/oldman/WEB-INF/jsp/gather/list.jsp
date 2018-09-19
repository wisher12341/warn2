<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
    <title>汇总信息</title>

    <link rel="stylesheet" type="text/css" href="${path}/css/gather.css"/>
    <script type="text/javascript" src="${path}/js/gather.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true" style="overflow: hidden">
<div id="table">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/gather/datagrid"
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
            <th data-options="field:'oid',width:fixWidth(0.1),align:'center',
            formatter: function(value,row,index){
            if (row.oldMan.oid){
               return row.oldMan.oid;
            } else {
               return '';
            }
     }" >人员ID</th>
            <th data-options="field:'oldName',width:fixWidth(0.1),align:'center',
            formatter: function(value,row,index){
            if (row.oldMan.oldName){
               return row.oldMan.oldName;
            } else {
               return '';
            }
     }" >姓名</th>
            <th data-options="field:'gatewayID',width:fixWidth(0.15),align:'center',
            formatter: function(value,row,index){
                        return row.oldMan.gatewayID
            }" >网关</th>
            <th data-options="field:'segment',width:fixWidth(0.15),align:'center',
            formatter: function(value,row,index){
            if(row.oldMan.segment){
                var binary=parseInt(row.oldMan.segment).toString(2)+'';
                switch (binary.length) {
                    case 1:
                        return row.oldMan.segment + '(000' + binary + ')';
                        break;
                    case 2:
                        return row.oldMan.segment + '(00' + binary + ')';
                        break;
                    case 3:
                        return row.oldMan.segment + '(0' + binary + ')';
                         break;
                    default:
                        return row.oldMan.segment + '(' + binary + ')';
            }
            }else{
            return '';}
     }" >网段标志</th>
            <th data-options="field:'data',width:fixWidth(0.5),formatter:formatAction" >房间、设备信息</th>
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
        <form id="search" method="post" action="${paht}/data/datagrid" novalidate>
            <%--<label>老人ID：</label>--%>
            <input class="easyui-searchbox" data-options="prompt:'ID'"  style="width:10%" name="oldMan.oid" />
            <input class="easyui-searchbox" data-options="prompt:'姓名'" style="width:10%" name="oldMan.oldName" />
            <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
               plain="true" id="searchB" onclick="formSearch()"><span>查询</span></a>
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
