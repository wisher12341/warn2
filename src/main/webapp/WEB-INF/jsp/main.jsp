<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/4/21
  Time: 22:45
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

<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>长者关怀系统</title>
  <link rel="shortcut icon" href="${path}/images/title.ico">
  <!--
      注意样式表优先级
      主题样式必须在easyui组件样式后。
  -->

  <link href="${path}/easyUI_insdep/themes/insdep/easyui.css" rel="stylesheet" type="text/css">


  <!--
      themes/insdep/easyui_animation.css
      Insdep对easyui的额外增加的动画效果样式，根据需求引入或不引入，此样式不会对easyui产生影响
  -->
  <link href="${path}/easyUI_insdep/themes/insdep/easyui_animation.css" rel="stylesheet" type="text/css">

  <!--
      themes/insdep/easyui_plus.css
      Insdep对easyui的额外增强样式,内涵所有 insdep_xxx.css 的所有组件样式
      根据需求可单独引入insdep_xxx.css或不引入，此样式不会对easyui产生影响
  -->
  <link href="${path}/easyUI_insdep/themes/insdep/easyui_plus.css" rel="stylesheet" type="text/css">

  <!--
      themes/insdep/insdep_theme_default.css
      Insdep官方默认主题样式,更新需要自行引入，此样式不会对easyui产生影响
  -->
  <link href="${path}/easyUI_insdep/themes/insdep/insdep_theme_default.css" rel="stylesheet" type="text/css">

  <!--
      themes/insdep/icon.css
      美化过的easyui官方icons样式，根据需要自行引入
  -->
  <link href="${path}/easyUI_insdep/themes/insdep/icon.css" rel="stylesheet" type="text/css">

  <!--
      2017/03/22 新增
      plugin/font-awesome-4.7.0/css/font-awesome.min.css
      第三方图标库样式，用于显示左侧菜单栏图标，根据需要自行引入
  -->
  <link href="${path}/easyUI_insdep/plugin/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="${path}/easyUI_insdep/jquery.min.js"></script>
  <script type="text/javascript" src="${path}/easyUI_insdep/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="${path}/easyUI_insdep/themes/insdep/jquery.insdep-extend.min.js"></script>

  <link href="${path}/css/main.css" rel="stylesheet" type="text/css">

  <%--dwr--%>
  <%--<script type='text/javascript' src="${path}/dwr/engine.js"></script>--%>
  <%--<script type='text/javascript' src="${path}/dwr/util.js"></script>--%>
  <%--<script type='text/javascript' src="${path}/dwr/interface/Remote.js"></script>--%>
</head>

<body>

<div id="process">
  <span>长者关怀系统</span>
</div>

