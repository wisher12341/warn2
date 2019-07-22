package com.warn.entity;

import java.util.List;

public class SensorData {
    private Integer id;
    private List<Integer> gatewayIDs;
    private Integer sensorPointID;
    private Integer sensorID;
    private Integer sensorData;
    private String timeString;
    private String date;
    private List<Integer> sensorPointIDs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getGatewayIDs() {
        return gatewayIDs;
    }

    public void setGatewayIDs(List<Integer> gatewayIDs) {
        this.gatewayIDs = gatewayIDs;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getSensorPointIDs() {
        return sensorPointIDs;
    }

    public void setSensorPointIDs(List<Integer> sensorPointIDs) {
        this.sensorPointIDs = sensorPointIDs;
    }
}
