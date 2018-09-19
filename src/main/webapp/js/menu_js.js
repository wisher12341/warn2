$(function(){

});


function formatAction(value,row,index) {
    var name=row.menu.name;
    if(row.id=="601"||row.id=="1101"||row.id=="1102"||row.id=="1105"){
        name+="（后台）";
    }
    if(row.id=="602"||row.id=="1103"||row.id=="1104"||row.id=="1106"){
        name+="（前台）";
    }
    if(row.menu.parentid==0) {
        return '<span class="parent functionName" name="'+row.menu.id+'" pid="'+row.menu.parentid+'" id="'+row.id+'">'+name+'</span>';
    }else {
        return '<span class="children functionName" name="'+row.menu.id+'" pid="'+row.menu.parentid+'" id="'+row.id+'"">'+name+'</span>';
    }
}



function recover() {
    $.ajax({
        type: "POST",
        url: pathJs + "/authority/recoverAuthority",
        dataType: "json",
        success: function (data) {
            refresh();
        }
    });
}


var url;//存储请求的地址
var mesTitle;//存储消息信息

//添加
function addDialog(){
    $('#dlg_addMenu').dialog('open').dialog('setTitle', '新增菜单');
    $('#addMenu').form('clear');
    url = pathJs + "/menu/addMenu";
}

function saveMenu(){
    $('#addMenu').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '新增成功';
                $('#dlg_addMenu').dialog('close');
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
        var id = row.id;
        $('#dlg_del').dialog('open').dialog('setTitle', '删除菜单信息');
        url = pathJs + "/menu/deleteMenu?id=" + id;
        mesTitle = '删除成功';
    } else {
        $.messager.alert('提示', '请选择要删除的条目！', 'error');
    }
}

function conf_del() {
    $('#delMenu').form('submit', {
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
        $('#dlg_altMenu').dialog('open').dialog('setTitle', '修改菜单信息');
        $("input.auto").each(function(index, element) { //为input赋值
            var name=$(this).attr("name");
            var value;
            if(name=='id') {
                value= eval("row." + name);
            }else{
                value= eval("row.menu." + name);
            }
            $(this).val(value);
        });
        $('#preId').val(row.id);
        url = pathJs + "/menu/editMenu";
    }else {
        $.messager.alert('提示', '请选择要修改的条目！', 'error');
    }
}

function alt_save(){
    $('#altMenu').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '修改成功';
                $('#dlg_altMenu').dialog('close');
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
