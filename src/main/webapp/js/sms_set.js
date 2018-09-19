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
                url: pathJs+"/sms/editSmsSendEntity",
                dataType: "json",
                data:{
                    id:row.id,
                    phone:row.phone,
                    orderSms:row.orderSms,
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

    $("#order").datagrid({
        onBeforeEdit:function(index,row){
            row.editing = true;
            updateActions_w(index);
        },
        onAfterEdit:function(index,row){
            $.ajax({
                type: "POST",
                url: pathJs+"/sms/editSmsOrder",
                dataType: "json",
                data:{
                    id:row.id,
                    orderSms:row.orderSms,
                    timeSms:row.timeSms
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
        url: pathJs + "/sms/smsSysSwitch",
        dataType: "json",
        data:{
            openSys:e.target.value
        },
        async:false,
        success: function (data) {
            // $('#warn').datagrid('reload');
        }
    });
}


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


//删除
function delPhone(target){
    var id = target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.innerText;
    $('#dlg_del').dialog('open').dialog('setTitle', '删除手机信息');
    url = pathJs + "/sms/deleteSmsSendEntity?id=" + id;
    mesTitle = '删除成功';
}

function conf_del(target) {

    $('#delPhone').form('submit', {
        url: url,
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                $('#dlg_del').dialog('close');
                $('#sms').datagrid('reload');
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


function formatActionW(value,row,index){
    if (row.editing){
        var s = '<input type="button" class="saveButton easyui-linkbutton" value="保存" onclick="saverow_w(this)" /> ';
        var c = '<input type="button" class="cancelButton easyui-linkbutton" value="取消" onclick="cancelrow_w(this)" />';
        return s+c;
    } else {
        var e = '<input type="button" class="editButton easyui-linkbutton" value="编辑" onclick="editrow_w(this)" /> ';
        var d = '<input type="button" class="deleteButton easyui-linkbutton" value="删除" onclick="delOrder(this)" />';
        return e+d;
    }
}

function updateActions_w(index){
    $('#order').datagrid('updateRow',{
        index:index,
        row:{}
    });
}
function getRowIndex_w(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}
function editrow_w(target){
    $('#order').datagrid('beginEdit', getRowIndex_w(target));
}

function saverow_w(target){
    $('#order').datagrid('endEdit', getRowIndex_w(target));
}
function cancelrow_w(target){
    $('#order').datagrid('cancelEdit', getRowIndex_w(target));
}

//删除
function delOrder(target){
    var id = target.parentNode.parentNode.previousSibling.previousSibling.innerText;
    $('#dlg_del').dialog('open').dialog('setTitle', '删除手机信息');
    url = pathJs + "/sms/deleteSmsOrder?id=" + id;
    mesTitle = '删除成功';
}

function conf_del2(target) {

    $('#order').datagrid('deleteEdit', getRowIndex(target));

    $('#delPhone').form('submit', {
        url: url,
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                $('#dlg_del').dialog('close');
                $('#sms').datagrid('reload');
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


function fixWidth(percent){
    // alert(document.body.clientWidth);
    // alert(percent);
    // alert(document.body.clientWidth * percent);
    return document.body.clientWidth * percent ;
}

//查询
function formSearch(){
    var searchForm = $('#search').form();
    $('#sms').datagrid('load', serializeObject(searchForm));
    // $('#search').form('clear');
}
// 表格序列化
function serializeObject(form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    })
    return o;
}


var url;//存储请求的地址
var mesTitle;//存储消息信息

//添加手机
function addPhone(){
    $('#dlg_addPhone').dialog('open').dialog('setTitle', '新增手机');
    $('#addPhone').form('clear');
    url = pathJs + "/sms/addSmsSendEntity";
}

function savePhone(){
    $('#addPhone').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '新增成功';
                $('#dlg_addPhone').dialog('close');
                $('#sms').datagrid('reload');
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

function refreshPhone(){
    $('#search').form('clear');
    var searchForm = $('#search').form();
    $('#sms').datagrid('load',serializeObject(searchForm));
    $('#sms').datagrid('reload');
}



//添加顺序
function addOrder(){
    $('#dlg_addOrder').dialog('open').dialog('setTitle', '新增顺序');
    $('#addOrder').form('clear');
    url = pathJs + "/sms/addSmsOrder";
}

function saveOrder(){
    $('#addOrder').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '新增成功';
                $('#dlg_addOrder').dialog('close');
                $('#order').datagrid('reload');
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

function refreshOrder(){
    $('#order').datagrid('load');
    $('#order').datagrid('reload');
}