package com.warn.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/23.
 */
public class ManModelDtos {
    private int oid;//老人id
    private List<String> times;//时间段
    private List<String[]> timeRooms;//时间段对应的房间  可以有多个房间  #后面是活动类型 休息还是活动  户外的话没有类型 1活动  2休息


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
