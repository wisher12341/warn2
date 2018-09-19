
var url;//存储请求的地址
var mesTitle;//存储消息信息

function formatActionData(value,row,index){
    var title=[];
    title=row.dataW.split('@');
    var message='';
    for(var i=1;i<title.length;i++){
        var content=[];
        content=title[i].split('%');
        // message+="<span class='messageT'>"+content[0]+"</span><br/>";
        for(var j=1;j<content.length;j++){
            //注意 这里是 中文输入法 ‘：’
            var titleData=content[j].split("：")[0];
            var data=content[j].split("：")[1];
            message+='<span class="titleData">'+titleData+'：</span><span class="data">'+data+'&nbsp;&nbsp;&nbsp;</span> ';
            if(j%2==0){
                message+="<br/>";
            }

        }
    }
    return message;
}

function formatAction(value,row,index){
    var e;
    if(row.readW=="是") {
        e = '<input type="button" class="read" disabled value="已读"/> ';
    }else{
        e = '<input type="button" class="noRead"  value="未读" onclick="read(event)" /> ';
    }
    return e;
}
function read(event){
    var e=event||window.event;
    var wdid= e.target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.innerText;
    $.ajax({
        type: "POST",
        url: pathJs + "/warnHistory/messageRead",
        dataType: "json",
        data: {
            wdid: wdid
        },
        async: false,
        success: function (data) {
            //获取未读预警消息总数
            $.ajax({
                type: "GET",
                url: pathJs + "/warnHistory/getNoReadSum",
                dataType: "json",
                success: function (data) {
                    //iframe子页面调用父页面的元素
                    var valW = $('.wMessage', parent.document);
                    //alert(total.innerHTML);
                    valW.html(data.data);
                    var valT = $('.tMessage', parent.document);
                    //var valO = $('.oMessage', parent.document);
                    var valD = $('.dMessage', parent.document);
                    //valT.html(parseInt(valO.html())+parseInt(valW.html())+parseInt(valD.html()));
                    valT.html(parseInt(valW.html())+parseInt(valD.html()));
                    refresh();
                }
            });
        }
    });
}

//function detail(event){
//    var e=event||window.event;
//    var wdid= e.target.name.split('@')[0];
//    var r= e.target.name.split('@')[1];
//    $.ajax({
//        type: "POST",
//        url: pathJs + "/warnHistory/getMessage",
//        dataType: "json",
//        data:{
//            wdid: wdid
//        },
//        success: function (data) {
//            var title=[];
//            title=data.data.split('@');
//            var message='<br/><br/><br/>';
//            for(var i=0;i<title.length;i++){
//                var content=[];
//                content=title[i].split('%');
//                message+="<span class='messageT'>"+content[0]+"</span>"
//                for(var j=1;j<content.length;j++){
//                    message+="<p>"+content[j]+"</p>";
//                }
//            }
//            $.messager.alert('预警信息',message,'info');
//            if(r=="否") {
//                //标记该预警消息已读
//                $.ajax({
//                    type: "POST",
//                    url: pathJs + "/warnHistory/messageRead",
//                    dataType: "json",
//                    data: {
//                        wdid: wdid
//                    },
//                    async: false,
//                    success: function (data) {
//                        //获取未读预警消息总数
//                        $.ajax({
//                            type: "GET",
//                            url: pathJs + "/warnHistory/getNoReadSum",
//                            dataType: "json",
//                            success: function (data) {
//                                //iframe子页面调用父页面的元素
//                                var valW = $('.wMessage', parent.document);
//                                //alert(total.innerHTML);
//                                valW.html(data.data);
//                                var valT = $('.tMessage', parent.document);
//                                var valO = $('.oMessage', parent.document);
//                                valT.html(parseInt(valO.html())+parseInt(valW.html()));
//                                refresh();
//                            }
//                        });
//                    }
//                });
//            }
//        }
//    });
//
//}