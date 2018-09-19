/**
 * Created by netlab606 on 2017/5/20.
 */
$(function(){
    // alert(user);
    // alert(username);
    if(username!=null&&username!=""){
        //获取菜单栏
        $.ajax({
            type: "POST",
            url: pathJs+"/getUserMenu",
            dataType: "json",
            async:false,//一定要加 不然可能会渲染失败 一定要等html标签创建好
            success:function(data){
                var $ul=$("#bs-example-navbar-collapse-1 .navbar-nav");
                for(var i=0;i<data.length;i++){
                    var liFather=$('<li class="dropdown"><a href="#" class="dropdown-toggle loginD" style="display: none" data-toggle="dropdown">'+data[i].text+'<span class="caret"></span></a> </li>');
                    var ul=$('<ul class="dropdown-menu" role="menu">');
                    for(var j=0;j<data[i].children.length;j++){
                        // urls.push(data[i].children[j].url);
                        //alert(data[i].children[j].url);
                        var li=$('<li><a href="javascript:void(0)" name="'+pathJs+data[i].children[j].url+'" onclick="toIframe(event)">'+data[i].children[j].text+'</a></li>');
                        ul.append(li);
                    }
                    liFather.append(ul);
                    $ul.append(liFather);
                }
            }
        });

        $("#login").css("display","none");
        $("#welcome").css("display","block");
        $(".loginD").css("display","block");
        getNoReadSum();
        // if(username!="admin"){
            //这个方法用来启动该页面的ReverseAjax功能
            dwr.engine.setActiveReverseAjax(true);
            //设置在页面关闭时，通知服务端销毁会话
            dwr.engine.setNotifyServerOnPageUnload(true);
            // alert(1);
        // }
    }


//未读消息跳转
    $("#noReadMessageWarn").click(function(){
        var url=pathJs+"/getUserNoReadWarn";
        $("#carousel-example-generic").hide();
        var iframe=$('<iframe src="'+url+'" width="100%" style="overflow: hidden"></iframe>');
        var navHeight=$("nav").height();
        var totalHeight=$(document.body).height();
        var iframeHeight=totalHeight-navHeight;
        iframe.attr("height",iframeHeight);
        var $div=$("#toIframe");
        $div.html("");
        $div.append(iframe);
    });

    //$("#noReadMessageOut").click(function(){
    //    var url=pathJs+"/getUserNoReadOut";
    //    $("#carousel-example-generic").hide();
    //    var iframe=$('<iframe src="'+url+'" width="100%" style="overflow: hidden"></iframe>');
    //    var navHeight=$("nav").height();
    //    var totalHeight=$(document.body).height();
    //    var iframeHeight=totalHeight-navHeight;
    //    iframe.attr("height",iframeHeight);
    //    var $div=$("#toIframe");
    //    $div.html("");
    //    $div.append(iframe);
    //});

    $("#noReadMessageDown").click(function(){
        var url=pathJs+"/getUserNoReadDown";
        $("#carousel-example-generic").hide();
        var iframe=$('<iframe src="'+url+'" width="100%" style="overflow: hidden"></iframe>');
        var navHeight=$("nav").height();
        var totalHeight=$(document.body).height();
        var iframeHeight=totalHeight-navHeight;
        iframe.attr("height",iframeHeight);
        var $div=$("#toIframe");
        $div.html("");
        $div.append(iframe);
    });

    if(getCookie_index("zoom")!=null&&getCookie_index("zoom")!=""){
        var url=pathJs+"/map/play";
        $("#carousel-example-generic").hide();
        var iframe=$('<iframe id="iframe_ifr" src="'+url+'" width="100%" style="overflow: hidden"></iframe>');
        var navHeight=$("nav").height();
        var totalHeight=$(document.body).height();
        var iframeHeight=totalHeight-navHeight;
        iframe.attr("height",iframeHeight);
        var $div=$("#toIframe");
        $div.html("");
        $div.append(iframe);
    }
});

