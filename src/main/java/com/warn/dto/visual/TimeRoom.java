package com.warn.dto.visual;

import java.sql.Time;

/**
 * 某一房间  活动的具体信息
 * Created by admin on 2017/4/13.
 */
public class TimeRoom  implements Comparable<TimeRoom> {
    private String time;//时间段
    private String value;//一共多长时间 单位分钟
    private String type;//活动还是休息  a表示活动 b表示休息 n表示不在该房间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int compareTo(TimeRoom timeRoom){
        return this.getTime().compareTo(timeRoom.getTime());
    }
}
