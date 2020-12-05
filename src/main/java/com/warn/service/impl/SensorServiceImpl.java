package com.warn.service.impl;

import com.warn.controller.SystemController;
import com.warn.dao.*;
import com.warn.dto.*;
import com.warn.dwr.Remote;
import com.warn.entity.*;
import com.warn.entity.model.AreaModel;
import com.warn.entity.model.RoomModel;
import com.warn.exception.NullFromDBException;
import com.warn.exception.WarnException;
import com.warn.mongodb.model.SensorCollection;
import com.warn.service.*;
import com.warn.util.StaticVal;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2017/4/10.
 */
@Service
public class SensorServiceImpl implements SensorService{

    @Autowired
    SmsServiceImpl smsService;
    @Autowired
    MapDao mapDao;

    @Autowired
    CommonService commonService;

    //行为
    public static Map<OldMan,ScheduledExecutorService> timer=new HashMap<>();//存放各个老人行为的定时任务
    public static Map<OldMan,Integer> threshold1=new HashMap<>();//存放一级预警的阈值
    public static Map<OldMan,Integer> threshold2=new HashMap<>();//存放二级预警的阈值
    public static Map<OldMan,Warn> warnMap=new HashMap<>();//存放老人的预警模型
//    private static Map<OldMan,String> noMove=new HashMap<OldMan,String>();//存储老人不动的最初时间 时 分 秒;
    private static Map<OldMan,Integer> prePosition = new HashMap<>();//存放老人的上一个位置
    private static Map<Integer,Integer> roomPosition = new HashMap<>();//房间内的位置信息
    private static Map<Integer,Map<Integer,Integer>> roomPs = new HashMap<>();
//    private static Map<OldMan,Room> noMovaRoom=new HashMap<OldMan, Room>();//存储老人不动之前所在的房间
    public static Map<OldMan,Boolean> warn1=new HashMap<OldMan,Boolean>();//存储是否对该老人已经进行一级报警 如果已经报过警，则不重复一级报警
    public static Map<OldMan,Boolean> warn2=new HashMap<OldMan,Boolean>();//存储是否对该老人已经进行二级报警 如果已经报过警，则不重复二级报警
    //温度
    public static Map<Room,Boolean> wendu=new HashMap<Room,Boolean>();//存储是否对该老人已经进行温度报警 如果已经报过警，则不重复报警
    //光强
    public static Map<Room,Boolean> light=new HashMap<Room,Boolean>();//存储是否对该老人已经进行光强报警 如果已经报过警，则不重复报警
    public static Map<Room,String> lightRoom=new HashMap<Room,String>();//存储可能要预警的房间的最初时间
    public static Map<Room,ScheduledExecutorService> lightTimer=new HashMap<>();//存放各个老人房间光强持续时间的定时任务


    //出门
    public static Map<OldMan,String> door=new HashMap<OldMan,String>();//存储老人是否出门了（门动的时间）  出门了就不用进行行为预警了;
    public static Map<OldMan,Boolean> outdoorY=new HashMap<OldMan,Boolean>();//存储确定老人是否已出门  出门之后  不用进行行为检测
    public static Map<OldMan,Boolean> warnNoCome=new HashMap<OldMan,Boolean>();//存储是否对该老人已经进行了未回来报警 如果已经报过警，则不重复一级报警
    public static Map<OldMan,ScheduledExecutorService> timerDoor=new HashMap<>();//存放各个老人门的定时任务
    public static Map<OldMan,Integer> thresholdOutDoor=new HashMap<>();//存放多长时间算出门的预警的阈值
    public static Map<OldMan,Integer> thresholdNoComeDoor=new HashMap<>();//存放多长时间没回来预警的阈值

    @Autowired
    OutHistoryService outHistoryService;
    @Autowired
    DataDao dataDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    ThresholdDao thresholdDao;
    @Autowired
    ModelDao modelDao;
    @Autowired
    EquipDao equipDao;
    @Autowired
    WarnHistoryService warnHistoryService;

    //门不动时不发数据
    public void checkDoorData(List<SensorCollection> sensorCollections)throws NullFromDBException,WarnException{
        SystemController.logger.info("======================================门动预警=========================================================");
        try {
            final OldMan oldMan = dataDao.getOldManByGatewayID(sensorCollections.get(0).getGatewayID());
            if (oldMan == null) {
                throw new NullFromDBException("门动预警：找不到老人");
            }
            // 门动了
            for (SensorCollection sensorCollection : sensorCollections) {
                //只取门开的时刻  15门开 240关
                if (sensorCollection.getSensorData() == 15) {
                    if (timerDoor.get(oldMan) != null) {
                        timerDoor.get(oldMan).shutdown();
                        timerDoor.remove(oldMan);
                    }
                    if (outdoorY.get(oldMan) != null) {
                        outdoorY.remove(oldMan);
                    }
                    if (warnNoCome.get(oldMan) != null) {
                        warnNoCome.remove(oldMan);
                    }
                    final String time;
                    if(oldMan.getVersion() == 1)
                        time = sensorCollection.getHour() + ":" + sensorCollection.getMinute() + ":" + sensorCollection.getSecond();
                    else
                    {
                        String ctime[] = sensorCollection.getTime().split(" ");
                        time = ctime[1];
                    }

                    final Threshold_out threshold_out = thresholdDao.getDoorThresholdByOid(oldMan.getOid());
                    if (threshold_out == null) {
                        throw new NullFromDBException("门动预警：找不到阈值");
                    }
                    thresholdOutDoor.put(oldMan, threshold_out.getOutThreshold());
                    thresholdNoComeDoor.put(oldMan, threshold_out.getNoComeThreshold());

                    Runnable runnable = new Runnable() {
                        public void run() {
                            Date d = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                            String currentTime = sdf.format(d);
                            SystemController.logger.info("当前时间：" + currentTime + "   门动时间：" + time);
                            int value = intervalTime(currentTime, time) / 60;//当前时间与最初时间的差值 单位分钟
                            SystemController.logger.info("老人已经出门：" + value + "分钟");
                            if (warnNoCome.get(oldMan) != null) {
                                SystemController.logger.info("已进行了出门未归预警 则停止定时任务");
                                if (timerDoor.get(oldMan) != null) {
                                    timerDoor.get(oldMan).shutdown();
                                }
                            } else {
                                if (outdoorY.get(oldMan) == null) {
                                    SystemController.logger.info("还没进行出门报告  value=" + value + " 多久算 出门阈值=" + thresholdOutDoor.get(oldMan));
                                    if (value >= thresholdOutDoor.get(oldMan)) {
                                        SystemController.logger.info("老人已出门");
//                                        DwrData dwrData = new DwrData();
//                                        dwrData.setType("outdoor_out");
                                        Outdoor outdoor = new Outdoor();
                                        outdoor.setOldMan(oldMan);
                                        outdoor.setOut(time);
                                        outdoor.setTypeD("出门");
                                        outdoor.setDataD(time);
                                        outdoor.setReadD("否");
                                        outdoor.setThreshold_out(threshold_out);
                                        Date d1 = new Date();
                                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        sdf1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                                        String dateNowStr = sdf1.format(d1);
                                        outdoor.setTimeD(dateNowStr);
                                        //顺序不能变 为了得到outdoor的id值
                                        outHistoryService.addOutDoor(outdoor);
//                                        dwrData.setOutdoor(outdoor);
//                                        dwrData.setId(outdoor.getOdid());
                                        outdoorY.put(oldMan, true);
                                        //推送
//                                        Remote.noticeNewOrder(dwrData);
                                        SystemController.logger.info("已出门");
                                    }
                                } else {
                                    //已出门 多久没回来  预警
                                    SystemController.logger.info("已出门时间  value=" + value + "  多久没回来阈值=" + thresholdNoComeDoor.get(oldMan));
                                    if (value >= thresholdNoComeDoor.get(oldMan)) {
                                        SystemController.logger.info("老人还没回来，超时");
                                        DwrData dwrData = new DwrData();
                                        dwrData.setType("outdoor_nocome");
                                        Outdoor outdoor = new Outdoor();
                                        outdoor.setOldMan(oldMan);
                                        outdoor.setOut(time);
                                        outdoor.setThreshold_out(threshold_out);
                                        dwrData.setOutdoor(outdoor);
                                        warnNoCome.put(oldMan, true);
                                        //存入历史消息
                                        warnHistoryService.addWarnHistory(dwrData);
                                        SystemController.logger.info("已存入历史消息");
                                        //推送
                                        Remote.noticeNewOrder(dwrData);
                                        sendPost2(dwrData,StaticVal.url2);

                                        //地图更新
//                                        HouseMarker houseMarker=new HouseMarker();
//                                        houseMarker.setOid(oldMan.getOid());
//                                        houseMarker.setStyleIndex(8); //红色
//                                        houseMarker.setDetail("未归预警&nbsp;&nbsp;&nbsp;出门时刻："+dwrData.getOutdoor().getOut());
                                        oldMan.setStatus(2);
                                        mapUpdate(oldMan);

                                        //启动短信定时任务
                                        smsService.smsSwitch();
                                        SystemController.logger.info("已进行未归预警");
                                    }
                                }
                            }
                        }
                    };


                    if (door.get(oldMan) == null) {
                        door.put(oldMan, time);
                        ScheduledExecutorService service = Executors
                                .newSingleThreadScheduledExecutor();
                        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                        service.scheduleAtFixedRate(runnable, 1, 60, TimeUnit.SECONDS);
                        timerDoor.put(oldMan, service);
                    } else {
                        //判断 两次门动的时间差
                        int value = intervalTime(time, door.get(oldMan)) / 60;
                        SystemController.logger.info("两次出门时差：" + value + "分钟");
                        if (value >= thresholdOutDoor.get(oldMan)) {
                            //回来了  进行报告 并存储该行为时间段
                            SystemController.logger.info("回来了");
//                            DwrData dwrData = new DwrData();
//                            dwrData.setType("outdoor_come");
                            Outdoor outdoor = new Outdoor();
                            outdoor.setOldMan(oldMan);
                            outdoor.setDataD(door.get(oldMan) + "-" + time);
                            outdoor.setReadD("否");
                            outdoor.setTypeD("回来");
                            outdoor.setThreshold_out(threshold_out);
                            Date d = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                            String dateNowStr = sdf.format(d);
                            outdoor.setTimeD(dateNowStr);
                            //顺序不能变 为了得到outdoor的id值
                            outHistoryService.addOutDoor(outdoor);
//                            dwrData.setOutdoor(outdoor);
//                            dwrData.setId(outdoor.getOdid());
//                            Remote.noticeNewOrder(dwrData);
                            outdoorY.remove(oldMan);
                            door.remove(oldMan);
                        } else {
                            SystemController.logger.info("开门  但是没出门");
                            //没超过 多久算出门的阈值 则进行覆盖
                            door.put(oldMan, time);
                            if (timerDoor.get(oldMan) != null) {
                                timerDoor.get(oldMan).shutdown();
                                timerDoor.remove(oldMan);
                            }
                            ScheduledExecutorService service = Executors
                                    .newSingleThreadScheduledExecutor();
                            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                            service.scheduleAtFixedRate(runnable, 1, 60, TimeUnit.SECONDS);
                            timerDoor.put(oldMan, service);
                        }
                    }
                }
            }
        }catch (NullFromDBException e1){
            throw e1;
        }catch (Exception e){
            throw new WarnException("door inner error:"+e.getMessage());
        }
    }

