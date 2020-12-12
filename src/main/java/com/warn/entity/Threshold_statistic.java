package com.warn.entity;

public class Threshold_statistic {
    public Integer id;
    public Integer roomId;
    private Integer area;
    private String areaInfo;
    private Integer sThreshold;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getsThreshold() {
        return sThreshold;
    }

    public void setsThreshold(Integer sThreshold) {
        this.sThreshold = sThreshold;
    }
}
