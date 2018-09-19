package com.warn.entity;

/**
 * Created by admin on 2017/4/7.
 */
public class Equipment {

    private String eid;//设备ID 主键 不自动增长
    private String eName;//设备名称
    private Integer roomId;//对应房间ID
    private String eRegtime;
    private Integer oldId;//对应老人ID

    //以下 不在数据库的表中
    private String gatewayID;//网关
    private String segment;//网段标志



    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String geteRegtime() {
        return eRegtime;
    }

    public void seteRegtime(String eRegtime) {
        this.eRegtime = eRegtime;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }
}
