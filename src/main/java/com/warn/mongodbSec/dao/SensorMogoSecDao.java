package com.warn.mongodbSec.dao;

import com.warn.mongodb.model.SensorCollection;
import com.warn.mongodbSec.model.SecSensorCollection;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * Created by admin on 2017/5/5.
 */
public interface SensorMogoSecDao {


    MongoTemplate getMongoTemplate();

    List<SensorCollection> findByTime(String start, String end, Integer gatewayID, List<Integer> closeWarns);
}
