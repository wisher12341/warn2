package com.warn.dto;

import com.warn.entity.OldMan;
import com.warn.entity.Room;
import com.warn.entity.Threshold_light;
import com.warn.entity.Threshold_wendu;

/**
 * Created by admin on 2017/4/26.
 */
public class Warn_light {
    private Threshold_light threshold_light;
    private Integer light;//当前光强
    private String time;//起始时间
    private Integer value;//当前持续时间 单位分钟
    private OldMan oldMan;


    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public Threshold_light getThreshold_light() {
        return threshold_light;
    }

    public void setThreshold_light(Threshold_light threshold_light) {
        this.threshold_light = threshold_light;
    }

    public Integer getLight() {
        return light;
    }

    public void setLight(Integer light) {
        this.light = light;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
