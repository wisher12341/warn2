package com.warn.entity;

/**
 * 出门阈值  过多久确定出门
 * Created by admin on 2017/4/28.
 */
public class Threshold_out {

    private Integer outId;
    private Integer outThreshold;//多久算出门 单位分钟
    private OldMan oldMan;
    private Integer noComeThreshold;//多久没回来 进行报警 单位分钟

    public Integer getOutId() {
        return outId;
    }

    public void setOutId(Integer outId) {
        this.outId = outId;
    }

    public Integer getOutThreshold() {
        return outThreshold;
    }

    public void setOutThreshold(Integer outThreshold) {
        this.outThreshold = outThreshold;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public Integer getNoComeThreshold() {
        return noComeThreshold;
    }

    public void setNoComeThreshold(Integer noComeThreshold) {
        this.noComeThreshold = noComeThreshold;
    }
}
