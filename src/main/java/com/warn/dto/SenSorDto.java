package com.warn.dto;

import com.warn.entity.Equipment;
import com.warn.entity.OldMan;
import com.warn.entity.Room;

/**
 * 传感器原始信息
 * Created by netlab606 on 2017/5/20.
 */
public class SenSorDto {
    private String sensorId;
    private String sensorType;
    private Integer sensorData;
    private Integer gatewayID;
    private String time;
    private OldMan oldMan;
    private Room room;
    private Equipment equipment;

    public Integer getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(Integer gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }


    public Integer getSensorData() {
        return sensorData;
    }

    public void setSensorData(Integer sensorData) {
        this.sensorData = sensorData;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
