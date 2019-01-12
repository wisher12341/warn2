package com.warn.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 对应 传感器数据
 * Created by admin on 2017/4/7.
 */
@Document(collection = "SensorCollection")
public class SensorCollection implements Serializable {

    private static final long serialVersionUID = 179822189115264434L;

    @Id
    private String id;
    private String sensorPointID;//传感器Id
    private Integer sensorID;//传感器种类  行为1 热释电  能够表示人是否活动  行为数据： 15动  240不动  255持续活动超过1分钟
    private Integer sensorData;//传感器数据
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;
    private Integer gatewayID;//网关ID  也就是人员ID

    private String timeString;//日期 年月日  数据库本身没有该字段 方便条件查询

    public SensorCollection() {
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

    public void setTime(String time) {
        this.timeString = time;
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
        SensorCollection sensorCollection= (SensorCollection) obj;
        return sensorCollection.getId().equals(id);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + id.hashCode();
        return result;
    }
}
