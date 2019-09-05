package com.warn.sensordata.dao;

import com.warn.entity.SensorData;
import com.warn.mongodb.model.SensorCollection;
import org.springframework.data.mongodb.core.MongoTemplate;
import sun.management.Sensor;

import java.util.List;

/**
 * Created by admin on 2017/5/5.
 */
public interface SensorMogoSecDao {


    MongoTemplate getMongoTemplate();

    List<SensorCollection> findByTime(String start, String end, Integer gatewayID, List<Integer> closeWarns);

    List<SensorCollection> findToStatistic(Integer gateWayId,List<Integer> sensorPointIds,Integer limit);

    List<SensorCollection> findToStatisticBeta(Integer gateWayId,List<Integer> sensorPointIds,String start,String end);

    List<SensorData> migrateData(int skip, int limit);
}
