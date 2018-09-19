var url;//存储请求的地址
var mesTitle;//存储消息信息


function formatAction(value,row,index){
    var e;
    if(row.timerSwitch==0) {
        e = '<input class="switch" type="checkbox" onclick="change(event)" name="' + row.timerSwitch+ '"> ';
    }else{
        e = '<input class="switch select" type="checkbox" onclick="change(event)"  name="' + row.timerSwitch + '"checked> ';
    }
    return e;
}


function change(event){
    var e=event||window.event;
    var timerSwitch=e.target.name;
    //var oid=e.target.;
    var oid= e.target.parentNode.parentNode.previousSibling.previousSibling.innerText;
    //alert(timerSwitch);
    if(timerSwitch==1){
        timerSwitch=0;
    }else{
        timerSwitch=1;
    }
    //alert(timerSwitch);
    $.ajax({
        type: "POST",
        url: pathJs + "/timer/updateTimer",
        dataType: "json",
        data:{
            timerSwitch:timerSwitch,
            oid:oid
        },
        async:false,
        success: function (data) {
            refresh();
        }
    });
}

