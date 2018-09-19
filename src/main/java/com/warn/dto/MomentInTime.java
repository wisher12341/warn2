package com.warn.dto;

/**
 * 某一时刻是否在某一时间段内 以及 该时间段时长 和该时刻距该时间段的结束时间的时长 单位秒
 * Created by admin on 2017/4/20.
 */
public class MomentInTime {
    private Boolean inTime;//某一时刻是否在某一时间段内
    private Integer time;//该时间段时长  单位秒
//    private Integer toEnd;//该时刻距该时间段的结束时间的时长 单位秒
    private String flag;//如果在，则该时间段属于 a活动 还是 r休息  a&r有时活动有时休息

    public Boolean isInTime() {
        return inTime;
    }

    public void setInTime(Boolean inTime) {
        this.inTime = inTime;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

//    public Integer getToEnd() {
//        return toEnd;
//    }
//
//    public void setToEnd(Integer toEnd) {
//        this.toEnd = toEnd;
//    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
