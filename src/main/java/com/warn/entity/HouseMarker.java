package com.warn.entity;

/**
 * 房屋标注   当前版本 不用了
 * Created by netlab606 on 2017/7/7.
 */
public class HouseMarker {

    private Integer id;
    private Integer oid; //对应老人ID
    private String x;//坐标
    private String y;//坐标
    private Integer styleIndex;//样式索引
    private Integer jid;//对应街道ID

    private String detail;

    private Integer qid;//对应区ID

    private String oldName;// 用于 标注 添加时 人员的select

    private OldMan oldMan;//统计用


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
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

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    @Override
    public String toString() {
        return "HouseMarker{" +
                "id=" + id +
                ", oid=" + oid +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", styleIndex=" + styleIndex +
                ", jid=" + jid +
                ", detail='" + detail + '\'' +
                ", qid=" + qid +
                ", oldName='" + oldName + '\'' +
                ", oldMan=" + oldMan +
                '}';
    }
}
