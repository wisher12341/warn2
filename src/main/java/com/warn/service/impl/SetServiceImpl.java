package com.warn.service.impl;

import com.warn.controller.SystemController;
import com.warn.dao.DataDao;
import com.warn.dao.EquipDao;
import com.warn.dao.RoomDao;
import com.warn.dao.UserDao;
import com.warn.dto.Count;
import com.warn.dto.SysSetDTO;
import com.warn.dto.TimeDto;
import com.warn.dto.insdep.Tree_insdep;
import com.warn.entity.Menu;
import com.warn.entity.OldMan;
import com.warn.entity.Role;
import com.warn.entity.User;
import com.warn.exception.GetMDBException;
import com.warn.exception.NullFromDBException;
import com.warn.mongodb.dao.SensorMogoDao;
import com.warn.mongodb.model.SensorCollection;
import com.warn.service.SetService;
import com.warn.service.TimerService;
import com.warn.service.UserService;
import com.warn.sms.SMSConstants;
import com.warn.sms.SMSUtil;
import com.warn.util.StaticVal;
import com.warn.util.UserCookieUtil;
import com.warn.util.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 与系统相关的参数配置
 * Created by admin on 2017/4/4.
 */
@Service
public class SetServiceImpl implements SetService {

    @Autowired
    SensorMogoDao sensorMogoDao;
    @Autowired
    TimerService timerService;

//    @Autowired
//    SMSUtil smsUtil;
//
//    /**
//     * 管理员直接操作的 系统总开关
//     * @param openSys
//     */
//    @Override
//    public void smsSysSwitch(Integer openSys) {
//        if(openSys==1){
//            //打开
//            SystemController.logger.info("总开关开启");
//            try {
//                SMSConstants.openSys=openSys;
//                smsUtil.sendPre();
//            }catch (NullFromDBException e1){
//                SystemController.logger.info("短信定时器出现null值："+e1.getMessage());
//                //关闭定时器
//                SMSConstants.openSys=0;
//                if (SMSUtil.smsTimer.get("timer") != null) {
//                    SMSUtil.smsTimer.get("timer").shutdown();
//                    SMSUtil.smsTimer.remove("timer");
//                }
//            }catch (Exception e){
//                SystemController.logger.info("短信定时器任务算法出错："+e.getMessage());
//                //关闭定时器
//                SMSConstants.openSys=0;
//                if (SMSUtil.smsTimer.get("timer") != null) {
//                    SMSUtil.smsTimer.get("timer").shutdown();
//                    SMSUtil.smsTimer.remove("timer");
//                }
//            }
//        }else{
//            //关闭
//            SMSConstants.openSys=openSys;
//            if (SMSUtil.smsTimer.get("timer") != null) {
//                SMSUtil.smsTimer.get("timer").shutdown();
//                SMSUtil.smsTimer.remove("timer");
//                SystemController.logger.info("短信定时任务关闭");
//            }
//            SystemController.logger.info("总开关关闭");
//        }
//    }
//
//    /**
//     * 产生新的预警消息， 启动短信任务
//     */
//    @Override
//    public void smsSwitch() {
//        try {
//            smsUtil.sendPre();
//        }catch (NullFromDBException e1){
//            SystemController.logger.info("短信定时器出现null值："+e1.getMessage());
//            //关闭定时器
//            SMSConstants.openSys=0;
//            if (SMSUtil.smsTimer.get("timer") != null) {
//                SMSUtil.smsTimer.get("timer").shutdown();
//                SMSUtil.smsTimer.remove("timer");
//            }
//        }catch (Exception e){
//            SystemController.logger.info("短信定时器任务算法出错："+e.getMessage());
//            //关闭定时器
//            SMSConstants.openSys=0;
//            if (SMSUtil.smsTimer.get("timer") != null) {
//                SMSUtil.smsTimer.get("timer").shutdown();
//                SMSUtil.smsTimer.remove("timer");
//            }
//        }
//    }


    @Override
    public SysSetDTO getSysAllSet() {
        SysSetDTO sysSetDTO=new SysSetDTO();
//        sysSetDTO.setOpenSys(SMSConstants.openSys);
//        sysSetDTO.setSmsTime(SMSConstants.smsTime);
        sysSetDTO.setAccessBatabaseTime(StaticVal.accessDatabaseTime);
        sysSetDTO.setTimerOpen(StaticVal.timerOpen);
        sysSetDTO.setGatewayDown(StaticVal.gatewayDown);
        sysSetDTO.setEquipDown(StaticVal.equipDown);
        return sysSetDTO;
    }

//    @Override
//    public void setSendSMSTime(int time) {
//        SMSConstants.smsTime=time;
//    }

    @Override
    public void setAccessBatabaseTime(int time) {
        StaticVal.accessDatabaseTime=time;
    }

    @Override
    public void timerOpen(Integer timerOpen) {
        StaticVal.timerOpen=timerOpen;
        if(timerOpen==1){
            SystemController.logger.info("自动总开关开");
            //开启的话  先把 检测时间段内有数据的 老人的预警开关 开启
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            String currentTime = sdf.format(d);

            Date s = new Date(d.getTime() - (StaticVal.accessDatabaseTime + 1) * 60 * 1000); //开始的时间 当前时间推迟一分钟秒   因为传感器发送到数据库 有 1-59秒不等的延迟
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            String startTime = dateFormat.format(s);

            SystemController.logger.info("预警开关——当前检测时间段：" + startTime + "-------" + currentTime);
            List<SensorCollection> sensorCollectionList = null;
            try {
                List<Integer> closeWarns=new ArrayList<>();
                for (OldMan oldMan : StaticVal.oldManTimer.keySet()) {
                    if(StaticVal.oldManTimer.get(oldMan)!=true) {
                        closeWarns.add(oldMan.getOid());
                        SystemController.logger.info("关闭的老人：" + oldMan.getOid());
                    }
                }
                //从mongodb数据库获得该时间段 该老人的数据
                sensorCollectionList = sensorMogoDao.findByTime(startTime, currentTime,null,closeWarns);

                for(SensorCollection sensorCollection:sensorCollectionList){
                    SystemController.logger.info(sensorCollection.toString());
                }

                //启动 有数据的 老人的预警开关
                heartDeal(sensorCollectionList,closeWarns);
            } catch (GetMDBException e1) {
                SystemController.logger.info(e1.getMessage());
            } catch (Exception e) {
                SystemController.logger.info("获取传感器数据时出错");
                SystemController.logger.info(e.getMessage());
            }
        }
    }

    private void heartDeal(List<SensorCollection> sensorCollectionListAll, List<Integer> closeWarns) {
        for(Integer gatewayID:closeWarns) {
            for (SensorCollection sensorCollection : sensorCollectionListAll) {
                if(sensorCollection.getGatewayID().intValue()==gatewayID.intValue()){
                    OldMan oldMan=new OldMan(gatewayID);
                    if(StaticVal.oldManTimer.get(oldMan)==null||StaticVal.oldManTimer.get(oldMan)==false){
                        TimeDto timeDto=new TimeDto(1,oldMan);
                        timerService.updateTimer(timeDto);
                    }
                }
            }
        }
    }

    @Override
    public void setDown(int time,String name) {
        if(name.equals("网关故障（分钟）")){
            StaticVal.gatewayDown=time;
        }else {
            StaticVal.equipDown = time;
        }
    }


}
