package com.warn.service.impl;

import com.warn.controller.SensorController;
import com.warn.controller.SystemController;
import com.warn.dao.DataDao;
import com.warn.dao.EquipDao;
import com.warn.dao.RoomDao;
import com.warn.dto.*;
import com.warn.dwr.Remote;
import com.warn.entity.*;
import com.warn.exception.GetMDBException;
import com.warn.exception.NullFromDBException;
import com.warn.exception.WarnException;
import com.warn.mongodb.dao.SensorMogoDao;
import com.warn.mongodb.model.SensorCollection;
import com.warn.mongodbSec.dao.SensorMogoSecDao;
import com.warn.mongodbSec.model.SecSensorCollection;
import com.warn.service.DownHistoryService;
import com.warn.service.SensorService;
import com.warn.service.TimerService;
import com.warn.util.StaticVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2017/4/30.
 */
@Service
public class TimerServiceImpl implements TimerService {

    @Autowired
    DataDao dataDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    SensorMogoDao sensorMogoDao;
    @Autowired
    SensorService sensorService;
    @Autowired
    EquipDao equipDao;
    @Autowired
    DownHistoryService downHistoryService;
    @Autowired
    SensorMogoSecDao sensorMogoSecDao;

    public static List<SensorCollection> preSensorCollections=new ArrayList<>();//存取 上一次获得的传感器记录 因为加了一分钟的延迟 所以在最新的检测时间段中删除之前进行过预警的数据
   // public static List<SensorCollection> preSecSensorCollections = new ArrayList<>();
    public static Map<OldMan,ScheduledExecutorService> databaseTimer=new HashMap<>();//存放各个老人读取数据库的定时任务

    public static Map<OldMan,String> oldManGatewayDown=new HashMap<>();//存放各个老人网关传的数据量为0的开始时间  用于网关故障  如果 值为 ‘0’则表示已经报过警 避免重复报警

    public static Map<EquipDown,String> oldManEquipDown=new HashMap<>();//存放各个老人某个设备传的数据量为0的开始时间  用于设备故障 如果 值为 ‘0’则表示已经报过警 避免重复报警

    @Override
    public DataGrid getDatagrid(PageHelper page, OldMan oldMan) {
        DataGrid dataGrid=new DataGrid();
        List<TimeDto> timeDtos=new ArrayList<>();
        List<TimeDto> timeDtoAll=new ArrayList<>();
        //先 仅根据oid查询
        if(oldMan.getOid()!=null) {
            TimeDto timeDto = new TimeDto();
            OldMan oldMan1=dataDao.getOldManByOid(oldMan.getOid());
            timeDto.setOldMan(oldMan1);
            timeDto.setTimerSwitch(StaticVal.oldManTimer.get(oldMan) ? 1 : 0);
            timeDtos.add(timeDto);
            dataGrid.setTotal((long) 1);
        }else {
            dataGrid.setTotal((long) StaticVal.oldManTimer.size());
            page.setStart((page.getPage() - 1) * page.getRows());
            page.setEnd(page.getPage() * page.getRows());
            for (OldMan key : StaticVal.oldManTimer.keySet()) {
                TimeDto timeDto = new TimeDto();
                timeDto.setOldMan(key);
                timeDto.setTimerSwitch(StaticVal.oldManTimer.get(key) ? 1 : 0);
                timeDtoAll.add(timeDto);
            }
            if(page.getEnd()>timeDtoAll.size()){
                page.setEnd(timeDtoAll.size());
            }
            for(int i=page.getStart();i<page.getEnd();i++){
                if(timeDtoAll.get(i)!=null){
                    timeDtos.add(timeDtoAll.get(i));
                }
            }
        }
        dataGrid.setRows(timeDtos);

        return dataGrid;
    }

