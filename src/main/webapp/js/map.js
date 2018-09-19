/**
 * 本地图的数据 都是实时更新  新添加 街道、房屋标注  其他相关的统计信息都实时更新  不用刷新页面
 * 房屋状态的改变 由后台推送至前台 进行实时更新 不用刷新页面
 * Created by netlab606 on 2017/7/7.
 */


/**
 * 存储新建的标注的信息
 */
//房屋信息
var indexOwn;//标注的样式的索引
var xOwn,yOwn;//标注的坐标
// var oidOwn;//老人编号
var jidOwn;//街道编号

//街道信息
var jNameOwn;//街道名称
var qidOwn;//区编号


var Sum={'sum':0,'greenSum':0,'yellowSum':0,'redSum':0,'district':[]};//存储 统计信息

var preZoom;//用于存储 上一个 地图级别  避免重复取数据


var selectStreet=[];//存储添加标注时  街道的选择  实时更新
var selectDistrict=[];//存储添加标注时  区的选择  实时更新
// var selectOldMan=[];//存储添加标注时  人员的选择  实时更新



var map = new BMap.Map("container");
map.setMapStyle({style:'googlelite'});
if(getCookie("zoom")!=null&&getCookie("zoom")!=""){

    map.centerAndZoom(new BMap.Point(parseFloat(getCookie("center_x_y").split(",")[0]),parseFloat(getCookie("center_x_y").split(",")[1])), getCookie("zoom"));
    // map.centerAndZoom(new BMap.Point(121.481, 31.238), getCookie("zoom"));
    if(getCookie("zoom")>=16&&getCookie("zoom")<=18){
        getLouMarkers();
    }else if(getCookie("zoom")==19){
        getLouMarkers_label()
    }else{
        getJieDaoMarkers();
    }
}else {
    map.centerAndZoom(new BMap.Point(121.481, 31.238), 13);
}

map.addEventListener("zoomend", function(){
    //区：<=13  街道：14.15  房屋 >=16
    // alert("地图缩放至：" + this.getZoom() + "级");
    //区级别 且 上一个地图级别不是区级别
    if(this.getZoom()<=13&&preZoom!=null&&preZoom>13){
        //清空覆盖物
        map.clearOverlays();
        getQuMarkers();
    }else if((this.getZoom()==14||this.getZoom()==15||this.getZoom()==16)&&preZoom!=14&&preZoom!=15&&preZoom!=16){
        // console.log(point);
        //清空覆盖物
        map.clearOverlays();
        //街道级别 且 上一个地图级别不是街道级别
        getJieDaoMarkers();
    }else if((this.getZoom()>=17&&this.getZoom()<=18)&&(preZoom<17||preZoom>18)){
        //清空覆盖物
        map.clearOverlays();
        //房屋级别 且 上一个地图级别不是房屋级别   没有label
        getLouMarkers();
    }else if(this.getZoom()==19){
        map.clearOverlays();
         //房屋级别 且 上一个地图级别不是房屋级别   有label
        getLouMarkers_label();
    }
    preZoom=this.getZoom();
});
// map.addEventListener("click", function(e){
//     alert(e.point.lng + ", " + e.point.lat);
// });
map.enableScrollWheelZoom();


var infoWin;
var jInfoWin;
var curMkr = null; // 记录当前添加的Mkr

var mkrTool = new BMapLib.MarkerTool(map, {autoClose: true});


