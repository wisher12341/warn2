package com.warn.entity;

/**
 * 楼标注
 * Created by netlab606 on 2017/9/2.
 */
public class LouMarker {

    private Integer id;
    private String xG;
    private String yG;
    private String xR;
    private String yR;
    private String xY;
    private String yY;
    private Integer jid;

    private String info;
    private Integer greenSum;//绿色人数 正常
    private Integer yellowSum;//黄色人数 正在接受服务
    private Integer redSum;////红色人数 预警

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getxG() {
        return xG;
    }

    public void setxG(String xG) {
        this.xG = xG;
    }

    public String getyG() {
        return yG;
    }

    public void setyG(String yG) {
        this.yG = yG;
    }

    public String getxR() {
        return xR;
    }

    public void setxR(String xR) {
        this.xR = xR;
    }

    public String getyR() {
        return yR;
    }

    public void setyR(String yR) {
        this.yR = yR;
    }

    public String getxY() {
        return xY;
    }

    public void setxY(String xY) {
        this.xY = xY;
    }

    public String getyY() {
        return yY;
    }

    public void setyY(String yY) {
        this.yY = yY;
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
