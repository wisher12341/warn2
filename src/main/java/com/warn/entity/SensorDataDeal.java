package com.warn.entity;

/**
 * 将原始传感器数据的行为进行处理 转化为方便处理的格式
 * Created by admin on 2017/4/10.
 */
public class SensorDataDeal {
    private OldMan oldMan;//传感器数据归属的老人
    private Room activityRoom;//活动的房间
    private String time;//活动时的时刻  取最后一条数据  hh:mm:ss  如果不动的话 也是最初不动的时刻

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public Room getActivityRoom() {
        return activityRoom;
    }

    public void setActivityRoom(Room activityRoom) {
        this.activityRoom = activityRoom;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SensorDataDeal{" +
                "oldMan=" + oldMan +
                ", activityRoom=" + activityRoom +
                ", time='" + time + '\'' +
                '}';
    }
}
