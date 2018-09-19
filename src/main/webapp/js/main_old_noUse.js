$(function() {
    $('#mainMenu').tree({
        url : pathJs+'/getMenu',
        parentField : 'pid',
        onClick : function(node) {
            if (node.attributes.url) {
                var src = pathJs + node.attributes.url;
                if (!$.startWith(node.attributes.url, '/')) {	//不是本项目的url，例如www.baidu.com
                    src = node.attributes.url;
                }
                var tabs = $('#mainTabs');
                var opts = {
                    title : node.text,
                    closable : true,
                    iconCls : node.iconCls,
                    content : $.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:99%;height:99%;padding-left:2px;" frameBorder="0"></iframe>', src),
                    border : false,
                    fit : true
                };
                if (tabs.tabs('exists', opts.title)) {
                    tabs.tabs('select', opts.title);
                } else {
                    tabs.tabs('add', opts);
                }
            }
        }
    });

    $.ajax({
        type: "GET",
        url: pathJs+"/getRole",
        dataType: "json",
        success:function(data){
            if(data.data.name=="前台人员"){
                //这个方法用来启动该页面的ReverseAjax功能
                dwr.engine.setActiveReverseAjax( true);
                //设置在页面关闭时，通知服务端销毁会话
                dwr.engine.setNotifyServerOnPageUnload( true);

                //var code=$('#usercode').val();//这里是你传入的参数，可以是直接写自己的值，用于区别session 实现 筛选 发送推送再用
                //Remote.getData(code);//调用后台程序
            }
        }
    });

});
$.formatString = function(str) {
    for ( var i = 0; i < arguments.length - 1; i++) {
        str = str.replace("{" + i + "}", arguments[i + 1]);
    }
    return str;
};
$.startWith = function(source, str) {
    var reg = new RegExp("^" + str);
    return reg.test(source);
};
function warn(type,data){
    alert(data);
}