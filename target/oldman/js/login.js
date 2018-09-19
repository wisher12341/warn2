$(function(){
    //错误信息
    if(message!=null){
        $("#error").html(message);
    }
});


function login(){
    if($(".autologin").is(":checked")){
        $(".autologinch").val("Y");
    }
    alert($(".autologinch").val());
    $("#loginForm").submit();
}