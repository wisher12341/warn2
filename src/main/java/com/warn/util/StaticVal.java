package com.warn.util;

import com.warn.dto.AuthorityDTO;
import com.warn.entity.OldMan;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理静态变量
 * Created by admin on 2017/4/30.
 */
public class StaticVal {

    public static Integer timerOpen=0;//预警自动启动总开关  是否自动启动 根据心跳数据 自动开启 那些 预警开关被关的老人的 预警开关
    //0关闭  1开启   默认关闭   在心跳数据确定网关发送故障时，如果是关闭状态，在系统关闭该老人预警开关后，之后即使有数据也无法自动启动，需要管理员手动启动
    //如果是开启状态，在系统关闭该老人预警开关后，如果网关恢复正常，则自动开启该老人的预警开关
    //系统刚启动时，由于各个老人的预警开关都是关闭的， 再打开总开关后，就会启动 所有在当前检测时间段内 有数据的 老人的开关

    public static Map<OldMan,Boolean> oldManTimer=new HashMap<>(); //此处存储管理每个老人预警开关的Map
    public static Integer accessDatabaseTime=1;//此处存储每过几分钟 访问一次数据库 （默认6  单位 分钟）

    public static Integer gatewayDown=5; //网关故障的阈值   单位：分钟
    public static Integer equipDown=5; //设备故障的阈值   单位：分钟

    public static String url ="http://47.103.137.244:81/alarm/Forbidden";

    public static String url2 ="http://47.103.137.244:83/alarm/Forbidden";


//    public static String url_1 =

}
