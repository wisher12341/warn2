package com.warn.dto;
import java.util.List;

public class RoomModelDtos {
    private int oid;//房间id
    private List<String> times;//时间段
    private List<String[]> timeRooms;//时间段对应的房间  可以有多个房间  #后面是活动类型 休息还是活动  户外的话没有类型 1活动  2休息//用了师兄的，这里的Room实际代表区域


    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public List<String[]> getTimeRooms() {
        return timeRooms;
    }

    public void setTimeRooms(List<String[]> timeRooms) {
        this.timeRooms = timeRooms;
    }
}