    @Override
    public void updateTimer(final TimeDto timeDto) {
        //进行关操作
        if(timeDto.getTimerSwitch()==0){
            closeTimer(timeDto);
        }else {
            SystemController.logger.info("预警开启");
            try {
                StaticVal.oldManTimer.put(timeDto.getOldMan(), true);
                final OldMan oldMan=dataDao.getOldManByOid(timeDto.getOldMan().getOid());
                Integer version = oldMan.getVersion();

                //设备故障 预处理操作
                List<Equipment> equipmentList=equipDao.getAllEquipByOldManId(timeDto.getOldMan().getOid());
                final List<EquipDown> equipDownList=new ArrayList<>();
//                Date dDown = new Date();
//                SimpleDateFormat sdfDown = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                sdfDown.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//                String currentTimeDown = sdfDown.format(dDown);
                if(version == 1)
                for(Equipment equipment:equipmentList){
                    for(int i=2;i<5;i++) {
                        EquipDown equipDown=new EquipDown();
                        equipDown.setEid(equipment.getEid());
                        equipDown.setGatewayID(oldMan.getGatewayID());
                        // 2 ,3,4 分别代表 温度、湿度、光强   只检测这三个设备的故障
                        equipDown.setType(i);
                        equipDownList.add(equipDown);
//                        oldManEquipDown.put(equipDown, currentTimeDown);
                    }
                }

//                EquipDown equipDown=new EquipDown();
//                equipDown.setEid("0");
//                equipDown.setGatewayID(oldMan.getGatewayID());
//                equipDown.setType(7);
//                equipDownList.add(equipDown);

                //获取该老人的所有设备
//                final List<Equipment> equipments = equipDao.getAllEquipByOldManId(timeDto.getOldMan().getOid());
//                if(equipments==null){
//                    throw new NullFromDBException("定时任务：找不到设备");
//                }
                Runnable runnable = new Runnable() {
                    public void run() {

                        //已开启 预警自动总开关  获得所有预警开关被关闭的 老人的 网关ID  用于判断 是否自动启动该网关
                        List<Integer> closeWarns=new ArrayList<>();
                        if(StaticVal.timerOpen==1) {
                            SystemController.logger.info("自动总开关开");
                            for (OldMan oldMan : StaticVal.oldManTimer.keySet()) {
                                if(StaticVal.oldManTimer.get(oldMan)!=true) {
                                    closeWarns.add(Integer.parseInt(oldMan.getGatewayID()));
                                    SystemController.logger.info("关闭的老人的网关ID：" + oldMan.getGatewayID());
                                }
                            }
                        }


                        Date d = new Date();
//                    Date d1 = new Date(d.getTime() - 12*60*60*1000);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                        String currentTime = sdf.format(d);

                        Date s = new Date(d.getTime() - (StaticVal.accessDatabaseTime + 1) * 60 * 1000); //开始的时间 当前时间推迟5分钟   因为传感器发送到数据库 有 1-59秒不等的延迟
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                        String startTime = dateFormat.format(s);

                        SystemController.logger.info("当前检测时间段：" + startTime + "-------" + currentTime);
                        List<SensorCollection> sensorCollectionListAll = null;
                     //   List<SensorCollection> secSensorCollectionListALL = null;
                        try {
                            //从mongodb数据库获得该时间段 该老人的数据
//                            OldMan oldMan=dataDao.getOldManByOid(timeDto.getOldMan().getOid());
                            if(oldMan.getVersion() == 2)
                            sensorCollectionListAll = sensorMogoSecDao.findByTime(startTime, currentTime, Integer.parseInt(oldMan.getGatewayID()),closeWarns);
                            else
                            sensorCollectionListAll = sensorMogoDao.findByTime(startTime, currentTime, Integer.parseInt(oldMan.getGatewayID()),closeWarns);
                        } catch (GetMDBException e1) {
                            SystemController.logger.info(e1.getMessage());
                            closeTimer(timeDto);
                        } catch (Exception e) {
                            SystemController.logger.info("获取传感器数据时出错");
                            SystemController.logger.info(e.getMessage());
                            closeTimer(timeDto);
                        }
                      //  sensorCollectionListAll.addAll(secSensorCollectionListALL);

                        List<SensorCollection> sensorCollectionList=null;
                       // List<SensorCollection> secSensorCollectionList=null;
                        if(closeWarns.size()>0) {
//                            SystemController.logger.info("处理前：");
//                            for(SensorCollection sensorCollection:sensorCollectionListAll){
//                                SystemController.logger.info(sensorCollection.toString());
//                            }
                            sensorCollectionList = heartDeal(sensorCollectionListAll, closeWarns);
                           // secSensorCollectionList = heartDeal(secSensorCollectionListALL, closeWarns);
//                            SystemController.logger.info("处理后：");
//                            for(SensorCollection sensorCollection:sensorCollectionList){
//                                SystemController.logger.info(sensorCollection.toString());
//                            }
                        }else{
                            sensorCollectionList=sensorCollectionListAll;
                          //  secSensorCollectionList = secSensorCollectionListALL;
                        }

                        //删除之前 已检测的重复数据
                        if (preSensorCollections.size() > 0) {
                            for (SensorCollection sensorCollection : preSensorCollections) {
                                if (sensorCollectionList.contains(sensorCollection)) {
                                    sensorCollectionList.remove(sensorCollection);
                                }
                            }
                        }
//                        if (preSecSensorCollections.size() > 0) {
//                            for (SensorCollection sensorCollection : preSecSensorCollections) {
//                                if (secSensorCollectionList.contains(sensorCollection)) {
//                                    secSensorCollectionList.remove(sensorCollection);
//                                }
//                            }
//                        }

//                    List<SensorCollection> sensorCollectionList=sensorMogoDao.findByTime("2017-05-07 18:48:05","2017-05-07 18:54:05",equipments);
                        if (sensorCollectionList != null) {
//                            SystemController.logger.info("传感器数据数量：" + sensorCollectionList.size());
                            //故障处理
                            gatewayDown(currentTime,sensorCollectionList.size(),oldMan,equipDownList);//判断该老人的网关是否故障
                            if(sensorCollectionList.size()>0) {
                                equipDown(currentTime, sensorCollectionList, oldMan,equipDownList);//判断该老人的设备是否故障  只判断 温度、湿度、光强
                                lowBattery(sensorCollectionList,oldMan);
                            }
                        } else {
//                            SystemController.logger.info("传感器数据为空");
                        }
//                        if (secSensorCollectionList != null) {
////                            SystemController.logger.info("传感器数据数量：" + sensorCollectionList.size());
//                            //故障处理
//                            gatewayDown(currentTime,secSensorCollectionList.size(),oldMan,equipDownList);//判断该老人的网关是否故障
//                            if(secSensorCollectionList.size()>0) {
//                                equipDown(currentTime, secSensorCollectionList, oldMan,equipDownList);//判断该老人的设备是否故障  只判断 温度、湿度、光强
//                            }
//                        }
                        SensorCollections sensorCollections = new SensorCollections();
                        sensorCollections.setSensorCollections(sensorCollectionList);
//                        SensorCollections secSensorCollections = new SensorCollections();
//                        secSensorCollections.setSensorCollections(secSensorCollectionList);
                        SensorType sensorType = new SensorType();
                        if(oldMan.getVersion() == 1)
                            sensorType = sensorService.conType(sensorCollections);
                        if(oldMan.getVersion() == 2)
                            sensorType = sensorService.conSecType(sensorCollections);
                      //  SensorType sensorType1 = sensorService.conType(secSensorCollections);
//                        OldMan oldMan = null;
//                        if (sensorCollectionList.size() > 0) {
//                            oldMan = dataDao.getOldManByGatewayID(sensorCollectionList.get(0).getGatewayID());
//                        }
                        try {
                            if (oldMan != null && SensorServiceImpl.outdoorY.get(oldMan) != null && SensorServiceImpl.outdoorY.get(oldMan) == true) {
                                //门
                               // List<SensorCollection> doorSensorCollectionLis1 = sensorType1.getDoorSensorCollection();
                                List<SensorCollection> doorSensorCollectionLis = sensorType.getDoorSensorCollection();
                                if (doorSensorCollectionLis.size() > 0) {
                                    sensorService.checkDoorData(doorSensorCollectionLis);
                                    //让门的 定时任务先执行完
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                //温度预警
                                List<SensorCollection> wenduSensorCollectionLis = sensorType.getWenduSensorCollection();
                                if (wenduSensorCollectionLis.size() > 0) {
                                    sensorService.checkWenduData(wenduSensorCollectionLis);
                                }
                                //光亮预警
                                List<SensorCollection> lightSensorCollectionLis = sensorType.getLightSensorCollection();
                                if (lightSensorCollectionLis.size() > 0) {
                                    sensorService.checkLightData(lightSensorCollectionLis);
                                }
                                //行为预警/位置预警
                                List<SensorCollection> moveSensorCollectionLis = sensorType.getMoveSensorCollection();
                                if (moveSensorCollectionLis.size() > 0) {
                                    if(oldMan.getVersion() == 1)
                                    sensorService.checkMoveData(moveSensorCollectionLis);
                                    else
                                        sensorService.checkPositionData(moveSensorCollectionLis);
                                }
                            } else {
                                //行为预警/位置预警
                                List<SensorCollection> moveSensorCollectionLis = sensorType.getMoveSensorCollection();
                                if (moveSensorCollectionLis.size() > 0) {
                                    if(oldMan.getVersion() == 1)
                                    sensorService.checkMoveData(moveSensorCollectionLis);
                                    else
                                        sensorService.checkPositionData(moveSensorCollectionLis);
                                }
                                //温度预警
                                List<SensorCollection> wenduSensorCollectionLis = sensorType.getWenduSensorCollection();
                                if (wenduSensorCollectionLis.size() > 0) {
                                    sensorService.checkWenduData(wenduSensorCollectionLis);
                                }
                                //光亮预警
                                List<SensorCollection> lightSensorCollectionLis = sensorType.getLightSensorCollection();
                                if (lightSensorCollectionLis.size() > 0) {
                                    sensorService.checkLightData(lightSensorCollectionLis);
                                }
                                //门
                                List<SensorCollection> doorSensorCollectionLis = sensorType.getDoorSensorCollection();
                                if (doorSensorCollectionLis.size() > 0) {
                                    sensorService.checkDoorData(doorSensorCollectionLis);
                                }
                            }
                        } catch (NullFromDBException e1) {
                            SystemController.logger.info("读取Mysql数据库，null值异常");
                            SystemController.logger.info(e1.getMessage());
                            closeTimer(timeDto);
                        } catch (Exception e) {
                            SystemController.logger.info("预警算法异常");
                            SystemController.logger.info(e.getMessage());
                            closeTimer(timeDto);
                        }

                        preSensorCollections = sensorCollectionList;

                    }
                };

                //开启 数据库定时任务
                ScheduledExecutorService service = Executors
                        .newSingleThreadScheduledExecutor();
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                service.scheduleAtFixedRate(runnable, 1, StaticVal.accessDatabaseTime * 60, TimeUnit.SECONDS);
                databaseTimer.put(timeDto.getOldMan(), service);

            }catch (NullFromDBException e1){
                SystemController.logger.info("定时器出现null值："+e1.getMessage());
                closeTimer(timeDto);
            }catch (Exception e){
                SystemController.logger.info("定时器任务算法出错："+e.getMessage());
                closeTimer(timeDto);
            }
        }

    }
    //门磁电量
    public void lowBattery(List<SensorCollection> sensorCollectionList, OldMan oldman) {
        for(SensorCollection sensorCollection : sensorCollectionList){
            EquipDown equipDown = new EquipDown();
            equipDown.setEid(sensorCollection.getSensorPointID());
            equipDown.setType(5);
            equipDown.setGatewayID(sensorCollection.getGatewayID()+"");
            if(sensorCollection.getSensorID() == 6){
                DownData downData=new DownData();
                downData.setOldMan(oldman);
                downData.setTypeDown("门磁电量低");
                Date d1 = new Date();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                String dateNowStr = sdf1.format(d1);
                downData.setTimeDown(dateNowStr);
                downData.setReadDown("否");
                downData.setDataDown("网关："+sensorCollection.getGatewayID()+"  设备ID："+sensorCollection.getSensorPointID()+"  设备种类：门磁");
                downHistoryService.addDownData(downData);

                DwrData dwrData = new DwrData();
                dwrData.setType("equipDown");
                dwrData.setOldMan(oldman);
                dwrData.setEquipDown(equipDown);
                //推送前台  提示设备故障
                Remote.noticeNewOrder(dwrData);
                SystemController.logger.info("门磁电量报警");

            }

        }
    }

    //设备故障
    private void equipDown(String currentTime, List<SensorCollection> sensorCollectionList, OldMan oldMan, List<EquipDown> equipDownList) {
        for(EquipDown equipDown:equipDownList) {
//            SystemController.logger.info("检测设备："+equipDown.getEid()+"--"+equipDown.getType());
            int f=0;//标志 有没有某个设备的记录     网关、设备ID、种类  标志一个特点设备
            for (SensorCollection sensorCollection : sensorCollectionList) {
                if(sensorCollection.getGatewayID()==Integer.parseInt(equipDown.getGatewayID())&&sensorCollection.getSensorPointID().equals(equipDown.getEid())&&
                        sensorCollection.getSensorID()==equipDown.getType()){
//                    SystemController.logger.info("有数据");
                    if(oldManEquipDown.get(equipDown)!=null) {
                        oldManEquipDown.remove(equipDown);
                    }
                    f=1;
                    break;
                }
            }
            if(f==0){
//                SystemController.logger.info("该设备没数据");
                //当前时间段没有该设备的记录 不为空 且没报过警
                if(oldManEquipDown.get(equipDown)!=null&&!oldManEquipDown.get(equipDown).equals("0")){
                    int value=sensorService.intervalTime(currentTime.substring(11,currentTime.length()),oldManEquipDown.get(equipDown)); //单位秒
//                    SystemController.logger.info("该设备没数据的时间："+(value/60));
                    if((value/60)>=StaticVal.equipDown){
                        DownData downData=new DownData();
                        downData.setOldMan(oldMan);
                        downData.setTypeDown("设备故障");
                        Date d1 = new Date();
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                        String dateNowStr = sdf1.format(d1);
                        downData.setTimeDown(dateNowStr);
                        downData.setReadDown("否");
                        downData.setDataDown("网关："+equipDown.getGatewayID()+"  设备ID："+equipDown.getEid()+"  设备种类："+equipDown.getType());
                        downHistoryService.addDownData(downData);

                        DwrData dwrData = new DwrData();
                        dwrData.setType("equipDown");
                        dwrData.setOldMan(oldMan);
                        dwrData.setEquipDown(equipDown);
                        //推送前台  提示设备故障
                        Remote.noticeNewOrder(dwrData);
                        SystemController.logger.info("设备故障报警");
                        oldManEquipDown.put(equipDown,"0");
                    }
                }else if(oldManEquipDown.get(equipDown)==null){
//                    SystemController.logger.info("该设备第一次为空");
                    //为空 且没报过警
                    oldManEquipDown.put(equipDown,currentTime.substring(11,currentTime.length()));
                }
            }
        }

    }

    //网关故障
    private void gatewayDown(String currentTime, int size, OldMan oldMan, List<EquipDown> equipDownList) {
        if(size==0){
            SystemController.logger.info("网关没数据");
            //之前没有的话 就存入map   有的话 判断到目前位置的时间间隔有没有超过阈值  值为0表示已经报过警了
            if(oldManGatewayDown.get(oldMan)!=null&&!oldManGatewayDown.get(oldMan).equals("0")){
                int value=sensorService.intervalTime(currentTime.substring(11,currentTime.length()),oldManGatewayDown.get(oldMan)); //单位秒
                SystemController.logger.info("网关没数据的时间："+(value/60));
                if((value/60)>=StaticVal.gatewayDown){
                    DownData downData=new DownData();
                    downData.setOldMan(oldMan);
                    downData.setTypeDown("网关故障");
                    Date d1 = new Date();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                    String dateNowStr = sdf1.format(d1);
                    downData.setTimeDown(dateNowStr);
                    downData.setReadDown("否");
                    downData.setDataDown("网关："+oldMan.getGatewayID());
                    downHistoryService.addDownData(downData);

                    DwrData dwrData = new DwrData();
                    dwrData.setType("gatewayDown");
                    dwrData.setOldMan(oldMan);
                    //推送前台  提示设备故障
                    Remote.noticeNewOrder(dwrData);
                    SystemController.logger.info("网关故障报警");
                    //网关报警 该老人所有设备的时间清零0
                    for(EquipDown equipDown:equipDownList){
                        if(oldManEquipDown.get(equipDown)!=null){
                            oldManEquipDown.remove(equipDown);
                        }
                    }
                    oldManGatewayDown.put(oldMan,"0");
                }
            }else if(oldManGatewayDown.get(oldMan)==null){
                //为空 且没报过警
                SystemController.logger.info("网关第一次为空");
                oldManGatewayDown.put(oldMan,currentTime.substring(11,currentTime.length()));
            }
        }else{
            //删除该老人的键值
            if(oldManGatewayDown.get(oldMan)!=null){
                oldManGatewayDown.remove(oldMan);
            }
        }
    }

    //处理 是否要启动某个已经关闭的老人  并从数据中删除该老人的传感器数据
    private List<SensorCollection> heartDeal(List<SensorCollection> sensorCollectionListAll, List<Integer> closeWarns) {
        for(Integer gatewayID:closeWarns) {
            for (SensorCollection sensorCollection : sensorCollectionListAll) {
                if(sensorCollection.getGatewayID().intValue()==gatewayID.intValue()){
                    OldMan oldMan=dataDao.getOldManByGatewayID(gatewayID);
                    if(StaticVal.oldManTimer.get(oldMan)==null||StaticVal.oldManTimer.get(oldMan)==false){
                        TimeDto timeDto=new TimeDto(1,oldMan);
                        updateTimer(timeDto);
                    }
                    sensorCollectionListAll.remove(sensorCollection);
                }
            }
        }
        return sensorCollectionListAll;
    }

    private List<SecSensorCollection> heartDealSec(List<SecSensorCollection> sensorCollectionListAll, List<Integer> closeWarns) {
        for(Integer gatewayID:closeWarns) {
            for (SecSensorCollection secSensorCollection : sensorCollectionListAll) {
                if(secSensorCollection.getGatewayID().intValue()==gatewayID.intValue()){
                    OldMan oldMan=dataDao.getOldManByGatewayID(gatewayID);
                    if(StaticVal.oldManTimer.get(oldMan)==null||StaticVal.oldManTimer.get(oldMan)==false){
                        TimeDto timeDto=new TimeDto(1,oldMan);
                        updateTimer(timeDto);
                    }
                    sensorCollectionListAll.remove(secSensorCollection);
                }
            }
        }
        return sensorCollectionListAll;
    }
    @Override
    public void addSwitch() {
        List<OldMan> oldMans=dataDao.getAllOldMan();
        for(OldMan oldMan:oldMans){
            if(StaticVal.oldManTimer.get(oldMan)==null){
                StaticVal.oldManTimer.put(oldMan,false);
            }
        }
    }

    public void closeTimer(TimeDto timeDto){
        SystemController.logger.info("预警关闭");
        //map清除
        TimerServiceImpl.oldManEquipDown.remove(timeDto.getOldMan());
        TimerServiceImpl.oldManGatewayDown.remove(timeDto.getOldMan());
        SensorServiceImpl.threshold1.remove(timeDto.getOldMan());
        SensorServiceImpl.threshold2.remove(timeDto.getOldMan());
        SensorServiceImpl.warnMap.remove(timeDto.getOldMan());
        SensorServiceImpl.warn1.remove(timeDto.getOldMan());
        SensorServiceImpl.warn2.remove(timeDto.getOldMan());
        SensorServiceImpl.door.remove(timeDto.getOldMan());
        SensorServiceImpl.outdoorY.remove(timeDto.getOldMan());
        SensorServiceImpl.warnNoCome.remove(timeDto.getOldMan());
        SensorServiceImpl.thresholdOutDoor.remove(timeDto.getOldMan());
        SensorServiceImpl.thresholdNoComeDoor.remove(timeDto.getOldMan());

        StaticVal.oldManTimer.put(timeDto.getOldMan(),false);
        //关操作的同时将两个定时任务关掉
        if(databaseTimer.get(timeDto.getOldMan())!=null){
            databaseTimer.get(timeDto.getOldMan()).shutdown();
            databaseTimer.remove(timeDto.getOldMan());
            SystemController.logger.info("数据库定时器关闭");
        }
        if(SensorServiceImpl.timer.get(timeDto.getOldMan())!=null){
            SensorServiceImpl.timer.get(timeDto.getOldMan()).shutdown();
            SensorServiceImpl.timer.remove(timeDto.getOldMan());
            SystemController.logger.info("行为定时器关闭");
        }
        if(SensorServiceImpl.timerDoor.get(timeDto.getOldMan())!=null){
            SensorServiceImpl.timerDoor.get(timeDto.getOldMan()).shutdown();
            SensorServiceImpl.timerDoor.remove(timeDto.getOldMan());
            SystemController.logger.info("门定时器关闭");
        }
        List<Room> rooms=roomDao.getAllRoomByOldManId(timeDto.getOldMan().getOid());
        for(Room room:rooms) {
            SensorServiceImpl.wendu.remove(room);
            SensorServiceImpl.light.remove(room);
            SensorServiceImpl.lightRoom.remove(room);
            if (SensorServiceImpl.lightTimer.get(room) != null) {
                SensorServiceImpl.lightTimer.get(room).shutdown();
                SensorServiceImpl.lightTimer.remove(room);
                SystemController.logger.info("光强定时器关闭");
            }
        }
    }


}
