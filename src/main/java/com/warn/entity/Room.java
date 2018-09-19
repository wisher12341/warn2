package com.warn.entity;

import java.util.List;

/**
 * Created by admin on 2017/4/5.
 */
//房间信息
public class Room {

    private Integer rid;
    private String roomName;
    private String collectId;//采集点 即设备ID
    private String nerRoom;//相邻房间ID，以 , 隔开
    private Integer oldId;//对应老人的ID
    private String rRegtime;//注册时间

    public String getNerRoom() {
        return nerRoom;
    }

    public void setNerRoom(String nerRoom) {
        this.nerRoom = nerRoom;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getrRegtime() {
        return rRegtime;
    }

    public void setrRegtime(String rRegtime) {
        this.rRegtime = rRegtime;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    //重写equals hashcode 不然获得不了以oldman为键的map的值
    @Override
    public boolean equals(Object obj) {
        Room room= (Room) obj;
        return room.getRid()==rid;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + rid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "rid=" + rid +
                ", roomName='" + roomName + '\'' +
                ", collectId='" + collectId + '\'' +
                ", nerRoom='" + nerRoom + '\'' +
                ", oldId=" + oldId +
                ", rRegtime='" + rRegtime + '\'' +
                '}';
    }
}
