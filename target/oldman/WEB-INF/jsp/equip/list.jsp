<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>设备信息</title>
  <link rel="stylesheet" type="text/css" href="${path}/css/data_css.css"/>
  <script type="text/javascript" src="${path}/js/equip_js.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/equip/datagrid"
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
        <th data-options="field:'gatewayID',width:fixWidth(0.13),align:'center'">网关</th>
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
    <form id="search" method="post" action="${paht}/equip/datagrid" novalidate>
        <input class="easyui-searchbox" data-options="prompt:'网关',validType:'length[1,4]'" data-options="validType:'maxLength[1,4]'" style="width:10%" name="gatewayID" />
        <input class="easyui-searchbox" data-options="prompt:'设备ID',validType:'length[1,4]'" data-options="validType:'maxLength[1,4]'" style="width:10%" name="eid" />
        <input class="easyui-searchbox" data-options="prompt:'设备名'"  style="width:10%" name="eName" />
        <input class="easyui-searchbox" data-options="prompt:'对应人员ID'"  style="width:10%" name="oldId" />
        <input class="easyui-searchbox" data-options="prompt:'对应房间ID'"  style="width:10%" name="roomId"/>
        <input data-options="prompt:'注册日期'"  style="width:10%" name="eRegtime" class="easyui-datebox" />
        <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
           plain="true" onclick="formSearch()"><span>查询</span></a>
    </form>
  </div>



  <!-- 删除对话框 -->
  <div id="dlg_del" class="easyui-dialog"
       style="width:300px;height:200px;padding:10px 20px" closed="true"
       buttons="#dlg_del_buttons">
    <form id="delEquip" method="post" novalidate>
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
  <div id="dlg_altEquip" class="easyui-dialog"
       style="width:400px;height:400px;padding:10px 20px" closed="true"
       buttons="#dlg_altEquip_buttons">
    <form id="altEquip" method="post" novalidate>
        <table>
            <tr>
                <td><span class="addButton">设备信息：</span></td>
                <input type="hidden" name="eid" class="auto"/>
                <input type="hidden" name="preOid" id="preOid"/>
            </tr>
            <tr>
                <td><span class="addButton">设备名：</span></td>
                <td><input name="eName" class="auto" type="text"/></td>
            </tr>
            <%--<tr>--%>
                <%--<td><span class="addButton">对应网关ID：</span></td>--%>
                <%--<td><input name="oldId" class="auto" type="text"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><span class="addButton">对应房间ID：</span></td>--%>
                <%--<td><input name="roomId" class="auto" type="text"/></td>--%>
            <%--</tr>--%>
            <tr>
                <td><span class="addButton">对应人员ID：</span></td>
                <td id="altgatewaySelect"></td>
            </tr>
            <tr>
                <td><span class="addButton">对应房间ID：</span></td>
                <td id="altroomSelect"></td>
            </tr>
        </table>
        <input name="eRegtime" class="auto" type="hidden"/>
    </form>
  </div>

  <!-- 修改对话框按钮 -->
  <div id="dlg_altEquip_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="alt_save()" style="width:90px"><span class="addButton">修改</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_altEquip').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>


  <!-- 新增人员对话框 -->
  <div id="dlg_addEquip" class="easyui-dialog"
       style="width:400px;height:400px;padding:10px 20px" closed="true"
       buttons="#dlg_addEquip_buttons">
    <form id="addEquip" method="post" novalidate>
      <table>
        <tr>
            <td><span class="addButton">设备信息：</span></td>
        </tr>
        <tr>
            <td><span class="addButton">设备ID：</span></td>
            <td><input name="gatewayTwo_Ten" value="2" type="radio"/>二进制<input name="gatewayTwo_Ten" value="10" type="radio">十进制</td>
        <tr>
          <tr>
              <td><span class="addButton"></span></td>
              <td><input name="eid" class="easyui-textbox" type="text" data-options="validType:'length[1,4]'"></td></tr>
          <tr>
            <td><span class="addButton">设备名：</span></td>
          <td><input name="eName" class="easyui-textbox" type="text"></td>
        </tr>
        <%--<tr>--%>
            <%--<td><span class="addButton">对应网关ID：</span></td>--%>
          <%--<td><input name="oldId" class="easyui-textbox" type="text"></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td><span class="addButton">对应房间ID：</span></td>--%>
          <%--<td><input name="roomId" class="easyui-textbox" type="text"></td>--%>
        <%--</tr>--%>
          <tr>
              <td><span class="addButton">对应人员ID：</span></td>
              <td id="gatewaySelect"></td>
          </tr>
          <tr>
              <td><span class="addButton">对应房间ID：</span></td>
              <td id="roomSelect"></td>
          </tr>
      </table>
    </form>
  </div>

  <!-- 新增论文对话框按钮 -->
  <div id="dlg_addEquip_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="saveEquip()" style="width:90px"><span class="addButton">保存</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_addEquip').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>


</div>

</body>
</html>
