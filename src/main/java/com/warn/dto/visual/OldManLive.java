package com.warn.dto.visual;

import java.util.ArrayList;
import java.util.List;

/**
 * 老人生活规律模型 可视化 格式
 * Created by admin on 2017/4/15.
 */
public class OldManLive {

    private List<String> roomNames=new ArrayList<String>();//房间名

    private List<OldManLive_Room> oldManLive_rooms=new ArrayList<OldManLive_Room>();//各时间段 在哪里

    public List<OldManLive_Room> getOldManLive_rooms() {
        return oldManLive_rooms;
    }

    public void setOldManLive_rooms(List<OldManLive_Room> oldManLive_rooms) {
        this.oldManLive_rooms = oldManLive_rooms;
    }

    public List<String> getRoomNames() {
        return roomNames;
    }

    public void setRooomNames(List<String> roomNames) {
        this.roomNames = roomNames;
    }
}