mkrTool.addEventListener("markend", function(e){
    var mkr = e.marker;
    if(map.getZoom()==14||map.getZoom()==15) {
        //街道的添加
        //拼接街道infowindow内容字串
        var jhtml = [];
        jhtml.push('<span style="font-size:12px">街道信息: </span><br/>');
        jhtml.push('<table border="0" cellpadding="1" cellspacing="1" >');
        jhtml.push('  <tr>');
        jhtml.push('      <td align="left" class="common">街道名称：</td>');
        jhtml.push('      <td colspan="2"><input type="text" maxlength="50" size="18"  id="jName"></td>');
        jhtml.push('      <td valign="top"><span class="star">*</span></td>');
        jhtml.push('  </tr>');
        jhtml.push('  <tr>');
        jhtml.push('      <td align="left" class="common">区：</td>');
        jhtml.push('      <td valign="top"><select id="selectQ_street">');
        for (var i = 0; i < selectDistrict.length; i++) {
            jhtml.push('<option value="' + selectDistrict[i].qid + ":" + selectDistrict[i].qName + '">' + selectDistrict[i].qName + '</option>');
        }
        jhtml.push('</select></td>');
        jhtml.push('      <td valign="top"><span class="star">*</span></td>');
        jhtml.push('  </tr>');
        jhtml.push('  <tr>');
        jhtml.push('      <td  align="center" colspan="3">');
        jhtml.push('          <input type="button" name="btnOK"  onclick="fnOK(1)" value="确定">&nbsp;&nbsp;');
        jhtml.push('          <input type="button" name="btnClear" onclick="fnClear(1);" value="重填">');
        jhtml.push('      </td>');
        jhtml.push('  </tr>');
        jhtml.push('</table>');

        jInfoWin = new BMap.InfoWindow(jhtml.join(""), {offset: new BMap.Size(0, -10)});

        mkr.openInfoWindow(jInfoWin);
        curMkr = mkr;
        xOwn = mkr.point.lng;
        yOwn = mkr.point.lat;
    }else if(map.getZoom()>=16) {
        //拼接楼infowindow内容字串
        var html = [];
        html.push('<span style="font-size:12px">楼信息: </span><br/>');
        html.push('<table border="0" cellpadding="1" cellspacing="1" >');
        html.push('  <tr>');
        html.push('      <td align="left" class="common">楼号：</td>');
        html.push('      <td valign="top"><input type="text" id="lou">');
        // for(var i=0;i<selectOldMan.length;i++){
        //     html.push('<option value="'+selectOldMan[i].oid+"#"+selectOldMan[i].oldName+'">'+selectOldMan[i].oid+"："+selectOldMan[i].oldName+'</option>');
        // }
        html.push('</select></td>');
        html.push('      <td valign="top"><span class="star">*</span></td>');
        html.push('  </tr>');
        html.push('  <tr>');
        html.push('      <td align="left" class="common">区：</td>');
        html.push('      <td valign="top"><select id="selectQ_house" onchange="selectDistrict_house(this.options[this.options.selectedIndex].value)">');
        html.push('<option></option>');
        for(var i=0;i<selectDistrict.length;i++){
            html.push('<option value="'+selectDistrict[i].qid+":"+selectDistrict[i].qName+'">'+selectDistrict[i].qName+'</option>');
        }
        html.push('</select></td>');
        html.push('      <td valign="top"><span class="star">*</span></td>');
        html.push('  </tr>');
        html.push('  <tr>');
        html.push('      <td align="left" class="common">街道：</td>');
        html.push('      <td colspan="2"><select id="selectJ_house">');
        html.push('</select></td>');
        html.push('  </tr>');
        html.push('  <tr>');
        html.push('      <td  align="center" colspan="3">');
        html.push('          <input type="button" name="btnOK"  onclick="fnOK(2)" value="确定">&nbsp;&nbsp;');
        // html.push('          <input type="button" name="btnClear" onclick="fnClear(2);" value="重填">');
        html.push('      </td>');
        html.push('  </tr>');
        html.push('</table>');



        infoWin = new BMap.InfoWindow(html.join(""), {offset: new BMap.Size(0, -10)});
        //房屋的添加
        mkr.openInfoWindow(infoWin);
        curMkr = mkr;
        xOwn = mkr.point.lng;
        yOwn = mkr.point.lat;



    }

});
init();




//初始化
function init(){
    /**
     * 地图级别判断
     * @type {[type]}
     */
    //默认地图级别12  显示区 统计信息
    getQuMarkers();//区
    getSums();//统计信息
    // divInit();
}


//打开样式面板
function openStylePnl(){
    document.getElementById("divStyle").style.display = "block";
}

//选择样式
function selectStyle(index){
    indexOwn=index;

    mkrTool.open(); //打开工具
    var icon = BMapLib.MarkerTool.SYS_ICONS[index]; //设置工具样式，使用系统提供的样式BMapLib.MarkerTool.SYS_ICONS[0] -- BMapLib.MarkerTool.SYS_ICONS[23]
    mkrTool.setIcon(icon);
    document.getElementById("divStyle").style.display = "none";
}

