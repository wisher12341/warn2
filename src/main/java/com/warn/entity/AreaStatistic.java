package com.warn.entity;

public class AreaStatistic {
    private int id;
    private String statisticInfo;
    private int oid;
    private int roomId;
    private String date;
    private String time;
    private Integer normal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatisticInfo() {
        return statisticInfo;
    }

    public void setStatisticInfo(String statisticInfo) {
        this.statisticInfo = statisticInfo;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public Integer getNormal() {
        return normal;
    }

    public void setNormal(Integer normal) {
        this.normal = normal;
    }
}
