<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%--<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<html>
<head>
    <title>短信模板</title>
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
  <script type="text/javascript" src="${path}/js/sms_template.js"></script>
    <script type="text/javascript" src="${path}/js/common.js"></script>
</head>
<body class="easyui-layout" fit="true">


<div region="center" border="true" singleSelect="true">


    <table id="sms" class="easyui-datagrid" title="短信模板参数" singleSelect="true" striped="true">
        <thead>
        <tr>
            <th data-options="field:'a',width:fixWidth(0.365),align:'center'">名称</th>
            <th data-options="field:'b',width:fixWidth(0.365),align:'center',editor:'text'" >参数</th>
            <th data-options="field:'c',width:fixWidth(0.2),align:'center'" formatter="formatAction"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td data-options="align:'center'">AppKey</td>
            <td data-options="align:'center'">${constants.APP_KEY}</td>
            <td></td>
        </tr>
        <tr>
            <td data-options="align:'center'">短信签名</td>
            <td data-options="align:'center'">${constants.SMS_SIGN}</td>
            <td></td>
        </tr>
        <tr>
            <td data-options="align:'center'">公共回传参数</td>
            <td data-options="align:'center'">${constants.EXTEND}</td>
            <td></td>
        </tr>
        <tr>
            <td data-options="align:'center'">短信类型</td>
            <td data-options="align:'center'">${constants.SMS_TYPE}</td>
            <td></td>
        </tr>
        <tr>
            <td data-options="align:'center'">短信模板ID</td>
            <td data-options="align:'center'">${constants.SMS_TEMPLATE_CODE}</td>
            <td></td>
        </tr>
        <tr>
            <td data-options="align:'center'">App Secret</td>
            <td data-options="align:'center'">${constants.SECRET}</td>
            <td></td>
        </tr>
        </tbody>
    </table>


</div>
</body>
</html>
