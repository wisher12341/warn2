package com.warn.entity;

/**
 * 派人去 服务时的 对应关系
 * Created by netlab606 on 2017/7/14.
 */
public class Serve {

    private Integer id;
    private Integer oid;
    private Integer sid;//服务人员的ID

    private String detai;//服务内容

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

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getDetai() {
        return detai;
    }

    public void setDetai(String detai) {
        this.detai = detai;
    }
}
