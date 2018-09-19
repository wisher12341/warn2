<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>人员信息</title>
  <link rel="stylesheet" type="text/css" href="${path}/css/data_css.css"/>
  <script type="text/javascript" src="${path}/js/room_js.js"></script>
  <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/room/datagrid"
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
      <th data-options="field:'roomName',width:fixWidth(0.18),align:'center'" >房间名</th>
      <th data-options="field:'collectId',width:fixWidth(0.18),align:'center',formatter:formatActionSensor" >对应设备ID</th>
      <th data-options="field:'oldId',width:fixWidth(0.1),align:'center'" >对应人员ID</th>
      <th data-options="field:'nerRoom',width:fixWidth(0.3),align:'center'">相邻房间</th>
      <th data-options="field:'rRegtime',width:fixWidth(0.16),align:'center'">注册时间</th>
    </tr>
    </thead>
  </table>

  <!-- 总控按钮 -->
  <div id="toolbar">
      <div id="buttonTool">
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-plus-square"
             plain="true" onclick="addDialog();"><span>新增</span></a>
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-pencil"
             plain="true" onclick="alt();"><span>修改</span></a>
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-trash"
             plain="true" onclick="del();"><span>删除</span></a>
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-download"
             plain="true" onclick="exportfile();"><span>导出</span></a>
          <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
             plain="true" onclick="refresh();"><span>刷新</span></a>
      </div>
    <form id="search" method="post" action="${paht}/room/datagrid" novalidate>
        <input class="easyui-searchbox" data-options="prompt:'房间ID'"  style="width:10%" name="rid" />
        <input class="easyui-searchbox" data-options="prompt:'房间名'"  style="width:10%" name="roomName" />
        <input class="easyui-searchbox" data-options="prompt:'对应设备ID',validType:'length[1,4]'"  style="width:10%" name="collectId" />
        <input class="easyui-searchbox" data-options="prompt:'对应人员ID'"  style="width:10%" name="oldId"/>
        <input class="easyui-searchbox" data-options="prompt:'相邻房间'"  style="width:15%" name="nerRoom"/>
        <input data-options="prompt:'注册日期'" style="width:10%" name="rRegtime" class="easyui-datebox" />
        <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
           plain="true" onclick="formSearch()"><span>查询</span></a>
    </form>

  </div>



  <!-- 删除对话框 -->
  <div id="dlg_del" class="easyui-dialog"
       style="width:300px;height:200px;padding:10px 20px" closed="true"
       buttons="#dlg_del_buttons">
    <form id="delRoom" method="post" novalidate>
      <label class="addButton">确定删除吗？</label>
    </form>
  </div>

  <!-- 删除对话框按钮 -->
  <div id="dlg_del_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="conf_del()" style="width:90px"><span class="addButton">删除</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_del').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>



  <!-- 修改对话框 -->
  <div id="dlg_altRoom" class="easyui-dialog"
       style="width:400px;height:400px;padding:10px 20px" closed="true"
       buttons="#dlg_altRoom_buttons">
    <form id="altRoom" method="post" novalidate>
        <table>
            <tr>
                <td><span class="addButton">房间信息：</span></td>
                <input type="hidden" name="rid" class="auto"/>
            </tr>
            <tr>
                <td><span class="addButton">房间名：</span></td>
                <td><input name="roomName" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><span class="addButton">对应设备ID：</span></td>
                <td><input name="gatewayTwo_Ten" value="2" type="radio"/>二进制<input name="gatewayTwo_Ten" value="10" type="radio">十进制</td>
            </tr>
            <tr>
                <td><span class="addButton"></span></td>
                <td><input name="collectId" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><span class="addButton">对应人员ID：</span></td>
                <td id="altgatewaySelect"></td>
            </tr>
            <tr>
                <td><span class="addButton">相邻房间：</span></td>
                <td><input name="nerRoom" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><input name="rRegtime" class="auto" type="hidden"/></td>
            </tr>
        </table>
    </form>
  </div>

  <!-- 修改对话框按钮 -->
  <div id="dlg_altRoom_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="alt_save()" style="width:90px"><span class="addButton">修改</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_altRoom').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>


  <!-- 新增房间对话框 -->
  <div id="dlg_addRoom" class="easyui-dialog"
       style="width:400px;height:400px;padding:10px 20px" closed="true"
       buttons="#dlg_addRoom_buttons">
    <form id="addRoom" method="post" novalidate>
      <table>
        <tr>
            <td><span class="addButton">房间信息：</span></td>
        </tr>
        <tr>
            <td><span class="addButton">房间名：</span></td>
          <td><input name="roomName" class="easyui-textbox" type="text"></td>
        </tr>
        <%--<tr>--%>
            <%--<td><span class="addButton">对应设备ID：</span></td>--%>
          <%--<td><input name="collectId" class="easyui-textbox" type="text"></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td><span class="addButton">对应网关ID：</span></td>--%>
          <%--<td><input name="oldId" class="easyui-textbox" type="text"></td>--%>
        <%--</tr>--%>
          <tr>
              <td><span class="addButton">对应人员ID：</span></td>
              <td id="gatewaySelect"></td>
          </tr>
        <tr>
            <td><span class="addButton">相邻房间：</span></td>
          <td><input name="nerRoom" class="easyui-textbox" type="text"></td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 新增房间对话框按钮 -->
  <div id="dlg_addRoom_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="saveRoom()" style="width:90px"><span class="addButton">保存</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_addRoom').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>


</div>


</body>
</html>
