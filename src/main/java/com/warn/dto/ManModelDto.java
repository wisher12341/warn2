package com.warn.dto;

import com.warn.entity.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * 生活规律模型 传给页面的格式
 * Created by admin on 2017/4/23.
 */
public class ManModelDto {
    private int mid;//数据库对应主键id
    private String times;//时间段
    private String timeRoom;//时间段对应的房间  可以有多个房间

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getTimeRoom() {
        return timeRoom;
    }

    public void setTimeRoom(String timeRoom) {
        this.timeRoom = timeRoom;
    }
}
