package com.warn.mongodb.dao;

import com.warn.entity.Equipment;
import com.warn.mongodb.model.SensorCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/5/5.
 */
public interface SensorMogoDao {


    MongoTemplate getMongoTemplate();

    List<SensorCollection> findByTime(String start,String end,Integer gatewayID,List<Integer> closeWarns);
}
