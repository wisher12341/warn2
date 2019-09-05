package com.warn.service.impl;

import com.warn.dao.DataDao;
import com.warn.dao.RoomDao;
import com.warn.dao.StatisticDao;
import com.warn.dao.ThresholdDao;
import com.warn.dto.DwrData;
import com.warn.dto.Warn_statistic;
import com.warn.dto.visual.AreaVisual;
import com.warn.dto.visual.AreaVisualList;
import com.warn.dto.visual.AreaVisualLists;
import com.warn.dwr.Remote;
import com.warn.entity.*;
import com.warn.mongodb.model.SensorCollection;
import com.warn.sensordata.dao.SensorMogoSecDao;
import com.warn.service.SensorService;
import com.warn.service.StatisticService;
import com.warn.service.WarnHistoryService;
import com.warn.util.DynamicDataSourceHolder;
import com.warn.util.Tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    ThresholdDao thresholdDao;
    @Autowired
    WarnHistoryService warnHistoryService;



   // public static Map<OldMan,Boolean> doorS=new HashMap<OldMan,Boolean>();//存储老人是否出门了（门动的时间）  出门了就不在计数;
   public static Boolean out = false;
   public static Boolean judge = false;
   public static Boolean key = true;
   @Override
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
                if(sensorCollections.size() - i < rSize * 2 * 4)
                    judge = true;
                else
                    for(int j = rSize * 2 * 2; j < rSize * 8 ; j++){ //出门判断(有门的霍尔数据的2-4分钟内，房间内 没有人的数据 的话，判断为出门)
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

       List<AreaStatistic> areaStatisticr = statisticDao.getStatisticByDate(today,oldMan.getOid());
       if(areaStatisticr.size() == 0){
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

    @Override
    public List<AreaVisualList> getStatisticArea(Integer oid,Integer rid,String time){
        //OldMan oldMan = dataDao.getOldManByOid(oid);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String current =  date.format(new Date());//现在的时间
        String today = current.split(" ")[0];
        List<AreaVisualList> areaVisualLists = new ArrayList<>();
        List<AreaStatistic> areaStatistics = new ArrayList<>();
        Room room1 = new Room();
        if(time == null)
            areaStatistics = statisticDao.getStatisticInfo(today,oid);//就算取不到也不会报Null 的异常
        else
            areaStatistics = statisticDao.getStatisticInfo(time,oid);
         if(areaStatistics.size() != 0)
             for(AreaStatistic areaStatistic:areaStatistics){
                 Room room = roomDao.getRoomById(areaStatistic.getRoomId());
                 AreaVisualList areaVisualList = new AreaVisualList();
                 List<AreaStatistic> areaStatistics1 = new ArrayList<>();
                 areaStatistics1.clear();
                 areaStatistics1.add(areaStatistic);
                 List<AreaVisual> areaVisuals = setVisuals(areaStatistics1,room);
                 areaVisualList.setAreaVisuals(areaVisuals);
                 areaVisualList.setRoomName(room.getRoomName());
                 areaVisualLists.add(areaVisualList);
              }
          else
         {
             AreaVisualList areaVisualList = new AreaVisualList();
             List<AreaVisual> areaVisuals = setVisuals(areaStatistics,room1);
             areaVisualList.setAreaVisuals(areaVisuals);
             areaVisualLists.add(areaVisualList);
         }
        return areaVisualLists;
    }

    @Override
    public List<AreaVisualLists> getStatisticAreaList(Integer oid,Integer rid) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat week = new SimpleDateFormat("EEEE");
        date.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        week.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date current = new Date();//现在的时间
        List<String> dates = new ArrayList<>();
        List<Room> roomList = roomDao.getAllRoomByOldManId(oid);
        List<AreaVisualLists> visualLists = new ArrayList<>();
        for(int j = 0; j < 7; j++){
            dates.add(date.format(current).split(" ")[0]);
            current = new Date(current.getTime() - 86400000);
        }
        for(Room room:roomList){
            AreaVisualLists visualList = new AreaVisualLists();
            List<AreaStatistic> areaStatistics = statisticDao.getStatisitcInfos(dates,oid,room.getRid());//就算取不到也不会报Null 的异常
            List<AreaVisualList> areaVisualLists = new ArrayList<>();
            for(AreaStatistic areaStatistic:areaStatistics){
                AreaVisualList areaVisualList = new AreaVisualList();
                areaVisualList.setDate(areaStatistic.getDate()+"("+week.format(current)+")");
                List<AreaStatistic> areaStatistics1 = new ArrayList<>();
                areaStatistics1.clear();
                areaStatistics1.add(areaStatistic);
                List<AreaVisual> areaVisuals = setVisuals(areaStatistics1,room);
                areaVisualList.setAreaVisuals(areaVisuals);
                areaVisualLists.add(areaVisualList);
                current = new Date(current.getTime() - 86400000);
            }
            Collections.reverse(areaVisualLists);
            visualList.setAreaVisual(areaVisualLists);
            visualList.setRoomName(room.getRoomName());
            visualLists.add(visualList);
        }
        return visualLists;

    }
    @Override
    public void transferData(List<SensorData> sensorDatas){

    }

    @Override
    public void checkStatistic(Integer oid,Integer rid){
        try {
            Random r = new Random();
            TimeUnit.SECONDS.sleep(r.nextInt(20));
        }catch(Exception e){
            System.out.println("timeunit wrong");
        }
        if(key){
            key  = false;
        }else
            return;
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date current = new Date();//现在的时间
        List<AreaVisualList> areaVisualLists = new ArrayList<>();
        List<AreaVisual> areaVisuals = new ArrayList<>();
        current = new Date(current.getTime() - 86400000);
        String today =  date.format(new Date()).split(" ")[0];
        List<AreaStatistic> areaStatistic1 =  statisticDao.getStatisticInfo(today,oid);//就算取不到也不会报Null 的异常
        Room room = roomDao.getRoomById(rid);
        List<AreaVisual> areaVisuals1 = setVisuals(areaStatistic1,room);
        for (int j = 0; j < 7; j++) {
            AreaVisualList areaVisualList = new AreaVisualList();
            String time = date.format(current).split(" ")[0];
            areaVisualList.setDate(time);
            List<AreaStatistic> areaStatistic = statisticDao.getStatisticInfo(time, oid);//就算取不到也不会报Null 的异常
            if(areaStatistic.get(0).getNormal() == 0){
                areaVisuals = setVisuals(areaStatistic,room);
                areaVisualList.setAreaVisuals(areaVisuals);
                areaVisualLists.add(areaVisualList);
                current = new Date(current.getTime() - 86400000);
            }else{
                j--;
            }
        }
        List<Threshold_statistic> threshold_statistics = thresholdDao.getThresholdSByRid(rid);
        Integer[] average = {0,0,0,0,0,0,0,0,0,0,0};
        Integer[] standard = {0,0,0,0,0,0,0,0,0,0,0};
        for(int i = 0;i <= 10;i++){
            Integer sum = 0;
            for(int j = 0;j < 7;j++){
                sum = sum + areaVisualLists.get(j).getAreaVisuals().get(i).getSumTime();
            }
            average[i] = sum / 7;
            double sumd = 0;
            double two = 2;
            for(int j = 0;j < 7;j++){
                sumd = sumd + Math.pow(areaVisualLists.get(j).getAreaVisuals().get(i).getSumTime().doubleValue()-average[i].doubleValue(),two);
            }
            standard[i] = (int)Math.sqrt(sumd/7);
            AreaVisual areaVisual = areaVisuals1.get(i);
            Integer deviation = Math.abs(areaVisual.getSumTime() - average[i]);
            Integer threshold = getThreshold(threshold_statistics,areaVisual.getAreaNumber());
            if(threshold != null)
                if((threshold == 404 && deviation > standard[i]) || (threshold!=404 && deviation > threshold)){  //报警
                    Warn_statistic warn_statistic = new Warn_statistic();
                    OldMan oldMan = dataDao.getOldManByOid(oid);
                    warn_statistic.setOldMan(oldMan);
                    warn_statistic.setRoom(room.getRoomName());
                    warn_statistic.setAreaInfo(areaVisual.getAreaName());
                    warn_statistic.setDate(today);
                    warn_statistic.setDeviation(deviation);
                    DwrData dwrData = new DwrData();
                    dwrData.setType("warn_statistic");
                    AreaStatistic areaStatistic = areaStatistic1.get(0);
                    areaStatistic.setNormal(1);
                    statisticDao.updateNormal(areaStatistic);
                    dwrData.setWarn_statistic(warn_statistic);
                    warnHistoryService.addWarnHistory(dwrData);
                    Remote.noticeNewOrder(dwrData);
                }
        }
        key = true;
    }

    private Integer getThreshold(List<Threshold_statistic> threshold_statistics,Integer num){
       for(Threshold_statistic threshold_statistic:threshold_statistics){
           if(threshold_statistic.getArea().equals(num))
               return num;
       }
       return null;
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
