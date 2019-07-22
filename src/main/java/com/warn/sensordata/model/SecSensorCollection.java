package com.warn.sensordata.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 对应 传感器数据2
 * Created by admin on 2018/12/25.
 */
@Document(collection = "SecSensorCollection")
public class SecSensorCollection implements Serializable {

    private static final long serialVersionUID = 179822189115264434L;

    @Id
    private String id;
    private String sensorPointID;//传感器Id
    private Integer sensorID;//传感器种类
    private Integer sensorData;//传感器数据
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;
    private Integer gatewayID;//网关ID  也就是人员ID

    private String timeString;

    public SecSensorCollection() {
    }


    public Integer getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(Integer gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String getSensorPointID() {
        return sensorPointID;
    }

    public void setSensorPointID(String sensorPointID) {
        this.sensorPointID = sensorPointID;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public Integer getSensorData() {
        return sensorData;
    }

    public void setSensorData(Integer sensorData) {
        this.sensorData = sensorData;
    }

    public Integer getSensorID() {
        return sensorID;
    }

    public void setSensorID(Integer sensorID) {
        this.sensorID = sensorID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getTime() {
        return timeString;
    }

    public void setTime(String timeString) {
        this.timeString = timeString;
    }

    @Override
    public String toString() {
        return "SensorCollection{" +
                "id='" + id + '\'' +
                ", sensorPointID='" + sensorPointID + '\'' +
                ", sensorID=" + sensorID +
                ", sensorData=" + sensorData +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", second='" + second + '\'' +
                ", gatewayID=" + gatewayID +
                ", time='" + timeString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        SecSensorCollection sensorCollection= (SecSensorCollection) obj;
        return sensorCollection.getId().equals(id);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + id.hashCode();
        return result;
    }
}