//提交数据
function fnOK(index){

    //楼添加
    if(index==2) {
        var lou_info = encodeHTML(document.getElementById("lou").value);
        var jid = encodeHTML(document.getElementById("selectJ_house").value).split(":")[0];
        // var jName_top=encodeHTML(document.getElementById("selectJ_house").value).split(":")[1];
        // var qName_top=encodeHTML(document.getElementById("selectQ_house").value).split(":")[1];

        // var qidTxt = encodeHTML(document.getElementById("txtQid").value);
        // var jidTxt = encodeHTML(document.getElementById("txtSid").value);

        // oidOwn = oid;
        jidOwn = jid;

        if (!lou_info) {
            alert("*字段必须填写");
            return;
        }

        if (curMkr) {
            curMkr.setTitle(lou_info + "：0");
            if(map.getZoom()==19) {
                var label = new BMap.Label("0", {offset: new BMap.Size(3, -18)});
                label.setStyle({
                    color: "red",
                    font: "8px Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                    backgroundColor: "transparent",
                    fontWeight: "bold",
                    border: "none"
                });
                curMkr.setLabel(label);
            }
        }
        if (infoWin.isOpen()) {
            map.closeInfoWindow();
        }
        /**
         *往后台发送数据
         *
         *
         */
        $.ajax({
            type: "POST",
            url: pathJs + "/map/addLouMarker",
            dataType: "json",
            data: {
                xG: xOwn,
                yG: yOwn,
                info: lou_info,
                jid: jidOwn
            },
            async: false,
            success: function (data) {
            }
        });
    }else{
        //街道添加   添加好后
        var jName = encodeHTML(document.getElementById("jName").value);
        var qid = encodeHTML(document.getElementById("selectQ_street").value).split(":")[0];

        var qName=encodeHTML(document.getElementById("selectQ_street").value).split(":")[1];

        qidOwn = qid;
        jNameOwn = jName;

        if (!qid||!jName) {
            alert("*字段必须填写");
            return;
        }
        if (curMkr) {
            //设置点击事件
            curMkr.addEventListener("click", function (e) {
                var opts = {
                    width : 200,     // 信息窗口宽度
                    height: 100,     // 信息窗口高度
                    title : this.getTitle()  // 信息窗口标题
                };
                var infoWindow = new BMap.InfoWindow("所属区："+qName+"<br/>购买服务总人数："+0+"<br/>正常（绿）："+0+"<br/>正在接受服务（黄）："+0+"<br/>预警（红）："+0,opts);  // 创建信息窗口对象
                this.openInfoWindow(infoWindow,new BMap.Point(this.point.lng,this.point.lat));
            });

            // curMkr.setAnimation(BMAP_ANIMATION_BOUNCE);
            //设置label
            // var lbl = new BMap.Label(name, {offset: new BMap.Size(1, 1)});
            // lbl.setStyle({border: "solid 1px gray"});
            // curMkr.setLabel(lbl);

            //设置title
            var title = "街道名称：" + jNameOwn;
            curMkr.setTitle(title);
            var label = new BMap.Label(jNameOwn+"：0",{offset:new BMap.Size(20,-10)});
            label.setStyle({
                color:"red",
                font:"16px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                backgroundColor:"white",
                fontWeight:"bold",
                padding:"4px 8px"
            });
            curMkr.setLabel(label);
        }
        if (jInfoWin.isOpen()) {
            map.closeInfoWindow();
        }

        // 在此用户可将数据提交到后台数据库中
        // alert("样式索引：" + indexOwn + "\nx坐标：" + xOwn + "\ny坐标：" + yOwn + "\n区编号：" + qidOwn + "\n街道名称：" + jNameOwn);

        /**
         *往后台发送数据
         *
         *
         */
        $.ajax({
            type: "POST",
            url: pathJs + "/map/addStreetMarker",
            dataType: "json",
            data: {
                jX: xOwn,
                jY: yOwn,
                qid: qidOwn,
                jName: jNameOwn,
                styleIndex: indexOwn
            },
            async: false,
            success: function (data) {
                //将新添加的 街道标注 更新到统计信息  以及街道的select
                var street={"name":jName,"sum":0,"greenSum":0,"yellowSum":0,"redSum":0};
                for(var i=0;i<Sum.district.length;i++){
                    if(Sum.district[i].name==qName) {
                        Sum.district[i].street.push(street);
                    }
                }
                var select={"jid":data.data,"jName":jName,"qid":qidOwn};
                selectStreet.push(select);
                divInit();
            }
        });
    }
}

//输入校验
function encodeHTML(a){
    return a.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;");
}

//重填数据
function fnClear(index){
    if(index==2) {
        //房屋
        // document.getElementById("txtOid").value = "";
    }else{
        //街道
        document.getElementById("jName").value = "";
    }
}

//初始化 统计信息
function getSums() {
    $.ajax({
        type: "GET",
        url: pathJs + "/map/getSums",
        dataType: "json",
        success: function (data) {
            //数据持久化
            Sum.sum=data.data.sum;
            Sum.greenSum=data.data.greenSum;
            Sum.yellowSum=data.data.yellowSum;
            Sum.redSum=data.data.redSum;

            for(var i=0;i<data.data.quMarkerList.length;i++){
                var district={"id":0,"name":"","sum":0,"greenSum":0,"yellowSum":0,"redSum":0,"street":[]};
                district.id=data.data.quMarkerList[i].id;
                district.name=data.data.quMarkerList[i].qName;
                district.sum=data.data.quMarkerList[i].sum;
                district.greenSum=data.data.quMarkerList[i].greenSum;
                district.yellowSum=data.data.quMarkerList[i].yellowSum;
                district.redSum=data.data.quMarkerList[i].redSum;
                for(var j=0;j<data.data.quMarkerList[i].jieDaoMarkerList.length;j++){
                    var street={"id":0,"name":"","sum":0,"greenSum":0,"yellowSum":0,"redSum":0};
                    street.id=data.data.quMarkerList[i].jieDaoMarkerList[j].id;
                    street.name=data.data.quMarkerList[i].jieDaoMarkerList[j].jName;
                    street.sum=data.data.quMarkerList[i].jieDaoMarkerList[j].sum;
                    street.greenSum=data.data.quMarkerList[i].jieDaoMarkerList[j].greenSum;
                    street.yellowSum=data.data.quMarkerList[i].jieDaoMarkerList[j].yellowSum;
                    street.redSum=data.data.quMarkerList[i].jieDaoMarkerList[j].redSum;
                    district.street.push(street);
                }
                Sum.district.push(district);
            }
            // alert(JSON.stringify(Sum));
            divInit();
        }
    });
}

