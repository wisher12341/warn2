/**
 * Created by admin on 2017/4/12.
 */
var mesTitle;//消息提示
var rooms=[];//某老人的所有房间
var rids=[];//对应rooms
var m=1;//计数
var oidId;
$(function(){
    $("#datagrid").datagrid({
        onClickRow: function () {
            //数组清空
            rooms=[];
            rids=[];
            m=1;
            //添加表单还原
            //$('.addModel').empty();
            $('.tableModel').html('<tr> <td><span class="addButton">时间段：</span></td> <td><input name="times[0]" class="easyui-textbox" type="text"></td> </tr> <tr> <td><span class="addButton">活动房间：</span></td> <td class="roomCheckbox"> </td> </tr> ');

            //$(".alt").hide();
            //$(".add").hide();
            //if($('td').size()>1){
            //    $(".alt").show();
            //}else{
            //    $(".add").show()
            //}

            var row = $('#datagrid').datagrid('getSelected');
            $('#tt').datagrid('load',{oid:row.oldId});
            $('#tt').datagrid('reload');
            $('#oldman').datagrid('load',{oid:row.oldId});
            $('#oldman').datagrid('reload');

            oldId=row.oldId;
            roomId = row.rid;
            //alert($(".oid").attr('name'));
            //alert($(".oid").val());

            //获得该老人所有的房间 做复选框
            $.ajax({
                type: "POST",
                url: pathJs+"/room/allArea",
                dataType: "json",
                data:{
                    rid:row.rid
                },
                success:function(data){
                    if (data.data.length > 0) {
                    for(var i=0;i<data.data.length;i++) {
                            rooms.push(data.data[i].info);
                            rids.push(data.data[i].num);
                            var checkbox = $('<span><input type="checkbox" onclick="selectBox()" name="timeRooms[0]"  value="' + data.data[i].num + '"/><span class="addButton">' + data.data[i].info + '</span></span>');
                            $(".roomCheckbox").append(checkbox);

                    }
                        rooms.push("户外");
                        rids.push(0);
                        var checkbox=$('<span><input type="checkbox" onclick="selectBox()" name="timeRooms[0]" value="0"/><span class="addButton">户外</span></span>');
                        $(".roomCheckbox").append(checkbox);
                    }
                }
            });
            //设置点击事件
            //selectBox();
        }
    });


    //$("#tt").datagrid({
    //    onBeforeEdit:function(index,row){
    //        row.editing = true;
    //        updateActions(index);
    //    },
    //    onAfterEdit:function(index,row){
    //        //修改阈值信息
    //        $.ajax({
    //            type: "POST",
    //            url: pathJs+"/model/updateRoomModel",
    //            dataType: "json",
    //            data:{
    //                rmid:row.rmid,
    //                active:row.active,
    //                rest:row.rest
    //            },
    //            success:function(data){
    //                if(data.success){
    //                    mesTitle='修改成功';
    //                }else{
    //                    mesTitle='修改失败';
    //                }
    //                $.messager.show({
    //                    title: mesTitle,
    //                    msg:data.data
    //                });
    //            }
    //        });
    //        row.editing = false;
    //        updateActions(index);
    //    },
    //    onCancelEdit:function(index,row){
    //        row.editing = false;
    //        updateActions(index);
    //    }
    //});
});





//添加
function addDialog(){
    $('#dlg_addModel').dialog('open').dialog('setTitle', '新增生活区域规律模型');
    $('#addModel').form('clear');
    //老人Id 要在clear之后设置
    $('.oid').val(roomId);
    url = pathJs + "/model/addRoomModel";
    alert(roomId);
}

