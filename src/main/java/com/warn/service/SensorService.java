package com.warn.service;


import com.warn.dto.SensorCollections;
import com.warn.dto.SensorType;
import com.warn.entity.Equipment;
import com.warn.entity.HouseMarker;
import com.warn.entity.OldMan;
import com.warn.mongodb.model.SensorCollection;

import java.util.List;

/**
 * Created by admin on 2017/4/10.
 */
public interface SensorService {
    void checkMoveData(List<SensorCollection> sensorCollections);

    void checkPositionData(List<SensorCollection> sensorCollections);

    void urgency(Equipment equip,Integer gatewayID);

//    SensorType conType(SensorCollections sensorCollections);

    void checkLightData(List<SensorCollection> lightSensorCollectionLis);

    void checkWenduData(List<SensorCollection> wenduSensorCollectionLis);

    void checkDoorData(List<SensorCollection> doorSensorCollectionLis);

    int intervalTime(String last, String pre);

//    Boolean checkSwitch(SensorCollections sensorCollections);

//    SensorType conType(SensorCollection[] sensorCollections);

//    Boolean checkSwitch(SensorCollection[] sensorCollections);

    Boolean checkSwitch(SensorCollections sensorCollections);

    SensorType conType(SensorCollections sensorCollections);

    SensorType conSecType(SensorCollections sensorCollections);

    void mapUpdate(OldMan oldMan);
}