//获得区数据
function getQuMarkers() {
    $.ajax({
        type: "GET",
        url: pathJs + "/map/getQuMarkers",
        dataType: "json",
        async:false, //一定要加 不然 渲染不了
        success: function (data) {
            //填充 区select
            if(selectDistrict.length==0){
                for(var i=0;i<data.data.length;i++) {
                    var district = {"qid": data.data[i].id, "qName": data.data[i].qName};
                    selectDistrict.push(district);
                }
            }

            for(var i=0;i<data.data.length;i++) {
                var icon = BMapLib.MarkerTool.SYS_ICONS[data.data[i].styleIndex];
                // icon.setSize(200);
                // icon.setImageSize(50);
                var point = new BMap.Point(data.data[i].qX, data.data[i].qY);
                var marker = new BMap.Marker(point, {icon: icon});
                marker.setTitle("区名称："+data.data[i].qName);
                // var sum=data.data[i].sum+"@"+data.data[i].greenSum+"@"+data.data[i].yellowSum+"@"+data.data[i].redSum;

                // marker.setAttribute("sum",sum);
                // marker.setAnimation(BMAP_ANIMATION_BOUNCE);
                marker.addEventListener("click", function (e) {
                    /**
                     *
                     * 获得该区的统计信息
                     */
                        // alert(this.getTitle());
                    var qName=this.getTitle().split("：")[1];

                    var opts = {
                            width : 200,     // 信息窗口宽度
                            height: 100,     // 信息窗口高度
                            title : this.getTitle()  // 信息窗口标题
                        };
                    //该区的统计情况  不能用data.data[i] 因为只有触发的时候才会渲染  触发时 data.data[i]是null
                    // var sums=this.getAttribute("sum").split("@");
                    // alert(JSON.stringify(Sum));
                    // alert(qName);
                    for(var i=0;i<Sum.district.length;i++) {
                        if(Sum.district[i].name==qName) {
                            varSum =Sum.district[i].sum;
                            varGreenSum =Sum.district[i].greenSum;
                            varYellowSum =Sum.district[i].yellowSum;
                            varRedSum =Sum.district[i].redSum;
                        }
                    }
                    var infoWindow = new BMap.InfoWindow("购买服务总人数："+varSum+"<br/>正常："+varGreenSum+"<br/>正在服务："+varYellowSum+"<br/>预警："+varRedSum,opts);  // 创建信息窗口对象
                    this.openInfoWindow(infoWindow,new BMap.Point(this.point.lng,this.point.lat));
                });
                map.addOverlay(marker);

                // var lHtml=[];
                // lHtml.push('<span style="font-size:12px;background-color: #00b5ad">'+data.data[i].qName+":"+data.data[i].sum +'</span><br/>');

                var label = new BMap.Label(data.data[i].qName+"："+data.data[i].sum,{offset:new BMap.Size(20,-10)});
                label.setStyle({
                    color:"red",
                    font:"16px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                    backgroundColor:"white",
                    fontWeight:"bold",
                    padding:"4px 8px"
                });
                marker.setLabel(label);
            }
        }
    });
}

