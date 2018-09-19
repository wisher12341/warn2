package com.warn.entity;

/**
 * 用于设备故障
 * Created by netlab606 on 2017/6/20.
 */
public class EquipDown {

    //这三个标志 确定一设备
    private String eid;
    private String gatewayID;
    private Integer type;

    private String time;


    @Override
    public boolean equals(Object obj) {
        EquipDown equipDown= (EquipDown) obj;
        return equipDown.getEid().equals(this.getEid())&&equipDown.getGatewayID().equals(this.getGatewayID())
                &&equipDown.getType().intValue()==this.getType().intValue();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + eid.hashCode()+gatewayID.hashCode();
        return result;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
