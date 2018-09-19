/**
 * Created by admin on 2017/4/21.
 */
var urls=[];//存放菜单项的url

//获取菜单栏 不能放在$(function中  否则渲染不了
$.ajax({
    type: "POST",
    url: pathJs+"/getMenu",
    dataType: "json",
    async:false,//一定要加 不然可能会渲染失败 一定要等html标签创建好
    success:function(data){
        var $div=$(".easyui-accordion");
        for(var i=0;i<data.length;i++){
            var div=$("<div title='"+data[i].text+"'></div>");
            var ul=$('<ul class="easyui-datalist" data-options="border:false,fit:true">');
            for(var j=0;j<data[i].children.length;j++){
                urls.push(data[i].children[j].url);
                //alert(data[i].children[j].url);
                var li=$('<li>'+data[i].children[j].text+'</li>');
                ul.append(li);
            }
            div.append(ul);
            $div.append(div);
        }
    }
});

$(function() {

    //location.reload();//自动刷新一下 防止最开始 布局没有加载出来
    //获取角色   一定是管理员
    $.ajax({
        type: "GET",
        url: pathJs + "/getRole",
        dataType: "json",
        success: function (data) {
            if (data.data.name == "超级管理人员") {
                $(".role").html(data.data.name + '<span class="color-grey badge" id="quanxian">最高权限</span>');
            }
            if (data.data.name == "管理人员") {
                $(".role").html(data.data.name + '<span class="color-grey badge" id="quanxian">部分权限</span>');
            }
            // if (data.data.name != "管理人员") {
                // $('.messageB').show();
                // $(".role").html(data.data.name+'<span class="color-grey badge" id="quanxian">无权限</span>');
                //这个方法用来启动该页面的ReverseAjax功能
                // dwr.engine.setActiveReverseAjax(true);
                //设置在页面关闭时，通知服务端销毁会话
                // dwr.engine.setNotifyServerOnPageUnload(true);
                // getNoReadSum();
                //var code=$('#usercode').val();//这里是你传入的参数，可以是直接写自己的值，用于区别session 实现 筛选 发送推送再用
                //Remote.getData(code);//调用后台程序
            // }
        }
    });

    //var array=[{'sensorPointID':8,'sensorID':1,'sensorData':15,'hour':7,'minute':33,'second':10},{'sensorPointID':8,'sensorID':1,'sensorData':240,'hour':7,'minute':33,'second':30}];
    //获取当前 数据库访问时间间隔
    // $.ajax({
    //     type: "GET",
    //     url: pathJs+"/getAccessBatabaseTime",
    //     dataType: "json",
    //     success: function (data) {
    //         $(".accessDatabase").val(data.data);
    //     }
    // });


    /*布局部分*/
    $('#master-layout').layout({
        fit: true/*布局框架全屏*/
    });
    /*右侧菜单控制部分*/
    var left_control_status = true;
    var left_control_panel = $("#master-layout").layout("panel", 'west');
    $(".left-control-switch").on("click", function () {
        if (left_control_status) {
            left_control_panel.panel('resize', {width: 70});
            left_control_status = false;
            $(".theme-left-normal").hide();
            $(".theme-left-minimal").show();
        } else {
            left_control_panel.panel('resize', {width: 200});
            left_control_status = true;
            $(".theme-left-normal").show();
            $(".theme-left-minimal").hide();
        }
        $("#master-layout").layout('resize', {width: '100%'})
    });
    /*右侧菜单控制结束*/

    //因为整个机制 将li转化为table 之后li设置为不可见，li的属性字段不能跟着添加到table中  所以只能遍历该table
    var m=0;
    $(".datagrid-body td").each(function(){
        $(this).attr("url",urls[m++]);
    });
    $("td").click(function(){
        if($(this).text()!="打开") {
            var tabs = $('#mainTabs');
            var src = pathJs + $(this).attr('url');
            var opts = {
                title: $(this).text(),
                closable: true,
                content: $.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', src),
                border: false,
                fit: true
            };
            if (tabs.tabs('exists', opts.title)) {
                tabs.tabs('select', opts.title);
            } else {
                tabs.tabs('add', opts);
            }
        }
    });

//首页
    //获取数量
    $.ajax({
        type: "GET",
        url: pathJs + "/getCount",
        dataType: "json",
        success: function (data) {
            var dflt = {
                min: 0,
                max: data.data.total,
                donut: true,
                gaugeWidthScale: 0.6,
                counter: true,
                hideInnerShadow: true
            }
            var gg1 = new JustGage({
                id: 'gg1',
                value: data.data.oldManC,
                defaults: dflt
            });

            var gg2 = new JustGage({
                id: 'gg2',
                value: data.data.roomC,
                defaults: dflt
            });
            var gg3 = new JustGage({
                id: 'gg3',
                value: data.data.equipC,
                defaults: dflt
            });
        }
    });

    // 进度条
    // var value = $('#p').progressbar('getValue');
    // if (value < 100){
    //     value += Math.floor(Math.random() * 20);
    //     $('#p').progressbar('setValue', value);
    //     setTimeout(arguments.callee, 200);
    // }else{
    //     $('#p').hide();
    //     $('.theme-left-normal').show();
    //     $('#mainTabs').show();
    //     $('.theme-navigate').show();
    // }




//未读消息跳转
//     $("#noReadMessageWarn").click(function(){
//         var tabs = $('#mainTabs');
//         var src =pathJs+"/getUserNoReadWarn?type=back";
//         var opts = {
//             title: "报警消息",
//             closable: true,
//             content: $.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', src),
//             border: false,
//             fit: true
//         };
//         if (tabs.tabs('exists', opts.title)) {
//             tabs.tabs('select', opts.title);
//         } else {
//             tabs.tabs('add', opts);
//         }
//     });
//
//     $("#noReadMessageOut").click(function(){
//         var tabs = $('#mainTabs');
//         var src =pathJs+"/getUserNoReadOut?type=back";
//         var opts = {
//             title: "出门消息",
//             closable: true,
//             content: $.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', src),
//             border: false,
//             fit: true
//         };
//         if (tabs.tabs('exists', opts.title)) {
//             tabs.tabs('select', opts.title);
//         } else {
//             tabs.tabs('add', opts);
//         }
//     });

});