//获得街道数据
function getJieDaoMarkers() {
    $.ajax({
        type: "GET",
        url: pathJs + "/map/getJieDaoMarkers",
        dataType: "json",
        async:false,
        success: function (data) {
            //填充 街道select
            if(selectStreet.length==0){
                for(var i=0;i<data.data.length;i++) {
                    var street = {"jid": data.data[i].id, "jName": data.data[i].jName, "qid": data.data[i].qid};
                    selectStreet.push(street);
                }
            }

            for(var i=0;i<data.data.length;i++) {

                var icon = BMapLib.MarkerTool.SYS_ICONS[data.data[i].styleIndex];
                // icon.setSize(200);
                var point = new BMap.Point(data.data[i].jX, data.data[i].jY);
                var marker = new BMap.Marker(point, {icon: icon});
                marker.setTitle("街道名称："+data.data[i].jName);
                // marker.setAnimation(BMAP_ANIMATION_BOUNCE);
                // var sum=data.data[i].qName+"@"+data.data[i].sum+"@"+data.data[i].greenSum+"@"+data.data[i].yellowSum+"@"+data.data[i].redSum;
                // marker.setAttribute("sum",sum);
                marker.addEventListener("click", function (e) {
                    /**
                     *
                     * 获得该街道的统计信息
                     */
                        // alert(this.getTitle());
                    var opts = {
                            width : 200,     // 信息窗口宽度
                            height: 130,     // 信息窗口高度
                            title : this.getTitle()  // 信息窗口标题
                        };
                    var jName=this.getTitle().split("：")[1];
                    var varQname;
                    var varSum;
                    var varGreenSum;
                    var varYellowSum;
                    var varRedSum;
                    for(var i=0;i<Sum.district.length;i++) {
                        for(var j=0;j<Sum.district[i].street.length;j++) {
                            if (Sum.district[i].street[j].name == jName) {
                                varSum = Sum.district[i].street[j].sum;
                                varGreenSum = Sum.district[i].street[j].greenSum;
                                varYellowSum = Sum.district[i].street[j].yellowSum;
                                varRedSum = Sum.district[i].street[j].redSum;
                                varQname=Sum.district[i].name;
                            }
                        }
                    }
                    //该街道的统计情况
                    var infoWindow = new BMap.InfoWindow("所属区："+varQname+"<br/>购买服务总人数："+varSum+"<br/>正常："+varGreenSum+"<br/>正在接受服务："+varYellowSum+"<br/>预警："+varRedSum,opts);  // 创建信息窗口对象
                    this.openInfoWindow(infoWindow,new BMap.Point(this.point.lng,this.point.lat));
                });
                map.addOverlay(marker);

                // var lHtml=[];
                // lHtml.push('<span style="font-size:12px;background-color: #00b5ad">'+data.data[i].qName+":"+data.data[i].sum +'</span><br/>');

                var label = new BMap.Label(data.data[i].jName+"："+data.data[i].sum,{offset:new BMap.Size(20,-10)});
                label.setStyle({
                    color:"red",
                    font:"16px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                    backgroundColor:"white",
                    fontWeight:"bold",
                    padding:"4px 8px"
                });
                marker.setLabel(label);
            }
        }
    });
}

//获得楼数据
function getLouMarkers() {
    $.ajax({
        type: "GET",
        url: pathJs + "/map/getLouMarkers",
        dataType: "json",
        async:false,
        success: function (data) {

            for(var i=0;i<data.data.length;i++) {
                // if(data.data[i].greenSum!=0){
                    var icon = BMapLib.MarkerTool.SYS_ICONS[6];
                    var point = new BMap.Point(data.data[i].xG, data.data[i].yG);
                    var marker = new BMap.Marker(point, {icon: icon});
                    marker.setTitle(data.data[i].info + "："+data.data[i].greenSum);
                    map.addOverlay(marker);
                // }
                if(data.data[i].yellowSum!=0){
                    var icon = BMapLib.MarkerTool.SYS_ICONS[9];
                    var point = new BMap.Point(data.data[i].xY, data.data[i].yY);
                    var marker = new BMap.Marker(point, {icon: icon});
                    marker.setTitle(data.data[i].info + "："+data.data[i].yellowSum);
                    map.addOverlay(marker);
                    // var label = new BMap.Label(data.data[i].yellowSum,{offset:new BMap.Size(0,0)});
                    // label.setStyle({
                    //     color:"white",
                    //     font:"8px Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                    //     backgroundColor:"transparent",
                    //     fontWeight:"bold"
                    // });
                    // marker.setLabel(label);
                }
                if(data.data[i].redSum!=0){
                    var icon = BMapLib.MarkerTool.SYS_ICONS[8];
                    var point = new BMap.Point(data.data[i].xR, data.data[i].yR);
                    var marker = new BMap.Marker(point, {icon: icon});
                    marker.setTitle(data.data[i].info + "："+data.data[i].redSum);
                    map.addOverlay(marker);
                    // var label = new BMap.Label(data.data[i].redSum,{offset:new BMap.Size(0,0)});
                    // label.setStyle({
                    //     color:"white",
                    //     font:"8px Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                    //     backgroundColor:"transparent",
                    //     fontWeight:"bold"
                    // });
                    // marker.setLabel(label);
                }

            }
        }
    });
}

