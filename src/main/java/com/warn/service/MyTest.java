package com.warn.service;

import com.warn.dao.SensorDataDao;
import com.warn.mongodb.model.SensorCollection;
import com.warn.util.DynamicDataSourceHolder;
import com.warn.util.Tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
@Repository
public class MyTest {

    @Autowired
    SensorDataDao sensorDataDao;

    @Autowired
    @Resource(name = "secondaryMongoTemplate")
    private MongoTemplate mongoTemplate;

    @Test
    public void testDataBase(){
        DynamicDataSourceHolder.setDataSource("defaultDataSource");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        SensorCollection sensorCollection = sensorDataDao.getDateEndRecord(Tool.getYesDate());
        sensorCollection.setTimeString(sdf.format(date));
        sensorDataDao.addDateRecord(sensorCollection);
    }

    @Test
    public void testDataBaseTwo(){
        Date date = new Date();
        DynamicDataSourceHolder.setDataSource("sensorDataSource");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        List<SensorCollection> sensorCollections = sensorDataDao.getDateEndGateway(Tool.getYesDate());
        for(SensorCollection sensorCollection:sensorCollections){
            SensorCollection sensorCollection1 = sensorDataDao.getDateGateway(sensorCollection);
            sensorCollection1.setTimeString(sdf.format(date));
            sensorDataDao.addDateGateway(sensorCollection1);
        }
    }

    @Test
    public void testMigrate(){
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.with(new Sort(Sort.Direction.DESC, "_id"));
        List<SensorCollection>  sensorCollections = getMongoTemplate().find(query.skip(2500000).limit(2501000), SensorCollection.class);
        sensorCollections.get(0);



    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }



}
