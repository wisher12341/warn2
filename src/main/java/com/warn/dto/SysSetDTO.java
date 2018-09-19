package com.warn.dto;

/**
 * 系统变量设置参数集
 * Created by netlab606 on 2017/5/30.
 */
public class SysSetDTO {
    private Integer openSys;//短信功能的总开关
    private Integer accessBatabaseTime;//访问传感器数据库的时间间隔

    private Integer  gatewayDown;//网关故障
    private Integer equipDown;//设备故障

    private Integer timerOpen;//预警自动 总开关

    public Integer getTimerOpen() {
        return timerOpen;
    }

    public void setTimerOpen(Integer timerOpen) {
        this.timerOpen = timerOpen;
    }

    public Integer getOpenSys() {
        return openSys;
    }

    public void setOpenSys(Integer openSys) {
        this.openSys = openSys;
    }
//
//    public Integer getSmsTime() {
//        return smsTime;
//    }
//
//    public void setSmsTime(Integer smsTime) {
//        this.smsTime = smsTime;
//    }

    public Integer getAccessBatabaseTime() {
        return accessBatabaseTime;
    }

    public void setAccessBatabaseTime(Integer accessBatabaseTime) {
        this.accessBatabaseTime = accessBatabaseTime;
    }

    public Integer getGatewayDown() {
        return gatewayDown;
    }

    public void setGatewayDown(Integer gatewayDown) {
        this.gatewayDown = gatewayDown;
    }

    public Integer getEquipDown() {
        return equipDown;
    }

    public void setEquipDown(Integer equipDown) {
        this.equipDown = equipDown;
    }
}