function getLouMarkers_label() {
    $.ajax({
        type: "GET",
        url: pathJs + "/map/getLouMarkers",
        dataType: "json",
        async:false,
        success: function (data) {

            for(var i=0;i<data.data.length;i++) {
                // if(data.data[i].greenSum!=0){
                    var icon = BMapLib.MarkerTool.SYS_ICONS[6];
                    var point = new BMap.Point(data.data[i].xG, data.data[i].yG);
                    var marker = new BMap.Marker(point, {icon: icon});
                    marker.setTitle(data.data[i].info + "："+data.data[i].greenSum);
                    map.addOverlay(marker);
                    var label = new BMap.Label(data.data[i].greenSum,{offset:new BMap.Size(3,-18)});
                    label.setStyle({
                        color:"red",
                        font:"8px Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                        backgroundColor:"transparent",
                        fontWeight:"bold",
                        border:"none"
                    });
                    marker.setLabel(label);
                // }
                if(data.data[i].yellowSum!=0){
                    var icon = BMapLib.MarkerTool.SYS_ICONS[9];
                    var point = new BMap.Point(data.data[i].xY, data.data[i].yY);
                    var marker = new BMap.Marker(point, {icon: icon});
                    marker.setTitle(data.data[i].info + "："+data.data[i].yellowSum);
                    map.addOverlay(marker);
                    var label = new BMap.Label(data.data[i].yellowSum,{offset:new BMap.Size(3,-18)});
                    label.setStyle({
                        color:"red",
                        font:"8px Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                        backgroundColor:"transparent",
                        fontWeight:"bold",
                        border:"none"
                    });
                    marker.setLabel(label);
                }
                if(data.data[i].redSum!=0){
                    var icon = BMapLib.MarkerTool.SYS_ICONS[8];
                    var point = new BMap.Point(data.data[i].xR, data.data[i].yR);
                    var marker = new BMap.Marker(point, {icon: icon});
                    marker.setTitle(data.data[i].info + "："+data.data[i].redSum);
                    map.addOverlay(marker);
                    var label = new BMap.Label(data.data[i].redSum,{offset:new BMap.Size(3,-18)});
                    label.setStyle({
                        color:"red",
                        font:"8px Tahoma,Helvetica,Arial,'宋体',sans-serif;",
                        backgroundColor:"transparent",
                        fontWeight:"bold",
                        border:"none"
                    });
                    marker.setLabel(label);
                }
            }
        }
    });
}

//html 显示 初始化
function divInit() {
    var main_gauge = echarts.init(document.getElementById('main_gauge'));
    var option_gaugge = {
        title: {
            text: '养老院床位数',
            x:'center',
            y: '90%',
            textStyle: {
                fontSize: '12',
                fontWeight: 'bold',
                color:'red'
            }
        },
        tooltip : {
            formatter: "{a} : {c}"
        },
        series: [
            {
                name: '已入住人数',
                type: 'gauge',
                //半径
                radius: 100,
                //起始角度。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
                startAngle: 180,
                //结束角度。
                endAngle: 0,
                center: ['50%', '80%'],
                min: 0,
                max: 50,
                detail: {formatter:'{value}',
                    textStyle: {
                        fontSize: 18
                },
                offsetCenter: [0, '15%']},
                data: [{value: 30, name: '已入住人数'}]
            }
        ]
    };
    main_gauge.setOption(option_gaugge);

    var main_bar = echarts.init(document.getElementById('main_bar'));
    var option_bar = {
        // color: ['#56c078'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        label:{
            normal:{
                show: true,
                position: 'top'}
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['剩余床位数', '预计入住人数'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                type:'bar',
                barWidth: '20%',
                data:[22, 31]
            }
        ]
    };
    main_bar.setOption(option_bar);

    var main_pie = echarts.init(document.getElementById('main_pie'));
    var option_pie = {
        tooltip: {
            trigger: 'item',
            formatter: function(params){
                if(params['value']==30){
                    return params['name']+":"+params['value']+' （'+(params['value']/100*100).toFixed(2)+'%）'
                }else {
                    return params['name']+":"+params['value']+' （100%）'
                }
            }
        },
        legend: {
            orient: 'vertical',
            left: '10%',
            bottom: '0%',
            data:['已参加居家养老人数','老年人总数']
        },
        series: [
            {
                center: ['50%', '55%'],
                type:'pie',
                radius: ['40%', '70%'],
                // avoidLabelOverlap: false,
                label: {
                    normal: {
                        show:true,
                        position: 'inside',
                        formatter:function(params){
                            return params['value'];
                        }
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '16',
                            fontWeight: 'bold'
                        }
                    }

                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data:[
                    {value:30, name:'已参加居家养老人数'},
                    {value:100,name:"老年人总数"}
                ]
            }
        ]
    };
    main_pie.setOption(option_pie);

    var main_pie_2 = echarts.init(document.getElementById('main_pie_2'));
    var option_pie_2 = {
        tooltip: {
            trigger: 'item',
            formatter: function(params){
                if(params['value']==10){
                    return params['name']+":"+params['value']+' （'+(params['value']/30*100).toFixed(2)+'%）'
                }else {
                    return params['name']+":"+params['value']+' （100%）'
                }
            }
        },
        legend: {
            orient: 'vertical',
            left: '10%',
            bottom: '-1%',
            data:['正在被服务人数','已参加居家养老人数']
        },
        series: [
            {
                type:'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show:true,
                        position: 'inside',
                        formatter:function(params){
                            return params['value'];
                        }
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '16',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data:[
                    {value:10, name:'正在被服务人数'},
                    {value:30,name:"已参加居家养老人数"}
                ]
            }
        ]
    };
    main_pie_2.setOption(option_pie_2);

    var main_pie_3 = echarts.init(document.getElementById('main_pie_3'));
    var option_pie_3 = {
        tooltip: {
            trigger: 'item',
            formatter: function(params){
                if(params['value']==1){
                    return params['name']+":"+params['value']+' （'+(params['value']/12*100).toFixed(2)+'%）'
                }else {
                    return params['name']+":"+params['value']+' （100%）'
                }
            }
        },
        legend: {
            orient: 'vertical',
            left: '0%',
            bottom: '-1%',
            data:['预警人数','已安装智能远程老人关怀系统']
        },
        series: [
            {
                type:'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show:true,
                        position: 'inside',
                        // textStyle: {
                        //     fontSize: '16',
                        //     fontWeight: 'bold'
                        // },
                        formatter:function(params){
                            return params['value'];
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data:[
                    {value:1, name:'预警人数'},
                    {value:12, name:'已安装智能远程老人关怀系统'}
                ]
            }
        ]
    };
    main_pie_3.setOption(option_pie_3);

}




