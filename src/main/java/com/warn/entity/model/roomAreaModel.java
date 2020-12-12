package com.warn.entity.model;

import com.warn.entity.Room;

public class roomAreaModel {
    private Integer id;
    private Room room = new Room();
    private String roomLive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getRoomLive() {
        return roomLive;
    }

    public void setRoomLive(String roomLive) {
        this.roomLive = roomLive;
    }
}
