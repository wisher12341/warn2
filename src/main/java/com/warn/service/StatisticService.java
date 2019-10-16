package com.warn.service;

import com.warn.dto.visual.AreaVisual;
import com.warn.dto.visual.AreaVisualList;
import com.warn.dto.visual.AreaVisualLists;
import com.warn.entity.SensorData;
import com.warn.mongodb.model.SensorCollection;

import java.util.List;

public interface StatisticService {
    void getStatisticData(Integer gateWayId);

    List<AreaVisualList> getStatisticArea(Integer oid,Integer rid,String time);

    List<AreaVisualLists> getStatisticAreaList(Integer oid, Integer rid);

//    void checkStatistic(Integer oid,Integer rid);

    void transferData(List<SensorData> sensorDatas);
}
