<%--
  Created by IntelliJ IDEA.
  User: lufy
  Date: 2019/1/12
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<html>
<head>
    <title>区域信息</title>
    <link rel="stylesheet" type="text/css" href="${path}/css/data_css.css"/>
    <script type="text/javascript" src="${path}/js/area_js.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
    <table id="datagrid" class="easyui-datagrid" fit="true" url="${path}/room/area/datagrid"
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
            <th data-options="field:'roomName',width:fixWidth(0.08),align:'center'" >房间名</th>
            <th data-options="field:'collectId',width:fixWidth(0.08),align:'center',formatter:formatActionSensor" >对应设备ID</th>
            <th data-options="field:'oldId',width:fixWidth(0.08),align:'center'" >对应人员ID</th>
            <th data-options="field:'numOne',width:fixWidth(0.05),align:'center'">区域1</th>
            <th data-options="field:'numTwo',width:fixWidth(0.05),align:'center'">区域2</th>
            <th data-options="field:'numThree',width:fixWidth(0.05),align:'center'">区域3</th>
            <th data-options="field:'numFour',width:fixWidth(0.05),align:'center'">区域4</th>
            <th data-options="field:'numFive',width:fixWidth(0.05),align:'center'">区域5</th>
            <th data-options="field:'numSix',width:fixWidth(0.05),align:'center'">区域6</th>
            <th data-options="field:'numSeven',width:fixWidth(0.05),align:'center'">区域7</th>
            <th data-options="field:'numEight',width:fixWidth(0.05),align:'center'">区域8</th>
            <th data-options="field:'numNine',width:fixWidth(0.05),align:'center'">区域9</th>
            <th data-options="field:'numTen',width:fixWidth(0.05),align:'center'">区域10</th>
            <th data-options="field:'rRegtime',width:fixWidth(0.16),align:'center'">注册时间</th>
        </tr>
        </thead>
    </table>

    <!-- 总控按钮 -->
    <div id="toolbar">
        <div id="buttonTool">
            <%--<a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-plus-square"--%>
               <%--plain="true" onclick="addDialog();"><span>新增</span></a>--%>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-pencil"
               plain="true" onclick="alt();"><span>设定</span></a>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-trash"
               plain="true" onclick="del();"><span>删除</span></a>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-download"
               plain="true" onclick="exportfile();"><span>导出</span></a>
            <a href="javascript:void(0);" class="easyui-linkbutton aaa toolB fa fa-refresh"
               plain="true" onclick="refresh();"><span>刷新</span></a>
        </div>
        <form id="search" method="post" action="${paht}/room/area/datagrid" novalidate>
            <input class="easyui-searchbox" data-options="prompt:'房间ID'"  style="width:10%" name="rid" />
            <input class="easyui-searchbox" data-options="prompt:'房间名'"  style="width:10%" name="roomName" />
            <input class="easyui-searchbox" data-options="prompt:'对应设备ID',validType:'length[1,4]'"  style="width:10%" name="collectId" />
            <input class="easyui-searchbox" data-options="prompt:'对应人员ID'"  style="width:10%" name="oldId"/>
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
                    <td><span class="addButton">区域1：</span></td>
                    <td><input name="numOne" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域2：</span></td>
                    <td><input name="numTwo" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域3：</span></td>
                    <td><input name="numThree" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域4：</span></td>
                    <td><input name="numFour" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域5：</span></td>
                    <td><input name="numFive" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域6：</span></td>
                    <td><input name="numSix" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域7：</span></td>
                    <td><input name="numSeven" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域8：</span></td>
                    <td><input name="numEight" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域9：</span></td>
                    <td><input name="numNine" class="auto" type="text"/></td>
                </tr>
                <tr>
                    <td><span class="addButton">区域10：</span></td>
                    <td><input name="numTen" class="auto" type="text"/></td>
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

