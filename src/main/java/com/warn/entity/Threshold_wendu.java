package com.warn.entity;

/**
 * 温度的阈值模型
 * Created by admin on 2017/4/26.
 */
public class Threshold_wendu {

    private Integer wid;
    private Room room;
    private Integer wThreshold;//温度阈值

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getwThreshold() {
        return wThreshold;
    }

    public void setwThreshold(Integer wThreshold) {
        this.wThreshold = wThreshold;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
