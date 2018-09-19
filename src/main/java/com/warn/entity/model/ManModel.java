package com.warn.entity.model;

import com.warn.entity.OldMan;

/**
 * Created by admin on 2017/4/11.
 */
//老人生活模型
public class ManModel {
    private Integer mid;
    private OldMan oldMan=new OldMan();
    private String live;


    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }
}
