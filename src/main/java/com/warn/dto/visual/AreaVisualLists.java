package com.warn.dto.visual;

import java.util.List;

public class AreaVisualLists {
    private List<AreaVisualList> areaVisual;
    private String roomName;

    public List<AreaVisualList> getAreaVisual() {
        return areaVisual;
    }

    public void setAreaVisual(List<AreaVisualList> areaVisual) {
        this.areaVisual = areaVisual;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
