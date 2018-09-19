package com.warn.dto;

import com.warn.entity.OldMan;

/**
 * 预警开关实体
 * Created by admin on 2017/4/30.
 */
public class TimeDto {
//    private Integer oid;//方便接受前端值
    private Integer timerSwitch;//开关 0关 1开  一开始都关
    private OldMan oldMan=new OldMan();
//    public Integer getOid() {
//        return oid;
//    }
//
//    public void setOid(Integer oid) {
//        this.oid = oid;
//    }


    public TimeDto() {
    }

    public TimeDto(Integer timerSwitch, OldMan oldMan) {
        this.timerSwitch = timerSwitch;
        this.oldMan = oldMan;
    }

    public Integer getTimerSwitch() {
        return timerSwitch;
    }

    public void setTimerSwitch(Integer timerSwitch) {
        this.timerSwitch = timerSwitch;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }
}
