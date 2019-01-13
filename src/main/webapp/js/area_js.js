$(function(){
    //获得用户角色
    $.ajax({
        type: "GET",
        url: pathJs+"/getRole",
        dataType: "json",
        success:function(data){
            if(data.data.name!="前台人员"){
                $(".admin").show();
            }
        }
    });
    //获得所有老人的网关ID
    $.ajax({
        type: "GET",
        url: pathJs+"/data/getOldmanId_NG",
        dataType: "json",
        success:function(data){
            if(data.success==true){
                //不能共用一个 会出错
                var $td=$('#gatewaySelect');
                var $alttd=$('#altgatewaySelect');
                var select=$('<select name="oldId"></select>');
                var altselect=$('<select name="oldId"></select>');
                for(var i=0;i<data.data.length;i++){
                    var option=$('<option value="'+data.data[i].oid+'">'+data.data[i].oldName+'('+data.data[i].oid+')'+'</option>');
                    var altoption=$('<option value="'+data.data[i].oid+'">'+data.data[i].oldName+'('+data.data[i].oid+')'+'</option>');
                    select.append(option);
                    altselect.append(altoption);
                }
                $td.append(select);
                $alttd.append(altselect);
            }
        }
    });

});

var url;//存储请求的地址
var mesTitle;//存储消息信息



//添加
function addDialog(){
    $('#dlg_addRoom').dialog('open').dialog('setTitle', '新增房间');
    $('#addRoom').form('clear');
    url = pathJs + "/room/addRoom";
}

function saveRoom(){
    $('#addRoom').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '新增成功';
                $('#dlg_addRoom').dialog('close');
                $('#datagrid').datagrid('reload');
            } else {
                mesTitle = '新增失败';
            }
            $.messager.show({
                title: mesTitle,
                msg: result.msg
            });
        }
    });
}


//删除
function del(){
    var row = $('#datagrid').datagrid('getSelected');
    if (row) {
        $('#dlg_del').dialog('open').dialog('setTitle', '删除房间信息');
        url = pathJs + "/room/deleteRoom?rid=" + row.rid+"&nerRoom="+row.nerRoom+"&oldId="+row.oldId;
        mesTitle = '删除成功';
    } else {
        $.messager.alert('提示', '请选择要删除的条目！', 'error');
    }
}

function conf_del() {
    $('#delRoom').form('submit', {
        url: url,
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                $('#dlg_del').dialog('close');
                $('#datagrid').datagrid('reload');
                mesTitle = '删除成功';
            } else {
                mesTitle = '删除失败';
            }
            $.messager.show({
                title: mesTitle,
                msg: result.msg
            });
        }
    });
}

//修改
function alt(){
    var row = $('#datagrid').datagrid('getSelected');
    if(row){
        $('#dlg_altRoom').dialog('open').dialog('setTitle', '设定区域信息');
        $("input.auto").each(function(index, element) { //为input赋值
            var name=$(this).attr("name");
            if(name=='collectId'){
                var value=eval("row."+name);
                $(this).val(parseInt(value).toString(2));
            }else {
                var value = eval("row." + name);
                $(this).val(value);
            }
        });
        var oid=row.oldId;
        $('#altgatewaySelect option').attr("selected",false);
        $('#altgatewaySelect option').each(function () {
            if($(this).val()==oid){
                $(this).attr('selected','selected');
            }
        });
        url = pathJs + "/room/editArea";
    }else {
        $.messager.alert('提示', '请选择要修改的条目！', 'error');
    }
}

function alt_save(){
    $('#altRoom').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '修改成功';
                $('#dlg_altRoom').dialog('close');
                $('#datagrid').datagrid('reload');
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