$.formatString = function(str) {
    for ( var i = 0; i < arguments.length - 1; i++) {
        str = str.replace("{" + i + "}", arguments[i + 1]);
    }
    return str;
};


// function getNoReadSum(){
//     //获取未读预警消息总数
//     $.ajax({
//         type: "GET",
//         url: pathJs + "/warnHistory/getNoReadSum",
//         dataType: "json",
//         success: function (data) {
//             $('.wMessage').html(data.data);
//             //出门未读消息总数
//             $.ajax({
//                 type: "GET",
//                 url: pathJs + "/outHistory/getNoReadSum",
//                 dataType: "json",
//                 success: function (data) {
//                     $('.oMessage').html(data.data);
//                     $('.tMessage').html(parseInt($('.oMessage').html())+parseInt($('.wMessage').html()));
//                 }
//             });
//         }
//     });
//
// }

// function warn(data){
//     getNoReadSum();
//     //紧急报警
//     if(data.type=="urgency"){
//         var urgencyMessage="<div class='eauip'><span class='messageT'>报警设备信息：" +
//             "</span><br><p>设备ID：<span class='messageD'>"+data.urgency.equip.eid+"</span></p>"+
//                 "<p>设备所在房间：<span class='messageD'>"+data.urgency.room.roomName+"</span></p></div>"+
//             "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
//             "老人ID：<span class='messageD'>"+data.urgency.oldMan.oid+"</span></p><p>" +
//             "老人姓名：<span class='messageD'>"+ data.urgency.oldMan.oldName+"</span></p><p>" +
//             "老人电话：<span class='messageD'>"+ data.urgency.oldMan.oldPhone+"</span></p><p>" +
//             "老人住址：<span class='messageD'>"+ data.urgency.oldMan.oldAddress+"</span></p></div>";
//         $.messager.alert('紧急报警！',urgencyMessage,'danger');
//     }else {
//         //预警信息
//         var warnMessage = "";
//         var title = "";
//         if (data.type == "warn_move") {
//             //行为预警
//             title="行为预警";
//             warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
//             "老人ID：<span class='messageD'>" + data.warn.oldMan.oid + "</span></p><p>" +
//             "老人姓名：<span class='messageD'>" + data.warn.oldMan.oldName + "</span></p><p>" +
//             "老人电话：<span class='messageD'>" + data.warn.oldMan.oldPhone + "</span></p><p>" +
//             "老人住址：<span class='messageD'>" + data.warn.oldMan.oldAddress + "</span></p></div>" +
//             "<div class='detail'><span class='messageT'>行为信息：</span><br><p>" +
//             "预警级别：<span class='messageD read'>" + data.warn.warnLevel + "</span></p><p>" +
//             "已经不动：<span class='messageD read'>" + data.warn.noMoveTime + " </span>分钟</p><p>" +
//             "所处房间：<span class='messageD'>" + data.warn.room.roomName + "</span></p><p>" +
//             "最初不动的时间：<span class='messageD'>" + data.warn.time + "</span></p><p>" +
//             "是否在该房间的生活规律模型中：<span class='messageD'>" + (data.warn.inTime == 'true' ? "在<p>模型所在时间段：<span class='messageD'>" + data.warn.times + "</span></p><p>" +
//             "规律类型：<span class='messageD'>" + (data.warn.flag == "a" ? "活动" : ((data.warn.flag == "r") ? "休息" : "活动、休息")) + "</span></p>" :
//                 "不在") + "</span></p></div><input name="+data.id+" type='button' value='已读' onclick='readA(event)'/>";
//         } else if (data.type == "warn_wendu") {
//             //温度预警
//             title="温度预警";
//             warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
//             "老人ID：<span class='messageD'>" + data.warn_wendu.oldMan.oid + "</span></p><p>" +
//             "老人姓名：<span class='messageD'>" + data.warn_wendu.oldMan.oldName + "</span></p><p>" +
//             "老人电话：<span class='messageD'>" + data.warn_wendu.oldMan.oldPhone + "</span></p><p>" +
//             "老人住址：<span class='messageD'>" + data.warn_wendu.oldMan.oldAddress + "</span></p></div>" +
//             "<div class='detail'><span class='messageT'>温度信息：</span><br><p>" +
//             "报警房间：<span class='messageD read'>" + data.warn_wendu.threshold_wendu.room.roomName + "</span></p><p>" +
//             "当前温度：<span class='messageD read'>" + data.warn_wendu.wendu + "</span></p><p>" +
//             "该房间温度阈值：<span class='messageD'>" + data.warn_wendu.threshold_wendu.wThreshold + "</span></p><p></div><input name="+data.id+" type='button' value='已读' onclick='readA(event)'/>";
//         } else if (data.type == "warn_light") {
//             //光强预警
//             title="光强预警";
//
//             warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
//             "老人ID：<span class='messageD'>" + data.warn_light.oldMan.oid + "</span></p><p>" +
//             "老人姓名：<span class='messageD'>" + data.warn_light.oldMan.oldName + "</span></p><p>" +
//             "老人电话：<span class='messageD'>" + data.warn_light.oldMan.oldPhone + "</span></p><p>" +
//             "老人住址：<span class='messageD'>" + data.warn_light.oldMan.oldAddress + "</span></p></div>" +
//             "<div class='detail'><span class='messageT'>光强信息：</span><br><p>" +
//             "报警房间：<span class='messageD read'>" + data.warn_light.threshold_light.room.roomName + "</span></p><p>" +
//             "当前光强：<span class='messageD read'>" + data.warn_light.light+ "</span></p><p>" +
//             "起止时间：<span class='messageD'>" + data.warn_light.time+ "</span></p><p>" +
//             "当前持续时间：<span class='messageD'>" + data.warn_light.value + "</span></p><p>" +
//             "该房间光强阈值：：<span class='messageD'>" + data.warn_light.threshold_light.lThreshold + "</span></p><p>" +
//             "检测时间段：<span class='messageD'>" + data.warn_light.threshold_light.times + "</span></p><p></div>";
//             "持续超过：<span class='messageD'>" + data.warn_light.threshold_light.continueTime + " 分钟报警</span></p><p></div><input name="+data.id+" type='button' value='已读' onclick='readA(event)'/>";
//         }else if(data.type=="outdoor_out"){
//             title="老人出门";
//             warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
//             "老人ID：<span class='messageD'>" + data.outdoor.oldMan.oid + "</span></p><p>" +
//             "老人姓名：<span class='messageD'>" + data.outdoor.oldMan.oldName + "</span></p><p>" +
//             "老人电话：<span class='messageD'>" + data.outdoor.oldMan.oldPhone + "</span></p><p>" +
//             "老人住址：<span class='messageD'>" + data.outdoor.oldMan.oldAddress + "</span></p></div>" +
//             "<div class='detail'><span class='messageT'>出门信息：</span><br><p>" +
//             "出门时刻：<span class='messageD read'>" + data.outdoor.out + "</span></p><p>" +
//             "出门阈值：超过<span class='messageD red'>" + data.outdoor.threshold_out.outThreshold+ "</span>分钟为出门</p><p></div><input name="+data.id+" type='button' value='已读' onclick='readB(event)'/>"
//
//         }else if(data.type=="outdoor_nocome"){
//             title="老人出门未归预警";
//             warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
//             "老人ID：<span class='messageD'>" + data.outdoor.oldMan.oid + "</span></p><p>" +
//             "老人姓名：<span class='messageD'>" + data.outdoor.oldMan.oldName + "</span></p><p>" +
//             "老人电话：<span class='messageD'>" + data.outdoor.oldMan.oldPhone + "</span></p><p>" +
//             "老人住址：<span class='messageD'>" + data.outdoor.oldMan.oldAddress + "</span></p></div>" +
//             "<div class='detail'><span class='messageT'>未归信息：</span><br><p>" +
//             "出门时刻：<span class='messageD read'>" + data.outdoor.out + "</span></p><p>" +
//             "未归阈值：<span class='messageD'>" + data.outdoor.threshold_out.noComeThreshold+ "分钟</span></p><p></div><input name="+data.id+" type='button' value='已读' onclick='readA(event)'/>"
//         }else if(data.type=="outdoor_come"){
//             title="老人回来";
//             warnMessage = "<div class='oldMan'><span class='messageT'>老人信息：</span><br><p>" +
//             "老人ID：<span class='messageD'>" + data.outdoor.oldMan.oid + "</span></p><p>" +
//             "老人姓名：<span class='messageD'>" + data.outdoor.oldMan.oldName + "</span></p><p>" +
//             "老人电话：<span class='messageD'>" + data.outdoor.oldMan.oldPhone + "</span></p><p>" +
//             "老人住址：<span class='messageD'>" + data.outdoor.oldMan.oldAddress + "</span></p></div>" +
//             "<div class='detail'><span class='messageT'>回来信息：</span><br><p>" +
//             "出门时间段：<span class='messageD read'>" + data.outdoor.dataD + "</span></p><p></div><input name="+data.id+" type='button' value='已读' onclick='readB(event)'/>"
//         }
//         $.messager.show({
//             title:title,
//             msg:warnMessage,
//             showType:'fade',
//             width:"15%",
//             height:'38%',
//             timeout:15000,
//             style:{
//                 right:'',
//                 top:document.body.scrollTop+document.documentElement.scrollTop,
//                 bottom:''
//             }
//         });
//     }
//
// }

// function readA(event){
//     var e=event||window.event;
//     //该预警消息已读
//     $.ajax({
//         type: "POST",
//         url: pathJs + "/warnHistory/messageRead",
//         dataType: "json",
//         data:{
//           wdid:e.target.name
//         },
//         async:false,
//         success: function (data) {}
//     });
//     getNoReadSum();
// }
// function readB(event){
//     var e=event||window.event;
//     //该预警消息已读
//     $.ajax({
//         type: "POST",
//         url: pathJs + "/outHistory/messageRead",
//         dataType: "json",
//         data:{
//             odid:e.target.name
//         },
//         async:false,
//         success: function (data) {}
//     });
//     getNoReadSum();
// }

// function shezhi(){
//     $(".shezhi").show();
// }

//form表单直接 提交的话  会跳转页面  即使是@ResponBody
function changeAccessDatabase(){
    $('#accessDatabaseForm').form('submit', {
        url: pathJs+"/setAccessBatabaseTime",
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            var mesTitle;
            if (result.success) {
                mesTitle = '修改成功';
            } else {
                mesTitle = '修改失败';
            }
            $.messager.show({
                title: mesTitle,
                msg: result.msg
            });
        }
    });
}