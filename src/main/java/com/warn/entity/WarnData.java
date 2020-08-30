package com.warn.entity;

/**
 * 预警历史信息
 * Created by admin on 2017/4/27.
 */
public class WarnData {

    private Integer wdid;
    private String typeW;//类型  紧急报警  行为预警 温度预警 光强预警
    private String oldName;//老人姓名
    private Integer oid;//老人id
    private String dataW;//具体报警内容
    private String readW;//是否已读  是 否
    private String timeW;//写入时间
    private Integer sms;

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }


//未显示 是否发送过短信 0没有 1发送过

    public Integer getWdid() {
        return wdid;
    }

    public void setWdid(Integer wdid) {
        this.wdid = wdid;
    }

    public String getTypeW() {
        return typeW;
    }

    public void setTypeW(String typeW) {
        this.typeW = typeW;
    }

    public String getTimeW() {
        return timeW;
    }

    public void setTimeW(String timeW) {
        this.timeW = timeW;
    }

    public String getReadW() {
        return readW;
    }

    public void setReadW(String readW) {
        this.readW = readW;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getDataW() {
        return dataW;
    }

    public void setDataW(String dataW) {
        this.dataW = dataW;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    @Override
    public boolean equals(Object obj) {
        WarnData warnData= (WarnData) obj;
        return warnData.getWdid().intValue()==this.getWdid().intValue();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + this.getWdid().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WarnData{" +
                "wdid=" + wdid +
                ", typeW='" + typeW + '\'' +
                ", oldName='" + oldName + '\'' +
                ", oid=" + oid +
                ", dataW='" + dataW + '\'' +
                ", readW='" + readW + '\'' +
                ", timeW='" + timeW + '\'' +
                '}';
    }
}
