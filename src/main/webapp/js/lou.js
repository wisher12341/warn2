


var url;//存储请求的地址
var mesTitle;//存储消息信息




//删除
function del(){
    var row = $('#datagrid').datagrid('getSelected');
    if (row) {
        var id = row.id;
        $('#dlg_del').dialog('open').dialog('setTitle', '删除');
        url = pathJs + "/map/deleteLouMarker?id=" + id;
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
        $('#dlg_altOldMan').dialog('open').dialog('setTitle', '修改');
        $("input.auto").each(function(index, element) { //为input赋值
            var name=$(this).attr("name");
            var value = eval("row." + name);
            $(this).val(value);
        });
        url = pathJs + "/map/editLouMarker";
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
