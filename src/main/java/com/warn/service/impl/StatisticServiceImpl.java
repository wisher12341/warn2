package com.warn.service.impl;

import com.warn.dao.DataDao;
import com.warn.dao.RoomDao;
import com.warn.dao.StatisticDao;
import com.warn.dto.visual.AreaVisual;
import com.warn.dto.visual.AreaVisualList;
import com.warn.entity.AreaStatistic;
import com.warn.entity.OldMan;
import com.warn.entity.Room;
import com.warn.mongodb.model.SensorCollection;
import com.warn.mongodbSec.dao.SensorMogoSecDao;
import com.warn.service.StatisticService;
import com.warn.util.Tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.geom.Area;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticServiceImpl implements StatisticService  {
    @Autowired
    SensorMogoSecDao sensorMogoSecDao;
    @Autowired
    DataDao dataDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    StatisticDao statisticDao;
   // public static Map<OldMan,Boolean> doorS=new HashMap<OldMan,Boolean>();//存储老人是否出门了（门动的时间）  出门了就不在计数;
   public static Boolean out = false;
   public static Boolean judge = false;
   @Override
   @Transactional
    public void getStatisticData(Integer gatewayId){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String current =  date.format(new Date());//现在的时间
        String yesterday = date.format(new Date().getTime() - 86400000);
        String start = yesterday.split(" ")[0] + " " + "20:00:00";
        String end = current.split(" ")[0] + " " + "20:00:00";
        String today = current.split(" ")[0];
        OldMan oldMan = dataDao.getOldManByGatewayID(gatewayId);
        List<Room> roomList = roomDao.getAllRoomByOldManId(oldMan.getOid());
        Integer rSize = roomList.size();
        List<AreaStatistic> areaStatistics = statisticDao.getStatisticByDate(today,oldMan.getOid());
        Integer areas[][] = new Integer[11][11];
        String statisticInfo[] = new String[11];
        for(Room room:roomList) {//更新，先获取今天的数据，如果没有就从零开始
            if (areaStatistics.size() == 0)
                for (int j = 0; j <= 10; j++) {
                    areas[Integer.parseInt(room.getCollectId())][j] = 0;
                    areas[0][10] = 0;
                    areas[0][0] = 0;
                }
            else{
                for (AreaStatistic areaStatistic : areaStatistics) {
                    if(areaStatistic.getRoomId() == room.getRid()){
                        String tempArea[] = areaStatistic.getStatisticInfo().split("#");
                        areas[Integer.parseInt(room.getCollectId())][0] = 0;
                        for(int j = 0; j <= 10 ; j++)
                            areas[Integer.parseInt(room.getCollectId())][j] = Integer.parseInt(tempArea[j]);
                    }
                }
                start = areaStatistics.get(0).getTime();
            }

        }
        List<Integer> sensorPointIds = new ArrayList<>();
        for(Room room:roomList){
            sensorPointIds.add(Integer.parseInt(room.getCollectId()));
        }
//            String now = date.format(new Date());//现在的时间
//            String startOfDay = now.split(" ")[0] + " " + "00:00:00";//当天的开始
//            String endOfDay = now.split(" ")[0] + " " + "23:59:59";//当天的结束
//            Long fTime = date.parse(startOfDay).getTime();//转换成时间戳
//            Long nTime = date.parse(now).getTime();
//            Long eTime = date.parse(endOfDay).getTime();
        //Integer limit = 3600 / 30 * roomList.size()+20;
        // List<SensorCollection> sensorCollections = sensorMogoSecDao.findToStatistic(gatewayId, sensorPointIds,limit);
        List<SensorCollection> sensorCollections = sensorMogoSecDao.findToStatisticBeta(gatewayId,sensorPointIds,start,end);
        Set<String> zero = new HashSet<>();
        // for(int i = limit - 1; i >=0 ; i--){
       Integer tempY = 10;
       Integer tempX = 0;
        for(int i=0;i<sensorCollections.size();i++){
            SensorCollection sensorCollection = sensorCollections.get(i);
            if(judge)//衔接上一个时间段的出门的判断
                for(int j = i;j <= rSize * 2 * 2;j ++){
                    SensorCollection sensorCollection1 = sensorCollections.get(i+j);
                    if(sensorCollection1.getSensorID() == 1)
                        if(sensorCollection1.getSensorData() != 0){
                            out = false;//没出门
                            break;
                        }
                    if(j == rSize * 4)
                        out = true;//出门了
                }
            if(sensorCollection.getSensorID() == 1){
                if(sensorCollection.getSensorData() == 0){
                    zero.add(sensorCollection.getSensorPointID());
                    if(zero.size() == rSize ){//出门了就户外，没出门就算其他区域
                        if(!out)
                            areas[tempX][tempY]++;
                        else
                            areas[0][0]++;
                        zero.clear();
                    }
                }
                else {
                    areas[Integer.parseInt(sensorCollection.getSensorPointID())][sensorCollection.getSensorData()]++;//在原来的数据上加
                    tempX = Integer.parseInt(sensorCollection.getSensorPointID());
                    tempY = sensorCollection.getSensorData();
                    zero.clear();
                    out = false;
                }
            }else{
                if(i <= rSize * 2 * 4)
                    judge = true;
                else
                    for(int j = rSize * 2 * 2; j <= rSize * 8 ; j++){ //出门判断(有门的霍尔数据的2-4分钟内，房间内 没有人的数据 的话，判断为出门)
                        SensorCollection sensorCollection1 = sensorCollections.get(i+j);
                        if(sensorCollection1.getSensorID() == 1)
                            if(sensorCollection1.getSensorData() != 0){
                                out = false;//没出门
                                break;
                            }
                        if(j == rSize * 8)
                            out = true;//出门了
                    }

            }
//                Long fiTime = date.parse(firstTime).getTime()

        }
        for(Room room:roomList){
            Integer i = Integer.parseInt(room.getCollectId());
            String temp = areas[0][0].toString() + "#";
            for(Integer j = 1;j <= 9;j++){
                temp = temp  + areas[i][j].toString() + "#";
            }
            temp = temp + areas[0][10].toString();
            statisticInfo[i] = temp;
        }
        if(areaStatistics.size() == 0){
            for(Room room:roomList){
                AreaStatistic areaStatistic = new AreaStatistic();
                areaStatistic.setDate(today);
                areaStatistic.setTime(current);
                areaStatistic.setOid(oldMan.getOid());
                areaStatistic.setRoomId(room.getRid());
                areaStatistic.setStatisticInfo(statisticInfo[Integer.parseInt(room.getCollectId())]);
                statisticDao.addAreaStatistic(areaStatistic);
            }
        }else{
            for(AreaStatistic areaStatistic:areaStatistics){
                for(Room room:roomList){
                    if(room.getRid() == areaStatistic.getRoomId())
                        areaStatistic.setStatisticInfo(statisticInfo[Integer.parseInt(room.getCollectId())]);
                }
                areaStatistic.setTime(current);
                statisticDao.updateAreaStatistic(areaStatistic);
            }
        }
    }

    public List<AreaVisual> getStatisticArea(Integer oid,Integer rid){
        //OldMan oldMan = dataDao.getOldManByOid(oid);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String current =  date.format(new Date());//现在的时间
        String today = current.split(" ")[0];
        List<AreaStatistic> areaStatistic = statisticDao.getStatisticInfo(today,oid,rid);//就算取不到也不会报Null 的异常
         Room room = roomDao.getRoomById(rid);
        List<AreaVisual> areaVisuals = setVisuals(areaStatistic,room);
        return areaVisuals;


    }

    public List<AreaVisualList> getStatisticAreaList(Integer oid,Integer rid) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date current = new Date();//现在的时间
        List<AreaVisualList> areaVisualLists = new ArrayList<>();
        for (int j = 0; j < 7; j++) {
            AreaVisualList areaVisualList = new AreaVisualList();
            String time = date.format(current).split(" ")[0];
            areaVisualList.setDate(time);
            List<AreaStatistic> areaStatistic = statisticDao.getStatisticInfo(time, oid, rid);//就算取不到也不会报Null 的异常
            Room room = roomDao.getRoomById(rid);
            List<AreaVisual> areaVisuals = setVisuals(areaStatistic,room);
            areaVisualList.setAreaVisuals(areaVisuals);
            areaVisualLists.add(areaVisualList);
            current = new Date(current.getTime() - 86400000);
        }
        return areaVisualLists;

    }
    private List<AreaVisual> setVisuals(List<AreaStatistic> areaStatistic,Room room){
        List<AreaVisual> areaVisuals = new ArrayList<>();
        if (areaStatistic.size() != 0) {
            //List<Room> roomList = roomDao.getAllRoomByOldManId(oldMan.getOid());
            String areaTime[] = areaStatistic.get(0).getStatisticInfo().split("#");
            for (int i = 0; i < areaTime.length; i++) {
                AreaVisual areaVisual = new AreaVisual();
                areaVisual.setAreaName(Tool.getPositionInfo(i, room));
                areaVisual.setAreaNumber(i);
                areaVisual.setSumTime(Integer.parseInt(areaTime[i]) / 2);//转化成分钟
                areaVisuals.add(areaVisual);
            }
        } else {
            for (int i = 0; i <= 10; i++) {
                AreaVisual areaVisual = new AreaVisual();
                areaVisual.setAreaName(Tool.getPositionInfo(i, room));
                areaVisual.setAreaNumber(i);
                areaVisual.setSumTime(0);
                areaVisuals.add(areaVisual);
            }
        }
        return areaVisuals;
    }

}
