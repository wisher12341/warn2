package com.warn.dto.visual;


import com.warn.dto.visual.TimeRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * 房间可视化处理 格式
 * Created by admin on 2017/4/13.
 */
public class RoomVisual {
    private Integer roomId;
    private String roomName;
    private List<TimeRoom> time=new ArrayList<TimeRoom>();

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<TimeRoom> getTime() {
        return time;
    }

    public void setTime(List<TimeRoom> time) {
        this.time = time;
    }
}