function louChange() {
    /**
     * 统计信息的更新
     */
    var point=map.getCenter();
    var center=JSON.stringify(point);
    //将当前的 地图级别  中心坐标存到 cookie中 时间1分钟
    setCookie("zoom",map.getZoom().toString(),60*1000);
    setCookie("center_x_y",center.split(",")[0].split(":")[1]+","+center.split(",")[1].split(":")[1].substring(0,center.split(",")[1].split(":")[1].length-1),60*1000);
    location.reload();
}

//按秒
function setCookie(name, value, time){
    var exp = new Date();
    exp.setTime(exp.getTime() + time);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString()+"path=/";
}

function getCookie(c_name){
    if (document.cookie.length>0){　　//先查询cookie是否为空，为空就return ""
        c_start=document.cookie.indexOf(c_name + "=")　　//通过String对象的indexOf()来检查这个cookie是否存在，不存在就为 -1　　
        if (c_start!=-1){
            c_start=c_start + c_name.length+1　　//最后这个+1其实就是表示"="号啦，这样就获取到了cookie值的开始位置
            c_end=document.cookie.indexOf(";",c_start)　　//其实我刚看见indexOf()第二个参数的时候猛然有点晕，后来想起来表示指定的开始索引的位置...这句是为了得到值的结束位置。因为需要考虑是否是最后一项，所以通过";"号是否存在来判断
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))　　//通过substring()得到了值。想了解unescape()得先知道escape()是做什么的，都是很重要的基础，想了解的可以搜索下，在文章结尾处也会进行讲解cookie编码细节
        }
    }
    return ""
}

function selectDistrict_house(value) {
    $('#selectJ_house').html("");
    var qid=value.split(":")[0];
    for(var i=0;i<selectStreet.length;i++){
        if(selectStreet[i].qid==qid){
            var $option=$('<option value="'+selectStreet[i].jid+":"+selectStreet[i].jName+'">'+selectStreet[i].jName+'</option>');
            $('#selectJ_house').append($option);
        }
    }

}

function quChange() {
    var $tbody=$("#tableSum tbody");
    $tbody.html("");
    for(var i=0;i<Sum.district.length;i++) {
        if(Sum.district[i].id==$("option:selected").val()) {
            var $tr = $("<tr onclick='tuChange("+Sum.district[i].id+",1,null)' class='district'></tr>");
            var $tdName = $("<td>" + Sum.district[i].name + "</td>");
            var $tdSum = $("<td>" + Sum.district[i].sum + "</td>");
            var $tdGreenSum = $("<td>" + Sum.district[i].greenSum + "</td>");
            var $tdYellowSum = $("<td>" + Sum.district[i].yellowSum + "</td>");
            var $tdRedSum = $("<td>" + Sum.district[i].redSum + "</td>");
            $tr.append($tdName);
            $tr.append($tdSum);
            $tr.append($tdGreenSum);
            $tr.append($tdYellowSum);
            $tr.append($tdRedSum);
            $tbody.append($tr);
            for (var j = 0; j < Sum.district[i].street.length; j++) {
                var $tr_s = $("<tr class='street'></tr>");
                var $tdName_s = $("<td onclick='tuChange("+Sum.district[i].street[j].id+",2,"+Sum.district[i].id+")'>" + Sum.district[i].street[j].name + "</td>");
                var $tdSum_s = $("<td>" + Sum.district[i].street[j].sum + "</td>");
                var $tdGreenSum_s = $("<td>" + Sum.district[i].street[j].greenSum + "</td>");
                var $tdYellowSum_s = $("<td>" + Sum.district[i].street[j].yellowSum + "</td>");
                var $tdRedSum_s = $("<td>" + Sum.district[i].street[j].redSum + "</td>");
                $tr_s.append($tdName_s);
                $tr_s.append($tdSum_s);
                $tr_s.append($tdGreenSum_s);
                $tr_s.append($tdYellowSum_s);
                $tr_s.append($tdRedSum_s);
                $tbody.append($tr_s);
            }
        }
    }

    tuChange($("option:selected").val(),1,null);
}


