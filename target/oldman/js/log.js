
var url;//存储请求的地址
var mesTitle;//存储消息信息



//添加
function addDialog(){
    $('#dlg_addLog').dialog('open').dialog('setTitle', '新增日志');
    $('#addLog').form('clear');
    url = pathJs + "/log/addLog";
}

function saveLog(){
    $('#addLog').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '新增成功';
                $('#dlg_addLog').dialog('close');
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
        $('#dlg_del').dialog('open').dialog('setTitle', '删除日志信息');
        url = pathJs + "/log/deleteLog?logId=" + row.logId;
        mesTitle = '删除成功';
    } else {
        $.messager.alert('提示', '请选择要删除的条目！', 'error');
    }
}

function conf_del() {
    $('#delLog').form('submit', {
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
        $('#dlg_altLog').dialog('open').dialog('setTitle', '修改日志信息');
        var name=$("#altInput").attr("name");
        var value=eval("row."+name);
        $("#altInput").val(value);
        url = pathJs + "/log/editLog";
    }else {
        $.messager.alert('提示', '请选择要修改的条目！', 'error');
    }
}

function alt_save(){
    $('#altLog').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '修改成功';
                $('#dlg_altLog').dialog('close');
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