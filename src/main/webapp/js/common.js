/**
 * Created by netlab606 on 2017/5/15.
 */
function fixWidth(percent){
    // alert(document.body.clientWidth);
    // alert(percent);
    // alert(document.body.clientWidth * percent);
    return document.body.clientWidth * percent ;
}

function refresh(){
    $('#search').form('clear');
    var searchForm = $('#search').form();
    $('#datagrid').datagrid('load',serializeObject(searchForm));
    $('#datagrid').datagrid('reload');
}

//查询
function formSearch(){
    var searchForm = $('#search').form();
    $('#datagrid').datagrid('load', serializeObject(searchForm));
    // $('#search').form('clear');
}

// 表格序列化
function serializeObject(form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    })
    return o;
}

function formatActionGateway(value,row,index) {
    if(row.gatewayID) {
        var binary = parseInt(row.gatewayID).toString(2) + "";
        switch (binary.length) {
            case 1:
                return row.gatewayID + "(000" + binary + ")";
                break;
            case 2:
                return row.gatewayID + "(00" + binary + ")";
                break;
            case 3:
                return row.gatewayID + "(0" + binary + ")";
                break;
            default:
                return row.gatewayID + "(" + binary + ")";
        }
    }else{
        return "";
    }
}
function formatActionSegment(value,row,index) {
    if(row.segment) {
        var binary = parseInt(row.segment).toString(2) + "";
        switch (binary.length) {
            case 1:
                return row.segment + " (000" + binary + ")";
                break;
            case 2:
                return row.segment + " (00" + binary + ")";
                break;
            case 3:
                return row.segment + " (0" + binary + ")";
                break;
            default:
                return row.segment + " (" + binary + ")";
        }
    }else{
        return "";
    }
}

function formatActionSensor(value,row,index) {
    if(row.collectId) {
        var binary = parseInt(row.collectId).toString(2) + "";
        switch (binary.length) {
            case 1:
                return row.collectId + " (000" + binary + ")";
                break;
            case 2:
                return row.collectId + " (00" + binary + ")";
                break;
            case 3:
                return row.collectId + " (0" + binary + ")";
                break;
            default:
                return row.collectId + " (" + binary + ")";
        }
    }else{
        return "";
    }
}

function formatActionSensor_Eid(value,row,index) {
    if(row.eid) {
        var binary = parseInt(row.eid).toString(2) + "";
        switch (binary.length) {
            case 1:
                return row.eid + " (000" + binary + ")";
                break;
            case 2:
                return row.eid + " (00" + binary + ")";
                break;
            case 3:
                return row.eid + " (0" + binary + ")";
                break;
            default:
                return row.eid + " (" + binary + ")";
        }
    }else{
        return "";
    }
}