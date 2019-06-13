var room_id;
$(function(){
    $("#datagrid").datagrid({
        onClickRow: function () {
            var row = $('#datagrid').datagrid('getSelected');
            room_id = row.rid;
            $('#statistic').datagrid('load',{rid:room_id});
            // $('#statistic').datagrid('reload');
        }
    });
    $("#statistic").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions(index);
        },
        onAfterEdit:function(index,row){
            //修改阈值信息
            $.ajax({
                type: "POST",
                url: pathJs+"/threshold/updateThresholdStatistic",
                dataType: "json",
                data:{
                    id:row.id,
                    sThreshold:row.sThreshold
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

function updateActions(index){
    $('#statistic').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow(target){
    $('#statistic').datagrid('beginEdit', getRowIndex(target));
}

function saverow(target){
    $('#statistic').datagrid('endEdit', getRowIndex(target));
}
function cancelrow(target){
    $('#statistic').datagrid('cancelEdit', getRowIndex(target));
}

function formatAction(value,row,index){
    if (row.editing){
        var s = '<input type="button" class="saveButton easyui-linkbutton" value="保存" onclick="saverow(this)" /> ';
        var c = '<input type="button" class="cancelButton easyui-linkbutton" value="取消" onclick="cancelrow(this)" />';
        return s+c;
    } else {
        var e = '<input type="button" class="editButton easyui-linkbutton" value="编辑" onclick="editrow(this)" /> ';
        return e;
    }
}
function fixHtmlWidth(percent){
    return $("#statistic").width()*percent;
}