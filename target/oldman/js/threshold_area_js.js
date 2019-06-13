/**
 * Created by admin on 2017/4/12.
 */
var mesTitle;//消息提示
var room_id;
$(function(){
    $("#datagrid").datagrid({
        onClickRow: function () {
            var row = $('#datagrid').datagrid('getSelected');
            room_id = row.rid;
            $('#tt').datagrid('load',{rid:room_id});
           // alert(room_id);
            $('#tt').datagrid('reload');
            $('#light').datagrid('load',{oid:row.oldId});
            $('#light').datagrid('reload');
            $('#wendu').datagrid('load',{oid:row.oldId});
            $('#wendu').datagrid('reload');
            $('#door').datagrid('load',{oid:row.oldId});
            $('#door').datagrid('reload');
        }
    });
    //行为
    $("#tt").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions(index);
        },
        onAfterEdit:function(index,row){
            //修改阈值信息
            $.ajax({
                type: "POST",
                url: pathJs+"/threshold/updateThresholdArea",
                dataType: "json",
                data:{
                    id:row.id,
                    area:row.area,
                    a1Threshold:row.a1Threshold,
                    r1Threshold:row.r1Threshold,
                    a2Threshold:row.a2Threshold,
                    r2Threshold:row.r2Threshold,
                    n1Threshold:row.n1Threshold,
                    n2Threshold:row.n2Threshold,
                    door:row.door
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
    //温度
    $("#wendu").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions_w(index);
        },
        onAfterEdit:function(index,row){
            //修改阈值信息
            $.ajax({
                type: "POST",
                url: pathJs+"/threshold/updateThreshold_w",
                dataType: "json",
                data:{
                    wid:row.wid,
                    wThreshold:row.wThreshold
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

    //光强
    $("#light").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions_l(index);
        },
        onAfterEdit:function(index,row){
            //修改阈值信息
            $.ajax({
                type: "POST",
                url: pathJs+"/threshold/updateThreshold_l",
                dataType: "json",
                data:{
                    lid:row.lid,
                    lThreshold:row.lThreshold,
                    times:row.times,
                    continueTime:row.continueTime
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
            updateActions_l(index);
        },
        onCancelEdit:function(index,row){
            row.editing = false;
            updateActions_l(index);
        }
    });


    //出门
    $("#door").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions_d(index);
        },
        onAfterEdit:function(index,row){
            //修改阈值信息
            $.ajax({
                type: "POST",
                url: pathJs+"/threshold/updateThreshold_d",
                dataType: "json",
                data:{
                    outId:row.outId,
                    outThreshold:row.outThreshold,
                    noComeThreshold:row.noComeThreshold
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

function fixHtmlWidth(percent){
    return $("#tt").width()*percent;
}

//行内编辑器
function updateActions(index){
    $('#tt').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow(target){
    $('#tt').datagrid('beginEdit', getRowIndex(target));
}

function saverow(target){
    $('#tt').datagrid('endEdit', getRowIndex(target));
}
function cancelrow(target){
    $('#tt').datagrid('cancelEdit', getRowIndex(target));
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




//行内编辑器
function updateActions_w(index){
    $('#wendu').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex_w(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow_w(target){
    $('#wendu').datagrid('beginEdit', getRowIndex_w(target));
}

function saverow_w(target){
    $('#wendu').datagrid('endEdit', getRowIndex_w(target));
}
function cancelrow_w(target){
    $('#wendu').datagrid('cancelEdit', getRowIndex_w(target));
}

function formatAction_w(value,row,index){
    if (row.editing){
        var s = '<input type="button" class="saveButton easyui-linkbutton" value="保存" onclick="saverow_w(this)" /> ';
        var c = '<input type="button" class="cancelButton easyui-linkbutton" value="取消" onclick="cancelrow_w(this)" />';
        return s+c;
    } else {
        var e = '<input type="button" class="editButton easyui-linkbutton" value="编辑" onclick="editrow_w(this)" /> ';
        return e;
    }
}



//行内编辑器
function updateActions_l(index){
    $('#light').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex_l(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow_l(target){
    $('#light').datagrid('beginEdit', getRowIndex_l(target));
}

function saverow_l(target){
    $('#light').datagrid('endEdit', getRowIndex_l(target));
}
function cancelrow_l(target){
    $('#light').datagrid('cancelEdit', getRowIndex_l(target));
}

function formatAction_l(value,row,index){
    if (row.editing){
        var s = '<input type="button" class="saveButton easyui-linkbutton" value="保存" onclick="saverow_l(this)" /> ';
        var c = '<input type="button" class="cancelButton easyui-linkbutton" value="取消" onclick="cancelrow_l(this)" />';
        return s+c;
    } else {
        var e = '<input type="button" class="editButton easyui-linkbutton" value="编辑" onclick="editrow_l(this)" /> ';
        return e;
    }
}


//行内编辑器
function updateActions_d(index){
    $('#door').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex_d(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow_d(target){
    $('#door').datagrid('beginEdit', getRowIndex_d(target));
}

function saverow_d(target){
    $('#door').datagrid('endEdit', getRowIndex_d(target));
}
function cancelrow_d(target){
    $('#door').datagrid('cancelEdit', getRowIndex_d(target));
}

function formatAction_d(value,row,index){
    if (row.editing){
        var s = '<input type="button" class="saveButton easyui-linkbutton" value="保存" onclick="saverow_d(this)" /> ';
        var c = '<input type="button" class="cancelButton easyui-linkbutton" value="取消" onclick="cancelrow_d(this)" />';
        return s+c;
    } else {
        var e = '<input type="button" class="editButton easyui-linkbutton" value="编辑" onclick="editrow_d(this)" /> ';
        return e;
    }
}
