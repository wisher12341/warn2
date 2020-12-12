package com.warn.sms;

/**
 * 使用阿里大于短信通知 模板常量类
 * Created by netlab606 on 2017/5/28.
 */
public class SMSConstants {

    public static int openSys=0;//默认发短信的功能是关闭的  系统人员进行管理  总的

    //公共参数：正式环境请求地址http格式
    public static String URL="http://gw.api.taobao.com/router/rest";

    //公共参数：签名的摘要算法，可选值为：hmac，md5。
    public static String SIGN_METHOD_MD5="md5";
    //公共参数：签名的摘要算法，可选值为：hmac，md5。
    public static String SIGN_METHOD_HMAC="hmac";
    //请求参数：短信签名，传入的短信签名必须是在阿里大于“管理中心-短信签名管理”中的可用签名
    public static String SMS_SIGN="乐健LJSERVICE1";
    //请求参数：公共回传参数
    public static String EXTEND="123456";
    //请求参数：短信类型，传入值请填写normal
    public static String SMS_TYPE="normal";
    public static String SMS_TEMPLATE_CODE="SMS_125027514";

    //返回信息编码为utf-8
    public static String CHARSET_UTF8="utf-8";

}
