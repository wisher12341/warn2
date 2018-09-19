<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/common/easyui.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>地图</title>
  <link href="${path}/css/map.css" rel="stylesheet" type="text/css">
  <link href="${path}/css/table.css" rel="stylesheet" type="text/css">
  <script src="${path}/js/echarts/echarts.min.js" type="text/javascript"></script>
  <%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>--%>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=q26hMcHcdSmP4RMfDKuZYBo13FAmf4j3"></script>
  <script type="text/javascript" src="http://api.map.baidu.com/library/MarkerTool/1.2/src/MarkerTool_min.js"></script>

  <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
  <script src="https://img.hcharts.cn/highcharts/highcharts-3d.js"></script>
  <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
  <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
<style type="text/css">


  body{
    padding: 10px 20px 10px 20px;
  }
  #divAdd{
    background: #fff;
    margin-top: 9.1%;
    position: absolute;
    left: 8px;
    bottom: 8px;
  }
  .visual{
    width: 35%;
    margin-left: 65%;
  }
  #main_gauge{
    width: 20%;
    height:35%;
    position: absolute;
    top:64%;
    right: 16%;
    /*display: none;*/
  }
  #main_bar{
    width: 18%;
    height:35%;
    position: absolute;
    top:65%;
    right: 0%;
    /*display: none;*/
  }
  #main_pie{
    position: absolute;
    top:-11%;
    right: 18%;
    width: 15%;
    height:46%;
    /*z-index: 999;*/

  }
  #main_pie_2{
    position: absolute;
    top:-6%;
    right: 2%;
    width: 15%;
    height:40%;
    /*margin-right: -2%;*/
  }
  #main_pie_3{
    width: 15%;
    height:40%;
    position: absolute;
    top:25%;
    right: 12%;
    /*margin-right: -9%;*/
    margin-top: 1%;

  }
</style>
</head>
<body style="overflow-x: hidden">

<div class="map" id="container"></div>
<div class="visual">
  <div id="main_gauge"></div>
  <div id="main_bar"></div>
  <div id="main_pie"></div>
  <div id="main_pie_2"></div>
  <div id="main_pie_3"></div>

</div>

<div id="divAdd">
<input type="button" class="btn" value="选择标注样式" onclick="openStylePnl()" />
<input type="button" class="btn" value="关闭" onclick="mkrTool.close();document.getElementById('divStyle').style.display = 'none'" />
<div id="divStyle" >
  <ul>
    <%--<li>--%>
      <%--<a onclick="selectStyle(0)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:12px;height:21px;background-position: 0 0" /></a>--%>
      <%--<a onclick="selectStyle(1)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:12px;height:21px;background-position: -23px 0" /></a>--%>
      <%--<a onclick="selectStyle(2)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:12px;height:21px;background-position: -46px 0" /></a>--%>
      <%--<a onclick="selectStyle(3)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:12px;height:21px;background-position: -69px 0" /></a>--%>
      <%--<a onclick="selectStyle(4)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:12px;height:21px;background-position: -92px 0" /></a>--%>
      <%--<a onclick="selectStyle(5)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:12px;height:21px;background-position: -115px 0" /></a>--%>
    <%--</li>--%>
    <li>
      <a onclick="selectStyle(6)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:19px;height:25px;background-position: 0 -21px" /></a>
    </li>
    <li>
      <a onclick="selectStyle(12)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:17px;height:21px;background-position: 0 -46px " /></a>
      <a onclick="selectStyle(13)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:17px;height:21px;background-position: -23px  -46px " /></a>
      <a onclick="selectStyle(14)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:17px;height:21px;background-position: -46px  -46px " /></a>
      <a onclick="selectStyle(15)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:17px;height:21px;background-position: -69px  -46px " /></a>
      <a onclick="selectStyle(16)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:17px;height:21px;background-position: -92px  -46px " /></a>
      <a onclick="selectStyle(17)" href = 'javascript:void(0)'><img src="http://api.map.baidu.com/library/MarkerTool/1.2/examples/images/transparent.gif" style="width:17px;height:21px;background-position: -115px  -46px " /></a>
    </li>
  </ul>
</div>
</div>
</body>
<script type="text/javascript">
    $(function(){
        $(".active",parent.document).removeClass("active");
        $("#index + li + li",parent.document).addClass("active");
    });
</script>
<script type="text/javascript" src="${path}/js/map.js"></script>
</html>
