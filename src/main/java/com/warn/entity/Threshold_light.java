package com.warn.entity;

/**
 * 光强阈值
 * Created by admin on 2017/4/26.
 */
public class Threshold_light {

    private Integer lid;
    private Room room;
    private Integer lThreshold;//光强阈值
    private String times;//检测时间段 一般为晚上
    private Integer continueTime;//持续超过多长时间报警 单位分钟

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getlThreshold() {
        return lThreshold;
    }

    public void setlThreshold(Integer lThreshold) {
        this.lThreshold = lThreshold;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public Integer getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(Integer continueTime) {
        this.continueTime = continueTime;
    }
}
