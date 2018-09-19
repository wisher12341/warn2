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
  <script type="text/javascript" src="${path}/js/set.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">


<div region="center" border="true" singleSelect="true">


    <table id="warn" class="easyui-datagrid" title="预警机制参数设置" singleSelect="true" striped="true">
        <thead>
        <tr>
            <th data-options="field:'a',width:fixWidth(0.365),align:'center'">名称</th>
            <th data-options="field:'b',width:fixWidth(0.365),align:'center',editor:'numberspinner'" >参数</th>
            <th data-options="field:'c',width:fixWidth(0.2),align:'center'" formatter="formatActionW"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td data-options="align:'center'">获取传感器数据时间间隔（分钟）</td>
            <td data-options="align:'center'" id="accessBaseTime"></td>
        </tr>
        <tr>
            <td data-options="align:'center'">预警自动开关</td>
            <td data-options="align:'center'" id="timerOpen"></td>
        </tr>
        </tbody>
    </table>


    <table id="down" class="easyui-datagrid" title="故障机制参数设置" singleSelect="true" striped="true">
        <thead>
        <tr>
            <th data-options="field:'a',width:fixWidth(0.365),align:'center'">名称</th>
            <th data-options="field:'b',width:fixWidth(0.365),align:'center',editor:'numberspinner'" >参数</th>
            <th data-options="field:'c',width:fixWidth(0.2),align:'center'" formatter="formatActionDown"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td data-options="align:'center'">网关故障（分钟）</td>
            <td data-options="align:'center'" id="gatewayDown"></td>
        </tr>
        <tr>
            <td data-options="align:'center'">设备故障（分钟）</td>
            <td data-options="align:'center'" id="equipDown"></td>
        </tr>
        </tbody>
    </table>

    <%--<table  class="easyui-datagrid" title="短信参数设置" singleSelect="true" striped="true">--%>
        <%--<thead>--%>
        <%--<tr>--%>
            <%--<th data-options="field:'a',width:fixWidth(0.45),align:'center'">名称</th>--%>
            <%--<th data-options="field:'b',width:fixWidth(0.45),align:'center'" >参数</th>--%>
            <%--&lt;%&ndash;<th data-options="field:'c',width:fixWidth(0.2),align:'center'" formatter="formatAction"></th>&ndash;%&gt;--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--<tbody>--%>
        <%--<tr>--%>
            <%--<td data-options="align:'center'">预警自动开关</td>--%>
            <%--<td data-options="align:'center'" id="timerOpen"></td>--%>
        <%--</tr>--%>
        <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<td data-options="align:'center'">消息未读多久后发送短信（分钟）</td>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<td data-options="align:'center'" id="smsTime"></td>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
        <%--</tbody>--%>
    <%--</table>--%>

    <%--<table id="sms" class="easyui-datagrid" title="发送短信参数设置" singleSelect="true" striped="true">--%>
        <%--<thead>--%>
        <%--<tr>--%>
            <%--<th data-options="field:'a',width:fixWidth(0.365),align:'center'">名称</th>--%>
            <%--<th data-options="field:'b',width:fixWidth(0.365),align:'center',editor:'numberspinner'" >参数</th>--%>
            <%--<th data-options="field:'c',width:fixWidth(0.2),align:'center'" formatter="formatAction"></th>--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--<tbody>--%>
            <%--<tr>--%>
                <%--<td data-options="align:'center'">开启短信功能</td>--%>
                <%--<td data-options="align:'center'" id="openSys"></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td data-options="align:'center'">消息未读多久后发送短信（分钟）</td>--%>
                <%--<td data-options="align:'center'" id="smsTime"></td>--%>
            <%--</tr>--%>
        <%--</tbody>--%>
    <%--</table>--%>
<script type="text/javascript">
    //获得系统参数   只能放在这里  不能放到js文件 以及$(function(){})中 不然会渲染不了
    $.ajax({
        type: "GET",
        url: pathJs+"/set/getOpenSys",
        dataType: "json",
        async:false,
        success:function(data){
            if(data.data.timerOpen==0){
                $('#timerOpen').html('<input class="switch" type="checkbox" onclick="change(event)" value="0">');
            }else{
                $('#timerOpen').html('<input class="switch select" type="checkbox" onclick="change(event)" checked value="1"> ');
            }
//            $('#smsTime').html(data.data.smsTime);
            $('#accessBaseTime').html(data.data.accessBatabaseTime);
            $('#gatewayDown').html(data.data.gatewayDown);
            $('#equipDown').html(data.data.equipDown);
        }
    });
</script>
</div>
</body>
</html>
