package com.warn.entity;

import java.util.List;

/**
 * 街道 标注
 * Created by netlab606 on 2017/7/7.
 */
public class JieDaoMarker {

    private Integer id;
    private String jName; //街道名称
    private String jX;//坐标
    private String jY;//坐标
    private Integer styleIndex;
    private Integer qid;//对应区ID
    private String qName;//区名称

    private Integer sum;//该街道的有多少房屋标注
    private Integer greenSum;//绿色房屋标注数 正常
    private Integer yellowSum;//黄色房屋标注数 正在接受服务
    private Integer redSum;////红色房屋标注数 预警


//    private List<HouseMarker> houseMarkerList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getjName() {
        return jName;
    }

    public void setjName(String jName) {
        this.jName = jName;
    }

    public String getjX() {
        return jX;
    }

    public void setjX(String jX) {
        this.jX = jX;
    }

    public String getjY() {
        return jY;
    }

    public void setjY(String jY) {
        this.jY = jY;
    }

    public Integer getStyleIndex() {
        return styleIndex;
    }

    public void setStyleIndex(Integer styleIndex) {
        this.styleIndex = styleIndex;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
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

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

}