function saveModel(){
    $("input:checkbox:checked").each(function(){
        //连续点多次保存 会导致 value追加多个$  所以要先进行删除
        if($(this).val().indexOf("$")!=-1){
            $(this).val($(this).val().substring(0,$(this).val().indexOf("$")));
        }

        if($(this).val()!=0) {
            $(this).val($(this).val() +"\$"+ $(this).next().find("option:selected").val());
        }
       // alert($(this).val());
    });
    $('#addModel').form('submit', {
        url: url,
        onSubmit: function () {
            return $(this).form();
        },
        success: function (result) {
            var result = eval('(' + result + ')');
            if (result.success) {
                mesTitle = '新增成功';
                //$(".alt").show();
                //$(".add").hide();
                $('#dlg_addModel').dialog('close');
                $('#oldman').datagrid('load',{oid:oldId});
                $('#oldman').datagrid('reload');
                $('#tt').datagrid('load',{oid:oldId});
                $('#tt').datagrid('reload');
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
function addModel(){
    var $tr1=$('<tr></tr>');
    var $tr2=$('<tr></tr>');
    var tdTime=$('<td>时间段</td>');
    var tdTime2=$('<td><input name="times['+m+']" class="easyui-textbox" type="text" /></td>');
    var tdRooms=$('<td>活动区域</td>');
    var tdRooms2=$('<td class="roomCheckbox"></td>');
    for(var i=0;i<rooms.length;i++){
        var checkbox=$('<span><input type="checkbox" onclick="selectBox()" name="timeRooms['+m+']" value="'+rids[i]+'"/>'+rooms[i]+'</span>');
        tdRooms2.append(checkbox);
    }
    $tr1.append(tdTime);
    $tr1.append(tdTime2);
    $(".tableModel").append($tr1);
    $tr2.append(tdRooms);
    $tr2.append(tdRooms2);
    $(".tableModel").append($tr2);
    m++;
    //添加新节点后 再出发添加点击事件
    //selectBox();
}

//修改
//function alt(){
//    var row = $('#datagrid').datagrid('getSelected');
//    if(row){
//        $('#dlg_altOldMan').dialog('open').dialog('setTitle', '修改老人信息');
//        $("input.auto").each(function(index, element) { //为input赋值
//            var name=$(this).attr("name");
//            var value=eval("row."+name);
//            $(this).val(value);
//        });
//        url = pathJs + "/data/editOldman";
//    }else {
//        $.messager.alert('提示', '请选择要修改的条目！', 'error');
//    }
////}
//
////function alt_save(){
////    $('#altOldMan').form('submit', {
////        url: url,
////        onSubmit: function () {
////            return $(this).form();
////        },
////        success: function (result) {
////            var result = eval('(' + result + ')');
////            if (result.success) {
////                mesTitle = '修改成功';
////                $('#dlg_altOldMan').dialog('close');
////                $('#datagrid').datagrid('reload');
////            } else {
////                mesTitle = '修改失败';
////            }
////            $.messager.show({
////                title: mesTitle,
////                msg: result.msg
////            });
////        }
////    });
////}
//
//

function fixHtmlWidth(percent){
    return $("#tt").width()*percent;
}


function selectBox(){
    var select=$('<select class="ridSelect" name="ridType"><option value ="1">活动</option><option value ="2">休息</option> </select>');
    $('input:checkbox').each(function(){
        if(this.checked==false&&$(this).next().attr('name')=="ridType"){
            $(this).next().hide();
        }
    });
    $("input:checkbox:checked").each(function(){
        if($(this).val()==0){

        }else if($(this).next().attr('name')=="ridType"&&$(this).next().is(":visible")==false) {
            $(this).next().show();
        }else if($(this).next().attr('name')=="ridType"&&$(this).next().is(":visible")==true) {

        }else{
            $(this).after(select);
        }
    });

    //var e=event||window.event;
    //e.target.value='100';
    //if(e.target.class!='select'){
    //    e.target.class="select";
    //    if(e.target.value!=0) {
    //        var select=$('<select class="ridSelect" name="ridType"><option value ="1">活动</option><option value ="2">休息</option> </select>');
    //        //e.target
    //    }else{
    //        $(this).after('<input class="ridSelect" name="ridType" value="0" style="display: none"/>');
    //    }
    //}else{
    //    e.target.class="";
    //}
}



//行内编辑器
//function updateActions(index){
//    $('#tt').datagrid('updateRow',{
//        index:index,
//        row:{}
//    });
//}
//function getRowIndex(target){
//    var tr = $(target).closest('tr.datagrid-row');
//    return parseInt(tr.attr('datagrid-row-index'));
//}
//function editrow(target){
//    $('#tt').datagrid('beginEdit', getRowIndex(target));
//}
//
//function saverow(target){
//    $('#tt').datagrid('endEdit', getRowIndex(target));
//}
//function cancelrow(target){
//    $('#tt').datagrid('cancelEdit', getRowIndex(target));
//}
//
//function formatAction(value,row,index){
//    if (row.editing){
//        var s = '<a href="#" onclick="saverow(this)">保存</a> ';
//        var c = '<a href="#" onclick="cancelrow(this)">取消</a>';
//        return s+c;
//    } else {
//        var e = '<a href="#" onclick="editrow(this)">编辑</a> ';
//        return e;
//    }
//}





