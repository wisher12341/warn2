package com.warn.dto;

import com.warn.entity.OldMan;
import com.warn.entity.Room;

/**
 * Created by admin on 2017/4/5.
 */
//传给前台的报警信息  行为报警
public class Warn {



    private Integer warnLevel;//报警级别  1表示一级 2表示二级
    private OldMan oldMan;
    private Integer noMoveTime;//到目前为止不动时间的时间  单位分钟
    private String time;//最初不动的时刻
    private Room room;//不动时所在的房间
    private String inTime;//是否在该房间的生活规律模型中   不能用布尔值 否则前端接受不到
    private String times;//在该房间的生活规律模型中的  规律时间段
    private String flag;//针对 times 的类型  a表示活动 r休息



    public Integer getWarnLevel() {
        return warnLevel;
    }

    public void setWarnLevel(Integer warnLevel) {
        this.warnLevel = warnLevel;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public Integer getNoMoveTime() {
        return noMoveTime;
    }

    public void setNoMoveTime(Integer noMoveTime) {
        this.noMoveTime = noMoveTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Warn{" +
                "warnLevel=" + warnLevel +
                ", oldMan=" + oldMan +
                ", noMoveTime=" + noMoveTime +
                ", time='" + time + '\'' +
                ", room=" + room +
                ", inTime='" + inTime + '\'' +
                ", times='" + times + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
