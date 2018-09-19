package com.warn.entity;

/**
 * 出门消息
 * Created by admin on 2017/4/28.
 */
public class Outdoor {

    private String typeD;//用于存入出门历史信息的分类  out出门 come回来
    private Integer odid;
    private Integer oid; //方便出门历史信息查询
    private String oldName;//方便出门历史信息查询
    private OldMan oldMan;
    private String out;//出门的时间  不在outhistory数据库中
    private String dataD;//回来时  整个在户外的时间段  以及一些其他信息
    private String readD;//标记是否已读  是  否
    private String timeD;//添加时间
    private Threshold_out threshold_out;

    public Integer getOdid() {
        return odid;
    }

    public void setOdid(Integer odid) {
        this.odid = odid;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getDataD() {
        return dataD;
    }

    public void setDataD(String dataD) {
        this.dataD = dataD;
    }

    public String getReadD() {
        return readD;
    }

    public void setReadD(String readD) {
        this.readD = readD;
    }

    public String getTimeD() {
        return timeD;
    }

    public void setTimeD(String timeD) {
        this.timeD = timeD;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Threshold_out getThreshold_out() {
        return threshold_out;
    }

    public void setThreshold_out(Threshold_out threshold_out) {
        this.threshold_out = threshold_out;
    }

    public String getTypeD() {
        return typeD;
    }

    public void setTypeD(String typeD) {
        this.typeD = typeD;
    }
}
