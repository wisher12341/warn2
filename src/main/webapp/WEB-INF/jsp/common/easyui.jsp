<%--
  Created by IntelliJ IDEA.
  User: luoyu
  Date: 2017/3/2
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>

<%@ page import="com.warn.entity.User"%>
<%
    User user = (User)request.getSession().getAttribute("USER");
%>

<script type="text/javascript">
    var pathJs = "${path}"; //在JS文件中使用  JS环境不能用EL表达式
</script>
<!-- easyui -->
<link rel="stylesheet" type="text/css" href="${path}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${path}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${path}/easyui/demo/demo.css">
<script type="text/javascript" src="${path}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${path}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/easyui/locale/easyui-lang-zh_CN.js"></script>

<%--第三方图标--%>
<link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

<%--<link rel="stylesheet" type="text/css" href="${path}/easyUI_insdep/themes/insdep/easyui.css">--%>
<%--<link rel="stylesheet" type="text/css" href="${path}/easyUI_insdep/themes/insdep/icon.css">--%>
<%--&lt;%&ndash;<link rel="stylesheet" type="text/css" href="${path}/easyui/demo/demo.css">&ndash;%&gt;--%>
<%--<script type="text/javascript" src="${path}/js/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="${path}/js/jquery.easyui.min.js"></script>--%>
<%--<script type="text/javascript" src="${path}/easyUI_insdep/jquery.easyui.min.js"></script>--%>
<%--<script type="text/javascript" src="${path}/easyUI_insdep/easyui-lang-zh_CN.js"></script>--%>


