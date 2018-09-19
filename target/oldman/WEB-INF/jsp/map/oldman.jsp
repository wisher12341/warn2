<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>人员绑定</title>
  <link rel="stylesheet" type="text/css" href="${path}/css/data_css.css"/>
  <script type="text/javascript" src="${path}/js/map_oldman.js"></script>
  <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/map/oldman/data"
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
      <th data-options="field:'oid',width:fixWidth(0.2),align:'center'">人员ID</th>
        <th data-options="field:'oldName',width:fixWidth(0.2),align:'center'" >人员姓名</th>
      <th data-options="field:'qid',hidden:'true',formatter: function(value,row,index){
                if (row.quMarker && row.quMarker.id){
                    return row.quMarker.id;
                } else {
                    return '';
                }
           }" ></th>
        <th data-options="field:'jid',hidden:'true',formatter: function(value,row,index){
                if (row.jieDaoMarker.id && row.jieDaoMarker.id){
                    return row.jieDaoMarker.id;
                } else {
                    return '';
                }
           }" ></th>
        <th data-options="field:'lid',hidden:'true',formatter: function(value,row,index){
                if (row.louMarker.id && row.louMarker.id){
                    return row.louMarker.id;
                } else {
                    return '';
                }
           }" ></th>
      <th data-options="field:'mapAddress',width:fixWidth(0.4),align:'center'" >地址</th>
        <th data-options="field:'action',width:fixWidth(0.2),align:'center'"  formatter="formatAction">操作</th>
    </tr>
    </thead>
  </table>

  <!-- 总控按钮 -->
  <div id="toolbar">
      <div id="buttonTool">
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
             plain="true" onclick="refresh();"><span>刷新</span></a>
      </div>
    <form id="search" method="post" action="${paht}/map/oldman/data" novalidate>
        <input class="easyui-searchbox" data-options="prompt:'人员ID'"  style="width:10%" name="oid" />
        <input class="easyui-searchbox" data-options="prompt:'人员姓名'"  style="width:10%" name="oldName" />
        <input class="easyui-searchbox" data-options="prompt:'区ID'"  style="width:10%" name="quMarker.id" />
        <input class="easyui-searchbox" data-options="prompt:'街道ID'"  style="width:10%" name="jieDaoMarker.id" />
        <input class="easyui-searchbox" data-options="prompt:'楼ID'"  style="width:10%" name="louMarker.id" />
        <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
           plain="true" onclick="formSearch()"><span>查询</span></a>
    </form>

  </div>

    <!-- 修改对话框 -->
    <div id="dlg_altOldMan" class="easyui-dialog"
         style="width:400px;height:400px;padding:10px 20px" closed="true"
         buttons="#dlg_altOldMan_buttons">
        <form id="altOldMan" method="post" novalidate>
            <table>
                <tr>
                    <td><span class="addButton">人员信息：</span></td>
                </tr>
                <tr>
                    <td><span class="addButton">人员ID：</span></td>
                    <td><span id="oid"></span></td>
                </tr>
                <tr>
                    <td><span class="addButton">人员姓名：</span></td>
                    <td><span id="name"></span></td>
                </tr>
                <tr>
                    <td><span class="addButton">区：</span></td>
                    <td><select id="quMarker" onchange="quInit(this.options[this.options.selectedIndex].value,0,0)"></select></td>
                </tr>
                <tr>
                    <td><span class="addButton">街道：</span></td>
                    <td><select id="jieDaoMarker" onchange="jieDaoInit(this.options[this.options.selectedIndex].value,0)"></select></td>
                </tr>
                <tr>
                    <td><span class="addButton">楼：</span></td>
                    <td><select name="louId" id="louMarker"></select></td>
                </tr>
            </table>
            <input type="hidden" name="oid" class="oid"/>
        </form>
    </div>

    <!-- 修改对话框按钮 -->
    <div id="dlg_altOldMan_buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="alt_save()" style="width:90px"><span class="addButton">设置</span></a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_altOldMan').dialog('close')"
           style="width:90px"><span class="addButton">取消</span></a>
    </div>
</div>


</body>
</html>
