package com.warn.sensordata.dao.impl;

import com.warn.dao.SensorDataDao;
import com.warn.exception.GetMDBException;
import com.warn.exception.WarnException;
import com.warn.sensordata.dao.SensorMogoSecDao;
import com.warn.mongodb.model.SensorCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2017/5/5.
 */
@Repository
public class SensorMogoSecDaoImpl implements SensorMogoSecDao {

    @Autowired
    @Resource(name = "secondaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Autowired
    SensorDataDao sensorDataDao;

    public List<SensorCollection> findByTime(String start, String end, Integer gatewayID, List<Integer> closeWarns) throws GetMDBException,WarnException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));


        try {
            Long startTime = sdf.parse(start).getTime();
            Long endTime = sdf.parse(end).getTime();
            List<Integer> gatewayIDs = new ArrayList<>();
            if (gatewayID != null) {
                gatewayIDs.add(gatewayID);
            }
            for (Integer oid : closeWarns) {
                gatewayIDs.add(oid);
            }
            List<SensorCollection> sensorCollections = sensorDataDao.findByTime(gatewayIDs,startTime, endTime);
            if(sensorCollections.size() != 0)
                Collections.reverse(sensorCollections);
            return sensorCollections;
        }catch (Exception e){
            throw new WarnException("mysql2 inner error"+e.getMessage());
        }
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public List<SensorCollection> findToStatistic(Integer gateWayId,List<Integer> sensorPointIds,Integer limit){


        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "_id"));
        Criteria c = Criteria.where("gatewayID").is(gateWayId).and("sensorPointID").in(sensorPointIds).and("sensorID").in(1,5);
        query.addCriteria(c);
        return getMongoTemplate().find(query.skip(0).limit(limit), SensorCollection.class);
    }

    public List<SensorCollection> findToStatisticBeta(Integer gateWayId,List<Integer> sensorPointIds,String start,String end){
        Query query;
        Criteria c;
        c = Criteria.where("gatewayID").is(gateWayId).and("sensorPointID").in(sensorPointIds).and("sensorID").in(1,5).and("timeString").gte(start).lte(end);
        query = new Query(c);
        if(query==null){
            throw new GetMDBException("query为null");
        }
        return getMongoTemplate().find(query, SensorCollection.class);
    }

}