    //出门 报告 在行为预警之前  检测的数据为  出门的数据 和有门的那个房间的数据
//    public void checkDoorData1(List<SensorCollection> sensorCollections){
//        OldMan oldMan=dataDao.getOldManByEquipId(sensorCollections.get(0).getSensorPointID());
//
//        for(SensorCollection sensorCollection:sensorCollections){
//            //门动的数据
//            if(sensorCollection.getSensorID()==5){
//                // 门动了
//                if(sensorCollection.getSensorData()==1){
//                    String currentTime=sensorCollection.getHour()+":"+sensorCollection.getMinute()+":"+sensorCollection.getSecond();
//                    if(door.get(oldMan)==null||door.get(oldMan).equals("")){
//                        door.put(oldMan,currentTime);
//                    }else {
//                        int dThreshold=thresholdDao.getDoorThresholdByOid(oldMan.getOid()).getOutThreshold();//出门阈值（过多久才算出门）单位分钟
//                        //判断 两次门动的时间差
//                        if(intervalTime(currentTime,door.get(oldMan))>=dThreshold*60){
//                            //回来了  进行报告 并存储该行为时间段
//                            DwrData dwrData=new DwrData();
//                            dwrData.setType("outdoor_come");
//                            Outdoor outdoor=new Outdoor();
//                            outdoor.setOldMan(oldMan);
//                            outdoor.setTimes(door.get(oldMan) + "-" + currentTime);
//                            outdoor.setReadO("是");
//                            //顺序不能变 为了得到outdoor的id值
//                            outDoorDao.addOutDoor(outdoor);
//                            dwrData.setOutdoor(outdoor);
//
//                            Remote.noticeNewOrder(dwrData);
//                            outdoorY.remove(oldMan);
//                            door.remove(oldMan);
//                        }else{
//                            door.remove(oldMan);
//                            door.put(oldMan,currentTime);
//                        }
//                    }
//                }
//            }
//            //与门相连接的 房间的行为数据
//            else{
//                //未确定老人出门
//                if(outdoorY.get(oldMan)==null) {
//                    if (door.get(oldMan) != null && !door.get(oldMan).equals("")) {
//                        //在房间没活动
//                        if (sensorCollection.getSensorData() == 240) {
//                            String currentTime = sensorCollection.getHour() + ":" + sensorCollection.getMinute() + ":" + sensorCollection.getSecond();
//                            int dThreshold = thresholdDao.getDoorThresholdByOid(oldMan.getOid()).getOutThreshold();//出门阈值（过多久才算出门）单位分钟
//                            if (intervalTime(currentTime, door.get(oldMan)) >= dThreshold * 60) {
//                                //超过阈值时间 进行报告 老人出门的行为  不存储
//                                DwrData dwrData = new DwrData();
//                                dwrData.setType("outdoor_out");
//                                Outdoor outdoor=new Outdoor();
//                                outdoor.setOldMan(oldMan);
//                                outdoor.setOut(currentTime);
//                                dwrData.setOutdoor(outdoor);
//                                Remote.noticeNewOrder(dwrData);
//                                outdoorY.put(oldMan, true);
//                            }
//                        }
//                        //门动之后在房间内有活动记录  则清除所有map
//                        else {
//                            door.remove(oldMan);
//                        }
//                    }
//                }
//            }
//        }
//
//
////        数据处理完后 如果确定出门且还没回来 进行预警判断
//        if(outdoorY.get(oldMan)==true){
//
//        }
//    }

    public void checkPositionData1(List<SensorCollection> sensorCollections) throws NullFromDBException, WarnException{
        SystemController.logger.info("======================================行为预警1.0=========================================================");
        try {
            final SensorDataDeal sensorDataDeal = new SensorDataDeal();
            SensorCollection sensorCollection = null;
            //只记录最后一个动的数据 显示的房间数据
            if(sensorCollections.size() >= 2)
            for (int i = sensorCollections.size() - 1; i >= 1; i--) {
                if ((sensorCollections.get(i).getSensorData()-sensorCollections.get(i-1).getSensorData()) != 0 && sensorCollections.get(i).getSensorData() != 0 ) {
                    sensorCollection = sensorCollections.get(i);
                    break;
                }
            }

            if (sensorCollection == null) {
                SystemController.logger.info("没有位置改变的数据");
                return;
//                sensorCollection = sensorCollections.get(sensorCollections.size() - 1);
            }
            Room room=roomDao.getRoomByGateWayId_SensorId(sensorCollection.getGatewayID(),sensorCollection.getSensorPointID());
            if(room==null){
                throw new NullFromDBException("行为预警：找不到房间");
            }
            sensorDataDeal.setActivityRoom(room);
            String position = null;
            if(sensorCollection.getSensorData() != 0)
            position = commonService.getPositionInfo(sensorCollection.getSensorData(),room);
            else
                position = commonService.getPositionInfo(10,room);
            OldMan oldMan=dataDao.getOldManByGatewayID(sensorCollection.getGatewayID());
            if(oldMan==null){
                throw new NullFromDBException("行为预警：找不到老人");
            }
            sensorDataDeal.setOldMan(oldMan);
            String ctime[] = sensorCollection.getTime().split(" ");
            sensorDataDeal.setTime(ctime[1]);
            SystemController.logger.info(sensorDataDeal.toString());
            if (warn1.get(sensorDataDeal.getOldMan()) != null) {
                warn1.remove(sensorDataDeal.getOldMan());
            }
            if (warn2.get(sensorDataDeal.getOldMan()) != null) {
                warn2.remove(sensorDataDeal.getOldMan());
            }
            if (timer.get(sensorDataDeal.getOldMan()) != null) {
                timer.get(sensorDataDeal.getOldMan()).shutdown();
                timer.remove(sensorDataDeal.getOldMan());
            }
            if (door.get(sensorDataDeal.getOldMan()) != null) {
                door.remove(sensorDataDeal.getOldMan());
            }
            if (warnNoCome.get(sensorDataDeal.getOldMan()) == null) {
                warnNoCome.remove(sensorDataDeal.getOldMan());
            }
            if (outdoorY.get(sensorDataDeal.getOldMan()) != null) {
                outdoorY.remove(sensorDataDeal.getOldMan());
            }
            if (timerDoor.get(sensorDataDeal.getOldMan()) != null) {
                timerDoor.get(sensorDataDeal.getOldMan()).shutdown();
                timerDoor.remove(sensorDataDeal.getOldMan());
            }

            Threshold threshold = thresholdDao.getThresholdByRoomId(sensorDataDeal.getActivityRoom().getRid());
            if(threshold==null){
                throw new NullFromDBException("行为预警：找不到阈值");
            }

            //判断 该时间是否在 该房间活动规律时间段内
            //活动该房间的活动规律信息
            RoomModel roomModel = modelDao.getRoomModelByRoomId(sensorDataDeal.getActivityRoom().getRid());
            if(roomModel==null){
                //如果没有该房间的活动模型的话  new 一个空
                roomModel=new RoomModel();
            }
            //暂不考虑 该时间段有时活动 有时休息
            MomentInTime momentInTime = new MomentInTime();
            MomentInTime momentInTimeA = new MomentInTime();
            MomentInTime momentInTimeR = new MomentInTime();
            String inTime = "";//在规律模型中的哪个时间段
            String active = roomModel.getActive();
            //遍历  房间规律模型的 活动时间段
            if (active != null && !active.equals("")) {
                ArrayList<String> activeTimes = new ArrayList<String>(Arrays.asList(roomModel.getActive().split("#"))); //xx:xx-yy:yy
                //判断是否第一个时间段与最后一个连着  比如睡觉00:00:00-06:30:00   20:30:00-24:00:00
                if (activeTimes.size() > 0) {
                    if (activeTimes.get(activeTimes.size() - 1).split("-")[1].equals("24:00") && activeTimes.get(0).split("-")[0].equals("00:00")) {
                        String time = activeTimes.get(activeTimes.size() - 1).split("-")[0] + "-" + activeTimes.get(0).split("-")[1];
                        activeTimes.remove(activeTimes.size() - 1);
                        activeTimes.remove(0);
                        activeTimes.add(time);
                    }
                    for (String activeTime : activeTimes) {
                        momentInTimeA = moment_timeDeal(sensorDataDeal.getTime(), activeTime);
                        if (!momentInTimeA.isInTime()) {
                            continue;
                        }
                        //在该时间段内  各时间段是没有重合的 所以只要一个时间段符合  其他时间段一定不符合 但是活动时间段可能与休息时间段重叠
                        else {
                            inTime = activeTime;
                            momentInTimeA.setFlag("a");
                            break;
                        }
                    }
                }
            }
            if (momentInTimeA.isInTime() == null) {
                momentInTimeA.setInTime(false);
            }
            if (!momentInTimeA.isInTime()) {
                String rest = roomModel.getRest();
                //遍历  房间规律模型的 休息时间段  活动时间段可能与休息时间段重叠
                if (rest != null && !rest.equals("")) {
                    ArrayList<String> restTimes = new ArrayList<String>(Arrays.asList(roomModel.getRest().split("#"))); //xx:xx-yy:yy
                    //判断是否第一个时间段与最后一个连着  比如睡觉00:00:00-06:30:00   20:30:00-24:00:00
                    if (restTimes.size() > 0) {
                        if (restTimes.get(restTimes.size() - 1).split("-")[1].equals("24:00") && restTimes.get(0).split("-")[0].equals("00:00")) {
                            String time = restTimes.get(restTimes.size() - 1).split("-")[0] + "-" + restTimes.get(0).split("-")[1];
                            restTimes.remove(restTimes.size() - 1);
                            restTimes.remove(0);
                            restTimes.add(time);
                        }
                        for (String restTime : restTimes) {
                            momentInTimeR = moment_timeDeal(sensorDataDeal.getTime(), restTime);
                            if (!momentInTimeR.isInTime()) {
                                continue;
                            }
                            //在该时间段内  各时间段是没有重合的 所以只要一个时间段符合  其他时间段一定不符合 但是活动时间段可能与休息时间段重叠
                            else {
                                inTime = restTime;
                                momentInTimeR.setFlag("r");
                                break;
                            }
                        }
                    }
                }
                if (momentInTimeR.isInTime() == null) {
                    momentInTimeR.setInTime(false);
                }
            }
            if (momentInTimeA != null && momentInTimeA.isInTime()) {
                momentInTime.setFlag("a");
                momentInTime.setInTime(true);
            } else if (momentInTimeR != null && momentInTimeR.isInTime()) {
//            if(momentInTime.getFlag().equals("a")){
//                momentInTime.setFlag("a&r");
//            }else{
                momentInTime.setFlag("r");
                momentInTime.setInTime(true);
//            }
            } else {
                momentInTime.setInTime(false);
            }
            Warn warn = new Warn();
            warn.setRoom(sensorDataDeal.getActivityRoom());
            warn.setOldMan(sensorDataDeal.getOldMan());
            warn.setTime(sensorDataDeal.getTime());
            warn.setPositon(position);
            //在规律模型中
            if (!inTime.equals("")) {
                warn.setInTime("true");
                warn.setTimes(inTime);
            } else {
                warn.setInTime("false");
            }

            //设置 阈值
            if (momentInTime.isInTime()) {
                //老人不动时 处于该房间规律模型的时间段内
                switch (momentInTime.getFlag()) {
                    case "a":
                        warn.setFlag("a");
                        threshold1.put(sensorDataDeal.getOldMan(), threshold.getA1Threshold() * 60);
                        threshold2.put(sensorDataDeal.getOldMan(), threshold.getA2Threshold() * 60);
                        break;
                    case "r":
                        warn.setFlag("r");
                        threshold1.put(sensorDataDeal.getOldMan(), threshold.getR1Threshold() * momentInTimeR.getTime() / 100);
                        threshold2.put(sensorDataDeal.getOldMan(), threshold.getR2Threshold() * momentInTimeR.getTime() / 100);
//                    break;
//                case "a&r":
//                    warn.setFlag("a&r");
//                    //该时间段 既可能休息又可能活动 去两者 值小的
//                    int a=threshold.getA1Threshold()*60;
//                    int r=threshold.getR1Threshold()*momentInTimeR.getTime();
//                    threshold1.put(sensorDataDeal.getOldMan(),(a>=r?r:a));
//                    int a2=threshold.getA2Threshold()*60;
//                    int r2=threshold.getR2Threshold()*momentInTime.getTime();
//                    threshold2.put(sensorDataDeal.getOldMan(),(a2>=r2?r2:a2));
                }
            } else {
                //不在规律模型的时间段
                threshold1.put(sensorDataDeal.getOldMan(), threshold.getN1Threshold() * 60);
                threshold2.put(sensorDataDeal.getOldMan(), threshold.getN2Threshold() * 60);
            }

            warnMap.put(sensorDataDeal.getOldMan(), warn);


            Runnable runnable = new Runnable() {
                public void run() {
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                    String currentTime = sdf.format(d);
                    SystemController.logger.info("当前时间：" + currentTime + "   最初不动时间：" + sensorDataDeal.getTime());
                    int value = intervalTime(currentTime, sensorDataDeal.getTime());
                    SystemController.logger.info("老人已经不动：" + (value / 60) + "分钟");
                    if (warn2.get(sensorDataDeal.getOldMan()) != null || outdoorY.get(sensorDataDeal.getOldMan()) != null) {
                        //进行了二级预警 或者 老人出门 停止定时任务
                        SystemController.logger.info("已进行了二级预警 或者 老人出门 停止定时任务");
                        if (timer.get(sensorDataDeal.getOldMan()) != null) {
                            timer.get(sensorDataDeal.getOldMan()).shutdown();
                            SystemController.logger.info("已停止定时器");
                        }

                    } else {
                        //一级预警
                        SystemController.logger.info("还没进行二级预警且没有出门");
                        if (warn1.get(sensorDataDeal.getOldMan()) == null) {
                            SystemController.logger.info("还没进行一级预警  value=" + value + "  一级阈值=" + threshold1.get(sensorDataDeal.getOldMan()));
                            if (value >= threshold1.get(sensorDataDeal.getOldMan())) {
                                SystemController.logger.info("一级报警");
                                Warn warn = warnMap.get(sensorDataDeal.getOldMan());
                                warn.setWarnLevel(1);
                                warn.setNoMoveTime(value / 60);
                                DwrData dwrData = new DwrData();
                                dwrData.setType("warn_position");
                                dwrData.setWarn(warn);
                                SystemController.logger.info(warn.toString());
                                //存入历史消息
                                warnHistoryService.addWarnHistory(dwrData);
                                SystemController.logger.info("已存入历史消息");
                                //推送
                                Remote.noticeNewOrder(dwrData);
                                sendPost2(dwrData,StaticVal.url2);
                                //地图更新
//                                HouseMarker houseMarker=new HouseMarker();
//                                houseMarker.setOid(dwrData.getWarn().getOldMan().getOid());
//                                houseMarker.setStyleIndex(8); //红色
//                                houseMarker.setDetail("行为预警&nbsp;&nbsp;&nbsp;不动开始时刻："+dwrData.getWarn().getTime());
//                                mapUpdate(houseMarker);

                                sensorDataDeal.getOldMan().setStatus(2);
                                mapUpdate(sensorDataDeal.getOldMan());

                                //启动短信定时任务
                                smsService.smsSwitch();
                                SystemController.logger.info("已进行报警");
                                //设置已经进行了一级报警
                                warn1.put(sensorDataDeal.getOldMan(), true);
                            }
                        } else {
                            //二级预警
                            SystemController.logger.info("还没进行二级预警  value=" + value + "  二级阈值=" + threshold2.get(sensorDataDeal.getOldMan()));
                            if (value >= threshold2.get(sensorDataDeal.getOldMan())) {
                                SystemController.logger.info("二级报警");
                                Warn warn = warnMap.get(sensorDataDeal.getOldMan());
                                warn.setWarnLevel(2);
                                warn.setNoMoveTime(value / 60);
                                DwrData dwrData = new DwrData();
                                dwrData.setType("warn_position");
                                dwrData.setWarn(warn);
                                //存入历史消息
                                warnHistoryService.addWarnHistory(dwrData);
                                //推送
                                Remote.noticeNewOrder(dwrData);
                                sendPost2(dwrData,StaticVal.url2);
                                //地图更新
//                                HouseMarker houseMarker=new HouseMarker();
//                                houseMarker.setOid(dwrData.getWarn().getOldMan().getOid());
//                                houseMarker.setStyleIndex(8); //红色
//                                houseMarker.setDetail("行为预警&nbsp;&nbsp;&nbsp;不动开始时刻："+dwrData.getWarn().getTime());
//                                mapUpdate(houseMarker);
                                sensorDataDeal.getOldMan().setStatus(2);
                                mapUpdate(sensorDataDeal.getOldMan());

                                //启动短信定时任务
                                smsService.smsSwitch();
                                //设置已经进行了二级报警.2
                                warn2.put(sensorDataDeal.getOldMan(), true);
                            }
                        }
                    }
                }
            };

            ScheduledExecutorService service = Executors
                    .newSingleThreadScheduledExecutor();
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            service.scheduleAtFixedRate(runnable, 1, 60, TimeUnit.SECONDS);

            timer.put(sensorDataDeal.getOldMan(), service);
//具体过程为如果在最后一个动的时间之后，老人一直不动话，此线程不会被停止，所以会继续计时知道超出阈值。如果老人再次有动作后，删除之前的线程，重新开始计时线程，转353.
        }catch (NullFromDBException e1){
            throw e1;
        }catch (Exception e){
            throw new WarnException("move inner error:"+e.getMessage());
        }
    }

