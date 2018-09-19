$(function(){

});


function formatActionFunction(value,row,index) {
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


function formatActionS(value,row,index) {
    if(row.superManager==0) {
        return '<input class="switch" type="checkbox" onclick="change(event,1)" name="' + row.superManager+ '"> ';
    }else{
        return '<input class="switch" type="checkbox" onclick="change(event,1)" name="' + row.superManager + '"checked> ';
    }

}
function formatActionM(value,row,index) {
    if(row.manager==0) {
        return '<input class="switch" type="checkbox" onclick="change(event,2)" name="' + row.manager+ '"> ';
    }else{
        return '<input class="switch" type="checkbox" onclick="change(event,2)" name="' + row.manager + '"checked> ';
    }
}
function formatActionU(value,row,index) {
    if(row.user==0) {
        return '<input class="switch" type="checkbox" onclick="change(event,3)" name="' + row.user+ '"> ';
    }else{
        return '<input class="switch" type="checkbox" onclick="change(event,3)"  name="' + row.user + '"checked> ';
    }

}
function change(event,type){
    var e=event||window.event;
    var cb=e.target.name;
    cb=cb==1?0:1;
    e.target.name=cb;
    //超级管理人员  type是因为 单选框所在的位置不同
    if(type==1){
        //判断是不是父菜单
        var pid=e.target.parentNode.parentNode.previousSibling.firstChild.firstChild.getAttribute('pid');
        var id=e.target.parentNode.parentNode.previousSibling.firstChild.firstChild.getAttribute('id');
        if(pid==0){
            //父菜单的话  选中 则其子菜单都选中  取消 则其子菜单都取消
            $('span[pid='+id+']').each(function(){
                if(cb==1) {
                    //选中
                    $(this).parent().parent().next().children().children().attr("name",1);
                    $(this).parent().parent().next().children().children().prop("checked",true) ;
                }else{
                    $(this).parent().parent().next().children().children().attr("name",0);
                    $(this).parent().parent().next().children().children().prop("checked",false) ;
                }
            });
        }else{
            //子菜单 只有在选中的情况下 才会影响父菜单  父菜单也要选中
            $('span[name='+pid+']').parent().parent().next().children().children().attr("name", 1).prop("checked", true);
        }
    }
    //管理人员
    if(type==2){
        //判断是不是父菜单
        var pid=e.target.parentNode.parentNode.previousSibling.previousSibling.firstChild.firstChild.getAttribute('pid');
        var id=e.target.parentNode.parentNode.previousSibling.previousSibling.firstChild.firstChild.getAttribute('id');
        if(pid==0){
            //父菜单的话  选中 则其子菜单都选中  取消 则其子菜单都取消
            $('span[pid='+id+']').each(function(){
                if(cb==1) {
                    //选中
                    $(this).parent().parent().next().next().children().children().attr("name",1);
                    $(this).parent().parent().next().next().children().children().prop("checked",true) ;
                }else{
                    $(this).parent().parent().next().next().children().children().attr("name",0);
                    $(this).parent().parent().next().next().children().children().prop("checked",false) ;
                }
            });
        }else{
            //子菜单 只有在选中的情况下 才会影响父菜单  父菜单也要选中
            $('span[name='+pid+']').parent().parent().next().next().children().children().attr("name", 1).prop("checked", true);
        }
    }
    //前台人员
    if(type==3){
        //判断是不是父菜单
        var pid=e.target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.firstChild.firstChild.getAttribute('pid');
        var id=e.target.parentNode.parentNode.previousSibling.previousSibling.previousSibling.firstChild.firstChild.getAttribute('id');
        if(pid==0){
            //父菜单的话  选中 则其子菜单都选中  取消 则其子菜单都取消
            $('span[pid='+id+']').each(function(){
                if(cb==1) {
                    //选中
                    $(this).parent().parent().next().next().next().children().children().attr("name",1);
                    $(this).parent().parent().next().next().next().children().children().prop("checked",true) ;
                }else{
                    $(this).parent().parent().next().next().next().children().children().attr("name",0);
                    $(this).parent().parent().next().next().next().children().children().prop("checked",false) ;
                }
            });
        }else{
            //子菜单 只有在选中的情况下 才会影响父菜单  父菜单也要选中
            $('span[name='+pid+']').parent().parent().next().next().next().children().children().attr("name", 1).prop("checked", true);
        }
    }
}


function save() {
    var data=[];
    $('.functionName').each(function(){
        var subData={id:"",superManager:"",manager:"",user:""};
        subData.id=$(this).attr('name');
        subData.superManager=$(this).parent().parent().next().children().children().attr('name');
        subData.manager=$(this).parent().parent().next().next().children().children().attr('name');
        subData.user=$(this).parent().parent().next().next().next().children().children().attr('name');
        data.push(subData);
    });
    $.ajax({
        type: "POST",
        url: pathJs + "/authority/editAuthority",
        dataType: "json",
        contentType:"application/json", //发送json数据要加上这这一句
        data: JSON.stringify(data),//转换为json对象
        success: function (data) {
            refresh();
        }
    });
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