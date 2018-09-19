package com.warn.dto;

import com.warn.mongodb.model.SensorCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于将接受的传感器数据分类
 * Created by admin on 2017/4/25.
 */
public class SensorType {
    //要先创建 不然报空指针
    private List<SensorCollection> moveSensorCollection=new ArrayList<>();
    private List<SensorCollection> wenduSensorCollection=new ArrayList<>();
    private List<SensorCollection> lightSensorCollection=new ArrayList<>();
    private List<SensorCollection> doorSensorCollection=new ArrayList<>();

    public List<SensorCollection> getMoveSensorCollection() {
        return moveSensorCollection;
    }

    public void setMoveSensorCollection(List<SensorCollection> moveSensorCollection) {
        this.moveSensorCollection = moveSensorCollection;
    }

    public List<SensorCollection> getLightSensorCollection() {
        return lightSensorCollection;
    }

    public void setLightSensorCollection(List<SensorCollection> lightSensorCollection) {
        this.lightSensorCollection = lightSensorCollection;
    }

    public List<SensorCollection> getWenduSensorCollection() {
        return wenduSensorCollection;
    }

    public void setWenduSensorCollection(List<SensorCollection> wenduSensorCollection) {
        this.wenduSensorCollection = wenduSensorCollection;
    }

    public List<SensorCollection> getDoorSensorCollection() {
        return doorSensorCollection;
    }

    public void setDoorSensorCollection(List<SensorCollection> doorSensorCollection) {
        this.doorSensorCollection = doorSensorCollection;
    }
}