    //如果接受到数据就证明老人活动  如果老人不动的话就不发送数据
    public void checkMoveData(List<SensorCollection> sensorCollections) throws NullFromDBException,WarnException{
        SystemController.logger.info("======================================行为预警=========================================================");
        try {
            final SensorDataDeal sensorDataDeal = new SensorDataDeal();
            SensorCollection sensorCollection = null;
            //只记录最后一个动的数据 显示的房间数据
            for (int i = sensorCollections.size() - 1; i >= 0; i--) {
                if (sensorCollections.get(i).getSensorData() != 240) {
                    sensorCollection = sensorCollections.get(i);
                    break;
                }
            }
//        如果没有非240的数据 就取最后一条 直接返回 不作处理
            if (sensorCollection == null) {
                SystemController.logger.info("没有非240的行为数据");
                return;
//                sensorCollection = sensorCollections.get(sensorCollections.size() - 1);
            }
            Room room=roomDao.getRoomByGateWayId_SensorId(sensorCollection.getGatewayID(),sensorCollection.getSensorPointID());
            if(room==null){
                throw new NullFromDBException("行为预警：找不到房间");
            }
            sensorDataDeal.setActivityRoom(room);
            OldMan oldMan=dataDao.getOldManByGatewayID(sensorCollection.getGatewayID());
            if(oldMan==null){
                throw new NullFromDBException("行为预警：找不到老人");
            }
            sensorDataDeal.setOldMan(oldMan);
            sensorDataDeal.setTime(sensorCollection.getHour() + ":" + sensorCollection.getMinute() + ":" + sensorCollection.getSecond());
            SystemController.logger.info(sensorDataDeal.toString());
            if (warn1.get(sensorDataDeal.getOldMan()) != null) {
                warn1.remove(sensorDataDeal.getOldMan());
            }
            if (warn2.get(sensorDataDeal.getOldMan()) != null) {
                warn2.remove(sensorDataDeal.getOldMan());
            }
            if (timer.get(sensorDataDeal.getOldMan()) != null) {
                timer.get(sensorDataDeal.getOldMan()).shutdown();
                timer.remove(sensorDataDeal.getOldMan());
            }
            if (door.get(sensorDataDeal.getOldMan()) != null) {
                door.remove(sensorDataDeal.getOldMan());
            }
            if (warnNoCome.get(sensorDataDeal.getOldMan()) == null) {
                warnNoCome.remove(sensorDataDeal.getOldMan());
            }
            if (outdoorY.get(sensorDataDeal.getOldMan()) != null) {
                outdoorY.remove(sensorDataDeal.getOldMan());
            }
            if (timerDoor.get(sensorDataDeal.getOldMan()) != null) {
                timerDoor.get(sensorDataDeal.getOldMan()).shutdown();
                timerDoor.remove(sensorDataDeal.getOldMan());
            }

            Threshold threshold = thresholdDao.getThresholdByRoomId(sensorDataDeal.getActivityRoom().getRid());
            if(threshold==null){
                throw new NullFromDBException("行为预警：找不到阈值");
            }

            //判断 该时间是否在 该房间活动规律时间段内
            //活动该房间的活动规律信息
            RoomModel roomModel = modelDao.getRoomModelByRoomId(sensorDataDeal.getActivityRoom().getRid());
            if(roomModel==null){
                //如果没有该房间的活动模型的话  new 一个空
                roomModel=new RoomModel();
            }
            //暂不考虑 该时间段有时活动 有时休息
            MomentInTime momentInTime = new MomentInTime();
            MomentInTime momentInTimeA = new MomentInTime();
            MomentInTime momentInTimeR = new MomentInTime();
            String inTime = "";//在规律模型中的哪个时间段
            String active = roomModel.getActive();
            //遍历  房间规律模型的 活动时间段
            if (active != null && !active.equals("")) {
                ArrayList<String> activeTimes = new ArrayList<String>(Arrays.asList(roomModel.getActive().split("#"))); //xx:xx-yy:yy
                //判断是否第一个时间段与最后一个连着  比如睡觉00:00:00-06:30:00   20:30:00-24:00:00
                if (activeTimes.size() > 0) {
                    if (activeTimes.get(activeTimes.size() - 1).split("-")[1].equals("24:00") && activeTimes.get(0).split("-")[0].equals("00:00")) {
                        String time = activeTimes.get(activeTimes.size() - 1).split("-")[0] + "-" + activeTimes.get(0).split("-")[1];
                        activeTimes.remove(activeTimes.size() - 1);
                        activeTimes.remove(0);
                        activeTimes.add(time);
                    }
                    for (String activeTime : activeTimes) {
                        momentInTimeA = moment_timeDeal(sensorDataDeal.getTime(), activeTime);
                        if (!momentInTimeA.isInTime()) {
                            continue;
                        }
                        //在该时间段内  各时间段是没有重合的 所以只要一个时间段符合  其他时间段一定不符合 但是活动时间段可能与休息时间段重叠
                        else {
                            inTime = activeTime;
                            momentInTimeA.setFlag("a");
                            break;
                        }
                    }
                }
            }
            if (momentInTimeA.isInTime() == null) {
                momentInTimeA.setInTime(false);
            }
            if (!momentInTimeA.isInTime()) {
                String rest = roomModel.getRest();
                //遍历  房间规律模型的 休息时间段  活动时间段可能与休息时间段重叠
                if (rest != null && !rest.equals("")) {
                    ArrayList<String> restTimes = new ArrayList<String>(Arrays.asList(roomModel.getRest().split("#"))); //xx:xx-yy:yy
                    //判断是否第一个时间段与最后一个连着  比如睡觉00:00:00-06:30:00   20:30:00-24:00:00
                    if (restTimes.size() > 0) {
                        if (restTimes.get(restTimes.size() - 1).split("-")[1].equals("24:00") && restTimes.get(0).split("-")[0].equals("00:00")) {
                            String time = restTimes.get(restTimes.size() - 1).split("-")[0] + "-" + restTimes.get(0).split("-")[1];
                            restTimes.remove(restTimes.size() - 1);
                            restTimes.remove(0);
                            restTimes.add(time);
                        }
                        for (String restTime : restTimes) {
                            momentInTimeR = moment_timeDeal(sensorDataDeal.getTime(), restTime);
                            if (!momentInTimeR.isInTime()) {
                                continue;
                            }
                            //在该时间段内  各时间段是没有重合的 所以只要一个时间段符合  其他时间段一定不符合 但是活动时间段可能与休息时间段重叠
                            else {
                                inTime = restTime;
                                momentInTimeR.setFlag("r");
                                break;
                            }
                        }
                    }
                }
                if (momentInTimeR.isInTime() == null) {
                    momentInTimeR.setInTime(false);
                }
            }
            if (momentInTimeA != null && momentInTimeA.isInTime()) {
                momentInTime.setFlag("a");
                momentInTime.setInTime(true);
            } else if (momentInTimeR != null && momentInTimeR.isInTime()) {
//            if(momentInTime.getFlag().equals("a")){
//                momentInTime.setFlag("a&r");
//            }else{
                momentInTime.setFlag("r");
                momentInTime.setInTime(true);
//            }
            } else {
                momentInTime.setInTime(false);
            }
            Warn warn = new Warn();
            warn.setRoom(sensorDataDeal.getActivityRoom());
            warn.setOldMan(sensorDataDeal.getOldMan());
            warn.setTime(sensorDataDeal.getTime());
            //在规律模型中
            if (!inTime.equals("")) {
                warn.setInTime("true");
                warn.setTimes(inTime);
            } else {
                warn.setInTime("false");
            }

            //设置 阈值
            if (momentInTime.isInTime()) {
                //老人不动时 处于该房间规律模型的时间段内
                switch (momentInTime.getFlag()) {
                    case "a":
                        warn.setFlag("a");
                        threshold1.put(sensorDataDeal.getOldMan(), threshold.getA1Threshold() * 60);
                        threshold2.put(sensorDataDeal.getOldMan(), threshold.getA2Threshold() * 60);
                        break;
                    case "r":
                        warn.setFlag("r");
                        threshold1.put(sensorDataDeal.getOldMan(), threshold.getR1Threshold() * momentInTimeR.getTime() / 100);
                        threshold2.put(sensorDataDeal.getOldMan(), threshold.getR2Threshold() * momentInTimeR.getTime() / 100);
//                    break;
//                case "a&r":
//                    warn.setFlag("a&r");
//                    //该时间段 既可能休息又可能活动 去两者 值小的
//                    int a=threshold.getA1Threshold()*60;
//                    int r=threshold.getR1Threshold()*momentInTimeR.getTime();
//                    threshold1.put(sensorDataDeal.getOldMan(),(a>=r?r:a));
//                    int a2=threshold.getA2Threshold()*60;
//                    int r2=threshold.getR2Threshold()*momentInTime.getTime();
//                    threshold2.put(sensorDataDeal.getOldMan(),(a2>=r2?r2:a2));
                }
            } else {
                //不在规律模型的时间段
                threshold1.put(sensorDataDeal.getOldMan(), threshold.getN1Threshold() * 60);
                threshold2.put(sensorDataDeal.getOldMan(), threshold.getN2Threshold() * 60);
            }

            warnMap.put(sensorDataDeal.getOldMan(), warn);


            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                        String currentTime = sdf.format(d);
                        SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，当前时间：" + currentTime + "   最初不动时间：" + sensorDataDeal.getTime());
                        int value = intervalTime(currentTime, sensorDataDeal.getTime());
                        SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，老人已经不动：" + (value / 60) + "分钟");
                        if (warn2.get(sensorDataDeal.getOldMan()) != null || outdoorY.get(sensorDataDeal.getOldMan()) != null) {
                            //进行了二级预警 或者 老人出门 停止定时任务
                            SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，已进行了二级预警 或者 老人出门 停止定时任务");
                            if (timer.get(sensorDataDeal.getOldMan()) != null) {
                                timer.get(sensorDataDeal.getOldMan()).shutdown();
                                SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，已停止定时器");
                            }

                        } else {
                            //一级预警
                            SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，还没进行二级预警且没有出门");
                            if (warn1.get(sensorDataDeal.getOldMan()) == null) {
                                SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，还没进行一级预警  value=" + value + "  一级阈值=" + threshold1.get(sensorDataDeal.getOldMan()));
                                if (value >= threshold1.get(sensorDataDeal.getOldMan())) {
                                    SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，一级报警");
                                    Warn warn = warnMap.get(sensorDataDeal.getOldMan());
                                    warn.setWarnLevel(1);
                                    warn.setNoMoveTime(value / 60);
                                    DwrData dwrData = new DwrData();
                                    dwrData.setType("warn_move");
                                    dwrData.setWarn(warn);
                                    SystemController.logger.info(warn.toString());
                                    //存入历史消息
                                    warnHistoryService.addWarnHistory(dwrData);
                                    SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，已存入历史消息");
                                    //推送
                                    Remote.noticeNewOrder(dwrData);
                                    sendPost2(dwrData, StaticVal.url2);
                                    //地图更新
//                                HouseMarker houseMarker=new HouseMarker();
//                                houseMarker.setOid(dwrData.getWarn().getOldMan().getOid());
//                                houseMarker.setStyleIndex(8); //红色
//                                houseMarker.setDetail("行为预警&nbsp;&nbsp;&nbsp;不动开始时刻："+dwrData.getWarn().getTime());
//                                mapUpdate(houseMarker);

                                    sensorDataDeal.getOldMan().setStatus(2);
                                    mapUpdate(sensorDataDeal.getOldMan());

                                    //启动短信定时任务
                                    smsService.smsSwitch();
                                    SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，已进行报警");
                                    //设置已经进行了一级报警
                                    warn1.put(sensorDataDeal.getOldMan(), true);
                                }
                            } else {
                                //二级预警
                                SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，还没进行二级预警  value=" + value + "  二级阈值=" + threshold2.get(sensorDataDeal.getOldMan()));
                                if (value >= threshold2.get(sensorDataDeal.getOldMan())) {
                                    SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，二级报警");
                                    Warn warn = warnMap.get(sensorDataDeal.getOldMan());
                                    warn.setWarnLevel(2);
                                    warn.setNoMoveTime(value / 60);
                                    DwrData dwrData = new DwrData();
                                    dwrData.setType("warn_move");
                                    dwrData.setWarn(warn);
                                    //存入历史消息
                                    warnHistoryService.addWarnHistory(dwrData);
                                    //推送
                                    Remote.noticeNewOrder(dwrData);
                                    sendPost2(dwrData, StaticVal.url2);
                                    //地图更新
//                                HouseMarker houseMarker=new HouseMarker();
//                                houseMarker.setOid(dwrData.getWarn().getOldMan().getOid());
//                                houseMarker.setStyleIndex(8); //红色
//                                houseMarker.setDetail("行为预警&nbsp;&nbsp;&nbsp;不动开始时刻："+dwrData.getWarn().getTime());
//                                mapUpdate(houseMarker);
                                    sensorDataDeal.getOldMan().setStatus(2);
                                    mapUpdate(sensorDataDeal.getOldMan());

                                    //启动短信定时任务
                                    smsService.smsSwitch();
                                    //设置已经进行了二级报警.2
                                    warn2.put(sensorDataDeal.getOldMan(), true);
                                }
                            }
                        }
                    }catch (Exception e){
                        SystemController.logger.info("网关id："+oldMan.getGatewayID()+"，报错了");
                        e.printStackTrace();
                    }

                }
            };

            ScheduledExecutorService service = Executors
                    .newSingleThreadScheduledExecutor();
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            service.scheduleAtFixedRate(runnable, 1, 60, TimeUnit.SECONDS);

            timer.put(sensorDataDeal.getOldMan(), service);
