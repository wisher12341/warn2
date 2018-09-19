<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>账号信息</title>

    <link rel="stylesheet" type="text/css" href="${path}/css/account_css.css"/>

  <script type="text/javascript" src="${path}/js/account_js.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden; width: 90%;height: 80%">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/account/datagrid"
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
      <th data-options="field:'id',width:fixWidth(0.1),align:'center'">ID</th>
      <th data-options="field:'username',width:fixWidth(0.3),align:'center'">账号</th>
      <th data-options="field:'password',width:fixWidth(0.3),align:'center'">密码</th>
        <th data-options="field:'roleId',hidden:true,align:'center'" >角色ID</th>
      <th data-options="field:'name',width:fixWidth(0.3),align:'center'" >角色</th>
    </thead>
  </table>

  <!-- 总控按钮 -->
  <div id="toolbar">
      <div id="buttonTool">
    <a href="javascript:void(0);" class="easyui-linkbutton admin toolB fa fa-plus-square"
        plain="true" onclick="addDialog();"><span>新增</span></a>
    <a href="javascript:void(0);" class="easyui-linkbutton admin toolB fa fa-pencil"
       plain="true" onclick="alt();"><span>修改</span></a>
    <a href="javascript:void(0);" class="easyui-linkbutton admin toolB fa fa-trash"
       plain="true" onclick="del();"><span>删除</span></a>
          <a href="javascript:void(0);" class="easyui-linkbutton admin toolB fa fa-refresh"
             plain="true" onclick="refresh();"><span>刷新</span></a>
  </div>
  </div>



  <!-- 删除对话框 -->
  <div id="dlg_del" class="easyui-dialog"
       style="width:300px;height:200px;padding:10px 20px" closed="true"
       buttons="#dlg_del_buttons">
    <form id="delAccount" method="post" novalidate>
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
  <div id="dlg_altAccount" class="easyui-dialog"
       style="width:400px;height:400px;padding:10px 20px" closed="true"
       buttons="#dlg_altAccount_buttons">
    <form id="altAccount" method="post" novalidate>
        <table>
            <tr>
                <td><span class="addButton">账户信息：</span></td>
                <input type="hidden" name="id" class="auto"/>
            </tr>
            <tr>
                <td><span class="addButton">账号：</span></td>
                <td><input name="username" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><span class="addButton">密码：</span></td>
                <td><input name="password" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><span class="addButton">角色：</span></td>
                <td><input name="name" class="auto" type="text"/></td>
            </tr>
        </table>
    </form>
  </div>

  <!-- 修改对话框按钮 -->
  <div id="dlg_altAccount_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="alt_save()" style="width:90px"><span class="addButton">修改</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_altAccount').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>


  <!-- 新增老人对话框 -->
  <div id="dlg_addAccount" class="easyui-dialog"
       style="width:400px;height:400px;padding:10px 20px" closed="true"
       buttons="#dlg_addAccount_buttons">
    <form id="addAccount" method="post" novalidate>
      <table>
          <tr>
              <td><span class="addButton">账户信息：</span></td>
          </tr>
          <tr>
              <td><span class="addButton">账号：</span></td>
              <td><input name="username" type="text"/></td>
          </tr>
          <tr>
              <td><span class="addButton">密码：</span></td>
              <td><input name="password" type="text"/></td>
          </tr>
          <tr>
              <td><span class="addButton">角色：</span></td>
              <td><input name="name" type="text"/></td>
          </tr>
      </table>
    </form>
  </div>

  <!-- 新增老人对话框按钮 -->
  <div id="dlg_addAccount_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="saveAccount()" style="width:90px"><span class="addButton">保存</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_addAccount').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>


</div>

</body>
</html>
