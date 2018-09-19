/**
 * Created by admin on 2017/5/30.
 */
$(function(){
    $("#sms").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions(index);
        },
        onAfterEdit:function(index,row){
            $.ajax({
                type: "POST",
                url: pathJs+"/sms/editTemplate",
                dataType: "json",
                data:{
                    name:row.a,
                    data:row.b
                    // orderSms:row.orderSms,
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
            updateActions(index);
        },
        onCancelEdit:function(index,row){
            row.editing = false;
            updateActions(index);
        }
    });


});


function formatAction(value,row,index){
    if (row.editing){
        var s = '<input type="button" class="saveButton easyui-linkbutton" value="保存" onclick="saverow(this)" /> ';
        var c = '<input type="button" class="cancelButton easyui-linkbutton" value="取消" onclick="cancelrow(this)" />';
        return s+c;
    } else {
        var e = '<input type="button" class="editButton easyui-linkbutton" value="编辑" onclick="editrow(this)" /> ';
        var d = '<input type="button" class="deleteButton easyui-linkbutton" value="删除" onclick="delPhone(this)" />';
        return e+d;
    }
}

function updateActions(index){
    $('#sms').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow(target){
    $('#sms').datagrid('beginEdit', getRowIndex(target));
}

function saverow(target){
    $('#sms').datagrid('endEdit', getRowIndex(target));
}
function cancelrow(target){
    $('#sms').datagrid('cancelEdit', getRowIndex(target));
}


