package com.warn.entity.model;

public class AreaModel {
    private Integer aid;
    private Integer roomId;
    private Integer area;
    private String areaInfo;
    private String areaActiveTime;
    private String areaRestTime;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo;
    }

    public String getAreaActiveTime() {
        return areaActiveTime;
    }

    public void setAreaActiveTime(String areaActiveTime) {
        this.areaActiveTime = areaActiveTime;
    }

    public String getAreaRestTime() {
        return areaRestTime;
    }

    public void setAreaRestTime(String areaRestTime) {
        this.areaRestTime = areaRestTime;
    }
}
