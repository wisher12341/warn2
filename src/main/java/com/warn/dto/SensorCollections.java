package com.warn.dto;

import com.warn.mongodb.model.SensorCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于接受传感器的数据
 * Created by admin on 2017/4/24.
 */
public class SensorCollections {
    private List<SensorCollection> sensorCollections=new ArrayList<>();

    public List<SensorCollection> getSensorCollections() {
        return sensorCollections;
    }

    public void setSensorCollections(List<SensorCollection> sensorCollections) {
        this.sensorCollections = sensorCollections;
    }
}
