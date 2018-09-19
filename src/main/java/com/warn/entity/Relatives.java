package com.warn.entity;

/**
 * Created by admin on 2017/4/5.
 */
//紧急联系人信息
public class Relatives {

    private Integer relid;
    private String rName;
    private String rPhone;
    private String rAddress;
    private Integer oldId;

    public Integer getRelid() {
        return relid;
    }

    public void setRelid(Integer relid) {
        this.relid = relid;
    }

    public String getrPhone() {
        return rPhone;
    }

    public void setrPhone(String rPhone) {
        this.rPhone = rPhone;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrAddress() {
        return rAddress;
    }

    public void setrAddress(String rAddress) {
        this.rAddress = rAddress;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }
}
