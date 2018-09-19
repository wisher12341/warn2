package com.warn.dto.visual;

/**
 * 老人生活规律模型 需要的房间信息
 * Created by admin on 2017/4/15.
 */
public class OldManLive_Room {

    private String roomName;//房间名   可能会出现同一时间段，可能会在多个房间 房间值以%号隔开 06:30-07:00@4%33 这个时间段 可能出现在4或者33  根据大数据分析出来
    private String time;//时间段
    private String value;//一共多长时间 单位分钟

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
