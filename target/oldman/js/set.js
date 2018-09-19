/**
 * Created by admin on 2017/5/30.
 */
$(function(){


    $("#warn").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions_w(index);
        },
        onAfterEdit:function(index,row){
            $.ajax({
                type: "POST",
                url: pathJs+"/set/setAccessBatabaseTime",
                dataType: "json",
                data:{
                    time:row.b
                },
                success:function(data){
                    if(data.success){
                        mesTitle='修改成功';
                    }else{
                        mesTitle='修改失败';
                    }
                    $.messager.show({
                        title: mesTitle,
                        msg:data.data
                    });
                }
            });
            row.editing = false;
            updateActions_w(index);
        },
        onCancelEdit:function(index,row){
            row.editing = false;
            updateActions_w(index);
        }
    });


    $("#down").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions_d(index);
        },
        onAfterEdit:function(index,row){
            $.ajax({
                type: "POST",
                url: pathJs+"/set/setDown",
                dataType: "json",
                data:{
                    name:row.a,
                    time:row.b
                },
                success:function(data){
                    if(data.success){
                        mesTitle='修改成功';
                    }else{
                        mesTitle='修改失败';
                    }
                    $.messager.show({
                        title: mesTitle,
                        msg:data.data
                    });
                }
            });
            row.editing = false;
            updateActions_d(index);
        },
        onCancelEdit:function(index,row){
            row.editing = false;
            updateActions_d(index);
        }
    });
});


function change(event){
    var e=event||window.event;
    if(e.target.value==0){
        e.target.value=1;
    }else{
        e.target.value=0;
    }
    $.ajax({
        type: "POST",
        url: pathJs + "/set/timerOpen",
        dataType: "json",
        data:{
            timerOpen:e.target.value
        },
        async:false,
        success: function (data) {
            // $('#warn').datagrid('reload');
        }
    });
}




function formatActionW(value,row,index){
    if(row.a=='预警自动开关'){
        return '';
    }
    if (row.editing){
        var s = '<input type="button" class="saveButton easyui-linkbutton" value="保存" onclick="saverow_w(this)" /> ';
        var c = '<input type="button" class="cancelButton easyui-linkbutton" value="取消" onclick="cancelrow_w(this)" />';
        return s+c;
    } else {
        var e = '<input type="button" class="editButton easyui-linkbutton" value="编辑" onclick="editrow_w(this)" /> ';
        return e;
    }
}

function updateActions_w(index){
    $('#warn').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex_w(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow_w(target){
    $('#warn').datagrid('beginEdit', getRowIndex_w(target));
}

function saverow_w(target){
    $('#warn').datagrid('endEdit', getRowIndex_w(target));
}
function cancelrow_w(target){
    $('#warn').datagrid('cancelEdit', getRowIndex_w(target));
}


$.extend($.fn.datagrid.defaults.editors, {
    numberspinner: {
        init: function(container, options){
            var input = $('<input type="text">').appendTo(container);
            return input.numberspinner(options);
        },
        destroy: function(target){
            $(target).numberspinner('destroy');
        },
        getValue: function(target){
            return $(target).numberspinner('getValue');
        },
        setValue: function(target, value){
            $(target).numberspinner('setValue',value);
        },
        resize: function(target, width){
            $(target).numberspinner('resize',width);
        }
    }
});


function formatActionDown(value,row,index){
    if (row.editing){
        var s = '<input type="button" class="saveButton easyui-linkbutton" value="保存" onclick="saverow_d(this)" /> ';
        var c = '<input type="button" class="cancelButton easyui-linkbutton" value="取消" onclick="cancelrow_d(this)" />';
        return s+c;
    } else {
        var e = '<input type="button" class="editButton easyui-linkbutton" value="编辑" onclick="editrow_d(this)" /> ';
        return e;
    }
}

function updateActions_d(index){
    $('#down').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex_d(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow_d(target){
    $('#down').datagrid('beginEdit', getRowIndex_d(target));
}

function saverow_d(target){
    $('#down').datagrid('endEdit', getRowIndex_d(target));
}
function cancelrow_d(target){
    $('#down').datagrid('cancelEdit', getRowIndex_d(target));
}