//具体过程为如果在最后一个动的时间之后，老人一直不动话，此线程不会被停止，所以会继续计时知道超出阈值。如果老人再次有动作后，删除之前的线程，重新开始计时线程，转353.
        }catch (NullFromDBException e1){
            throw e1;
        }catch (Exception e){
            throw new WarnException("move inner error:"+e.getMessage());
        }
    }

    //之前的行为预警   机制是即使老人不动也接受不动的数据
    //比如出门多久没回来 对于出门也要有个阈值处理 就进行报警 在filter_deal方法中进行判断 如果已经判断出出门 在后面的分析不用进行房间的分析了
//    public void checkMoveData(List<SensorCollection> sensorCollections) {
//        OldMan oldMan=dataDao.getOldManByEquipId(sensorCollections.get(0).getSensorPointID());
//        //门动了 则不进行行为预警
//        if(door.get(oldMan)==null||door.get(oldMan).equals("")) {
//            logger.info("===============================================================================================");
//            SensorDataDeal sensorDataDeal = filter_deal(sensorCollections);
//            //传感器数据显示 老人在这个时间段（1分钟）内活动过  存储老人最后一次动的房间  同时清除noMove的数据
//            if (sensorDataDeal.isActivity()) {
//                logger.info("老人活动:活动房间：" + sensorDataDeal.getActivityRoom().getRoomName() + "\n活动时间：" + sensorDataDeal.getTime());
//
//                noMovaRoom.put(sensorDataDeal.getOldMan(), sensorDataDeal.getActivityRoom());
//                //如果确定老人活动了 就把noMove Map集合 该老人的 不动时间 删除
//                if (noMove.get(sensorDataDeal.getOldMan()) != null) {
//                    noMove.remove(sensorDataDeal.getOldMan());
//                }
//                if (warn1.get(sensorDataDeal.getOldMan()) != null) {
//                    warn1.remove(sensorDataDeal.getOldMan());
//                }
//                if (warn2.get(sensorDataDeal.getOldMan()) != null) {
//                    warn2.remove(sensorDataDeal.getOldMan());
//                }
//            }
//            //老人在这个时间内 没活动过
//            else {
//                //第一次不动 不进行预警
//                logger.info("老人不动:现在不动的时间：" + sensorDataDeal.getTime());
//                String noMoveTime = noMove.get(sensorDataDeal.getOldMan());
//                if (noMoveTime == null || noMoveTime.equals("")) {
//                    logger.info("老人不动:最初时间：" + sensorDataDeal.getTime());
//                    noMove.put(sensorDataDeal.getOldMan(), sensorDataDeal.getTime());
//                } else {
//                    if (warn2.get(sensorDataDeal.getOldMan()) == null || warn2.get(sensorDataDeal.getOldMan()) != true) {
//                        //还没进行二级报警 如果二级报警都已经触发  则不再进行报警
//                        warnDeal(sensorDataDeal);
//                    }
//                }
//            }
//        }
//    }

//    传感器数据过滤 过滤正常情况  并进行数据模型转换
//    public SensorDataDeal filter_deal(List<SensorCollection> sensorCollections){
//        SensorDataDeal sensorDataDeal=new SensorDataDeal();
//        OldMan oldMan = null;
//        String lastSensorId = null;//存最后动的传感器Id 避免进行多余的房间查询
//        //对每条传感器数据进行处理  大概每几秒一个数据  传来的是一分钟内各个房间的数据  也就是1个房间的记录就有十几条
//        for(int i=0;i<sensorCollections.size();i++){
//            SensorCollection sensorCollection=sensorCollections.get(i);
//            String sensorPointObjID=sensorCollection.getSensorPointID();
//            if(oldMan==null) {
//                oldMan = dataDao.getOldManByEquipId(sensorPointObjID);//获得数据属于的老人
//                sensorDataDeal.setOldMan(oldMan);
//            }
//            //判断老人是否活动  只要在这一分钟内 活动就算动 存最近时刻的数据   240不动 15动 255持续动了1分钟
//            if(sensorCollection.getSensorData()!=240){
//                sensorDataDeal.setActivity(true);
//                lastSensorId=sensorPointObjID;
//                sensorDataDeal.setTime(sensorCollection.getHour()+":"+sensorCollection.getMinute()+":"+sensorCollection.getSecond());
//            }
//            //最后一条数据 且  这段时间一直没动过， 存最后一条的时间 hh:mm:ss
//            //如果需要显示最初不动时间的年月日 可以先设置一个变量 在最后推送的时候加上
//            if(i==sensorCollections.size()-1&&sensorDataDeal.isActivity()==false){
//                sensorDataDeal.setTime(sensorCollection.getHour()+":"+sensorCollection.getMinute()+":"+sensorCollection.getSecond());
//            }
//        }
//        //存在这个时间段内最后一次动的房间
//        if(lastSensorId!=null) {
//            Room room = roomDao.getRoomByEquipId(lastSensorId);
//            sensorDataDeal.setActivityRoom(room);
//        }
//        return sensorDataDeal;
//    }


    //预警机制
