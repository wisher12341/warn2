var timer;
$(function(){
    //实时更新 10秒一次
    // timer=setInterval(function(){
    //     var searchForm = $('#search').form();
    //     $('#datagrid').datagrid('load',serializeObject(searchForm));
    //     $('#datagrid').datagrid('reload');
    //     },10000);
});


function stopRefresh(event) {
    var e=event||window.event;
    //因为 虽然事件绑定的是a标签  但是子span标签也继承了该事件  导致点击不同位置 节点元素不一样  所以采取其他思路：获取text文字进行判断
    var content=e.target.innerText;
    if(content.indexOf("停止实时刷新")!=-1) {
        clearInterval(timer);
        e.target.innerText="开启实时刷新";
    }else{
        //实时更新 10秒一次
        timer=setInterval(function(){
            var searchForm = $('#search').form();
            $('#datagrid').datagrid('load',serializeObject(searchForm));
            $('#datagrid').datagrid('reload');
        },10000);
        e.target.innerText="停止实时刷新";
    }
}


function formatActionGatewayRaw(value,row,index) {
    if(!row.gatewayID){
        return "";
    }
    var binary=parseInt(row.gatewayID).toString(2)+"";
    switch (binary.length) {
        case 1:
            return row.gatewayID + " (000" + binary + ")";
            break;
        case 2:
            return row.gatewayID + " (00" + binary + ")";
            break;
        case 3:
            return row.gatewayID + " (0" + binary + ")";
            break;
        default:
            return row.gatewayID + " (" + binary + ")";
    }
}
function formatActionSegmentRaw(value,row,index) {
    if(!row.oldMan.segment){
        return "";
    }
    var binary=parseInt(row.oldMan.segment).toString(2)+"";
    switch (binary.length) {
        case 1:
            return row.oldMan.segment + " (000" + binary + ")";
            break;
        case 2:
            return row.oldMan.segment + " (00" + binary + ")";
            break;
        case 3:
            return row.oldMan.segment + " (0" + binary + ")";
            break;
        default:
            return row.oldMan.segment + " (" + binary + ")";
    }
}

function formatActionSensorRaw(value,row,index) {
    if(row.sensorId) {
        var binary = parseInt(row.sensorId).toString(2) + "";
        switch (binary.length) {
            case 1:
                return row.sensorId + " (000" + binary + ")";
                break;
            case 2:
                return row.sensorId + " (00" + binary + ")";
                break;
            case 3:
                return row.sensorId + " (0" + binary + ")";
                break;
            default:
                return row.sensorId + " (" + binary + ")";
        }
    }else {
        return "";
    }
}
