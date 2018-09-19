package com.warn.entity;

/**
 * 故障历史信息
 * Created by admin on 2017/4/27.
 */
public class DownData {

    private Integer downid;
    private String typeDown;//类型  网关故障 设备故障
    private String oldName;//老人姓名 方便预警历史信息查询
    private Integer oid;//老人id 方便预警历史信息查询
    private String dataDown;//具体报警内容
    private String readDown;//是否已读  是 否
    private String timeDown;//写入时间
    private OldMan oldMan;

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public Integer getDownid() {
        return downid;
    }

    public void setDownid(Integer downid) {
        this.downid = downid;
    }

    public String getTypeDown() {
        return typeDown;
    }

    public void setTypeDown(String typeDown) {
        this.typeDown = typeDown;
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

    public String getDataDown() {
        return dataDown;
    }

    public void setDataDown(String dataDown) {
        this.dataDown = dataDown;
    }

    public String getReadDown() {
        return readDown;
    }

    public void setReadDown(String readDown) {
        this.readDown = readDown;
    }

    public String getTimeDown() {
        return timeDown;
    }

    public void setTimeDown(String timeDown) {
        this.timeDown = timeDown;
    }

    @Override
    public boolean equals(Object obj) {
        DownData warnData= (DownData) obj;
        return warnData.getDownid().intValue()==this.getDownid().intValue();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + this.getDownid().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DownData{" +
                "downId=" + downid +
                ", typeDown='" + typeDown + '\'' +
                ", oldName='" + oldName + '\'' +
                ", oid=" + oid +
                ", dataDown='" + dataDown + '\'' +
                ", readDown='" + readDown + '\'' +
                ", timeDown='" + timeDown + '\'' +
                '}';
    }
}
