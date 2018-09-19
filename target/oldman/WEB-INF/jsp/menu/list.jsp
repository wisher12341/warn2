<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>菜单信息</title>

    <link rel="stylesheet" type="text/css" href="${path}/css/authority_css.css"/>

  <script type="text/javascript" src="${path}/js/menu_js.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style=" width: 90%;height: 100%">
  <%--直接用权限的接口就好--%>
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/authority/datagrid"
         toolbar="#toolbar"
         fitColumns="true"
         singleSelect="true"
         striped="true"
         border="false"
         nowrap="false">
    <thead>
    <tr>
      <th data-options="field:'name',width:fixWidth(0.3),align:'center'"  formatter="formatAction">名称</th>
      <th data-options="field:'id',width:fixWidth(0.15),align:'center'" >ID</th>
      <th data-options="field:'parentid',width:fixWidth(0.15),align:'center',
      formatter: function(value,row,index){
                if(row.menu.parentid!=0){
                return row.menu.parentid
                }else{
                return '';
                }
           }" >父级菜单ID</th>
      <th data-options="field:'url',width:fixWidth(0.3),align:'center',
      formatter: function(value,row,index){
                return row.menu.url
           }" >接口地址</th>


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
        <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
           plain="true" onclick="refresh();"><span>刷新</span></a>
        <a href="javascript:void(0);" class="easyui-linkbutton admin toolB fa fa-pencil"
           plain="true" onclick="recover();"><span>恢复最初设置</span></a>
  </div>
  </div>


    <!-- 删除对话框 -->
    <div id="dlg_del" class="easyui-dialog"
         style="width:300px;height:200px;padding:10px 20px" closed="true"
         buttons="#dlg_del_buttons">
      <form id="delMenu" method="post" novalidate>
        <label c>确定删除吗？</label>
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
    <div id="dlg_altMenu" class="easyui-dialog"
         style="width:400px;height:400px;padding:10px 20px" closed="true"
         buttons="#dlg_altMenu_buttons">
      <form id="altMenu" method="post" novalidate>
        <table>
          <input type="hidden" name="preId" id="preId"/>
          <tr>
            <td><span class="addButton">菜单信息：</span></td>
          </tr>
          <tr>
            <td><span class="addButton">名称：</span></td>
            <td><input name="name" class="auto" type="text"></td>
          </tr>
          <tr>
            <td><span class="addButton">ID：</span></td>
            <td><input name="id" class="auto" type="text"/></td>
          </tr>
          <tr>
            <td><span class="addButton">父级菜单ID：</span></td>
            <td><input name="parentid" class="auto" type="text"/></td>
          </tr>
          <tr>
            <td><span class="addButton">接口地址：</span></td>
            <td><input name="url" class="auto" type="text"/></td>
          </tr>
        </table>
      </form>
    </div>

    <!-- 修改对话框按钮 -->
    <div id="dlg_altMenu_buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c6"
         iconCls="icon-ok" onclick="alt_save()" style="width:90px"><span class="addButton">修改</span></a>
      <a href="javascript:void(0)" class="easyui-linkbutton"
         iconCls="icon-cancel" onclick="javascript:$('#dlg_altMenu').dialog('close')"
         style="width:90px"><span class="addButton">取消</span></a>
    </div>


    <!-- 新增人员对话框 -->
    <div id="dlg_addMenu" class="easyui-dialog"
         style="width:400px;height:400px;padding:10px 20px" closed="true"
         buttons="#dlg_addMenu_buttons">
      <form id="addMenu" method="post" novalidate>
        <table>
          <tr>
            <td><span class="addButton">菜单信息：</span></td>
          </tr>
          <tr>
            <td><span class="addButton">名称：</span></td>
            <td><input name="name" class="easyui-textbox" type="text"></td>
          </tr>
          <tr>
            <td><span class="addButton">ID：</span></td>
            <td><input name="id" class="easyui-textbox" type="text"></td>
          </tr>
          <tr>
            <td><span class="addButton">父级菜单ID：</span></td>
            <td><input name="parentid" class="easyui-textbox" type="text"></td>
          </tr>
          <tr>
            <td><span class="addButton">接口住址：</span></td>
            <td><input name="url" class="easyui-textbox" type="text"></td>
          </tr>
        </table>
      </form>
    </div>

    <!-- 新增人员对话框按钮 -->
    <div id="dlg_addMenu_buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c6"
         iconCls="icon-ok" onclick="saveMenu()" style="width:90px"><span class="addButton">保存</span></a>
      <a href="javascript:void(0)" class="easyui-linkbutton"
         iconCls="icon-cancel" onclick="javascript:$('#dlg_addMenu').dialog('close')"
         style="width:90px"><span class="addButton">取消</span></a>
    </div>




</div>

</body>
</html>
