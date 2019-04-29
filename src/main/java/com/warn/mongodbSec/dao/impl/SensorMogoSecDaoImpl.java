package com.warn.mongodbSec.dao.impl;

import com.warn.exception.GetMDBException;
import com.warn.exception.WarnException;
import com.warn.mongodbSec.dao.SensorMogoSecDao;
import com.warn.mongodb.model.SensorCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/5/5.
 */
@Repository
public class SensorMogoSecDaoImpl implements SensorMogoSecDao {

    @Autowired
    @Resource(name = "secondaryMongoTemplate")
    private MongoTemplate mongoTemplate;


    public List<SensorCollection> findByTime(String start, String end, Integer gatewayID, List<Integer> closeWarns) throws GetMDBException,WarnException {

        //时间 和 设备ID 都要转换成int型  映射到mongodb数据库

        try {
//            String[] startTimes = start.split(" ");
//            String[] startYMD = startTimes[0].split("-");
//            String[] startHMS = startTimes[1].split(":");
//            int startYear = Integer.parseInt(startYMD[0]);
//            int startMonth = Integer.parseInt(startYMD[1]);
//            int startDay = Integer.parseInt(startYMD[2]);
//            int startHour = Integer.parseInt(startHMS[0]);
//            int startMinute = Integer.parseInt(startHMS[1]);
//            int startSecond = Integer.parseInt(startHMS[2]);
//
//            String[] endTimes = end.split(" ");
//            String[] endYMD = endTimes[0].split("-");
//            String[] endHMS = endTimes[1].split(":");
//            int endYear = Integer.parseInt(endYMD[0]);
//            int endMonth = Integer.parseInt(endYMD[1]);
//            int endDay = Integer.parseInt(endYMD[2]);
//            int endHour = Integer.parseInt(endHMS[0]);
//            int endMinute = Integer.parseInt(endHMS[1]);
//            int endSecond = Integer.parseInt(endHMS[2]);

            List<Integer> gatewayIDs=new ArrayList<>();
//            for (Equipment equipment : equipments) {
//                eids.add(Integer.parseInt(equipment.getEid()));
//            }
            if(gatewayID!=null) {
                gatewayIDs.add(gatewayID);
            }
            for(Integer oid:closeWarns){
                gatewayIDs.add(oid);
            }


            //构造查询语句
            Query query;
            Criteria c;
            //正常时间区间
            Criteria c1;
            Criteria c2;
            Criteria c3;

            //特殊时间区间
            Criteria c1_1;
            Criteria c1_2;
            Criteria c2_1;
            Criteria c2_2;
            c=Criteria.where("timeString").gte(start).lte(end).and("gatewayID").in(gatewayIDs);
            query = new Query(c);

            //由于 数据库的的时间 分成了一个个字段
            //时间间隔：几分钟
//            if (startMonth > endMonth) {
//                //2017-12-31 23:58:00   2018-01-01 00:04:00的情况   startMonth>endMonth 必然 startDay>endDay 必然 startHour>endHour 必然 startMinute>endMinute
//                c1_1 = Criteria.where("year").gte(startYear).lte(endYear).and("month").is(startMonth).and("day").is(startDay).and("hour").is(startHour).and("minute").gt(startMinute).and("gatewayID").in(gatewayIDs);
//                c1_2 = Criteria.where("year").gte(startYear).lte(endYear).and("month").is(startMonth).and("day").is(startDay).and("hour").is(startHour).and("minute").is(startMinute).and("second").gte(startSecond).and("gatewayID").in(gatewayIDs);
//                c2_1 = Criteria.where("year").gte(startYear).lte(endYear).and("month").is(endMonth).and("day").is(endDay).and("hour").is(endHour).and("minute").lt(endMinute).and("gatewayID").in(gatewayIDs);
//                c2_2 = Criteria.where("year").gte(startYear).lte(endYear).and("month").is(endMonth).and("day").is(endDay).and("hour").is(endHour).and("minute").is(endMinute).and("second").lte(endSecond).and("gatewayID").in(gatewayIDs);
//
//                c = new Criteria();
//
//                query = new Query(c.orOperator(c1_1, c1_2, c2_1, c2_2));
//            } else {
//                //同年  月：endMonth>=startMonth
//                if (startDay > endDay) {
//                    //2017-11-31 23:58:00  2017-12-01 00:04:00的情况  startDay>endDay 必然 startHour>endHour 必然 startMinute>endMinute
//                    c1_1 = Criteria.where("year").is(endYear).and("month").is(startMonth).and("day").is(startDay).and("hour").is(startHour).and("minute").gt(startMinute).and("gatewayID").in(gatewayIDs);
//                    c1_2 = Criteria.where("year").is(endYear).and("month").is(startMonth).and("day").is(startDay).and("hour").is(startHour).and("minute").is(startMinute).and("second").gte(startSecond).and("gatewayID").in(gatewayIDs);
//                    c2_1 = Criteria.where("year").is(endYear).and("month").is(endMonth).and("day").is(endDay).and("hour").is(endHour).and("minute").lt(endMinute).and("gatewayID").in(gatewayIDs);
//                    c2_2 = Criteria.where("year").is(endYear).and("month").is(endMonth).and("day").is(endDay).and("hour").is(endHour).and("minute").is(endMinute).and("second").lte(endSecond).and("gatewayID").in(gatewayIDs);
//                    c = new Criteria();
//                    query = new Query(c.orOperator(c1_1, c1_2, c2_1, c2_2));
//                } else {
//                    //同年 月：endMonth>=startMonth  日：endDay>=startDay
//                    if (startHour > endHour) {
//                        //2017-11-20 23:58:00   2017-11-21 00:04:00的情况   startHour>endHour 必然 startMinute>endMinute
//                        c1_1 = Criteria.where("year").is(endYear).and("month").is(startMonth).and("day").is(startDay).and("hour").is(startHour).and("minute").gt(startMinute).and("gatewayID").in(gatewayIDs);
//                        c1_2 = Criteria.where("year").is(endYear).and("month").is(startMonth).and("day").is(startDay).and("hour").is(startHour).and("minute").is(startMinute).and("second").gte(startSecond).and("gatewayID").in(gatewayIDs);
//                        c2_1 = Criteria.where("year").is(endYear).and("month").is(endMonth).and("day").is(endDay).and("hour").is(endHour).and("minute").lt(endMinute).and("gatewayID").in(gatewayIDs);
//                        c2_2 = Criteria.where("year").is(endYear).and("month").is(endMonth).and("day").is(endDay).and("hour").is(endHour).and("minute").is(endMinute).and("second").lte(endSecond).and("gatewayID").in(gatewayIDs);
//                        c = new Criteria();
//
//                        query = new Query(c.orOperator(c1_1, c1_2, c2_1, c2_2));
//                    } else {
//                        //同年 月：endMonth>=startMonth  日：endDay>=startDay 时：endHour>=startHour
//                        if (startMinute > endMinute) {
//                            //2017-11-20 10:58:00   2017-11-20 11:04:00的情况
//                            c1_1 = Criteria.where("year").is(endYear).and("month").is(startMonth).and("day").is(startDay).and("hour").is(startHour).and("minute").gt(startMinute).and("gatewayID").in(gatewayIDs);
//                            c1_2 = Criteria.where("year").is(endYear).and("month").is(startMonth).and("day").is(startDay).and("hour").is(startHour).and("minute").is(startMinute).and("second").gte(startSecond).and("gatewayID").in(gatewayIDs);
//                            c2_1 = Criteria.where("year").is(endYear).and("month").is(endMonth).and("day").is(endDay).and("hour").is(endHour).and("minute").lt(endMinute).and("gatewayID").in(gatewayIDs);
//                            c2_2 = Criteria.where("year").is(endYear).and("month").is(endMonth).and("day").is(endDay).and("hour").is(endHour).and("minute").is(endMinute).and("second").lte(endSecond).and("gatewayID").in(gatewayIDs);
//                            c = new Criteria();
//
//                            query = new Query(c.orOperator(c1_1, c1_2, c2_1, c2_2));
//                        } else {
//                            //同年 月：endMonth>=startMonth  日：endDay>=startDay 时：endHour>=startHour 分：endMinute>=startMinute
//                            c1 = Criteria.where("year").gte(startYear).lte(endYear).and("month").gte(startMonth).lte(endMonth).and("day").gte(startDay).lte(endDay)
//                                    .and("hour").gte(startHour).lte(endHour).and("minute").is(startMinute).and("second").gte(startSecond).and("gatewayID").in(gatewayIDs);
//                            c2 = Criteria.where("year").gte(startYear).lte(endYear).and("month").gte(startMonth).lte(endMonth).and("day").gte(startDay).lte(endDay)
//                                    .and("hour").gte(startHour).lte(endHour).and("minute").is(endMinute).and("second").lte(endSecond).and("gatewayID").in(gatewayIDs);
//                            c3 = Criteria.where("year").gte(startYear).lte(endYear).and("month").gte(startMonth).lte(endMonth).and("day").gte(startDay).lte(endDay)
//                                    .and("hour").gte(startHour).lte(endHour).and("minute").gt(startMinute).lt(endMinute).and("gatewayID").in(gatewayIDs);
//                            c = new Criteria();
//
//                            query = new Query(c.orOperator(c1, c2, c3));
//                        }
//                    }
//                }
//
//            }
            if(query==null){
                throw new GetMDBException("query为null");
            }
            return getMongoTemplate().find(query, SensorCollection.class);
        }catch (GetMDBException e1){
            throw e1;
        }catch (Exception e){
            throw new WarnException("mongodb inner error"+e.getMessage());
        }
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public List<SensorCollection> findToStatistic(Integer gateWayId,Integer sensorPointId){

    }
}
