<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%--<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<html>
<head>
    <title>区域模型信息</title>
    <script type="text/javascript">
        var pathJs = "${path}"; //在JS文件中使用  JS环境不能用EL表达式
    </script>
    <%--不能用项目中的easyui版本, 出现了 行内编辑器BUG  --%>
    <%--不能用项目中easyui、easyui_insdep文件夹的jquery.easyui.min.js版本, 会出现 行内编辑器BUG  --%>
    <link rel="stylesheet" type="text/css" href="${path}/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${path}/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${path}/easyui/demo/demo.css">

    <%--第三方图标--%>
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="${path}/css/model.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${path}/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${path}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${path}/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${path}/js/area_model_js.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div region="west" border="true" style="width: 28%">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/room/area/datagrid1" title="人员列表"
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
            <th data-options="field:'oldId',width:fixWidth(0.08),align:'center'" >对应人员ID</th>
            <%--<th data-options="field:'oid',width:fixWidth(0.3),align:'center'" >ID</th>--%>
            <th data-options="field:'oldName',width:fixWidth(0.3),align:'center'" >姓名</th>
            <th data-options="field:'rid',width:fixWidth(0.08),align:'center',hidden:true">房间ID</th>
            <th data-options="field:'roomName',width:fixWidth(0.12),align:'center'" >房间名</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar">
        <form id="search" method="post" action="${paht}/data/area/datagrid1" novalidate>
            <input class="easyui-searchbox" data-options="prompt:'ID'"  id="searchOid" name="oldId" /><br/>
            <%--<input class="easyui-searchbox" data-options="prompt:'姓名'" name="oldName" />--%>
        </form>
        <div id="buttonTool">
            <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
               plain="true" onclick="formSearch()"><span>查询</span></a>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
               plain="true" onclick="refresh();"><span>刷新</span></a>
        </div>
    </div>
</div>

<div region="center" border="true" singleSelect="true" >
    <table id="oldman" class="easyui-datagrid" fitColumns="true" singleSelect="true" url="${path}/model/getAreaRoomModel" title="人员生活规律" toolbar="#toolbar2"
           striped="true" style="overflow: hidden;">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:true">ID</th>
            <th data-options="field:'times',width:fixHtmlWidth(0.55),align:'center'" >活动时间</th>
            <th data-options="field:'timeArea',width:fixHtmlWidth(0.55),align:'center'" >活动区域</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar2">
        <div id="buttonTool1">
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-plus-square"
               plain="true" onclick="addDialog();"><span>新增</span></a>
            <%--<a href="javascript:void(0);" class="easyui-linkbutton alt"--%>
            <%--iconCls="icon-edit" plain="true" onclick="alt();" >修改</a>--%>
        </div>
    </div>

    <table id="tt" class="easyui-datagrid" fitColumns="true" singleSelect="true" url="${path}/model/getAreaModel" title="区域活动规律" striped="true"
           style="overflow: hidden;">
        <thead>
        <tr>
            <th data-options="field:'aid',hidden:true">ID</th>
            <th data-options="field:'areaInfo',width:fixHtmlWidth(0.2),align:'center'">区域</th>
            <th data-options="field:'areaActiveTime',width:fixHtmlWidth(0.45),align:'center'" >在该区域活动的时间段</th>
            <th data-options="field:'areaRestTime',width:fixHtmlWidth(0.45),align:'center'" >在该区域休息的时间段</th>
            <%--<th field="action" width="80" align="center" formatter="formatAction"></th>--%>
        </tr>
        </thead>
    </table>
</div>

<!-- 修改对话框 -->
<%--<div id="dlg_altOldMan" class="easyui-dialog"--%>
<%--style="width:400px;height:400px;padding:10px 20px" closed="true"--%>
<%--buttons="#dlg_altOldMan_buttons">--%>
<%--<form id="altOldMan" method="post" novalidate>--%>
<%--<table>--%>
<%--<tr>--%>
<%--<td>老人信息：</td>--%>
<%--<input type="hidden" name="oid" class="auto"/>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>老人姓名：</td>--%>
<%--<td><input name="oldName" class="auto" type="text"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>老人电话：</td>--%>
<%--<td><input name="oldPhone" class="auto" type="text"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>老人住址：</td>--%>
<%--<td><input name="oldAddress" class="auto" type="text"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td><input name="oldRegtime" class="auto" type="hidden"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>紧急联系人信息：</td>--%>
<%--<input type="hidden" name="relatives.relid" class="auto"/>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>姓名：</td>--%>
<%--<td><input name="relatives.rName" class="auto" type="text"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>电话：</td>--%>
<%--<td><input name="relatives.rPhone" class="auto" type="text"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>住址：</td>--%>
<%--<td><input name="relatives.rAddress" class="auto" type="text"/></td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--<input type="hidden" name="relatives.oldId" class="auto" />--%>
<%--</form>--%>
<%--</div>--%>

<%--<!-- 修改对话框按钮 -->--%>
<%--<div id="dlg_altOldMan_buttons">--%>
<%--<a href="javascript:void(0)" class="easyui-linkbutton c6"--%>
<%--iconCls="icon-ok" onclick="alt_save()" style="width:90px">修改</a>--%>
<%--<a href="javascript:void(0)" class="easyui-linkbutton"--%>
<%--iconCls="icon-cancel" onclick="javascript:$('#dlg_altOldMan').dialog('close')"--%>
<%--style="width:90px">取消</a>--%>
<%--</div>--%>


<!-- 新增模型对话框 -->
<div id="dlg_addModel" class="easyui-dialog"
     style="width:400px;height:400px;padding:10px 20px" closed="true"
     buttons="#dlg_addModel_buttons">
    <form id="addModel" method="post" novalidate>
        <input type="hidden" name="oid" class="oid" value="1"/>
        <table class="tableModel">
            <tr>
                <td><span class="addButton">时间段：</span></td>
                <td><input name="times[0]" class="easyui-textbox" type="text"></td>
            </tr>
            <tr>
                <td><span class="addButton">活动区域：</span></td>
                <td class="roomCheckbox">
                </td>
            </tr>
        </table>
        <input type="button" value="添加" onclick="addModel()" class="addButton" />
    </form>
</div>

<!-- 新增模型对话框按钮 -->
<div id="dlg_addModel_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="saveModel()" style="width:90px"><span class="addButton">保存</span></a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg_addModel').dialog('close')"
       style="width:90px"><span class="addButton">取消</span></a>
</div>

</body>
</html>
