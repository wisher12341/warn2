package com.warn.dto;

import com.warn.entity.QuMarker;

import java.util.List;

/**
 * 标注 统计
 * Created by netlab606 on 2017/7/10.
 */
public class MarkerSum {

    private List<QuMarker> quMarkerList;
    private Integer sum;
    private Integer greenSum;
    private Integer yellowSum;
    private Integer redSum;

    public List<QuMarker> getQuMarkerList() {
        return quMarkerList;
    }

    public void setQuMarkerList(List<QuMarker> quMarkerList) {
        this.quMarkerList = quMarkerList;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getGreenSum() {
        return greenSum;
    }

    public void setGreenSum(Integer greenSum) {
        this.greenSum = greenSum;
    }

    public Integer getYellowSum() {
        return yellowSum;
    }

    public void setYellowSum(Integer yellowSum) {
        this.yellowSum = yellowSum;
    }

    public Integer getRedSum() {
        return redSum;
    }

    public void setRedSum(Integer redSum) {
        this.redSum = redSum;
    }
}
