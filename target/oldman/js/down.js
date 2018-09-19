
var url;//存储请求的地址
var mesTitle;//存储消息信息


function formatActionData(value,row,index){
    return '<span class="messageT">'+row.dataDown+'</span>';
}

function formatAction(value,row,index){
    var e;
    if(row.readDown=="是") {
        e = '<input type="button" class="read easyui-linkbutton" disabled value="已读"/> ';
    }else{
        e = '<input type="button"  class="noRead" value="未读" onclick="read(event)" /> ';
    }
    return e;
}

function read(event){
    var e=event||window.event;
    var downid= e.target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.innerText;
    $.ajax({
        type: "POST",
        url: pathJs + "/downHistory/messageRead",
        dataType: "json",
        data: {
            downid: downid
        },
        async: false,
        success: function (data) {
            //获取未读预警消息总数
            $.ajax({
                type: "GET",
                url: pathJs + "/downHistory/getNoReadSum",
                dataType: "json",
                success: function (data) {
                    //iframe子页面调用父页面的元素
                    var valD = $('.dMessage', parent.document);
                    //alert(total.innerHTML);
                    valD.html(data.data);
                    var valT = $('.tMessage', parent.document);
                    var valW = $('.wMessage', parent.document);
                    //var valO = $('.oMessage', parent.document);
                    valT.html(parseInt(valO.html())+parseInt(valW.html())+parseInt(valD.html()));
                    valT.html(parseInt(valW.html())+parseInt(valD.html()));
                    refresh();
                }
            });
        }
    });
}


//function detail(event){
//    var e=event||window.event;
//    var odid= e.target.name.split('@')[0];
//    var r= e.target.name.split('@')[1];
//    $.ajax({
//        type: "POST",
//        url: pathJs + "/outHistory/getMessage",
//        dataType: "json",
//        data:{
//            odid: odid
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
//            $.messager.alert('出门信息',message,'info');
//            if(r=="否") {
//                //标记该预警消息已读
//                $.ajax({
//                    type: "POST",
//                    url: pathJs + "/outHistory/messageRead",
//                    dataType: "json",
//                    data: {
//                        odid: odid
//                    },
//                    async: false,
//                    success: function (data) {
//                        //获取未读预警消息总数
//                        $.ajax({
//                            type: "GET",
//                            url: pathJs + "/outHistory/getNoReadSum",
//                            dataType: "json",
//                            success: function (data) {
//                                //iframe子页面调用父页面的元素
//                                var valO = $('.oMessage', parent.document);
//                                //alert(total.innerHTML);
//                                valO.html(data.data);
//                                var valT = $('.tMessage', parent.document);
//                                var valW = $('.wMessage', parent.document);
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