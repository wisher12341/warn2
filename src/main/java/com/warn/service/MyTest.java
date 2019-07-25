package com.warn.service;

import com.warn.dao.SensorDataDao;
import com.warn.mongodb.model.SensorCollection;
import com.warn.util.DynamicDataSourceHolder;
import com.warn.util.Tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class MyTest {

    @Autowired
    SensorDataDao sensorDataDao;


    @Test
    public void testDataBase(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        String yesterday = sdf.format(calendar.getTime());
        DynamicDataSourceHolder.setDataSource("sensorDataSource");
        SensorCollection sensorCollection = sensorDataDao.getDateEndRecord(yesterday);
        sensorDataDao.addDateRecord(sensorCollection);
    }

    @Test
    public void testDataBaseTwo(){
        DynamicDataSourceHolder.setDataSource("sensorDataSource");
        List<SensorCollection> sensorCollections = sensorDataDao.getDateEndGateway(Tool.getYesDate());
        for(SensorCollection sensorCollection:sensorCollections){
            SensorCollection sensorCollection1 = sensorDataDao.getDateGateway(sensorCollection);
            sensorDataDao.addDateGateway(sensorCollection1);
        }
    }



}
