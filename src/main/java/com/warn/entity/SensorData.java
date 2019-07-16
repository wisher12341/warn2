package com.warn.entity;

public class SensorData {
    private Integer id;
    private Integer gatewayID;
    private Integer sensorPointID;
    private Integer sensorID;
    private Integer sensorData;
    private String timeString;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(Integer gatewayID) {
        this.gatewayID = gatewayID;
    }

    public Integer getSensorPointID() {
        return sensorPointID;
    }

    public void setSensorPointID(Integer sensorPointID) {
        this.sensorPointID = sensorPointID;
    }

    public Integer getSensorID() {
        return sensorID;
    }

    public void setSensorID(Integer sensorID) {
        this.sensorID = sensorID;
    }

    public Integer getSensorData() {
        return sensorData;
    }

    public void setSensorData(Integer sensorData) {
        this.sensorData = sensorData;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
