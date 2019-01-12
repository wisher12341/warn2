package com.warn.entity;

import java.util.List;

/**
 * Created by admin on 2017/4/5.
 */
//老人基本信息
public class OldMan {

    private Integer oid;
    private String gatewayID;//网关ID   存入数据库是十进制  显示是十进制+二进制  转换在前端完成
    private String segment;//网段标志
    private String oldName; //姓名
    private String oldPhone;//电话
    private String oldAddress;//地址
    private String oldRegtime;//注册时间

    private Integer status;//状态 红绿黄
    private Integer louId;//楼地址

    private Relatives relatives;//紧急联系人信息

    private QuMarker quMarker;//所在区
    private JieDaoMarker jieDaoMarker;//所在街道
    private LouMarker louMarker;//所在楼
    private String mapAddress;//区-街道-楼
    private Integer version;//版本

    public OldMan() {
    }

    public OldMan(Integer oid) {
        this.oid = oid;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLouId() {
        return louId;
    }

    public void setLouId(Integer louId) {
        this.louId = louId;
    }

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer id) {
        this.oid = id;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone;
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    public Relatives getRelatives() {
        return relatives;
    }

    public void setRelatives(Relatives relatives) {
        this.relatives = relatives;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getOldRegtime() {
        return oldRegtime;
    }

    public void setOldRegtime(String oldRegtime) {
        this.oldRegtime = oldRegtime;
    }

    public QuMarker getQuMarker() {
        return quMarker;
    }

    public void setQuMarker(QuMarker quMarker) {
        this.quMarker = quMarker;
    }

    public JieDaoMarker getJieDaoMarker() {
        return jieDaoMarker;
    }

    public void setJieDaoMarker(JieDaoMarker jieDaoMarker) {
        this.jieDaoMarker = jieDaoMarker;
    }

    public LouMarker getLouMarker() {
        return louMarker;
    }

    public void setLouMarker(LouMarker louMarker) {
        this.louMarker = louMarker;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    //重写equals hashcode 不然获得不了以oldman为键的map的值
    @Override
    public boolean equals(Object obj) {
        OldMan oldMan= (OldMan) obj;
        return oldMan.getOid().intValue()==oid.intValue();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + oid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OldMan{" +
                "oid=" + oid +
                ", gatewayID=" + gatewayID +
                ", segment='" + segment + '\'' +
                ", oldName='" + oldName + '\'' +
                ", oldPhone='" + oldPhone + '\'' +
                ", oldAddress='" + oldAddress + '\'' +
                ", oldRegtime='" + oldRegtime + '\'' +
                ", relatives=" + relatives +
                '}';
    }
}
