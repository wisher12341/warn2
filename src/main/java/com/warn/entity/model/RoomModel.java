package com.warn.entity.model;

import com.warn.entity.Room;

/**
 * Created by admin on 2017/4/11.
 */
//房间活动模型
public class RoomModel {
    private Integer rmid;
    private Room room;
    private String active; //活动时间段
    private String rest;//休息时间段

    public Integer getRmid() {
        return rmid;
    }

    public void setRmid(Integer rmid) {
        this.rmid = rmid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


}