//    public Boolean warnDeal(SensorDataDeal sensorDataDeal){
//        //计算 到目前为止老人不动的时间 单位秒
//        int noMoveSecond=intervalTime(sensorDataDeal.getTime(),noMove.get(sensorDataDeal.getOldMan()));
//        logger.info("目前为止不动时间："+(noMoveSecond/60)+"分钟");
//
//        //最初不动的时间
//        String noMoveTime=noMove.get(sensorDataDeal.getOldMan());
//        //获得不动 之前所在的房间
//        Room room=noMovaRoom.get(sensorDataDeal.getOldMan());
//        Threshold threshold=thresholdDao.getThresholdByRoomId(room.getRid());
//        //判断 该最初不动时间是否在 该房间活动规律时间段内
//        //活动该房间的活动规律信息
//        RoomModel roomModel =modelDao.getRoomModelByRoomId(room.getRid());
//        MomentInTime momentInTime=null;
//        String inTime="";//在规律模型中的哪个时间段
//        String active=roomModel.getActive();
//        //遍历  房间规律模型的 活动时间段
//        if(active!=null&&!active.equals("")) {
//            ArrayList<String> activeTimes = new ArrayList<String>(Arrays.asList(roomModel.getActive().split("#"))); //xx:xx-yy:yy
//            //判断是否第一个时间段与最后一个连着  比如睡觉00:00:00-06:30:00   20:30:00-24:00:00
//            if(activeTimes.size()>0) {
//                if (activeTimes.get(activeTimes.size()-1).split("-")[1].equals("24:00") && activeTimes.get(0).split("-")[0].equals("00:00")){
//                    String time=activeTimes.get(activeTimes.size()-1).split("-")[0]+"-"+activeTimes.get(0).split("-")[1];
//                    activeTimes.remove(activeTimes.size()-1);
//                    activeTimes.remove(0);
//                    activeTimes.add(time);
//                }
//                for (String activeTime : activeTimes) {
//                    momentInTime = moment_timeDeal(noMoveTime, activeTime);
//                    if (!momentInTime.isInTime()) {
//                        continue;
//                    }
//                    //在该时间段内  各时间段是没有重合的 所以只要一个时间段符合  其他时间段一定不符合 但是活动时间段可能与休息时间段重叠
//                    else {
//                        inTime = activeTime;
//                        momentInTime.setFlag("a");
//                        break;
//                    }
//                }
//            }
//        }
//        String rest=roomModel.getRest();
//        //遍历  房间规律模型的 休息时间段  活动时间段可能与休息时间段重叠
//        if(rest!=null&&!rest.equals("")) {
//            ArrayList<String> restTimes = new ArrayList<String>(Arrays.asList(roomModel.getRest().split("#"))); //xx:xx-yy:yy
//            //判断是否第一个时间段与最后一个连着  比如睡觉00:00:00-06:30:00   20:30:00-24:00:00
//            if(restTimes.size()>0) {
//                if (restTimes.get(restTimes.size() - 1).split("-")[1].equals("24:00") && restTimes.get(0).split("-")[0].equals("00:00")) {
//                    String time = restTimes.get(restTimes.size() - 1).split("-")[0] + "-" + restTimes.get(0).split("-")[1];
//                    restTimes.remove(restTimes.size() - 1);
//                    restTimes.remove(0);
//                    restTimes.add(time);
//                }
//                for (String restTime : restTimes) {
//                    momentInTime = moment_timeDeal(noMoveTime, restTime);
//                    if (!momentInTime.isInTime()) {
//                        continue;
//                    }
//                    //在该时间段内  各时间段是没有重合的 所以只要一个时间段符合  其他时间段一定不符合
//                    else {
//                        if (momentInTime.getFlag() != null && momentInTime.getFlag().equals("a")) {
//                            momentInTime.setFlag("a&r");
//                        } else {
//                            momentInTime.setFlag("r");
//                        }
//                        inTime = restTime;
//                        break;
//                    }
//                }
//            }
//        }
//        int thresholdValue;//阈值时间 如果是休息时的百分比的话  转化为时长 单位秒
//        if(momentInTime.isInTime()){
//            //老人不动时 处于该房间规律模型的时间段内
//            switch (momentInTime.getFlag()){
//                case "a":
//                    //一级报警
//                    if(warn1.get(sensorDataDeal.getOldMan())==null||warn1.get(sensorDataDeal.getOldMan())!=true) {
//                        thresholdValue = threshold.getA1Threshold() * 60;
//                        if (noMoveSecond >= thresholdValue) {
//                            //进行一级报警
//                            logger.info("一级报警 a");
//                            Warn warn=setWarnInTime(sensorDataDeal, noMoveSecond,inTime,"y");
//                            warn.setWarnLevel(1);
//                            warn.setFlag("a");
//
//                            DwrData dwrData=new DwrData();
//                            dwrData.setType("warn_move");
//                            dwrData.setWarn(warn);
//                            //存入历史消息
//                            warnHistoryService.addWarnHistory(dwrData);
//                            //推送
//                            Remote.noticeNewOrder(dwrData);
//                            //设置已经进行了一级报警
//                            warn1.put(sensorDataDeal.getOldMan(), true);
//                        }
//                    }else {
//                        //二级报警
//
//                        thresholdValue = threshold.getA2Threshold() * 60;
//                        if (noMoveSecond >= thresholdValue) {
//                            //进行二级报警
//                            logger.info("二级报警 a");
//                            Warn warn=setWarnInTime(sensorDataDeal, noMoveSecond,inTime,"y");
//                            warn.setWarnLevel(2);
//                            warn.setFlag("a");
//                            DwrData dwrData=new DwrData();
//                            dwrData.setType("warn_move");
//                            dwrData.setWarn(warn);
//                            warnHistoryService.addWarnHistory(dwrData);
//                            //推送
//                            Remote.noticeNewOrder(dwrData);
//                            //设置已经进行了二级报警
//                            warn2.put(sensorDataDeal.getOldMan(), true);
//                        }
//                    }
//                    break;
//                case "r":
//                    //一级报警
//                    if(warn1.get(sensorDataDeal.getOldMan())==null||warn1.get(sensorDataDeal.getOldMan())!=true) {
//                        thresholdValue = threshold.getR1Threshold()*momentInTime.getTime()/100;
//                        if (noMoveSecond >= thresholdValue) {
//                            //进行一级报警
//                            logger.info("一级报警 r");
//                            Warn warn=setWarnInTime(sensorDataDeal, noMoveSecond,inTime,"y");
//                            warn.setWarnLevel(1);
//                            warn.setFlag("r");
//                            DwrData dwrData=new DwrData();
//                            dwrData.setType("warn_move");
//                            dwrData.setWarn(warn);
//                            warnHistoryService.addWarnHistory(dwrData);
//                            //推送
//                            Remote.noticeNewOrder(dwrData);
//                            //设置已经进行了一级报警
//                            warn1.put(sensorDataDeal.getOldMan(), true);
//
//                        }
//                    }else {
//                        //二级报警
//                        thresholdValue = threshold.getR2Threshold() * momentInTime.getTime()/100;
//                        if (noMoveSecond >= thresholdValue) {
//                            //进行二级报警
//                            logger.info("二级报警 r");
//                            Warn warn=setWarnInTime(sensorDataDeal, noMoveSecond,inTime,"y");
//                            warn.setWarnLevel(2);
//                            warn.setFlag("r");
//                            DwrData dwrData=new DwrData();
//                            dwrData.setType("warn_move");
//                            dwrData.setWarn(warn);
//                            warnHistoryService.addWarnHistory(dwrData);
//                            //推送
//                            Remote.noticeNewOrder(dwrData);
//                            //设置已经进行了二级报警
//                            warn2.put(sensorDataDeal.getOldMan(), true);
//                        }
//                    }
//                    break;
//                case "a&r":
//                    //该时间段 既可能休息又可能活动 去两者 值小的
//                    if(warn1.get(sensorDataDeal.getOldMan())==null||warn1.get(sensorDataDeal.getOldMan())!=true) {
//                        int a=threshold.getA1Threshold()*60;
//                        int r=threshold.getR1Threshold()*momentInTime.getTime();
//                        thresholdValue=(a>=r?r:a);
//                        if (noMoveSecond >= thresholdValue) {
//                            //进行一级报警
//                            logger.info("一级报警 a&r");
//                            Warn warn=setWarnInTime(sensorDataDeal, noMoveSecond, inTime, "y");
//                            warn.setWarnLevel(1);
//                            warn.setFlag("a&r");
//                            DwrData dwrData=new DwrData();
//                            dwrData.setType("warn_move");
//                            dwrData.setWarn(warn);
//                            warnHistoryService.addWarnHistory(dwrData);
//                            //推送
//                            Remote.noticeNewOrder(dwrData);
//                            //设置已经进行了一级报警
//                            warn1.put(sensorDataDeal.getOldMan(), true);
//                        }
//                    }else {
//                        //二级报警
//                        int a=threshold.getA2Threshold()*60;
//                        int r=threshold.getR2Threshold()*momentInTime.getTime();
//                        thresholdValue=(a>=r?r:a);
//                        if (noMoveSecond >= thresholdValue) {
//                            //进行二级报警
//                            logger.info("二级报警 a&r");
//                            Warn warn=setWarnInTime(sensorDataDeal, noMoveSecond,inTime,"y");
//                            warn.setWarnLevel(2);
//                            warn.setFlag("a&r");
//                            DwrData dwrData=new DwrData();
//                            dwrData.setType("warn_move");
//                            dwrData.setWarn(warn);
//                            warnHistoryService.addWarnHistory(dwrData);
//                            //推送
//                            Remote.noticeNewOrder(dwrData);
//                            //设置已经进行了二级报警
//                            warn2.put(sensorDataDeal.getOldMan(), true);
//                        }
//                    }
//            }
//        }else{
//            //不在规律模型的时间段
//            if(warn1.get(sensorDataDeal.getOldMan())==null||warn1.get(sensorDataDeal.getOldMan())!=true) {
//                thresholdValue = threshold.getN1Threshold() * 60;
//                if (noMoveSecond >= thresholdValue) {
//                    //进行一级报警
//                    logger.info("一级报警 n");
//                    Warn warn=setWarnInTime(sensorDataDeal, noMoveSecond,inTime,"n");
//                    warn.setWarnLevel(1);;
//                    DwrData dwrData=new DwrData();
//                    dwrData.setType("warn_move");
//                    dwrData.setWarn(warn);
//                    warnHistoryService.addWarnHistory(dwrData);
//                    //推送
//                    Remote.noticeNewOrder(dwrData);
//                    //设置已经进行了一级报警
//                    warn1.put(sensorDataDeal.getOldMan(), true);
//                }
//            }else {
//                //二级报警
//                thresholdValue = threshold.getN2Threshold() * 60;
//                if (noMoveSecond >= thresholdValue) {
//                    //进行二级报警
//                    logger.info("二级报警 n");
//                    Warn warn=setWarnInTime(sensorDataDeal, noMoveSecond,inTime,"n");
//                    warn.setWarnLevel(2);
//                    DwrData dwrData=new DwrData();
//                    dwrData.setType("warn_move");
//                    dwrData.setWarn(warn);
//                    warnHistoryService.addWarnHistory(dwrData);
//                    //推送
//                    Remote.noticeNewOrder(dwrData);
//                    //设置已经进行了二级报警
//                    warn2.put(sensorDataDeal.getOldMan(), true);
//                }
//            }
//        }
//        return true;
//    }

    @Override
    //计算两个时刻直接的时间间隔 返回的时间单位为秒  输入：hh:mm:ss
    public int intervalTime(String last,String pre){
        String[] preTime=pre.split(":");
        String[] lastTime=last.split(":");
        int interval;
        //比如 pre: 23:00:00   last: 01:00:00 的情况
        if(last.compareTo(pre)<0){
            interval=(24-Integer.parseInt(preTime[0])+Integer.parseInt(lastTime[0]))*60*60+
                    (Integer.parseInt(lastTime[1])-Integer.parseInt(preTime[1]))*60+
                    (Integer.parseInt(lastTime[2])-Integer.parseInt(preTime[2]));
        }else {
            interval = (Integer.parseInt(lastTime[0]) - Integer.parseInt(preTime[0])) * 60 * 60 +
                    (Integer.parseInt(lastTime[1]) - Integer.parseInt(preTime[1])) * 60 +
                    (Integer.parseInt(lastTime[2]) - Integer.parseInt(preTime[2]));
        }
        return interval;
    }

    //计算某一时刻是否在某一时间段内 以及 该时间段时长 和该时刻距该时间段的结束时间的时长 单位秒
    public MomentInTime moment_timeDeal(String moment,String time){
        MomentInTime momentInTime=new MomentInTime();
        String[] times=time.split("-");// /xx:xx-yy:yy
        //模型时间段位 20:30:00-06:30:00的情况
        if(times[1].compareTo(times[0])<0){
            if(moment.compareTo(times[0])>0||moment.compareTo(times[1])<0){
                momentInTime.setInTime(true);
//                //距结束的时长  因为规律模型是 hh:mm 手动构造 hh:mm:ss形式
//                momentInTime.setToEnd(intervalTime(times[1] + ":00", moment));
                //时间段 时长
                momentInTime.setTime(intervalTime(times[1] + ":00", times[0] + ":00"));
            } else {
                momentInTime.setInTime(false);
            }
        }else {
            //直接比较字符串  不要拆分成 时分秒
            if (moment.compareTo(times[0]) >= 0 && moment.compareTo(times[1]) < 0) {
                momentInTime.setInTime(true);
//                //距结束的时长  因为规律模型是 hh:mm 手动构造 hh:mm:ss形式
//                momentInTime.setToEnd(intervalTime(times[1] + ":00", moment));
                //时间段 时长
                momentInTime.setTime(intervalTime(times[1] + ":00", times[0] + ":00"));
            } else {
                momentInTime.setInTime(false);
            }
        }
        return momentInTime;
    }

    //设置warn对象的通用信息 减少重复的代码  isInTime 表示是否在规律的时间段内 y在 n不在
//    public Warn setWarnInTime(SensorDataDeal sensorDataDeal,int noMoveSecond,String inTime,String isInTime){
//        Warn warn=new Warn();
//        warn.setRoom(noMovaRoom.get(sensorDataDeal.getOldMan()));
//        warn.setOldMan(sensorDataDeal.getOldMan());
//        warn.setNoMoveTime(noMoveSecond);
//        warn.setTime(noMove.get(sensorDataDeal.getOldMan()));
//        if (isInTime.equals("y")) {
//            warn.setInTime("true");
//            warn.setTimes(inTime);
//        }else{
//            warn.setInTime("false");
//        }
//        return warn;
//    }




    //紧急报警
    @Override
    public void urgency(Equipment equip,Integer gatewayID) throws NullFromDBException,WarnException {
        try {
            Equipment equipment = equipDao.getEquipmentByGateWayId_SensorId(gatewayID,equip.getEid());
            if(equipment==null){
                throw new NullFromDBException("紧急报警：找不到设备");
            }
            OldMan oldMan = dataDao.getOldManByGatewayID(gatewayID);
            if(oldMan==null){
                throw new NullFromDBException("紧急报警：找不到老人");
            }
            Room room = roomDao.getRoomByGateWayId_SensorId(gatewayID,equip.getEid());
            if(room==null){
                throw new NullFromDBException("紧急报警：找不到房间");
            }
            Urgency urgency = new Urgency();
            urgency.setEquip(equipment);
            urgency.setOldMan(oldMan);
            urgency.setRoom(room);

            DwrData dwrData = new DwrData();
            dwrData.setType("urgency");
            dwrData.setUrgency(urgency);

            warnHistoryService.addWarnHistory(dwrData);
            SystemController.logger.info("已存入历史消息");
            Remote.noticeNewOrder(dwrData);

            //启动短信定时任务
            smsService.smsSwitch();

            //地图更新
//            HouseMarker houseMarker=new HouseMarker();
//            houseMarker.setOid(oldMan.getOid());
//            houseMarker.setStyleIndex(8); //红色
//            houseMarker.setDetail("设备ID：" + equip.getEid()+"&nbsp;&nbsp;设备所在房间：" + room.getRoomName());
//            mapUpdate(houseMarker);
            oldMan.setStatus(2);
            mapUpdate(oldMan);

        }catch (NullFromDBException e1){
            throw e1;
        }catch (Exception e){
            throw new WarnException("urgency inner error:"+e.getMessage());
        }

    }

    //地图更新
