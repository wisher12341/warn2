package com.warn.controller;

import com.warn.dto.*;
import com.warn.dwr.Remote;
import com.warn.entity.Equipment;
import com.warn.entity.OldMan;
import com.warn.exception.NullFromDBException;
import com.warn.mongodb.model.SensorCollection;
import com.warn.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 版本1 用于接受外部服务器发来的传感器数据   当前版本2 仅用来接受 紧急报警
 * Created by admin on 2017/4/10.
 */
@Controller
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    SensorService sensorService;



    //之前版本 用于接受 通信的服务器发送的传感器数据  由于存在  POST发送  跨域的问题 接受不到POST请求
    //不再使用
    @ResponseBody
    @RequestMapping(value = "/sensorData",method = RequestMethod.POST)
    public Result sensorData(SensorCollections sensorCollections){
        Boolean timerSwitch=sensorService.checkSwitch(sensorCollections);
        if(!timerSwitch){
            return new Result(true,"该老人预警机制没有打开");
        }
        SystemController.logger.info("该老人预警机制已开");
        SensorType sensorType=sensorService.conType(sensorCollections);
        //行为预警
        List<SensorCollection> moveSensorCollectionLis=sensorType.getMoveSensorCollection();
        if(moveSensorCollectionLis.size()>0) {
            sensorService.checkMoveData(moveSensorCollectionLis);
        }
        //温度预警
        List<SensorCollection> wenduSensorCollectionLis=sensorType.getWenduSensorCollection();
        if(wenduSensorCollectionLis.size()>0){
            sensorService.checkWenduData(wenduSensorCollectionLis);
        }
        //光亮预警
        List<SensorCollection> lightSensorCollectionLis=sensorType.getLightSensorCollection();
        if(lightSensorCollectionLis.size()>0){
            sensorService.checkLightData(lightSensorCollectionLis);
        }
        //门
        List<SensorCollection> doorSensorCollectionLis=sensorType.getDoorSensorCollection();
        if(doorSensorCollectionLis.size()>0){
            sensorService.checkDoorData(doorSensorCollectionLis);
        }
        return new Result(true);
    }

    //接受实时传来的传感器数据  先进行分类 一次接受的数据为 某一个老人同一时间段各房间的传感器数据
    //如果有出门的数据了， 则先判断是否出门 没出门再进行行为判断 出门的话就不用进行行为判断了
