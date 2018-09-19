/**
 * Created by netlab606 on 2017/9/4.
 */
function formatAction(value,row,index){
    var oid=row.oid;
    var name=row.oldName;
    var qid=row.quMarker.id;
    var jid=row.jieDaoMarker.id;
    var lid=row.louMarker.id;
    var s = '<input type="button" class="saveButton easyui-linkbutton" value="设置" onclick=setMap('+oid+',"'+name+'",'+qid+','+jid+','+lid+') /> ';
    return s;
}

function setMap(oid,oldName,qid,jid,lid) {
    $('#dlg_altOldMan').dialog('open').dialog('setTitle', '绑定地图信息');
    $("#oid").html(oid);
    $(".oid").val(oid);
    $("#name").html(oldName);
    //获得所有区
    $.ajax({
        type: "GET",
        url: pathJs+"/map/getQuMarkers",
        dataType: "json",
        async:false,
        success:function(data){
            if(data.success==true){
                var $select=$("#quMarker");
                $select.html("<option></option>");
                for(var i=0;i<data.data.length;i++){
                    var option;
                    if(qid==data.data[i].id){
                        option=$("<option value='"+data.data[i].id+"' selected>"+data.data[i].qName+"</option>");
                    }else{
                        option=$("<option value='"+data.data[i].id+"'>"+data.data[i].qName+"</option>");
                    }
                    $select.append(option);
                }
                quInit(qid,jid,lid);
            }
        }
    });
}

function quInit(qid,jid,lid) {
    //获得某个区的所有街道
    $.ajax({
        type: "GET",
        url: pathJs+"/map/"+qid+"/getJieDaoMarkers",
        dataType: "json",
        success:function(data){
            if(data.success==true){
                var $select=$("#jieDaoMarker");
                $select.html("<option></option>");
                for(var i=0;i<data.data.length;i++){
                    var option;
                    if(jid==data.data[i].id){
                        option=$("<option value='"+data.data[i].id+"' selected>"+data.data[i].jName+"</option>");
                    }else{
                        option=$("<option value='"+data.data[i].id+"'>"+data.data[i].jName+"</option>");
                    }
                    $select.append(option);
                }
                jieDaoInit(jid,lid);
            }
        }
    });
}
function jieDaoInit(jid,lid) {
    //获得某个街道的所有楼
    $.ajax({
        type: "GET",
        url: pathJs+"/map/"+jid+"/getLouMarkers",
        dataType: "json",
        success:function(data){
            if(data.success==true){
                var $select=$("#louMarker");
                $select.html("<option></option>");
                for(var i=0;i<data.data.length;i++){
                    var option;
                    if(lid==data.data[i].id){
                        option=$("<option value='"+data.data[i].id+"' selected>"+data.data[i].info+"</option>");
                    }else{
                        option=$("<option value='"+data.data[i].id+"'>"+data.data[i].info+"</option>");
                    }
                    $select.append(option);
                }
            }
        }
    });
}


function alt_save(){
    $('#altOldMan').form('submit', {
        url:pathJs+"/map/editOldmanMap",
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