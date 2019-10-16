package com.warn.sensordata.dao.impl;

import com.warn.dao.SensorDataDao;
import com.warn.entity.SensorData;
import com.warn.exception.GetMDBException;
import com.warn.exception.WarnException;
import com.warn.sensordata.dao.SensorMogoSecDao;
import com.warn.mongodb.model.SensorCollection;
import com.warn.util.DynamicDataSource;
import com.warn.util.DynamicDataSourceHolder;
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

    public List<SensorCollection> findByTime(String start, String end, Integer gatewayID, List<Integer> closeWarns,Integer type) throws GetMDBException,WarnException {
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
            List<SensorCollection> sensorCollections = new ArrayList<>();
            if(type == 1){
                sensorCollections = sensorDataDao.findByTime_fg(gatewayIDs,startTime,endTime);
            }else
                sensorCollections = sensorDataDao.findByTime(gatewayIDs,startTime,endTime);
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

    public List<SensorData> migrateData(int skip,int limit){
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.with(new Sort(Sort.Direction.DESC, "_id"));
        List<SensorData>  sensorDatas = getMongoTemplate().find(query.skip(skip).limit(limit), SensorData.class);
        return sensorDatas;
    }

    public List<SensorCollection> findToStatisticBeta(Integer gateWayId,List<Integer> sensorPointIds,String start,String end){

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        SensorData sensorData = new SensorData();
        sensorData.setGatewayID(gateWayId);
        sensorData.setSensorPointIDs(sensorPointIds);
        try{
            Long startTime = date.parse(start).getTime();
            Long endTime = date.parse(end).getTime();
            List<SensorCollection> sensorCollections = new ArrayList<>();
            if(sensorData.getGatewayID() != 43)
                sensorCollections = sensorDataDao.findToStatisticBeta(sensorData,startTime,endTime);
            else
                sensorCollections = sensorDataDao.findToStatisticBetaFt(sensorData,startTime,endTime);
            if(sensorCollections.size() != 0)
                Collections.reverse(sensorCollections);
            return sensorCollections;
        }catch (Exception e){
            throw new WarnException("mysql2 inner error"+e.getMessage());
        }
//        Query query;
//        Criteria c;
//        c = Criteria.where("gatewayID").is(gateWayId).and("sensorPointID").in(sensorPointIds).and("sensorID").in(1,5).and("timeString").gte(start).lte(end);
//        query = new Query(c);
//        if(query==null){
//            throw new GetMDBException("query为null");
//        }
//        return getMongoTemplate().find(query, SensorCollection.class);
    }

}
