<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
  <title>人员信息</title>

    <link rel="stylesheet" type="text/css" href="${path}/css/data.css"/>
    <!--
      themes/insdep/easyui_plus.css
      Insdep对easyui的额外增强样式,内涵所有 insdep_xxx.css 的所有组件样式
      根据需求可单独引入insdep_xxx.css或不引入，此样式不会对easyui产生影响
  -->
    <%--<link href="${path}/easyUI_insdep/themes/insdep/easyui_plus.css" rel="stylesheet" type="text/css">--%>

    <!--
        themes/insdep/insdep_theme_default.css
        Insdep官方默认主题样式,更新需要自行引入，此样式不会对easyui产生影响
    -->
    <%--<link href="${path}/easyUI_insdep/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">--%>

    <!--
        themes/insdep/icon.css
        美化过的easyui官方icons样式，根据需要自行引入
    -->
    <%--<link href="${path}/easyUI_insdep/themes/insdep/icon.css" rel="stylesheet" type="text/css">--%>
  <script type="text/javascript" src="${path}/js/data_js.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
  <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden; width: 90%;height: 80%">
  <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/data/datagrid"
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
      <th data-options="field:'oid',width:fixWidth(0.05),align:'center'" rowspan="2">人员ID</th>
      <th data-options="field:'oldName',width:fixWidth(0.05),align:'center'" rowspan="2">姓名</th>
        <th data-options="field:'gatewayID',width:fixWidth(0.09),align:'center'" rowspan="2">网关</th>
        <th data-options="field:'segment',width:fixWidth(0.09),align:'center',formatter:formatActionSegment" rowspan="2">网段标识</th>
      <th data-options="field:'oldPhone',width:fixWidth(0.08),align:'center'" rowspan="2">电话</th>
      <th data-options="field:'oldAddress',width:fixWidth(0.14),align:'center'" rowspan="2">住址</th>
      <th data-options="field:'oldRegtime',width:fixWidth(0.08),align:'center'" rowspan="2">注册时间</th>
      <th data-options="field:'rooms',width:fixWidth(0.052),align:'center',formatter:formatActionRoom" rowspan="2">房间</th>
        <th data-options="field:'equips',width:fixWidth(0.052),align:'center',formatter:formatActionEquip" rowspan="2">设备</th>
      <th data-options="field:'relid',hidden:true,formatter: function(value,row,index){if (row.relatives.relid){return row.relatives.relid;} else {return '';}}">紧急联系人ID</th>
      <th colspan="4">紧急联系人</th>
    </tr>
    <tr>
      <th data-options="field:'rName',width:fixWidth(0.08),align:'center',
      formatter: function(value,row,index){
                if (row.relatives.rName){
                    return row.relatives.rName;
                } else {
                    return '';
                }
           }">姓名</th>
      <th data-options="field:'rPhone',width:fixWidth(0.08),align:'center',
      formatter: function(value,row,index){
                if (row.relatives.rPhone){
                    return row.relatives.rPhone;
                } else {
                    return '';
                }
           }">电话</th>
      <th data-options="field:'rAddress',width:fixWidth(0.13),align:'center',
      formatter: function(value,row,index){
                if (row.relatives.rAddress){
                    return row.relatives.rAddress;
                } else {
                    return '';
                }
           }">住址</th>
        <th data-options="field:'oldId',hidden:true,
      formatter: function(value,row,index){
                if (row.relatives.oldId){
                    return row.relatives.oldId;
                } else {
                    return '';
                }
           }">紧急联系人对应人员ID</th>
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
    <form id="search" method="post" action="${paht}/data/datagrid" novalidate>
        <%--<label>ID：</label>--%>
        <input class="easyui-searchbox" data-options="prompt:'人员ID'"  style="width:10%" name="oid" />
            <input class="easyui-searchbox" data-options="prompt:'姓名'" style="width:10%" name="oldName" />
            <input class="easyui-searchbox" data-options="prompt:'网关',validType:'length[1,4]'" style="width:10%" name="gatewayID" />
            <input class="easyui-searchbox" data-options="prompt:'网段标识',validType:'length[1,4]'" style="width:10%" name="segment" />
        <input class="easyui-searchbox" data-options="prompt:'电话'" style="width:10%" name="oldPhone" />
        <input class="easyui-searchbox" data-options="prompt:'地址'" style="width:15%" name="oldAddress"/>
        <input  data-options="prompt:'注册日期'" style="width:10%" name="oldRegtime" class="easyui-datebox" />
        <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
           plain="true" id="searchB" onclick="formSearch()"><span>查询</span></a>
    </form>
  </div>



  <!-- 删除对话框 -->
  <div id="dlg_del" class="easyui-dialog"
       style="width:300px;height:200px;padding:10px 20px" closed="true"
       buttons="#dlg_del_buttons">
    <form id="delOldMan" method="post" novalidate>
      <label>确定删除吗？</label>
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
  <div id="dlg_altOldMan" class="easyui-dialog"
       style="width:400px;height:400px;padding:10px 20px" closed="true"
       buttons="#dlg_altOldMan_buttons">
    <form id="altOldMan" method="post" novalidate>
        <table>
            <tr>
                <td><span class="addButton">人员信息：</span></td>
            </tr>
            <tr>
                <td><span class="addButton">人员姓名：</span></td>
                <td><input name="oldName" class="auto" type="text"/></td>
            </tr>
            <%--<tr>--%>
                <%--<td><span class="addButton">网关：</span></td>--%>
                <%--<td><input name="gatewayTwo_TenE" value="2" type="radio"/>二进制<input name="gatewayTwo_TenE" value="10" type="radio">十进制</td>--%>
            <%--</tr>--%>
            <tr>
                <td><span class="addButton">网关：</span></td>
                <td><input name="gatewayID" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><span class="addButton">网段标识：</span></td>
                <td><input name="segmentTwo_TenE" value="2" type="radio"/>二进制<input name="segmentTwo_TenE" value="10" type="radio">十进制</td>
            </tr>
            <tr>
                <td><span class="addButton"></span></td>
                <td><input name="segment" class="auto" type="text" maxlength="4"/></td>
            </tr>
            <tr>
                <td><span class="addButton">人员电话：</span></td>
                <td><input name="oldPhone" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><span class="addButton">人员住址：</span></td>
                <td><input name="oldAddress" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><input name="oldRegtime" class="auto" type="hidden"/></td>
            </tr>
            <tr>
                <td><span class="addButton">紧急联系人信息：</span></td>
                <input type="hidden" name="relatives.relid" class="auto"/>
            </tr>
            <tr>
                <td><span class="addButton">姓名：</span></td>
                <td><input name="relatives.rName" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><span class="addButton">电话：</span></td>
                <td><input name="relatives.rPhone" class="auto" type="text"/></td>
            </tr>
            <tr>
                <td><span class="addButton">住址：</span></td>
                <td><input name="relatives.rAddress" class="auto" type="text"/></td>
            </tr>
        </table>
        <input type="hidden" name="oid" class="auto"/>
    </form>
  </div>

  <!-- 修改对话框按钮 -->
  <div id="dlg_altOldMan_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="alt_save()" style="width:90px"><span class="addButton">修改</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_altOldMan').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>


  <!-- 新增人员对话框 -->
  <div id="dlg_addOldMan" class="easyui-dialog"
       style="width:400px;height:400px;padding:10px 20px" closed="true"
       buttons="#dlg_addOldMan_buttons">
    <form id="addOldMan" method="post">
      <table>
        <tr>
            <td><span class="addButton">人员信息：</span></td>
        </tr>
          <tr>
              <td><span class="addButton">人员姓名：</span></td>
              <td><input name="oldName" class="easyui-textbox" type="text"></td>
          </tr>
          <%--<tr>--%>
              <%--<td><span class="addButton">网关：</span></td>--%>
              <%--<td><input name="gatewayTwo_Ten" value="2" type="radio"/>二进制<input name="gatewayTwo_Ten" value="10" type="radio">十进制</td>--%>
          <%--</tr>--%>
          <tr>
              <td><span class="addButton">网关：</span></td>
              <td><input name="gatewayID" class="easyui-textbox" type="text"></td>
          </tr>
          <tr>
              <td><span class="addButton">网段标识：</span></td>
              <td><input name="segmentTwo_Ten" value="2" type="radio"/>二进制<input name="segmentTwo_Ten" value="10" type="radio">十进制</td>
          </tr>
          <tr>
              <td><span class="addButton"></span></td>
              <td><input name="segment" class="easyui-textbox" type="text" data-options="validType:'length[1,4]'"></td>
          </tr>
        <tr>
            <td><span class="addButton">人员电话：</span></td>
          <td><input name="oldPhone" class="easyui-textbox" type="text"></td>
        </tr>
        <tr>
            <td><span class="addButton">人员住址：</span></td>
          <td><input name="oldAddress" class="easyui-textbox" type="text"></td>
        </tr>
        <tr>
            <td><span class="addButton">紧急联系人信息：</span></td>
        </tr>
        <tr>
            <td><span class="addButton">姓名：</span></td>
          <td><input name="relatives.rName" class="easyui-textbox" type="text"></td>
        </tr>
        <tr>
            <td><span class="addButton">电话：</span></td>
          <td><input name="relatives.rPhone" class="easyui-textbox" type="text"></td>
        </tr>
        <tr>
            <td><span class="addButton">住址：</span></td>
          <td><input name="relatives.rAddress" class="easyui-textbox" type="text"></td>
        </tr>
      </table>
        <input type="hidden" name="oid"/>
    </form>
  </div>

  <!-- 新增人员对话框按钮 -->
  <div id="dlg_addOldMan_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="saveOldMan()" style="width:90px"><span class="addButton">保存</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_addOldMan').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
  </div>


</div>

</body>
</html>
