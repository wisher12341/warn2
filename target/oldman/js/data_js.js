$(function(){
    //获得用户角色
    $.ajax({
        type: "GET",
        url: pathJs+"/getRole",
        dataType: "json",
        success:function(data){
            if(data.data.name !="前台人员"){
                $(".admin").show();
            }
        }
    });

});


var url;//存储请求的地址
var mesTitle;//存储消息信息

//添加
function addDialog(){
    $("input[name=gatewayTwo_Ten]:first").prop( "checked", true);
    $('#dlg_addOldMan').dialog('open').dialog('setTitle', '新增老人');
    $('#addOldMan').form('clear');
    $('input[name="segmentTwo_Ten"]:first-child').prop('checked',true);
    $('input[name="gatewayTwo_Ten"]:first-child').prop('checked',true);

    url = pathJs + "/data/addOldman";
}

function saveOldMan(){
    $('#addOldMan').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '新增成功';
                $('#dlg_addOldMan').dialog('close');
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
        var oid = row.oid;
        $('#dlg_del').dialog('open').dialog('setTitle', '删除老人信息');
        url = pathJs + "/data/deleteOldman?oldmanId=" + oid;
        mesTitle = '删除成功';
    } else {
        $.messager.alert('提示', '请选择要删除的条目！', 'error');
    }
}

function conf_del() {
    $('#delOldMan').form('submit', {
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
        $('#dlg_altOldMan').dialog('open').dialog('setTitle', '修改老人信息');
        $("input.auto").each(function(index, element) { //为input赋值
            var name=$(this).attr("name");
            if(name=='segment'){
                var v1=eval("row."+name);
                if(!v1){
                    $(this).val("");
                }else {
                    var v2 = parseInt(v1).toString(2);
                    var v3;
                    switch (v2.length) {
                        case 1:
                            v3 = "000" + v2;
                            break;
                        case 2:
                            v3 = "00" + v2;
                            break;
                        case 3:
                            v3 = "0" + v2;
                            break;
                        default:
                            v3 = v2;
                    }
                    $(this).val(v3);
                }
            }else {
                var value = eval("row." + name);
                $(this).val(value);
            }
        });
        $('input[name="segmentTwo_TenE"]:first-child').prop('checked',true);
        // $('input[name="gatewayTwo_TenE"]:first-child').prop('checked',true);
        url = pathJs + "/data/editOldman";
    }else {
        $.messager.alert('提示', '请选择要修改的条目！', 'error');
    }
}

function alt_save(){
    $('#altOldMan').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '修改成功';
                $('#dlg_altOldMan').dialog('close');
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


function formatActionRoom() {
    return '<input type="button" class="jump" onclick="rooms(event)" value="房间"/> ';
}


function formatActionEquip() {
    return '<input type="button" class="jump" onclick="equips(event)" value="设备"/> ';
}

function rooms(event) {
    var e=event||window.event;
    var oid= e.target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.innerText;


    //父对象的jquery
    var jq=top.jQuery;
    // var tabs = $('#mainTabs',parent.document); //此做法 没用
    var src = pathJs+"/room/getOldManRooms?oid="+oid+"&type=back";
    var opts = {
        title: "房间管理",
        closable: true,
        content: $.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', src),
        border: false,
        fit: true
    };
    if (jq("#mainTabs").tabs('exists', opts.title)) {
        jq("#mainTabs").tabs('close',"房间管理");
        // jq("#mainTabs").tabs('select', opts.title);
        jq("#mainTabs").tabs('add', opts);
    } else {
        jq("#mainTabs").tabs('add', opts);
    }

}

function equips(event) {
    var e=event||window.event;
    var oid= e.target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.previousSibling.innerText;
    var jq=top.jQuery;
    // var tabs = $('#mainTabs',parent.document);
    var src = pathJs+"/equip/getOldManEquips?oid="+oid+"&type=back";
    var opts = {
        title: "设备管理",
        closable: true,
        content: $.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', src),
        border: false,
        fit: true
    };
    if (jq("#mainTabs").tabs('exists', opts.title)) {
        jq("#mainTabs").tabs('close',"设备管理");
        // jq("#mainTabs").tabs('select', opts.title);
        jq("#mainTabs").tabs('add', opts);
    } else {
        jq("#mainTabs").tabs('add', opts);
    }
}


$.formatString = function(str) {
    for ( var i = 0; i < arguments.length - 1; i++) {
        str = str.replace("{" + i + "}", arguments[i + 1]);
    }
    return str;
};


