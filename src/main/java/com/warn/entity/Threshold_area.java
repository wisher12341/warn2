package com.warn.entity;

public class Threshold_area {
    private Integer id;
    private Integer roomId;
    private Integer area;
    private String areaInfo;
    private Integer a1Threshold;//活动时的阈值 一级 单位：分钟
    private Integer r1Threshold;//休息时的阈值 一级 单位：百分比
    private Integer a2Threshold;//活动时的阈值 二级 单位：分钟
    private Integer r2Threshold;//休息时的阈值 二级 单位：百分比
//    private Integer door;//该房间是否有让老人外出的门 由管理员手工设置  0表示没有  1表示有
    private Room room;

    private Integer n1Threshold;//不在生活规律时间段 不动时的 阈值 一级 单位 分钟
    private Integer n2Threshold;//不在生活规律时间段 不动时的 阈值 二级 单位 分钟

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

    public Integer getA1Threshold() {
        return a1Threshold;
    }

    public void setA1Threshold(Integer a1Threshold) {
        this.a1Threshold = a1Threshold;
    }

    public Integer getR1Threshold() {
        return r1Threshold;
    }

    public void setR1Threshold(Integer r1Threshold) {
        this.r1Threshold = r1Threshold;
    }

    public Integer getA2Threshold() {
        return a2Threshold;
    }

    public void setA2Threshold(Integer a2Threshold) {
        this.a2Threshold = a2Threshold;
    }

    public Integer getR2Threshold() {
        return r2Threshold;
    }

    public void setR2Threshold(Integer r2Threshold) {
        this.r2Threshold = r2Threshold;
    }

    public Integer getN1Threshold() {
        return n1Threshold;
    }

    public void setN1Threshold(Integer n1Threshold) {
        this.n1Threshold = n1Threshold;
    }

    public Integer getN2Threshold() {
        return n2Threshold;
    }

    public void setN2Threshold(Integer n2Threshold) {
        this.n2Threshold = n2Threshold;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
