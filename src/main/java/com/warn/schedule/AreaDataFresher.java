package com.warn.schedule;

import com.warn.dao.DataDao;
import com.warn.dao.SensorDataDao;
import com.warn.entity.SensorData;
import com.warn.mongodb.model.SensorCollection;
import com.warn.service.StatisticService;
import com.warn.util.DynamicDataSourceHolder;
import com.warn.util.Tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class AreaDataFresher {
    @Autowired
    StatisticService statisticService;
    @Autowired
    SensorDataDao sensorDataDao;
    @Autowired
    DataDao dataDao;

    //@Scheduled(cron = "0 2 0-12 * * ?")
    public void updateArea(){
        List<Integer> gatewayIDs = dataDao.getOldsToStatistic();
        for(Integer gatewayID:gatewayIDs){
            statisticService.getStatisticData(gatewayID);
        }
//        statisticService.getStatisticData(48);
//        statisticService.getStatisticData(43);
//        statisticService.getStatisticData(42);

    }

    //@Scheduled(cron = "0 0 12 * * ?")
//    public void check(){
//        statisticService.checkStatistic(48,155);
//    }

  // @Scheduled(cron = "0 0 0-12 * * ?")
    public void updateArea2(){
        statisticService.getStatisticData(48);
        statisticService.getStatisticData(43);
        statisticService.getStatisticData(42);
    }

  // @Scheduled(cron = "0 0 17 * * *")
    public void recordForSql(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        SensorCollection sensorCollection = sensorDataDao.getDateEndRecord(Tool.getYesDate());
        sensorCollection.setTimeString(Tool.getYesDate());
        sensorDataDao.addDateRecord(sensorCollection);
    }
    //@Scheduled(cron = "0 2 17 * * *")
    public void recordForSql_fg(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        SensorCollection sensorCollection = sensorDataDao.getDateEndRecord_fg(Tool.getYesDate());
        sensorCollection.setTimeString(Tool.getYesDate());
        sensorDataDao.addDateRecord_fg(sensorCollection);
    }

   //@Scheduled(cron = "0 5 17 * * *")
    public void recordGatewayForSql(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        List<SensorCollection> sensorCollections = sensorDataDao.getDateEndGateway(Tool.getYesDate());
        for(SensorCollection sensorCollection:sensorCollections){
            SensorCollection sensorCollection1 = sensorDataDao.getDateGateway(sensorCollection);
            sensorCollection1.setTimeString(Tool.getYesDate());
            sensorDataDao.addDateGateway(sensorCollection1);
        }

    }


}
