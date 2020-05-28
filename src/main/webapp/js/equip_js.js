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
        url: pathJs+"/data/getAllOldmanID_Name",
        dataType: "json",
        async:false,
        success:function(data){
            if(data.success==true){
                //不能共用一个 会出错
                var $td=$('#gatewaySelect');
                // var $alttd=$('#altgatewaySelect');
                var select=$('<select name="oldId" onchange="selectChange(event)"></select>');
                // var altselect=$('<select name="oldId" onchange="selectChange(event)"></select>');
                select.append($("<option selected='selected'>--请选择--</option>"));
                // altselect.append($("<option selected='selected'>--请选择--</option>"));
                for(var i=0;i<data.data.length;i++){
                    var option=$('<option value="'+data.data[i].oid+'">'+data.data[i].oldName+'('+data.data[i].oid+')'+'</option>');
                    // var altoption=$('<option value="'+data.data[i].oid+'">'+data.data[i].oldName+'('+data.data[i].oid+')'+'</option>');
                    select.append(option);
                    // altselect.append(altoption);
                }
                $td.append(select);
                // $alttd.append(altselect);
            }
        }
    });
});

var url;//存储请求的地址
var mesTitle;//存储消息信息

//网关ID change时间
function selectChange(event){
    var e=event||window.event;
    var gatewayID=e.target.options[e.target.selectedIndex].value;
    // alert(gatewayID);
    //获得该老人所有的房间
    $.ajax({
        type: "POST",
        url: pathJs+"/room/allRoom",
        data:{
            oldId:gatewayID
        },
        async:false,
        dataType: "json",
        success:function(data){
            if(data.success==true){
                //不能共用一个 会出错
                var $td=$('#roomSelect');
                var $alttd=$('#altroomSelect');
                $td.html("");
                $alttd.html("");
                var select=$('<select name="roomId"></select>');
                var altselect=$('<select name="roomId"></select>');
                select.append($("<option selected='selected'>--请选择--</option>"));
                altselect.append($("<option selected='selected'>--请选择--</option>"));
                for(var i=0;i<data.data.length;i++){
                    var option=$('<option value="'+data.data[i].rid+'">'+data.data[i].roomName+'('+data.data[i].rid+')'+'</option>');
                    var altoption=$('<option value="'+data.data[i].rid+'">'+data.data[i].roomName+'('+data.data[i].rid+')'+'</option>');
                    select.append(option);
                    altselect.append(altoption);
                }
                $td.append(select);
                $alttd.append(altselect);
            }
        }
    });
}

//添加
function addDialog(){
    $('#roomSelect').html("");
    $('#dlg_addEquip').dialog('open').dialog('setTitle', '新增设备');
    $('#addEquip').form('clear');
    $('input[name="segmentTwo_Ten"]:first-child').prop('checked',true);
    $('input[name="gatewayTwo_Ten"]:first-child').prop('checked',true);
    url = pathJs + "/equip/addEquip";
}

function saveEquip(){
    var eid  = document.getElementById("equipmentId").value;
    alert(eid);
    if (eid == "") {
        swal({
            text: "设备ID不能为空！",
            icon: "error"
        })
    }else{
        eid = parseInt(eid);
        if(!isNaN(eid)){
            if(eid < 0){
                swal({
                    text: "设备ID输入格式有误！",
                    icon: "error"
                })
            }else{
                $('#addEquip').form('submit', {
                    url: url,
                    onSubmit: function () {
                        return $(this).form();
                    },
                    success: function (result) {
                        var result = eval('(' + result + ')');
                        if (result.success) {
                            mesTitle = '新增成功';
                            $('#dlg_addEquip').dialog('close');
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
        }else{
            swal({
                text: "设备ID输入格式有误！",
                icon: "error"
            })
        }
    }

}

//删除
function del(){
    var row = $('#datagrid').datagrid('getSelected');
    if (row) {
        $('#dlg_del').dialog('open').dialog('setTitle', '删除设备信息');
        url = pathJs + "/equip/deleteEquip?eid=" + row.eid+"&oid="+row.oldId;
        mesTitle = '删除成功';
    } else {
        $.messager.alert('提示', '请选择要删除的条目！', 'error');
    }
}

function conf_del() {
    $('#delEquip').form('submit', {
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
        var $alttd=$('#altroomSelect');
        $alttd.html("");
            $.ajax({
                type: "POST",
                url: pathJs+"/room/allRoom",
                data:{
                    oldId:row.oldId
                },
                async:false,
                dataType: "json",
                success:function(data){
                    if(data.success==true){
                        var altselect=$('<select name="roomId"></select>');
                        altselect.append($("<option selected='selected'>--请选择--</option>"));
                        for(var i=0;i<data.data.length;i++){
                            var altoption=$('<option value="'+data.data[i].rid+'">'+data.data[i].roomName+'('+data.data[i].rid+')'+'</option>');
                            altselect.append(altoption);
                        }
                        $alttd.append(altselect);
                    }
                }
            });

        var $altgatetd=$('#altgatewaySelect');
        $altgatetd.html("");
        //获得所有老人的网关ID
        $.ajax({
            type: "GET",
            url: pathJs+"/data/getAllOldmanID_Name",
            dataType: "json",
            success:function(data){
                if(data.success==true){
                    var $alttd=$('#altgatewaySelect');
                    var altselect=$('<select name="oldId" onchange="selectChange(event)"></select>');
                    if(row.oldId==0) {
                        altselect.append($("<option selected='selected'>--请选择--</option>"));
                    }else{
                        altselect.append($("<option>--请选择--</option>"));
                    }
                    for(var i=0;i<data.data.length;i++){
                        var altoption;
                        if(data.data[i].oid==row.oldId){
                            altoption=$('<option value="'+data.data[i].oid+'" selected="selected">'+data.data[i].oldName+'('+data.data[i].oid+')'+'</option>');
                        }else {
                            altoption = $('<option value="' + data.data[i].oid + '">' + data.data[i].oldName + '(' + data.data[i].oid + ')' + '</option>');
                        }
                        altselect.append(altoption);
                    }
                    $alttd.append(altselect);
                }
            }
        });


        $('#dlg_altEquip').dialog('open').dialog('setTitle', '修改设备信息');
        $("input.auto").each(function(index, element) { //为input赋值
            var name=$(this).attr("name");
            var value=eval("row."+name);
            $(this).val(value);
        });
        $("#preOid").val(row.oldId);

        // var oid=row.oldId;
        var rid=row.roomId;


        // $('#altgatewaySelect option').attr("selected",false);
        $('#altroomSelect option').attr("selected",false);


        // if(oid!=0){
        //     $('#altgatewaySelect option').each(function () {
        //         if($(this).val()==oid){
        //             $(this).attr('selected','selected');
        //         }
        //     });
        // }


        if(rid!=0){
            $('#altroomSelect option').each(function () {
                if($(this).val()==rid){
                    $(this).attr('selected','selected');
                }
            });
        }

        url = pathJs + "/equip/editEquip";
    }else {
        $.messager.alert('提示', '请选择要修改的条目！', 'error');
    }
}

function alt_save(){
    $('#altEquip').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '修改成功';
                $('#dlg_altEquip').dialog('close');
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