//    @Override
//    public void mapUpdate(HouseMarker houseMarker) {
//        mapDao.editHouseMarker(houseMarker);
//        HouseMarker house=mapDao.getHouseMarkerByOid(houseMarker.getOid());
//        System.out.println(house.toString());
//        Remote.noticeMap(house);
//    }
    @Override
    public void mapUpdate(OldMan oldMan) {
        dataDao.editOldManStatus(oldMan);
        Remote.noticeMap();
    }


    //传感器数据 分类
    @Override
    public SensorType conType(SensorCollections sensorCollections) throws WarnException {
        try {
            SensorType sensorType = new SensorType();
            for (SensorCollection sensorCollection : sensorCollections.getSensorCollections()) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime=LocalDateTime.parse(sensorCollection.getTimeString(),df);
                sensorCollection.setHour(localDateTime.getHour()+"");
                sensorCollection.setMinute(localDateTime.getMinute()+"");
                sensorCollection.setSecond(localDateTime.getSecond()+"");

                sensorCollection.setHour((sensorCollection.getHour().length() == 1) ? "0" + sensorCollection.getHour() : sensorCollection.getHour());
                sensorCollection.setMinute((sensorCollection.getMinute().length() == 1) ? "0" + sensorCollection.getMinute() : sensorCollection.getMinute());
                sensorCollection.setSecond((sensorCollection.getSecond().length() == 1) ? "0" + sensorCollection.getSecond() : sensorCollection.getSecond());

                SystemController.logger.info(sensorCollection.toString());
                switch (sensorCollection.getSensorID()) {
                    case 1:
                        //行为
                        sensorType.getMoveSensorCollection().add(sensorCollection);
                        break;
                    case 2:
                        //温度
                        sensorType.getWenduSensorCollection().add(sensorCollection);
                        break;
                    case 3:
                        //湿度
                        break;
                    case 4:
                        //光强
                        sensorType.getLightSensorCollection().add(sensorCollection);
                        break;
                    case 5:
                        //门
                        sensorType.getDoorSensorCollection().add(sensorCollection);
                }
            }
            return sensorType;
        }catch (Exception e){
            throw new WarnException("conType inner error:"+e.getMessage());
        }
    }
    //传感器数据 分类
    @Override
    public SensorType conSecType(SensorCollections sensorCollections) throws WarnException {
        try {
            SensorType sensorType = new SensorType();
            for (SensorCollection sensorCollection : sensorCollections.getSensorCollections()) {
//                sensorCollection.setHour((sensorCollection.getHour().length() == 1) ? "0" + sensorCollection.getHour() : sensorCollection.getHour());
//                sensorCollection.setMinute((sensorCollection.getMinute().length() == 1) ? "0" + sensorCollection.getMinute() : sensorCollection.getMinute());
//                sensorCollection.setSecond((sensorCollection.getSecond().length() == 1) ? "0" + sensorCollection.getSecond() : sensorCollection.getSecond());;
                SystemController.logger.info(sensorCollection.toString());
                switch (sensorCollection.getSensorID()) {
                    case 1:
                        //行为
                        sensorType.getMoveSensorCollection().add(sensorCollection);
                        break;
                    case 2:
                        //温度
                        sensorType.getWenduSensorCollection().add(sensorCollection);
                        break;
                    case 3:
                        //湿度
                        break;
                    case 4:
                        //光强
                        sensorType.getLightSensorCollection().add(sensorCollection);
                        break;
                    case 5:
                        //门
                        sensorType.getDoorSensorCollection().add(sensorCollection);
                }
            }
            return sensorType;
        }catch (Exception e){
            throw new WarnException("conType inner error:"+e.getMessage());
        }
    }


    //光强度预警  遍历该时间间隔的所有值   光强不变时  传感器不发数据
    @Override
    public void checkLightData(List<SensorCollection> lightSensorCollectionLis) throws NullFromDBException,WarnException{
        SystemController.logger.info("======================================光强预警=========================================================");

            final OldMan oldMan = dataDao.getOldManByGatewayID(lightSensorCollectionLis.get(0).getGatewayID());
            if(oldMan==null){
                throw new NullFromDBException("光强预警：找不到老人");
            }
            //先获得所有房间的阈值 这样就不用每次查询数据库了
            List<Room> rooms = roomDao.getAllRoomByOldManId(oldMan.getOid());//获得该老人所有房间
            if(rooms==null){
                throw new NullFromDBException("光强预警：找不到房间");
            }
            List<Threshold_light> threshold_lights = thresholdDao.getLightThresholdByOid(oldMan.getOid());
            if(threshold_lights==null){
                throw new NullFromDBException("光强预警：找不到阈值");
            }
            final Warn_light warn_light = new Warn_light();
            warn_light.setOldMan(oldMan);
            Set<Room> roomN = new HashSet<>();//存储光强不正常的房间
            for (SensorCollection sensorCollection : lightSensorCollectionLis) {
                Room room;//当前房间
                final int l = sensorCollection.getSensorData();//获得当前光强
                //当前时间
                String currentTime;
                if(oldMan.getVersion() == 2) {
                    String time[] = sensorCollection.getTime().split(" ");
                    currentTime = time[1];
                }
                else
                    currentTime = sensorCollection.getHour()+":"+sensorCollection.getMinute()+":"+sensorCollection.getSecond();
                SystemController.logger.info("当前时间：" + currentTime + "\n光强：" + l);
                for (final Threshold_light threshold_light : threshold_lights) {
                    //找到对应阈值
                    if (threshold_light.getRoom().getCollectId().equals(sensorCollection.getSensorPointID())) {
                        room = threshold_light.getRoom();
                        SystemController.logger.info("对应房间："+room.getRoomName());
                        //达到阈值 且在时间内
                        if (l >= threshold_light.getlThreshold() && moment_timeDeal(currentTime, threshold_light.getTimes()).isInTime()) {
                            //如果定时任务已启动 则不处理 让定时任务继续 执行 检测持续时间
                            if (lightTimer.get(room) != null) {
                                continue;
                            } else {
                                //如果定时任务还没开启 则开启定时任务
                                SystemController.logger.info("开始检测光强 房间：" + room.toString());
                                lightRoom.put(room, currentTime);
                                final Room finalRoom = room;
                                Runnable runnable = new Runnable() {
                                    public void run() {
                                        Date d = new Date();
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                                        String currentTime1 = sdf.format(d);
                                        SystemController.logger.info("当前时间：" + currentTime1 + "   光强最初时间：" + lightRoom.get(finalRoom));
                                        int value = intervalTime(currentTime1, lightRoom.get(finalRoom)) / 60;//当前时间与最初时间的差值 单位分钟
                                        SystemController.logger.info("光强已持续：" + value + "分钟  阈值时间：" + threshold_light.getContinueTime());
                                        if (light.get(finalRoom) != null || !moment_timeDeal(currentTime1, threshold_light.getTimes()).isInTime()) {
                                            SystemController.logger.info("已进行了光强预警或者当前时间不在规定范围内  则停止定时任务");
                                            if (lightTimer.get(finalRoom) != null) {
                                                lightTimer.get(finalRoom).shutdown();
                                            }
                                        } else {
                                            if (value >= threshold_light.getContinueTime()) {
                                                SystemController.logger.info("达到阈值的光强且在规定时间段内  超过持续时间阈值  进行预警");
                                                warn_light.setThreshold_light(threshold_light);
                                                warn_light.setLight(l);
                                                warn_light.setValue(value);
                                                warn_light.setTime(lightRoom.get(threshold_light.getRoom()) + "-" + currentTime1);
                                                DwrData dwrData = new DwrData();
                                                dwrData.setType("warn_light");
                                                dwrData.setWarn_light(warn_light);
                                                warnHistoryService.addWarnHistory(dwrData);
                                                Remote.noticeNewOrder(dwrData);
                                                sendPost2(dwrData,StaticVal.url2);
                                                //地图更新
//                                                HouseMarker houseMarker=new HouseMarker();
//                                                houseMarker.setOid(oldMan.getOid());
//                                                houseMarker.setStyleIndex(8); //红色
//                                                houseMarker.setDetail("光强预警&nbsp;&nbsp;&nbsp;预警房间："+finalRoom.getRoomName()+";&nbsp;&nbsp;当前光强："+l);
//                                                mapUpdate(houseMarker);
                                                oldMan.setStatus(2);
                                                mapUpdate(oldMan);

                                                //启动短信定时任务
                                                smsService.smsSwitch();
                                                light.put(finalRoom, true);
                                            }
                                        }
                                    }
                                };
                                ScheduledExecutorService service = Executors
                                        .newSingleThreadScheduledExecutor();
                                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                                service.scheduleAtFixedRate(runnable, 1, 60, TimeUnit.SECONDS);
                                lightTimer.put(room, service);
                            }
                        }
                        //未达到阈值 或不在这个时间范围内
                        else {
                            if (lightTimer.get(room) != null) {
                                lightTimer.get(room).shutdown();
                                lightTimer.remove(room);
                            }
                            if (light.get(room) != null) {
                                light.remove(room);
                            }
                            if (lightRoom.get(room) != null) {
                                lightRoom.remove(room);
                            }
                        }
                    }
                }
            }
//        }catch (NullFromDBException e1){
//            throw e1;
//        }catch (Exception e){
//            throw new WarnException("light inner error:"+e.getCause());
//        }
    }


    //光强度预警 以前 的 一直接受数据 包括光强不变 也也接受数据
