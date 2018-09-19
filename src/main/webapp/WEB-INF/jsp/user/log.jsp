<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
    <title>日志信息</title>
    <script type="text/javascript" src="${path}/js/log.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
    <link href="${path}/css/log_user.css" rel="stylesheet" type="text/css">
    <%--<script type="text/javascript" src="${path}/js/paper_author.js"></script>--%>
</head>
<body class="easyui-layout" fit="true">

<div id="table">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/log/datagrid" title="日志信息"
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
            <th data-options="field:'logId',width:fixWidth(0.1),align:'center'">ID</th>
            <th data-options="field:'logData',width:fixWidth(0.8),align:'center'">内容</th>
            <th data-options="field:'logTime',width:fixWidth(0.2),align:'center'">添加时间</th>
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
        <form id="search" method="post" action="${path}/log/datagrid" novalidate>
            <input class="easyui-searchbox" data-options="prompt:'内容'"  style="width:30%" name="logData" />
            <input data-options="prompt:'添加日期'" style="width:10%" name="logTime" class="easyui-datebox" />
            <a href="javascript:void(0);" class="easyui-linkbutton fa fa-search aaa toolB"
               plain="true" onclick="formSearch()"><span>查询</span></a>
        </form>

    </div>

    <!-- 修改对话框 -->
    <div id="dlg_altLog" class="easyui-dialog"
         style="width:400px;height:400px;padding:10px 20px" closed="true"
         buttons="#dlg_altLog_buttons">
        <form id="altLog" method="post" novalidate>
            <table>
                <tr>
                    <td><span class="addButton">日志信息：</span></td>
                </tr>
                <tr>
                    <td><textarea name="logData" id="altInput"></textarea></td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 修改对话框按钮 -->
    <div id="dlg_altLog_buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="alt_save()" style="width:90px"><span class="addButton">修改</span></a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_altLog').dialog('close')"
           style="width:90px"><span class="addButton">取消</span></a>
    </div>

    <!-- 新增日志对话框 -->
    <div id="dlg_addLog" class="easyui-dialog"
         style="width:400px;height:400px;padding:10px 20px" closed="true"
         buttons="#dlg_addLog_buttons">
        <form id="addLog" method="post">
            <table>
                <tr>
                    <td><span class="addButton">日志信息：</span></td>
                </tr>
                <tr>
                    <td><textarea name="logData"></textarea></td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 新增人员对话框按钮 -->
    <div id="dlg_addLog_buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="saveLog()" style="width:90px"><span class="addButton">保存</span></a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_addLog').dialog('close')"
           style="width:90px"><span class="addButton">取消</span></a>
    </div>


    <!-- 删除对话框 -->
    <div id="dlg_del" class="easyui-dialog"
         style="width:300px;height:200px;padding:10px 20px" closed="true"
         buttons="#dlg_del_buttons">
        <form id="delLog" method="post" novalidate>
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
</div>

</body>

<script type="text/javascript">
    $(function(){
        $(".active",parent.document).removeClass("active");
        $("#index + li + li +li",parent.document).addClass("active");
    });
</script>
</html>
