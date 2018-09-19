<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%--<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<html>
<head>
    <title>系统设置</title>
  <script type="text/javascript">
    var pathJs = "${path}"; //在JS文件中使用  JS环境不能用EL表达式
  </script>

    <link rel="stylesheet" type="text/css" href="${path}/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${path}/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${path}/easyui/demo/demo.css">

    <%--第三方图标--%>
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="${path}/css/set.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${path}/easyui/jquery.min.js"></script>
  <script type="text/javascript" src="${path}/js/jquery.easyui.min.js"></script> <%--不能用项目中easyui、easyui_insdep文件夹的jquery.easyui.min.js版本, 会出现 行内编辑器BUG  --%>
  <script type="text/javascript" src="${path}/easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="${path}/js/sms_set.js"></script>
</head>
<body class="easyui-layout" fit="true">


<div region="center" border="true" singleSelect="true">





    <table  class="easyui-datagrid" title="短信参数设置" singleSelect="true" striped="true">
        <thead>
        <tr>
            <th data-options="field:'a',width:fixWidth(0.46),align:'center'">名称</th>
            <th data-options="field:'b',width:fixWidth(0.46),align:'center'" >参数</th>
            <%--<th data-options="field:'c',width:fixWidth(0.2),align:'center'" formatter="formatAction"></th>--%>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td data-options="align:'center'">短信功能开关</td>
                <td data-options="align:'center'" id="openSys"></td>
            </tr>
            <%--<tr>--%>
                <%--<td data-options="align:'center'">消息未读多久后发送短信（分钟）</td>--%>
                <%--<td data-options="align:'center'" id="smsTime"></td>--%>
            <%--</tr>--%>
        </tbody>
    </table>

    <table id="sms" class="easyui-datagrid" title="手机信息设置" singleSelect="true" striped="true" url="${path}/sms/datagrid" toolbar="#toolbar">
      <thead>
        <tr>
         <th data-options="field:'id',width:fixWidth(0.1),align:'center'">ID</th>
         <th data-options="field:'phone',width:fixWidth(0.3),align:'center',editor:'text'" >手机号</th>
         <th data-options="field:'orderSms',width:fixWidth(0.1),align:'center',editor:'numberspinner'">顺序</th>
         <th data-options="field:'timeSms',width:fixWidth(0.3),align:'center'" >时间间隔（分钟）</th>
         <th data-options="field:'action',width:fixWidth(0.1),align:'center'" formatter="formatAction"></th>
         </tr>
       </thead>
    </table>

    <div id="toolbar">
        <div id="buttonTool">
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-plus-square"
               plain="true" onclick="addPhone();"><span>新增</span></a>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
               plain="true" onclick="refreshPhone();"><span>刷新</span></a>
        </div>
        <form id="search" method="post" action="${paht}/sms/datagrid" novalidate>
            <%--<label>ID：</label>--%>
            <input class="easyui-searchbox" data-options="prompt:'电话'"  style="width:10%" name="phone" />
            <input class="easyui-searchbox" data-options="prompt:'顺序'" style="width:10%" name="orderSms" />
            <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
               plain="true" id="searchB" onclick="formSearch()"><span>查询</span></a>
        </form>
    </div>


        <table id="order" class="easyui-datagrid" title="顺序设置" singleSelect="true" striped="true" url="${path}/sms/datagridOrder" toolbar="#toolbar2">
            <thead>
            <tr>
                <th data-options="field:'id',hidden:true">ID</th>
                <th data-options="field:'orderSms',width:fixWidth(0.4),align:'center',editor:'numberspinner'" >顺序</th>
                <th data-options="field:'timeSms',width:fixWidth(0.4),align:'center',editor:'numberspinner'">时间间隔（分钟）</th>
                <th data-options="field:'action',width:fixWidth(0.1),align:'center'" formatter="formatActionW"></th>
            </tr>
            </thead>
        </table>
    <div id="toolbar2">
        <div id="buttonTool2">
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-plus-square"
               plain="true" onclick="addOrder();"><span>新增</span></a>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
               plain="true" onclick="refreshOrder();"><span>刷新</span></a>
        </div>
    </div>



    <!-- 删除手机对话框 -->
    <div id="dlg_del" class="easyui-dialog"
         style="width:300px;height:200px;padding:10px 20px" closed="true"
         buttons="#dlg_del_buttons">
        <form id="delPhone" method="post" novalidate>
            <label>确定删除吗？</label>
        </form>
    </div>

    <!-- 删除手机对话框按钮 -->
    <div id="dlg_del_buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="conf_del()" style="width:90px"><span class="addButton">删除</span></a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_del').dialog('close')"
           style="width:90px"><span class="addButton">取消</span></a>
    </div>

    <!-- 新增手机对话框 -->
    <div id="dlg_addPhone" class="easyui-dialog"
         style="width:400px;height:400px;padding:10px 20px" closed="true"
         buttons="#dlg_addPhone_buttons">
        <form id="addPhone" method="post" novalidate>
            <table>
                <tr>
                    <td><span class="addButton">电话信息：</span></td>
                </tr>
                <tr>
                    <td><span class="addButton">手机号：</span></td>
                    <td><input name="phone" type="text"></td>
                </tr>
                <tr>
                    <td><span class="addButton">顺序：</span></td>
                    <td><input name="orderSms" type="text"/></td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 新增手机对话框按钮 -->
    <div id="dlg_addPhone_buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="savePhone()" style="width:90px"><span class="addButton">保存</span></a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_addPhone').dialog('close')"
           style="width:90px"><span class="addButton">取消</span></a>
    </div>




    <!-- 删除顺序对话框 -->
    <div id="dlg_del2" class="easyui-dialog"
         style="width:300px;height:200px;padding:10px 20px" closed="true"
         buttons="#dlg_del_buttons2">
        <form id="delOrder" method="post" novalidate>
            <label>确定删除吗？</label>
        </form>
    </div>

    <!-- 删除顺序对话框按钮 -->
    <div id="dlg_del_buttons2">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="conf_del2()" style="width:90px"><span class="addButton">删除</span></a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_del2').dialog('close')"
           style="width:90px"><span class="addButton">取消</span></a>
    </div>


    <!-- 新增顺序对话框 -->
    <div id="dlg_addOrder" class="easyui-dialog"
         style="width:400px;height:400px;padding:10px 20px" closed="true"
         buttons="#dlg_addOrder_buttons">
        <form id="addOrder" method="post" novalidate>
            <table>
                <tr>
                    <td><span class="addButton">顺序信息：</span></td>
                </tr>
                <tr>
                    <td><span class="addButton">顺序：</span></td>
                    <td><input name="orderSms" type="text"></td>
                </tr>
                <tr>
                    <td><span class="addButton">时间间隔（分钟）：</span></td>
                    <td><input name="timeSms" type="text"/></td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 新增顺序对话框按钮 -->
    <div id="dlg_addOrder_buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="saveOrder()" style="width:90px"><span class="addButton">保存</span></a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_addOrder').dialog('close')"
           style="width:90px"><span class="addButton">取消</span></a>
    </div>


<script type="text/javascript">
    //获得系统参数   只能放在这里  不能放到js文件 以及$(function(){})中 不然会渲染不了
    $.ajax({
        type: "GET",
        url: pathJs+"/sms/getSMSSwitch",
        dataType: "json",
        async:false,
        success:function(data){
            if(data.data.openSys==0){
                $('#openSys').html('<input class="switch" type="checkbox" onclick="change(event)" value="0">');
            }else{
                $('#openSys').html('<input class="switch select" type="checkbox" onclick="change(event)" checked value="1"> ');
            }

        }
    });
</script>
</div>
</body>
</html>