//    @Override
//    public void checkLightData1(List<SensorCollection> lightSensorCollectionLis) {
//        SensorController.logger.info("======================================光强预警=========================================================");
//        OldMan oldMan=dataDao.getOldManByEquipId(lightSensorCollectionLis.get(0).getSensorPointID());
//        //先获得所有房间的阈值 这样就不用每次查询数据库了
//        List<Room> rooms=roomDao.getAllRoomByOldManId(oldMan.getOid());//获得该老人所有房间
//        List<Threshold_light> threshold_lights=thresholdDao.getLightThresholdByOid(oldMan.getOid());
//        Warn_light warn_light=new Warn_light();
//        warn_light.setOldMan(oldMan);
//        Set<Room> roomN=new HashSet<>();//存储光强不正常的房间
//        for(SensorCollection sensorCollection:lightSensorCollectionLis){
//            int f=0;//标志
//            Room room=null;//当前房间
//            int l=sensorCollection.getSensorData();//获得当前光强
//            //当前时间
//            String currentTime = sensorCollection.getHour() + ":" + sensorCollection.getMinute() + ":" + sensorCollection.getSecond();
//            SensorController.logger.info("当前时间："+currentTime+"\n光强："+l);
//            for(Threshold_light threshold_light:threshold_lights){
//                if(threshold_light.getRoom().getCollectId().equals(sensorCollection.getSensorPointID())){
//                    room=threshold_light.getRoom();
//                    //当前光强达到阈值 以及在范围区间内
//                    if(l>=threshold_light.getlThreshold()&&moment_timeDeal(currentTime, threshold_light.getTimes()).isInTime()){
//                        SensorController.logger.info("当前光强达到阈值 以及在范围区间"+threshold_light.getTimes()+"内");
//                        f=1;
//                        //还没有报过警
//                        if(light.get(threshold_light.getRoom())==null||light.get(threshold_light.getRoom())!=true) {
//                            //第一次符合条件
//                            if (lightRoom.get(threshold_light.getRoom()) == null || lightRoom.get(threshold_light.getRoom()).equals("")) {
//                                SensorController.logger.info("第一次发现光强达到阈值");
//                                lightRoom.put(threshold_light.getRoom(), currentTime);
//                            } else {
//                                int value=intervalTime(currentTime,lightRoom.get(threshold_light.getRoom()))/60;
//                                if(value>=threshold_light.getContinueTime()){
//                                    SensorController.logger.info("光强预警");
//                                    warn_light.setThreshold_light(threshold_light);
//                                    warn_light.setLight(l);
//                                    warn_light.setVaule(value);
//                                    warn_light.setTime(lightRoom.get(threshold_light.getRoom()) + "-" + currentTime);
//                                    DwrData dwrData = new DwrData();
//                                    dwrData.setType("warn_light");
//                                    dwrData.setWarn_light(warn_light);
//
//                                    warnHistoryService.addWarnHistory(dwrData);
//                                    Remote.noticeNewOrder(dwrData);
//                                    light.put(threshold_light.getRoom(), true);
//                                }
//                            }
//                        }
//                    }
//                }
//                break;
//            }
//            //只有在该房间 灯光一直处于正常状态下 再清除预警map
//            if(f==1){
//                roomN.add(room);
//            }
//        }
//        //清空存储该老人正常房间的预警map数据
//        for(Room room:rooms){
//            if(!roomN.contains(room)) {
//                if (light.get(room) != null) {
//                    light.remove(room);
//                }
//                if (lightRoom.get(room) != null) {
//                    lightRoom.remove(room);
//                }
//            }
//        }
//
//    }


    //温度预警 遍历该时间间隔的所有值  如果温度一直达到阈值 则不重复报警（只报一次 不管在同一个时间段内还是连续的时间段） 只有在下一个检测时间段内 温度都正常 该房间才能继续进行报警
    @Override
    public void checkWenduData(List<SensorCollection> wenduSensorCollectionLis) throws NullFromDBException,WarnException {
        SystemController.logger.info("======================================温度预警=========================================================");
        try {
            OldMan oldMan = dataDao.getOldManByGatewayID(wenduSensorCollectionLis.get(0).getGatewayID());
            if (oldMan == null) {
                throw new NullFromDBException("温度预警：找不到老人");
            }
            List<Room> rooms = roomDao.getAllRoomByOldManId(oldMan.getOid());//获得该老人所有房间
            if (rooms == null) {
                throw new NullFromDBException("温度预警：找不到房间");
            }
            List<Threshold_wendu> threshold_wendus = thresholdDao.getWenDuThresholdByOid(oldMan.getOid());
            if (threshold_wendus == null) {
                throw new NullFromDBException("温度预警：找不到阈值");
            }
            Warn_wendu warn_wendu = new Warn_wendu();
            warn_wendu.setOldMan(oldMan);
            Set<Room> roomN = new HashSet<>();//存储温度不正常的房间
            for (SensorCollection sensorCollection : wenduSensorCollectionLis) {
                int f = 0;//用于标志 温度是否正常  0正常  防止一直重复预警
                Room room = null;//当前房间
                int w = sensorCollection.getSensorData();//获得当前温度
                if(oldMan.getVersion() == 2)
                    SystemController.logger.info("当前时间：" + sensorCollection.getTime() + "\n温度：" + w);
                else
                    SystemController.logger.info("当前时间：" + sensorCollection.getHour()+":"+sensorCollection.getMinute()+":"+sensorCollection.getSecond() + "\n温度：" + w);
                for (Threshold_wendu threshold_wendu : threshold_wendus) {
                    if (threshold_wendu.getRoom().getCollectId().equals(sensorCollection.getSensorPointID())) {
                        room = threshold_wendu.getRoom();
                        SystemController.logger.info("对应房间："+room.getRoomName());
                        if (w >= threshold_wendu.getwThreshold()) {
                            f = 1;
                            //不重复报警
                            if (wendu.get(threshold_wendu.getRoom()) == null || wendu.get(threshold_wendu.getRoom()) != true) {
                                SystemController.logger.info("温度报警");
                                warn_wendu.setThreshold_wendu(threshold_wendu);
                                warn_wendu.setWendu(w);
                                if(wenduSensorCollectionLis.get(0).getGatewayID().equals("43")){
                                    SendAlarm sendAlarm = new SendAlarm();
                                    sendAlarm.setInfo(warn_wendu.getWendu().toString());
                                    sendAlarm.setTime(sensorCollection.getTime());
                                    sendAlarm.setLevel(warn_wendu.getThreshold_wendu().toString());
                                    sendAlarm.setType("wendu");
                                    sendPost(sendAlarm,StaticVal.url);
                                }else{
                                    DwrData dwrData = new DwrData();
                                    dwrData.setType("warn_wendu");
                                    dwrData.setWarn_wendu(warn_wendu);
                                    wendu.put(threshold_wendu.getRoom(), true);
                                    warnHistoryService.addWarnHistory(dwrData);
                                    Remote.noticeNewOrder(dwrData);
                                    oldMan.setStatus(2);
                                    mapUpdate(oldMan);
                                    //启动短信定时任务
                                    smsService.smsSwitch();
                                    sendPost2(dwrData,StaticVal.url2);
                                }

                                //地图更新
//                                HouseMarker houseMarker=new HouseMarker();
//                                houseMarker.setOid(oldMan.getOid());
//                                houseMarker.setStyleIndex(8); //红色
//                                houseMarker.setDetail("温度预警&nbsp;&nbsp;&nbsp;预警房间："+room.getRoomName()+";&nbsp;&nbsp;当前温度："+w);
//                                mapUpdate(houseMarker);

                            }
                        }
                        break;
                    }
                }
                //只有在该房间 该检测时间段 温度一直处于正常状态下 再清除预警map
                if (f == 1) {
                    roomN.add(room);
                }
            }
            //清空存储该老人正常房间的预警map数据  可以重新报警
            for (Room room : rooms) {
                if (!roomN.contains(room)) {
                    if (wendu.get(room) != null) {
                        wendu.remove(room);
                    }
                }
            }
        }catch (NullFromDBException e1){
            throw e1;
        }catch (Exception e){
            throw new WarnException("wendu inner error:"+e.getMessage());
        }
    }

    //对应之前版本  检测 预警开关是否开启  没有开启的话  不进行对接受的传感器数据 进行预警
    //不再使用
    @Override
    public Boolean checkSwitch(SensorCollections sensorCollections) {
        OldMan oldMan=dataDao.getOldManByGatewayID(sensorCollections.getSensorCollections().get(0).getGatewayID());
        if(oldMan!=null) {
            //防止没有添加
            if (StaticVal.oldManTimer.get(oldMan) == null) {
                StaticVal.oldManTimer.put(oldMan, false);
            }
            return StaticVal.oldManTimer.get(oldMan);
        }else{
            return false;
        }
    }

    public String getPositionInfo(int data, Room room){
        if(data != 0)
            switch (data){
                case 1:
                    return room.getNumOne();
                case 2:
                    return room.getNumTwo();
                case 3:
                    return room.getNumThree();
                case 4:
                    return room.getNumFour();
                case 5:
                    return room.getNumFive();
                case 6:
                    return room.getNumSix();
                case 7:
                    return room.getNumSeven();
                case 8:
                    return room.getNumEight();
                case 9:
                    return room.getNumNine();
                default:
                    return "地板";
            }
        return "无人";

    }

    public void checkPositionData(List<SensorCollection> sensorCollections) throws NullFromDBException, WarnException{
        SystemController.logger.info("======================================行为预警2.0=========================================================");
//        try {
            final SensorDataDeal sensorDataDeal = new SensorDataDeal();
            SensorCollection sensorCollection = null;
            OldMan oldMan=dataDao.getOldManByGatewayID(sensorCollections.get(0).getGatewayID());
            if(oldMan==null){
                throw new NullFromDBException("行为预警：找不到老人");
            }
            List<Room> roomList = roomDao.getAllRoomByOldManId(oldMan.getOid());
            Map<Integer,Integer> roomPosition = new HashMap<>();
            if(roomPs.get(oldMan.getOid()) != null)
                roomPosition = roomPs.get(oldMan.getOid());
            if(roomPosition.size() == 0)
            for(Room room:roomList){
                roomPosition.put(room.getRid(),11);//赋予初始值，没有位置的值为11
            }
            Map<Integer,Integer> roomPositionTmp = roomPosition;
            List<String> roomPos = new ArrayList<>();
            for(SensorCollection sensorCollection1:sensorCollections){
                Room room=roomDao.getRoomByGateWayId_SensorId(sensorCollection1.getGatewayID(),sensorCollection1.getSensorPointID());
                roomPos.add(room.getRid().toString()+"#"+sensorCollection1.getSensorData().toString());
            }
            Integer numOfChange = 0;
            for(String roomPs:roomPos){
                String tmp[] = roomPs.split("#");
                Integer room_id = Integer.parseInt(tmp[0]);
                Integer pos = Integer.parseInt(tmp[1]);
                if(!roomPosition.get(room_id).equals(pos)){
                    roomPosition.remove(room_id);
                    roomPosition.put(room_id,pos);
                    ++numOfChange;
                }
            }
            Set<String> setId = new HashSet<>();
            Boolean situation2 = true;
            if(numOfChange >= 1){
                int num = 0;
                for(int i = sensorCollections.size()-1;i>=0 ;i--){//考虑从覆盖区域离开的情况
                        if(setId.add(sensorCollections.get(i).getSensorPointID())){
                          if(sensorCollections.get(i).getSensorData() != 0) {
                              sensorCollection = sensorCollections.get(i);
                              break;
                          }
                          else
                              ++num;
                        }
                        if (num == roomPosition.size()) {
                            break;
                        }
                }
            }
                if(numOfChange == 0) {
                    SystemController.logger.info("位置没有变化");
                    return;
                }
            if (sensorCollection == null) {
                SystemController.logger.info("可探测范围内没有老人位置，可能在其他区域或者出门");
                sensorCollection = sensorCollections.get(sensorCollections.size()-1);
                situation2 = false;
                if(sensorCollection.getGatewayID().equals(43)){
                    if (warn1.get(sensorDataDeal.getOldMan()) != null) {
                        warn1.remove(sensorDataDeal.getOldMan());
                    }
                    if (warn2.get(sensorDataDeal.getOldMan()) != null) {
                        warn2.remove(sensorDataDeal.getOldMan());
                    }
                    if (timer.get(sensorDataDeal.getOldMan()) != null) {
                        timer.get(sensorDataDeal.getOldMan()).shutdown();
                        timer.remove(sensorDataDeal.getOldMan());
                    }
                }
//                sensorCollection = sensorCollections.get(sensorCollections.size() - 1);
            }
            roomPs.put(oldMan.getOid(),roomPosition);
            Room room=roomDao.getRoomByGateWayId_SensorId(sensorCollection.getGatewayID(),sensorCollection.getSensorPointID());

            if(room==null){
                throw new NullFromDBException("行为预警：找不到房间");
            }
            sensorDataDeal.setActivityRoom(room);
            String position = null;
            if(sensorCollection.getSensorData() != 0)
                position = commonService.getPositionInfo(sensorCollection.getSensorData(),room);
            else
                position = commonService.getPositionInfo(10,room);
            sensorDataDeal.setOldMan(oldMan);
            String ctime[] = sensorCollection.getTime().split(" ");
            sensorDataDeal.setTime(ctime[1]);
            SystemController.logger.info(sensorDataDeal.toString());
            if (warn1.get(sensorDataDeal.getOldMan()) != null) {
                warn1.remove(sensorDataDeal.getOldMan());
            }
            if (warn2.get(sensorDataDeal.getOldMan()) != null) {
                warn2.remove(sensorDataDeal.getOldMan());
            }
            if (timer.get(sensorDataDeal.getOldMan()) != null) {
                timer.get(sensorDataDeal.getOldMan()).shutdown();
                timer.remove(sensorDataDeal.getOldMan());
            }
            if (door.get(sensorDataDeal.getOldMan()) != null && situation2) {
                door.remove(sensorDataDeal.getOldMan());
            }
            if (warnNoCome.get(sensorDataDeal.getOldMan()) == null && situation2) {
                warnNoCome.remove(sensorDataDeal.getOldMan());
            }
            if (outdoorY.get(sensorDataDeal.getOldMan()) != null && situation2) {
                outdoorY.remove(sensorDataDeal.getOldMan());
            }
            if (timerDoor.get(sensorDataDeal.getOldMan()) != null && situation2) {
                timerDoor.get(sensorDataDeal.getOldMan()).shutdown();
                timerDoor.remove(sensorDataDeal.getOldMan());
            }
            Threshold_area threshold_area = new Threshold_area();
            if(sensorCollection.getSensorData() == 0)
                threshold_area.setArea(10);
            else
            threshold_area.setArea(sensorCollection.getSensorData());
            threshold_area.setRoomId(sensorDataDeal.getActivityRoom().getRid());
            Threshold_area threshold = thresholdDao.getThresholdAreaByRidAndNum(threshold_area);

            //Threshold threshold = thresholdDao.getThresholdByRoomId(sensorDataDeal.getActivityRoom().getRid());
            if(threshold==null){
                throw new NullFromDBException("行为预警：找不到阈值");
            }

            //判断 该时间是否在 该房间活动规律时间段内
            //活动该房间的活动规律信息
            AreaModel aModel = new AreaModel();
            if(sensorCollection.getSensorData() == 0)
            aModel.setArea(10);
            else
                aModel.setArea(sensorCollection.getSensorData());
            aModel.setRoomId(sensorDataDeal.getActivityRoom().getRid());
            AreaModel areaModel = modelDao.getAreaModelByRidAndArea(aModel);
          //  RoomModel roomModel = modelDao.getRoomModelByRoomId(sensorDataDeal.getActivityRoom().getRid());
            if(areaModel==null){
                //如果没有该房间的活动模型的话  new 一个空
               areaModel = new AreaModel();
            }
            //暂不考虑 该时间段有时活动 有时休息
            MomentInTime momentInTime = new MomentInTime();
            MomentInTime momentInTimeA = new MomentInTime();
            MomentInTime momentInTimeR = new MomentInTime();
            String inTime = "";//在规律模型中的哪个时间段
            String active = areaModel.getAreaActiveTime();
            //遍历  房间规律模型的 活动时间段
            if (active != null && !active.equals("")) {
                ArrayList<String> activeTimes = new ArrayList<String>(Arrays.asList(areaModel.getAreaActiveTime().split("#"))); //xx:xx-yy:yy
                //判断是否第一个时间段与最后一个连着  比如睡觉00:00:00-06:30:00   20:30:00-24:00:00
                if (activeTimes.size() > 0) {
                    if (activeTimes.get(activeTimes.size() - 1).split("-")[1].equals("24:00") && activeTimes.get(0).split("-")[0].equals("00:00")) {
                        String time = activeTimes.get(activeTimes.size() - 1).split("-")[0] + "-" + activeTimes.get(0).split("-")[1];
                        activeTimes.remove(activeTimes.size() - 1);
                        activeTimes.remove(0);
                        activeTimes.add(time);
                    }
                    for (String activeTime : activeTimes) {
                        momentInTimeA = moment_timeDeal(sensorDataDeal.getTime(), activeTime);
                        if (!momentInTimeA.isInTime()) {
                            continue;
                        }
                        //在该时间段内  各时间段是没有重合的 所以只要一个时间段符合  其他时间段一定不符合 但是活动时间段可能与休息时间段重叠
                        else {
                            inTime = activeTime;
                            momentInTimeA.setFlag("a");
                            break;
                        }
                    }
                }
            }
            if (momentInTimeA.isInTime() == null) {
                momentInTimeA.setInTime(false);
            }
            if (!momentInTimeA.isInTime()) {
                String rest = areaModel.getAreaRestTime();
                //遍历  房间规律模型的 休息时间段  活动时间段可能与休息时间段重叠
                if (rest != null && !rest.equals("")) {
                    ArrayList<String> restTimes = new ArrayList<String>(Arrays.asList(areaModel.getAreaRestTime().split("#"))); //xx:xx-yy:yy
                    //判断是否第一个时间段与最后一个连着  比如睡觉00:00:00-06:30:00   20:30:00-24:00:00
                    if (restTimes.size() > 0) {
                        if (restTimes.get(restTimes.size() - 1).split("-")[1].equals("24:00") && restTimes.get(0).split("-")[0].equals("00:00")) {
                            String time = restTimes.get(restTimes.size() - 1).split("-")[0] + "-" + restTimes.get(0).split("-")[1];
                            restTimes.remove(restTimes.size() - 1);
                            restTimes.remove(0);
                            restTimes.add(time);
                        }
                        for (String restTime : restTimes) {
                            momentInTimeR = moment_timeDeal(sensorDataDeal.getTime(), restTime);
                            if (!momentInTimeR.isInTime()) {
                                continue;
                            }
                            //在该时间段内  各时间段是没有重合的 所以只要一个时间段符合  其他时间段一定不符合 但是活动时间段可能与休息时间段重叠
                            else {
                                inTime = restTime;
                                momentInTimeR.setFlag("r");
                                break;
                            }
                        }
                    }
                }
                if (momentInTimeR.isInTime() == null) {
                    momentInTimeR.setInTime(false);
                }
            }
            if (momentInTimeA != null && momentInTimeA.isInTime()) {
                momentInTime.setFlag("a");
                momentInTime.setInTime(true);
            } else if (momentInTimeR != null && momentInTimeR.isInTime()) {
//            if(momentInTime.getFlag().equals("a")){
//                momentInTime.setFlag("a&r");
//            }else{
                momentInTime.setFlag("r");
                momentInTime.setInTime(true);
//            }
            } else {
                momentInTime.setInTime(false);
            }
            Warn warn = new Warn();
            warn.setRoom(sensorDataDeal.getActivityRoom());
            warn.setOldMan(sensorDataDeal.getOldMan());
            warn.setTime(sensorDataDeal.getTime());
            warn.setPositon(position);
            //在规律模型中
            if (!inTime.equals("")) {
                warn.setInTime("true");
                warn.setTimes(inTime);
            } else {
                warn.setInTime("false");
            }

            //设置 阈值
            if (momentInTime.isInTime()) {
                //老人不动时 处于该房间规律模型的时间段内
                switch (momentInTime.getFlag()) {
                    case "a":
                        warn.setFlag("a");
                        threshold1.put(sensorDataDeal.getOldMan(), threshold.getA1Threshold() * 60);
                        threshold2.put(sensorDataDeal.getOldMan(), threshold.getA2Threshold() * 60);
                        break;
                    case "r":
                        warn.setFlag("r");
                        threshold1.put(sensorDataDeal.getOldMan(), threshold.getR1Threshold() * momentInTimeR.getTime() / 100);
                        threshold2.put(sensorDataDeal.getOldMan(), threshold.getR2Threshold() * momentInTimeR.getTime() / 100);
//                    break;
//                case "a&r":
//                    warn.setFlag("a&r");
//                    //该时间段 既可能休息又可能活动 去两者 值小的
//                    int a=threshold.getA1Threshold()*60;
//                    int r=threshold.getR1Threshold()*momentInTimeR.getTime();
//                    threshold1.put(sensorDataDeal.getOldMan(),(a>=r?r:a));
//                    int a2=threshold.getA2Threshold()*60;
//                    int r2=threshold.getR2Threshold()*momentInTime.getTime();
//                    threshold2.put(sensorDataDeal.getOldMan(),(a2>=r2?r2:a2));
                }
            } else {
                //不在规律模型的时间段
                threshold1.put(sensorDataDeal.getOldMan(), threshold.getN1Threshold() * 60);
                threshold2.put(sensorDataDeal.getOldMan(), threshold.getN2Threshold() * 60);
            }

            warnMap.put(sensorDataDeal.getOldMan(), warn);


            Runnable runnable = new Runnable() {
                public void run() {
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                    String currentTime = sdf.format(d);
                    SystemController.logger.info("当前时间：" + currentTime + "   最初不动时间：" + sensorDataDeal.getTime());
                    int value = intervalTime(currentTime, sensorDataDeal.getTime());
                    SystemController.logger.info("老人已经不动：" + (value / 60) + "分钟");
                    if (warn2.get(sensorDataDeal.getOldMan()) != null || outdoorY.get(sensorDataDeal.getOldMan()) != null) {
                        //进行了二级预警 或者 老人出门 停止定时任务
                        SystemController.logger.info("已进行了二级预警 或者 老人出门 停止定时任务");
                        if (timer.get(sensorDataDeal.getOldMan()) != null) {
                            timer.get(sensorDataDeal.getOldMan()).shutdown();
                            SystemController.logger.info("已停止定时器");
                        }

                    } else {
                        //一级预警
                        SystemController.logger.info("还没进行二级预警且没有出门");
                        if (warn1.get(sensorDataDeal.getOldMan()) == null) {
                            SystemController.logger.info("还没进行一级预警  value=" + value + "  一级阈值=" + threshold1.get(sensorDataDeal.getOldMan()));
                            if (value >= threshold1.get(sensorDataDeal.getOldMan())) {
                                SystemController.logger.info("一级报警");

                                Warn warn = warnMap.get(sensorDataDeal.getOldMan());
                                warn.setWarnLevel(1);
                                warn.setNoMoveTime(value / 60);
                                if(sensorDataDeal.getOldMan().getGatewayID().equals("43")){
                                    SendAlarm sendAlarm = new SendAlarm();
                                    sendAlarm.setInfo(warn.getNoMoveTime().toString());
                                    sendAlarm.setTime(warn.getTime());
                                    sendAlarm.setLevel(warn.getWarnLevel().toString());
                                    sendAlarm.setType("position");
                                    sendPost(sendAlarm,StaticVal.url);
                                }

                                    DwrData dwrData = new DwrData();
                                    dwrData.setType("warn_position");
                                    dwrData.setWarn(warn);
                                    SystemController.logger.info(warn.toString());
                                    //存入历史消息
                                    warnHistoryService.addWarnHistory(dwrData);
                                    SystemController.logger.info("已存入历史消息");
                                    //推送
                                    Remote.noticeNewOrder(dwrData);

                                    //地图更新
//                                HouseMarker houseMarker=new HouseMarker();
//                                houseMarker.setOid(dwrData.getWarn().getOldMan().getOid());
//                                houseMarker.setStyleIndex(8); //红色
//                                houseMarker.setDetail("行为预警&nbsp;&nbsp;&nbsp;不动开始时刻："+dwrData.getWarn().getTime());
//                                mapUpdate(houseMarker);

                                    sensorDataDeal.getOldMan().setStatus(2);
                                    mapUpdate(sensorDataDeal.getOldMan());

                                    //启动短信定时任务
                                    smsService.smsSwitch();
                                    SystemController.logger.info("已进行报警");
                                    sendPost2(dwrData,StaticVal.url2);

                                //设置已经进行了一级报警
                                warn1.put(sensorDataDeal.getOldMan(), true);
                            }
                        } else {
                            //二级预警
                            SystemController.logger.info("还没进行二级预警  value=" + value + "  二级阈值=" + threshold2.get(sensorDataDeal.getOldMan()));
                            if (value >= threshold2.get(sensorDataDeal.getOldMan())) {
                                SystemController.logger.info("二级报警");
                                Warn warn = warnMap.get(sensorDataDeal.getOldMan());
                                warn.setWarnLevel(2);
                                warn.setNoMoveTime(value / 60);
                                if(sensorDataDeal.getOldMan().getGatewayID().equals("43")){
                                    SendAlarm sendAlarm = new SendAlarm();
                                    sendAlarm.setInfo(warn.getNoMoveTime().toString());
                                    sendAlarm.setTime(warn.getTime());
                                    sendAlarm.setLevel(warn.getWarnLevel().toString());
                                    sendAlarm.setType("position");
                                    sendPost(sendAlarm,StaticVal.url);
                                }

                                    DwrData dwrData = new DwrData();
                                    dwrData.setType("warn_position");
                                    dwrData.setWarn(warn);
                                    //存入历史消息
                                    warnHistoryService.addWarnHistory(dwrData);
                                    //推送
                                    Remote.noticeNewOrder(dwrData);
                                    sendPost2(dwrData,StaticVal.url2);
                                    //地图更新
//                                HouseMarker houseMarker=new HouseMarker();
//                                houseMarker.setOid(dwrData.getWarn().getOldMan().getOid());
//                                houseMarker.setStyleIndex(8); //红色
//                                houseMarker.setDetail("行为预警&nbsp;&nbsp;&nbsp;不动开始时刻："+dwrData.getWarn().getTime());
//                                mapUpdate(houseMarker);
                                    sensorDataDeal.getOldMan().setStatus(2);
                                    mapUpdate(sensorDataDeal.getOldMan());
                                    smsService.smsSwitch();



                                //启动短信定时任务

                                //设置已经进行了二级报警.2
                                warn2.put(sensorDataDeal.getOldMan(), true);
                            }
                        }
                    }
                }
            };

            ScheduledExecutorService service = Executors
                    .newSingleThreadScheduledExecutor();
            // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
            service.scheduleAtFixedRate(runnable, 1, 60, TimeUnit.SECONDS);

            timer.put(sensorDataDeal.getOldMan(), service);
//具体过程为如果在最后一个动的时间之后，老人一直不动话，此线程不会被停止，所以会继续计时知道超出阈值。如果老人再次有动作后，删除之前的线程，重新开始计时线程，转353.
//        }catch (NullFromDBException e1){
//            throw e1;
//        }catch (Exception e){
//            throw new WarnException("move inner error:"+e.getMessage());
//        }
    }
    private void  sendPost2(DwrData sendAlarm,String urlS) {
        //JSONObject json = new JSONObject();
        //
        JSONObject json = JSONObject.fromObject(sendAlarm);
        //
//        json.put("time",sendAlarm.getTime());
//        json.put("info",sendAlarm.getInfo());
//        json.put("type",sendAlarm.getType());
//        json.put("level",sendAlarm.getLevel());



        BufferedReader in = null;
        String status = "";
        String response = "";
        String content = json.toString();
        DataOutputStream out = null;
        try {
            URL url = new URL(urlS);
            // 打开和URL之间的连接
            URLConnection conn = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
            // 设置请求属性
            httpUrlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpUrlConnection.setRequestProperty("Accept-Charset","utf-8");
            httpUrlConnection.setRequestProperty("Charset","utf-8");
            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.connect();
            out = new DataOutputStream(httpUrlConnection.getOutputStream());
            // 获取URLConnection对象对应的输出流

            out.write(content.getBytes("UTF-8"));
            // 发送请求参数
            // flush输出流的缓冲
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response += line;
            }
            status = new Integer(httpUrlConnection.getResponseCode()).toString();
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) { out.close();}
                if (in != null) {in.close();}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void  sendPost(SendAlarm sendAlarm,String urlS){
        JSONObject json = new JSONObject();
        json.put("time",sendAlarm.getTime());
        json.put("info",sendAlarm.getInfo());
        json.put("type",sendAlarm.getType());
        json.put("level",sendAlarm.getLevel());
        BufferedReader in = null;
        String status = "";
        String response = "";
        String content = json.toString();
        DataOutputStream out = null;
        try {
            URL url = new URL(urlS);
            // 打开和URL之间的连接
            URLConnection conn = url.openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
            // 设置请求属性
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.connect();
            out = new DataOutputStream(httpUrlConnection.getOutputStream());
            // 获取URLConnection对象对应的输出流

            out.writeBytes(content);
            // 发送请求参数
            // flush输出流的缓冲
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response += line;
            }
            status = new Integer(httpUrlConnection.getResponseCode()).toString();
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) { out.close();}
                if (in != null) {in.close();}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