<div id="master-layout">
  <%--<div data-options="region:'north',border:false,bodyCls:'theme-header-layout'">--%>
  <%--</div>--%>

  <!--开始左侧菜单-->
  <div data-options="region:'west',border:false,bodyCls:'theme-left-layout'" style="width:15%;">


    <!--正常菜单-->
    <div class="theme-left-normal">
      <!--theme-left-switch 如果不需要缩进按钮，删除该对象即可-->
      <div class="left-control-switch theme-left-switch"><i class="fa fa-chevron-left fa-lg"></i></div>

      <!--start class="easyui-layout"-->
      <div class="easyui-layout" data-options="border:false,fit:true">
        <!--start region:'north'-->
        <div data-options="region:'north',border:false" style="height:100px;">
          <!--start theme-left-user-panel-->
          <div class="theme-left-user-panel">
            <dl>
              <dt>
                <img src="${path}/easyUI_insdep/themes/insdep/images/portrait86x86.png" width="43" height="43">
              </dt>
              <dd>
                <b class="badge-prompt"><%=user.getUsername()%></b>
                <span class="role"></span>
              </dd>
            </dl>
          </div>
          <!--end theme-left-user-panel-->
        </div>
        <!--end region:'north'-->

        <!--start region:'center'-->
        <div data-options="region:'center',border:false">
          <!--start easyui-accordion-->
          <div class="easyui-accordion" data-options="border:false,fit:true">
            <%--<div title="公共信息">--%>
              <%--<ul class="easyui-datalist" data-options="border:false,fit:true">--%>
                <%--<li>企业文化</li>--%>
                <%--<li>公文</li>--%>
                <%--<li>新闻公告</li>--%>
                <%--<li>重大信息</li>--%>
              <%--</ul>--%>
            <%--</div>--%>
          </div>
          <!--end easyui-accordion-->

        </div>
        <!--end region:'center'-->
      </div>
      <!--end class="easyui-layout"-->

    </div>
    <!--最小化菜单-->
    <div class="theme-left-minimal">
      <ul class="easyui-datalist" data-options="border:false,fit:true">
        <li><a class="left-control-switch"><i class="fa fa-chevron-right fa-2x"></i><p>打开</p></a></li>
      </ul>
    </div>

  </div>
  <!--结束左侧菜单-->

  <%--<div data-options="region:'center',border:false"  id="control" style="padding-top: 5px; background:#fff;">--%>
    <%--<div class="easyui-tabs" id="mainTabs" fit="true" border="false">--%>
      <%--<div title="首页" style="padding:10px;">--%>
        <%--<div style="margin-top:20px; float:left;min-width:600px;width:100%; height: 90%;">--%>
          <%--<h1 style="font-size:24px;">欢迎使用！</h1>--%>
        <%--</div>--%>
      <%--</div>--%>
    <%--</div>--%>
  <%--</div>--%>
  <div data-options="region:'center',border:false"  id="control" style="overflow: hidden">
    <div class="theme-navigate">

      <%--<div class="left messageB" style="display: none">--%>
        <%--<a href="#" class="easyui-menubutton" data-options="menu:'#mm',hasDownArrow:false">未读消息<span class="badge color-default tMessage"></span></a>--%>
        <%--<div id="mm" class="theme-navigate-menu-panel" style="width:180px;">--%>
          <%--<div id="noReadMessageWarn">预警消息<span class="badge color-important wMessage"></span></div>--%>
          <%--<div id="noReadMessageOut">出门消息<span class="badge color-success oMessage"></span></div>--%>
        <%--</div>--%>
      <%--</div>--%>

      <div class="right">
        <a href="/" class="btn">返回前台</a>
        <a href="#" class="easyui-menubutton theme-navigate-more-button" data-options="menu:'#more',hasDownArrow:false"></a>
        <div id="more" class="theme-navigate-more-panel">
          <div onclick="reLogin();">重新登录</div>
          <div onclick="logOut();">退出系统</div>
        </div>
      </div>
    </div>

    <div class="easyui-tabs theme-tab-black-block theme-tab-line-bold" style="width:100%;height:100%;" data-options="tabPosition:'top'" id="mainTabs">
      <div title="首页">
        <div id="welcome"></div>
        <div data-options="region:'north',border:false" style="height:40%">
        <div class="theme-user-info-panel">
          <div class="left cL">
            <img src="${path}/easyUI_insdep/themes/insdep/images/portrait86x86.png" >
          </div>
          <div class="right cR">
            <ul>
              <li><div id="gg1" class="gauge"  ></div><span>人员注册数量</span></li>
              <li><div id="gg2" class="gauge"  ></div><span>房间注册数量</span></li>
              <li><div id="gg3" class="gauge" ></div><span>设备注册数量</span></li>
            </ul>
          </div>
          <div class="center cC">
            <h1><%=user.getUsername()%></h1>
            <h2 class="role"></h2>
            <%--<ul>--%>
              <%--<li><i class="iconfont">&#xe61c;</i> examples@insdep.com</li>--%>
              <%--<li><i class="iconfont">&#xe65d;</i> 13000000000</li>--%>
            <%--</ul>--%>
          </div>

        </div>
          </div>
        <div data-options="region:'center',border:false" id="subCenter" style="height:60%">

          <div id="user-info-more" class="easyui-tabs theme-tab-blue-line theme-tab-body-unborder" data-options="tools:'#tab-tools',fit:true">

            <%--<div title="统计图" id="charts-layout" data-options="closable:true">--%>
              <%--<!--统计开始-->--%>

              <%--<div id="charts"></div>--%>
            <%--</div>--%>
            <div title="帮助"  style="padding:10px;">
              <%--This is the help content.--%>
              <%--<div class="shezhi" style="display: none;">--%>
                <%--<form id="accessDatabaseForm" method="post" novalidate>--%>
                  <%--<label>数据库访问时间间隔（单位分钟）：</label>--%>
                  <%--<input type="text" name="time" class="accessDatabase"/>--%>
                  <%--<input type="button" value="提交" onclick="changeAccessDatabase()"/>--%>
                <%--</form>--%>
            <%--</div>--%>
          </div>

        </div>
        <div id="tab-tools">
          <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-set'" onclick="shezhi()"></a>
        </div>
      </div>
      </div>
    </div>
  </div>


</div>
<%--自己的js--%>
<script type="text/javascript" src="${path}/js/main.js"></script>

<!--第三方插件加载-->
<script src="${path}/easyUI_insdep/plugin/justgage-1.2.2/raphael-2.1.4.min.js"></script>
<script src="${path}/easyUI_insdep/plugin/justgage-1.2.2/justgage.js"></script>





<script type="text/javascript" src="${path}/easyUI_insdep/plugin/ueditor-1.4.3.3/ueditor.config.js"></script>
<script type="text/javascript" src="${path}/easyUI_insdep/plugin/ueditor-1.4.3.3/ueditor.all.min.js"></script>


<link href="${path}/easyUI_insdep/plugin/cropper-2.3.4/dist/cropper.min.css" rel="stylesheet" type="text/css">
<script src="${path}/easyUI_insdep/plugin/cropper-2.3.4/dist/cropper.min.js"></script>


<!--第三方插件加载结束-->

</body>
</html>