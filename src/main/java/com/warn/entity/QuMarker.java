package com.warn.entity;

import java.util.List;

/**
 * 区 标注
 * Created by netlab606 on 2017/7/7.
 */
public class QuMarker {

    private Integer id;
    private String qName; //区名称
    private String qX;//坐标
    private String qY;//坐标
    private Integer styleIndex;


    private Integer sum;//该区的有多少房屋标注
    private Integer greenSum;//绿色房屋标注数 正常
    private Integer yellowSum;//黄色房屋标注数 正在接受服务
    private Integer redSum;////红色房屋标注数 预警


    private List<JieDaoMarker> jieDaoMarkerList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    public String getqX() {
        return qX;
    }

    public void setqX(String qX) {
        this.qX = qX;
    }

    public String getqY() {
        return qY;
    }

    public void setqY(String qY) {
        this.qY = qY;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getStyleIndex() {
        return styleIndex;
    }

    public void setStyleIndex(Integer styleIndex) {
        this.styleIndex = styleIndex;
    }

    public List<JieDaoMarker> getJieDaoMarkerList() {
        return jieDaoMarkerList;
    }

    public void setJieDaoMarkerList(List<JieDaoMarker> jieDaoMarkerList) {
        this.jieDaoMarkerList = jieDaoMarkerList;
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