function tuChange(id,type,qid) {
    var redSum;
    var greenSum;
    var yellowSum;
    var title_text;
    var title_text_2;
    var data=[];

    if(id==0){
        redSum=Sum.redSum;
        greenSum=Sum.greenSum;
        yellowSum=Sum.yellowSum;
        title_text="上海市状态比例图  （总人数:"+Sum.sum+"）";
        title_text_2="上海市区域分布图  （总人数:"+Sum.sum+"）";

        for(var i=0;i<Sum.district.length;i++){
            var val;
            if(Sum.district[i].id==1){
                //闵行区
                val={
                    name: Sum.district[i].name,
                    y:Sum.district[i].sum,
                    sliced: true,
                    selected: true
                }
            }else {
                val = [Sum.district[i].name, Sum.district[i].sum];
            }
            data.push(val);
        }
        $('#main_2').highcharts({
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            },
            title: {
                text: title_text_2
            },
            credits: {
                enabled: false
            },
            tooltip: {
                pointFormat: '{series.name}（{point.y}）: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 35,
                    dataLabels: {
                        enabled: true,
                        format: '{point.name}：'+'{point.percentage:.1f}%'
                    }
                }
            },
            series: [{
                type: 'pie',
                name: '人数占比',
                data: data,
                center:["50%","35%"]
            }]
        });
    }else if(type==1){
        //区
        for(var i=0;i<Sum.district.length;i++) {
            if(Sum.district[i].id==id){
                redSum=Sum.district[i].redSum;
                greenSum=Sum.district[i].greenSum;
                yellowSum=Sum.district[i].yellowSum;
                title_text=Sum.district[i].name+"状态比例图  （总人数:"+Sum.district[i].sum+"）";

                title_text_2=Sum.district[i].name+"区域分布图  （总人数:"+Sum.district[i].sum+"）";
                for(var j=0;j<Sum.district[i].street.length;j++){
                    var val;
                    if(Sum.district[i].street[j].id==1){
                        //古美街道
                        val={
                            name: Sum.district[i].street[j].name,
                            y:Sum.district[i].street[j].sum,
                            sliced: true,
                            selected: true
                        }
                    }else {
                        val = [Sum.district[i].street[j].name, Sum.district[i].street[j].sum];
                    }
                    data.push(val);
                }
                break;
            }
        }
        $('#main_2').highcharts({
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text:title_text_2
            },
            tooltip: {
                pointFormat: '{series.name}（{point.y}）: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 35,
                    dataLabels: {
                        enabled: true,
                        format: '{point.name}：'+'{point.percentage:.1f}%'
                    }
                }
            },
            series: [{
                type: 'pie',
                name: '人数占比',
                data: data,
                center:["50%","35%"]
            }]
        });
    }else{
        //街道
        for(var i=0;i<Sum.district.length;i++) {
            if (Sum.district[i].id == qid) {
                for (var j = 0; j < Sum.district[i].street.length; j++) {
                    if(id==Sum.district[i].street[j].id){
                        redSum=Sum.district[i].street[j].redSum;
                        greenSum=Sum.district[i].street[j].greenSum;
                        yellowSum=Sum.district[i].street[j].yellowSum;
                        title_text=Sum.district[i].street[j].name+"状态比例图  （总人数:"+Sum.district[i].street[j].sum+"）";
                        break;
                    }
                }
            }
        }
    }

    $('#main').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        colors:[
            '#56c078',//第一个颜色，欢迎加入Highcharts学习交流群294191384
            '#EEEE00',//第二个颜色
            '#d53a35'//第三个颜色
        ],
        credits: {
            enabled: false
        },
        title: {
            text:title_text
        },
        tooltip: {
            pointFormat: '{series.name}（{point.y}）: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}：'+'{point.percentage:.1f}%'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '人数占比',
            data: [
                {
                    name: '正常',
                    y:greenSum,
                    sliced: true,
                    selected: true
                },
                ['正在服务',yellowSum],
                ['预警',redSum]
            ],
            center:["50%","18%"]
        }]
    });

}