function getCookie_index(c_name){
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

function toIframe(event) {
    //清空 百度地图 添加标注时 设置的 cookie
    if(getCookie_index("zoom")!=null&&getCookie_index("zoom")!=""){
        setCookie("zoom", "", -1);
        setCookie("center_x_y", "", -1);
    }

    var e=event||window.event;
    var url=e.target.name;
    $("#carousel-example-generic").hide();
    var iframe=$('<iframe id="iframe_ifr" src="'+url+'" width="100%" style="overflow: hidden"></iframe>');
    var navHeight=$("nav").height();
    var totalHeight=$(document.body).height();
    var iframeHeight=totalHeight-navHeight;
    iframe.attr("height",iframeHeight);
    var $div=$("#toIframe");
    $div.html("");
    $div.append(iframe);
}

function warn(data){
    getNoReadSum();
    //紧急报警
    if(data.type=="urgency"){
        var wdid=data.id;
        var urgencyMessage="<div class='eauip'><span class='messageT'>报警设备信息：" +
            "</span><br><p>设备ID：<span class='messageD'>"+data.urgency.equip.eid+"</span></p>"+
            "<p>设备所在房间：<span class='messageD'>"+data.urgency.room.roomName+"</span></p></div>"+
            "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
            "老人ID：<span class='messageD'>"+data.urgency.oldMan.oid+"</span></p><p>" +
            "老人姓名：<span class='messageD'>"+ data.urgency.oldMan.oldName+"</span></p><p>" +
            "老人电话：<span class='messageD'>"+ data.urgency.oldMan.oldPhone+"</span></p><p>" +
            "老人住址：<span class='messageD'>"+ data.urgency.oldMan.oldAddress+"</span></p></div>";
        $.messager.alert('紧急报警！',urgencyMessage,'danger',function(){
            //该预警消息已读
            $.ajax({
                type: "POST",
                url: pathJs + "/warnHistory/urgencyRead",
                dataType: "json",
                data:{
                    wdid:wdid
                },
                async:false,
                success: function (data) {
                    getNoReadSum();
                }
            });
        });
        playSound("urgency");
    }else if(data.type=="gatewayDown"){
        var downid=data.downid;
        //网关故障
        var gatewayDownMessage="<div class='eauip'><span class='messageT'>网关故障信息：" +
            "</span><br><p>网关：<span class='messageD'>"+data.oldMan.gatewayID+"</span></p></div>"+
            "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
            "老人ID：<span class='messageD'>"+data.oldMan.oid+"</span></p><p>" +
            "老人姓名：<span class='messageD'>"+ data.oldMan.oldName+"</span></p><p>" +
            "老人电话：<span class='messageD'>"+ data.oldMan.oldPhone+"</span></p><p>" +
            "老人住址：<span class='messageD'>"+ data.oldMan.oldAddress+"</span></p></div>";
        $.messager.alert('网关故障！',gatewayDownMessage,'danger',function(){
            // 该网关故障消息已读
            $.ajax({
                type: "POST",
                url: pathJs + "/downHistory/messageRead",
                dataType: "json",
                data:{
                    downid:downid
                },
                async:false,
                success: function (data) {
                    getNoReadSum();
                }
            });
        });
        playSound("urgency");
    } else if(data.type=="equipDown"){
        //设备故障
        var downid=data.downid;
        var type;
        switch(data.equipDown.type){
            case 2:
                type="温度";
                break;
            case 3:
                type="湿度";
                break;
            case 4:
                type="光强";
                break;
            default:
                break;
        }
        var gatewayDownMessage="<div class='eauip'><span class='messageT'>设备故障信息：" +
            "</span><br><p>设备ID：<span class='messageD'>"+data.equipDown.eid+"</span></p>"+
            "<p>设备种类：<span class='messageD'>"+type+"</span></p></div>"+
            "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
            "老人ID：<span class='messageD'>"+data.oldMan.oid+"</span></p><p>" +
            "老人姓名：<span class='messageD'>"+ data.oldMan.oldName+"</span></p><p>" +
            "老人电话：<span class='messageD'>"+ data.oldMan.oldPhone+"</span></p><p>" +
            "老人住址：<span class='messageD'>"+ data.oldMan.oldAddress+"</span></p></div>";
        $.messager.alert('设备故障！',gatewayDownMessage,'danger',function(){
            //该网关故障消息已读
            $.ajax({
                type: "POST",
                url: pathJs + "/downHistory/messageRead",
                dataType: "json",
                data:{
                    downid:downid
                },
                async:false,
                success: function (data) {
                    getNoReadSum();
                }
            });
        });
        playSound("urgency");
    }else {
        //预警信息
        var warnMessage = "";
        var title = "";
        if (data.type == "warn_move") {
            //行为预警
            title="行为预警";
            warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
                "老人ID：<span class='messageD'>" + data.warn.oldMan.oid + "</span></p><p>" +
                "老人姓名：<span class='messageD'>" + data.warn.oldMan.oldName + "</span></p><p>" +
                "老人电话：<span class='messageD'>" + data.warn.oldMan.oldPhone + "</span></p><p>" +
                "老人住址：<span class='messageD'>" + data.warn.oldMan.oldAddress + "</span></p></div>" +
                "<div class='detail'><span class='messageT'>行为信息：</span><br><p>" +
                "预警级别：<span class='messageD read'>" + data.warn.warnLevel + "</span></p><p>" +
                "已经不动：<span class='messageD read'>" + data.warn.noMoveTime + " </span>分钟</p><p>" +
                "所处房间：<span class='messageD'>" + data.warn.room.roomName + "</span></p><p>" +
                "最初不动的时间：<span class='messageD'>" + data.warn.time + "</span></p><p>" +
                "是否在该房间的生活规律模型中：<span class='messageD'>" + (data.warn.inTime == 'true' ? "在<p>模型所在时间段：<span class='messageD'>" + data.warn.times + "</span></p><p>" +
                    "规律类型：<span class='messageD'>" + (data.warn.flag == "a" ? "活动" : ((data.warn.flag == "r") ? "休息" : "活动、休息")) + "</span></p>" :
                    "不在") + "</span></p></div><input name="+data.id+" type='button' value='已读' onclick='readA(event)'/>";
        } else if (data.type == "warn_wendu") {
            //温度预警
            title="温度预警";
            warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
                "老人ID：<span class='messageD'>" + data.warn_wendu.oldMan.oid + "</span></p><p>" +
                "老人姓名：<span class='messageD'>" + data.warn_wendu.oldMan.oldName + "</span></p><p>" +
                "老人电话：<span class='messageD'>" + data.warn_wendu.oldMan.oldPhone + "</span></p><p>" +
                "老人住址：<span class='messageD'>" + data.warn_wendu.oldMan.oldAddress + "</span></p></div>" +
                "<div class='detail'><span class='messageT'>温度信息：</span><br><p>" +
                "报警房间：<span class='messageD read'>" + data.warn_wendu.threshold_wendu.room.roomName + "</span></p><p>" +
                "当前温度：<span class='messageD read'>" + data.warn_wendu.wendu + "</span></p><p>" +
                "该房间温度阈值：<span class='messageD'>" + data.warn_wendu.threshold_wendu.wThreshold + "</span></p><p></div><input name="+data.id+" type='button' value='已读' onclick='readA(event)'/>";
        } else if (data.type == "warn_light") {
            //光强预警
            title="光强预警";

            warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
                "老人ID：<span class='messageD'>" + data.warn_light.oldMan.oid + "</span></p><p>" +
                "老人姓名：<span class='messageD'>" + data.warn_light.oldMan.oldName + "</span></p><p>" +
                "老人电话：<span class='messageD'>" + data.warn_light.oldMan.oldPhone + "</span></p><p>" +
                "老人住址：<span class='messageD'>" + data.warn_light.oldMan.oldAddress + "</span></p></div>" +
                "<div class='detail'><span class='messageT'>光强信息：</span><br><p>" +
                "报警房间：<span class='messageD read'>" + data.warn_light.threshold_light.room.roomName + "</span></p><p>" +
                "当前光强：<span class='messageD read'>" + data.warn_light.light+ "</span></p><p>" +
                "起止时间：<span class='messageD'>" + data.warn_light.time+ "</span></p><p>" +
                "当前持续时间：<span class='messageD'>" + data.warn_light.value + "</span></p><p>" +
                "该房间光强阈值：：<span class='messageD'>" + data.warn_light.threshold_light.lThreshold + "</span></p><p>" +
                "检测时间段：<span class='messageD'>" + data.warn_light.threshold_light.times + "</span></p><p></div>";
            "持续超过：<span class='messageD'>" + data.warn_light.threshold_light.continueTime + " 分钟报警</span></p><p></div><input name="+data.id+" type='button' value='已读' onclick='readA(event)'/>";
        }else if(data.type=="outdoor_out"){
            //没有了  不用再进行提示了  代码先保留
            title="老人出门";
            warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
                "老人ID：<span class='messageD'>" + data.outdoor.oldMan.oid + "</span></p><p>" +
                "老人姓名：<span class='messageD'>" + data.outdoor.oldMan.oldName + "</span></p><p>" +
                "老人电话：<span class='messageD'>" + data.outdoor.oldMan.oldPhone + "</span></p><p>" +
                "老人住址：<span class='messageD'>" + data.outdoor.oldMan.oldAddress + "</span></p></div>" +
                "<div class='detail'><span class='messageT'>出门信息：</span><br><p>" +
                "出门时刻：<span class='messageD read'>" + data.outdoor.out + "</span></p><p>" +
                "出门阈值：超过<span class='messageD red'>" + data.outdoor.threshold_out.outThreshold+ "</span>分钟为出门</p><p></div><input name="+data.id+" type='button' value='已读' onclick='readB(event)'/>"

        }else if(data.type=="outdoor_nocome"){
            title="老人出门未归预警";
            warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
                "老人ID：<span class='messageD'>" + data.outdoor.oldMan.oid + "</span></p><p>" +
                "老人姓名：<span class='messageD'>" + data.outdoor.oldMan.oldName + "</span></p><p>" +
                "老人电话：<span class='messageD'>" + data.outdoor.oldMan.oldPhone + "</span></p><p>" +
                "老人住址：<span class='messageD'>" + data.outdoor.oldMan.oldAddress + "</span></p></div>" +
                "<div class='detail'><span class='messageT'>未归信息：</span><br><p>" +
                "出门时刻：<span class='messageD read'>" + data.outdoor.out + "</span></p><p>" +
                "未归阈值：<span class='messageD'>" + data.outdoor.threshold_out.noComeThreshold+ "分钟</span></p><p></div><input name="+data.id+" type='button' value='已读' onclick='readA(event)'/>"
        }else if(data.type=="outdoor_come"){
            //没有了  不用再进行提示了  代码先保留
            title="老人回来";
            warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
                "老人ID：<span class='messageD'>" + data.outdoor.oldMan.oid + "</span></p><p>" +
                "老人姓名：<span class='messageD'>" + data.outdoor.oldMan.oldName + "</span></p><p>" +
                "老人电话：<span class='messageD'>" + data.outdoor.oldMan.oldPhone + "</span></p><p>" +
                "老人住址：<span class='messageD'>" + data.outdoor.oldMan.oldAddress + "</span></p></div>" +
                "<div class='detail'><span class='messageT'>回来信息：</span><br><p>" +
                "出门时间段：<span class='messageD read'>" + data.outdoor.dataD + "</span></p><p></div><input name="+data.id+" type='button' value='已读' onclick='readB(event)'/>"
        }
        $.messager.show({
            title:title,
            msg:warnMessage,
            showType:'fade',
            width:"15%",
            height:'38%',
            timeout:15000,
            style:{
                right:'',
                top:document.body.scrollTop+document.documentElement.scrollTop,
                bottom:''
            }
        });
        playSound("warn");
    }

}


//消息推送 播放提示音
function playSound(type)
{
    var borswer = window.navigator.userAgent.toLowerCase();
    if ( borswer.indexOf( "ie" ) >= 0 )
    {
        //IE内核浏览器
        var strEmbed;
        if(type=="warn") {
            strEmbed = '<embed name="embedPlay" src="' + pathJs + '/wav/warn.wav" autostart="true" hidden="true" loop="false"></embed>';
        }else{
            strEmbed = '<embed name="embedPlay" src="' + pathJs + '/wav/urgency.wav" autostart="true" hidden="true" loop="false"></embed>';
        }
        if ( $( "body" ).find( "embed" ).length <= 0 )
            $( "body" ).append( strEmbed );
        var embed = document.embedPlay;

        //浏览器不支持 audion，则使用 embed 播放
        embed.volume = 100;
        //embed.play();这个不需要
    } else
    {
        //非IE内核浏览器
        var strAudio;
        if(type=="warn") {
            strAudio = "<audio id='audioPlay' src='" + pathJs + "/wav/warn.wav' hidden='true'>";
        }else{
            strAudio = "<audio id='audioPlay' src='" + pathJs + "/wav/urgency.wav' hidden='true'>";
        }
        if ( $( "body" ).find( "audio" ).length <= 0 )
            $( "body" ).append( strAudio );
        var audio = document.getElementById( "audioPlay" );

        //浏览器支持 audion
        audio.play();
    }
}


//预警
function readA(event){
    var e=event||window.event;
    //该预警消息已读
    $.ajax({
        type: "POST",
        url: pathJs + "/warnHistory/messageRead",
        dataType: "json",
        data:{
            wdid:e.target.name
        },
        async:false,
        success: function (data) {}
    });
    getNoReadSum();
}

//出门   目前不用了  代码先保留
function readB(event){
    var e=event||window.event;
    //该预警消息已读
    $.ajax({
        type: "POST",
        url: pathJs + "/outHistory/messageRead",
        dataType: "json",
        data:{
            odid:e.target.name
        },
        async:false,
        success: function (data) {}
    });
    getNoReadSum();
}



function getNoReadSum(){
    //获取未读预警消息总数
    $.ajax({
        type: "GET",
        url: pathJs + "/warnHistory/getNoReadSum",
        dataType: "json",
        success: function (data) {
            $('.wMessage').html(data.data);
            //出门未读消息总数
            //$.ajax({
            //    type: "GET",
            //    url: pathJs + "/outHistory/getNoReadSum",
            //    dataType: "json",
            //    success: function (data) {
            //        $('.oMessage').html(data.data);
                    $.ajax({
                        type: "GET",
                        url: pathJs + "/downHistory/getNoReadSum",
                        dataType: "json",
                        success: function (data) {
                            $('.dMessage').html(data.data);
                            //$('.tMessage').html(parseInt($('.oMessage').html())+parseInt($('.wMessage').html())+parseInt($('.dMessage').html()));
                            $('.tMessage').html(parseInt($('.wMessage').html())+parseInt($('.dMessage').html()));
                        }
                    });
                }
            //});
        //}
    });

}

function reLogin(){
    $.ajax({
        type: "GET",
        url: pathJs + "/logout",
        dataType: "json",
        success: function (data) {
            if(data.success==true){
                // username="";
                document.location="/";
            }
        }
    });
}


function mapUpdate() {

    //当前frame 是地图页面  则进行实时更新  不是的话就不用更新了
    if($("#iframe_ifr").attr("src").indexOf("map")!=-1){
        //调用map.jsp 子页面的 函数
        document.getElementById("iframe_ifr").contentWindow.louChange();
    }
}

function clearCookie() {
    //清空 百度地图 添加标注时 设置的 cookie
    if(getCookie_index("zoom")!=null&&getCookie_index("zoom")!=""){
        setCookie("zoom", "", -1);
        setCookie("center_x_y", "", -1);
    }
}