//    @ResponseBody
//    @RequestMapping(value = "/sensorData",method = RequestMethod.POST)
//    public Result sensorData(@RequestBody SensorCollection[] sensorCollections){
//
//
//
//        //检查该老人的预警机制是否打开 没打开就直接return
//        Boolean timerSwitch=sensorService.checkSwitch(sensorCollections);
//        if(!timerSwitch){
//            return new Result(true,"该老人预警机制没有打开");
//        }
//        logger.info("该老人预警机制已开");
////
////        //测试开始
////        String filePath = "C:\\Users\\admin\\Desktop\\数据库文件及其说明\\SensorCollection.txt";
////        SensorCollections s=new SensorCollections();
////        try {
////            String encoding="GBK";
////            File file=new File(filePath);
////            if(file.isFile() && file.exists()){ //判断文件是否存在
////                InputStreamReader read = new InputStreamReader(
////                        new FileInputStream(file),encoding);//考虑到编码格式
////                BufferedReader bufferedReader = new BufferedReader(read);
////                String lineTxt = null;
////                Date d = new Date();
////                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
////                String firstTime = sdf.format(d);
//////                String firstTime="16:52:01";
////                int i=0;
////                while((lineTxt = bufferedReader.readLine()) != null){
////                    System.out.println(i++);
////                    String time="";
////                    String ti=lineTxt.substring(lineTxt.indexOf("hour")+6,lineTxt.indexOf("minute")-2)+":"+
////                            lineTxt.substring(lineTxt.indexOf("minute")+8,lineTxt.indexOf("second")-2)+":"+
////                            lineTxt.substring(lineTxt.indexOf("second")+8,lineTxt.indexOf("__v")-2);
////                    String[] times=ti.split(":");
////                    for(String t:times){
////                        if(t.length()==1){
////                            if(t.equals("0")){
////                                time+="00:";
////                            }else {
////                                time += "0" + t + ":";
////                            }
////                        }else{
////                            time+=t+":";
////                        }
////                    }
////                    time=time.substring(0,time.length()-1);
////                    String sensorPointID=lineTxt.substring(lineTxt.indexOf("sensorPointID")+15,lineTxt.indexOf("sensorPointID")+16);
////                    String sensorID=lineTxt.substring(lineTxt.indexOf("sensorID")+10,lineTxt.indexOf("sensorID")+11);
////                    String sensorData=lineTxt.substring(lineTxt.indexOf("sensorData")+12,lineTxt.indexOf("year")-2);
////                    System.out.println("时间比较：  当前数据时间："+time+"   第一个数据的时间："+firstTime);
////                    if(sensorService.intervalTime(time,firstTime)>=300&&time.compareTo(firstTime)>=0){
////                        Date d3 = new Date();
////                        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
////                        String t = sdf3.format(d3);
////                        SensorCollection sLast=s.getSensorCollections().get(s.getSensorCollections().size()-1);
////                        sLast.setHour((sLast.getHour().length() == 1) ? "0" + sLast.getHour() : sLast.getHour());
////                        sLast.setMinute((sLast.getMinute().length() == 1) ? "0" + sLast.getMinute() : sLast.getMinute());
////                        sLast.setSecond((sLast.getSecond().length() == 1) ? "0" + sLast.getSecond() : sLast.getSecond());
////                        String tS=sLast.getHour() + ":" + sLast.getMinute() + ":" + sLast.getSecond();//最后一条数据的时间
////                       while(tS.compareTo(t)>0){
////                           System.out.println("最后数据时间："+tS+"  当前时间："+t);
////                           Date d4 = new Date();
////                           SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm:ss");
////                           t = sdf4.format(d4);
////                           Thread.sleep(1000*60);
////                       }
////                        System.out.println("收到数据");
////                        SensorType sensorType=sensorService.conType(s);
////                        //行为预警
////                        List<SensorCollection> moveSensorCollectionLis=sensorType.getMoveSensorCollection();
////                        if(moveSensorCollectionLis.size()>0) {
////                            sensorService.checkMoveData(moveSensorCollectionLis);
////                        }
////                        //温度预警
////                        List<SensorCollection> wenduSensorCollectionLis=sensorType.getWenduSensorCollection();
////                        if(wenduSensorCollectionLis.size()>0){
////                            sensorService.checkWenduData(wenduSensorCollectionLis);
////                        }
////                        //光亮预警
////                        List<SensorCollection> lightSensorCollectionLis=sensorType.getLightSensorCollection();
////                        if(lightSensorCollectionLis.size()>0){
////                            sensorService.checkLightData(lightSensorCollectionLis);
////                        }
////                        //门
////                        List<SensorCollection> doorSensorCollectionLis=sensorType.getDoorSensorCollection();
////                        if(doorSensorCollectionLis.size()>0){
////                            sensorService.checkDoorData(doorSensorCollectionLis);
////                        }
//////                        firstTime=time;
////                        s.getSensorCollections().clear();
////                        Thread.sleep(1000 * 60 * 5);
////                        System.out.println("5分钟结束");
////
////                        Date d1 = new Date();
////                        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
////                        firstTime = sdf1.format(d1);
////                        while(sensorService.intervalTime(time,firstTime)>=300){
////                            System.out.println("下一条数据时间："+time+"  当前时间："+firstTime);
////                            if(time.compareTo(firstTime)<0){
////                                break;
////                            }
////                            Thread.sleep(1000 * 60 * 5);
////                            System.out.println("5分钟结束");
////                            Date d2 = new Date();
////                            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
////                            firstTime = sdf2.format(d2);
////                        }
////
////                        SensorCollection sensorCollection=new SensorCollection();
////                        sensorCollection.setSensorPointID(sensorPointID);
////                        sensorCollection.setSensorID(Integer.parseInt(sensorID));
////                        sensorCollection.setSensorData(Integer.parseInt(sensorData));
////                        sensorCollection.setHour(times[0]);
////                        sensorCollection.setMinute(times[1]);
////                        sensorCollection.setSecond(times[2]);
////                        s.getSensorCollections().add(sensorCollection);
////                    }else{
////                        SensorCollection sensorCollection=new SensorCollection();
////                        sensorCollection.setSensorPointID(sensorPointID);
////                        sensorCollection.setSensorID(Integer.parseInt(sensorID));
////                        sensorCollection.setSensorData(Integer.parseInt(sensorData));
////                        sensorCollection.setHour(times[0]);
////                        sensorCollection.setMinute(times[1]);
////                        sensorCollection.setSecond(times[2]);
////                        s.getSensorCollections().add(sensorCollection);
////                    }
////                }
////                read.close();
////            }else{
////                System.out.println("找不到指定的文件");
////            }
////        } catch (Exception e) {
////            System.out.println("读取文件内容出错");
////            e.printStackTrace();
////        }
//
//        //测试结束
//
//
//        SensorType sensorType=sensorService.conType(sensorCollections);
//        //行为预警
//        List<SensorCollection> moveSensorCollectionLis=sensorType.getMoveSensorCollection();
//        if(moveSensorCollectionLis.size()>0) {
//            sensorService.checkMoveData(moveSensorCollectionLis);
//        }
//        //温度预警
//        List<SensorCollection> wenduSensorCollectionLis=sensorType.getWenduSensorCollection();
//        if(wenduSensorCollectionLis.size()>0){
//            sensorService.checkWenduData(wenduSensorCollectionLis);
//        }
//        //光亮预警
//        List<SensorCollection> lightSensorCollectionLis=sensorType.getLightSensorCollection();
//        if(lightSensorCollectionLis.size()>0){
//            sensorService.checkLightData(lightSensorCollectionLis);
//        }
//        //门
//        List<SensorCollection> doorSensorCollectionLis=sensorType.getDoorSensorCollection();
//        if(doorSensorCollectionLis.size()>0){
//            sensorService.checkDoorData(doorSensorCollectionLis);
//        }
//        return new Result(true);
//    }

    //紧急报警功能   由于硬件的原因 参数oid 指的是网关ID  之前起名是 gatewayID  暂时改回oid 等硬件那边改好
    @ResponseBody
    @RequestMapping(value = "/sensorUrgency",method = RequestMethod.GET)
    public Result sensorUrgency(Equipment equip,Integer oid){
        SystemController.logger.info("紧急报警： 网关id："+oid+"  设备id："+equip.getEid());
        try {
            sensorService.urgency(equip,oid);
            return new Result(true);
        }catch (NullFromDBException e1){
            SystemController.logger.info("读取Mysql数据库，null值异常");
            SystemController.logger.info(e1.getMessage());
            return new Result(false);
        }catch (Exception e){
            SystemController.logger.info("紧急报警算法异常");
            SystemController.logger.info(e.getMessage());
            return new Result(false);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/sensorFace", method = RequestMethod.GET)
    public Result sensorFace(String name,String equipId,String position){
        OldMan oldMan = new OldMan();
        oldMan.setOldName(name);
        oldMan.setGatewayID(position);
        Warn warn = new Warn();
        warn.setPositon(position);
        warn.setOldMan(oldMan);
        warn.setFlag(equipId);
        DwrData dwrData = new DwrData();
        dwrData.setType("Face");
        dwrData.setOldMan(oldMan);
        dwrData.setWarn(warn);
        Remote.noticeNewOrder(dwrData);
        return new Result(true);
    }

//    @ResponseBody
//    @RequestMapping(value = "/sensorData",method = RequestMethod.GET)
//    public Result sensorDataGET(@RequestBody SensorCollection[] sensorCollections){
//
//        //检查该老人的预警机制是否打开 没打开就直接return
////        Boolean timerSwitch=sensorService.checkSwitch(sensorCollections);
////        if(!timerSwitch){
////            return new Result(true,"该老人预警机制没有打开");
////        }
//
////        logger.info("该老人预警机制已开");
////
////        //测试开始
////        String filePath = "C:\\Users\\admin\\Desktop\\数据库文件及其说明\\SensorCollection.txt";
////        SensorCollections s=new SensorCollections();
////        try {
////            String encoding="GBK";
////            File file=new File(filePath);
////            if(file.isFile() && file.exists()){ //判断文件是否存在
////                InputStreamReader read = new InputStreamReader(
////                        new FileInputStream(file),encoding);//考虑到编码格式
////                BufferedReader bufferedReader = new BufferedReader(read);
////                String lineTxt = null;
////                Date d = new Date();
////                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
////                String firstTime = sdf.format(d);
//////                String firstTime="16:52:01";
////                int i=0;
////                while((lineTxt = bufferedReader.readLine()) != null){
////                    System.out.println(i++);
////                    String time="";
////                    String ti=lineTxt.substring(lineTxt.indexOf("hour")+6,lineTxt.indexOf("minute")-2)+":"+
////                            lineTxt.substring(lineTxt.indexOf("minute")+8,lineTxt.indexOf("second")-2)+":"+
////                            lineTxt.substring(lineTxt.indexOf("second")+8,lineTxt.indexOf("__v")-2);
////                    String[] times=ti.split(":");
////                    for(String t:times){
////                        if(t.length()==1){
////                            if(t.equals("0")){
////                                time+="00:";
////                            }else {
////                                time += "0" + t + ":";
////                            }
////                        }else{
////                            time+=t+":";
////                        }
////                    }
////                    time=time.substring(0,time.length()-1);
////                    String sensorPointID=lineTxt.substring(lineTxt.indexOf("sensorPointID")+15,lineTxt.indexOf("sensorPointID")+16);
////                    String sensorID=lineTxt.substring(lineTxt.indexOf("sensorID")+10,lineTxt.indexOf("sensorID")+11);
////                    String sensorData=lineTxt.substring(lineTxt.indexOf("sensorData")+12,lineTxt.indexOf("year")-2);
////                    System.out.println("时间比较：  当前数据时间："+time+"   第一个数据的时间："+firstTime);
////                    if(sensorService.intervalTime(time,firstTime)>=300&&time.compareTo(firstTime)>=0){
////                        Date d3 = new Date();
////                        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
////                        String t = sdf3.format(d3);
////                        SensorCollection sLast=s.getSensorCollections().get(s.getSensorCollections().size()-1);
////                        sLast.setHour((sLast.getHour().length() == 1) ? "0" + sLast.getHour() : sLast.getHour());
////                        sLast.setMinute((sLast.getMinute().length() == 1) ? "0" + sLast.getMinute() : sLast.getMinute());
////                        sLast.setSecond((sLast.getSecond().length() == 1) ? "0" + sLast.getSecond() : sLast.getSecond());
////                        String tS=sLast.getHour() + ":" + sLast.getMinute() + ":" + sLast.getSecond();//最后一条数据的时间
////                       while(tS.compareTo(t)>0){
////                           System.out.println("最后数据时间："+tS+"  当前时间："+t);
////                           Date d4 = new Date();
////                           SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm:ss");
////                           t = sdf4.format(d4);
////                           Thread.sleep(1000*60);
////                       }
////                        System.out.println("收到数据");
////                        SensorType sensorType=sensorService.conType(s);
////                        //行为预警
////                        List<SensorCollection> moveSensorCollectionLis=sensorType.getMoveSensorCollection();
////                        if(moveSensorCollectionLis.size()>0) {
////                            sensorService.checkMoveData(moveSensorCollectionLis);
////                        }
////                        //温度预警
////                        List<SensorCollection> wenduSensorCollectionLis=sensorType.getWenduSensorCollection();
////                        if(wenduSensorCollectionLis.size()>0){
////                            sensorService.checkWenduData(wenduSensorCollectionLis);
////                        }
////                        //光亮预警
////                        List<SensorCollection> lightSensorCollectionLis=sensorType.getLightSensorCollection();
////                        if(lightSensorCollectionLis.size()>0){
////                            sensorService.checkLightData(lightSensorCollectionLis);
////                        }
////                        //门
////                        List<SensorCollection> doorSensorCollectionLis=sensorType.getDoorSensorCollection();
////                        if(doorSensorCollectionLis.size()>0){
////                            sensorService.checkDoorData(doorSensorCollectionLis);
////                        }
//////                        firstTime=time;
////                        s.getSensorCollections().clear();
////                        Thread.sleep(1000 * 60 * 5);
////                        System.out.println("5分钟结束");
////
////                        Date d1 = new Date();
////                        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
////                        firstTime = sdf1.format(d1);
////                        while(sensorService.intervalTime(time,firstTime)>=300){
////                            System.out.println("下一条数据时间："+time+"  当前时间："+firstTime);
////                            if(time.compareTo(firstTime)<0){
////                                break;
////                            }
////                            Thread.sleep(1000 * 60 * 5);
////                            System.out.println("5分钟结束");
////                            Date d2 = new Date();
////                            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
////                            firstTime = sdf2.format(d2);
////                        }
////
////                        SensorCollection sensorCollection=new SensorCollection();
////                        sensorCollection.setSensorPointID(sensorPointID);
////                        sensorCollection.setSensorID(Integer.parseInt(sensorID));
////                        sensorCollection.setSensorData(Integer.parseInt(sensorData));
////                        sensorCollection.setHour(times[0]);
////                        sensorCollection.setMinute(times[1]);
////                        sensorCollection.setSecond(times[2]);
////                        s.getSensorCollections().add(sensorCollection);
////                    }else{
////                        SensorCollection sensorCollection=new SensorCollection();
////                        sensorCollection.setSensorPointID(sensorPointID);
////                        sensorCollection.setSensorID(Integer.parseInt(sensorID));
////                        sensorCollection.setSensorData(Integer.parseInt(sensorData));
////                        sensorCollection.setHour(times[0]);
////                        sensorCollection.setMinute(times[1]);
////                        sensorCollection.setSecond(times[2]);
////                        s.getSensorCollections().add(sensorCollection);
////                    }
////                }
////                read.close();
////            }else{
////                System.out.println("找不到指定的文件");
////            }
////        } catch (Exception e) {
////            System.out.println("读取文件内容出错");
////            e.printStackTrace();
////        }
//
//        //测试结束
//
//
//        SensorType sensorType=sensorService.conType(sensorCollections);
//        //行为预警
//        List<SensorCollection> moveSensorCollectionLis=sensorType.getMoveSensorCollection();
//        if(moveSensorCollectionLis.size()>0) {
//            sensorService.checkMoveData(moveSensorCollectionLis);
//        }
//        //温度预警
//        List<SensorCollection> wenduSensorCollectionLis=sensorType.getWenduSensorCollection();
//        if(wenduSensorCollectionLis.size()>0){
//            sensorService.checkWenduData(wenduSensorCollectionLis);
//        }
//        //光亮预警
//        List<SensorCollection> lightSensorCollectionLis=sensorType.getLightSensorCollection();
//        if(lightSensorCollectionLis.size()>0){
//            sensorService.checkLightData(lightSensorCollectionLis);
//        }
//        //门
//        List<SensorCollection> doorSensorCollectionLis=sensorType.getDoorSensorCollection();
//        if(doorSensorCollectionLis.size()>0){
//            sensorService.checkDoorData(doorSensorCollectionLis);
//        }
//        return new Result(true);
//    }

}
