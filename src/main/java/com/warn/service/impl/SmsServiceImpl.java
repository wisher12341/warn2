package com.warn.service.impl;

import com.warn.controller.SystemController;
import com.warn.dao.SmsDao;
import com.warn.dto.Sms_template;
import com.warn.dto.SysSetDTO;
import com.warn.entity.SmsOrder;
import com.warn.entity.SmsSendEntity;
import com.warn.exception.NullFromDBException;
import com.warn.service.SmsService;
import com.warn.sms.SMSConstants;
import com.warn.sms.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与系统相关的参数配置
 * Created by admin on 2017/4/4.
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    SMSUtil smsUtil;
    @Autowired
    SmsDao smsDao;

    /**
     * 管理员直接操作的 系统总开关
     * @param openSys
     */
    @Override
    public void smsSysSwitch(Integer openSys) {
        if(openSys==1){
            //打开
            SystemController.logger.info("总开关开启");
            try {
                SMSConstants.openSys=openSys;
                smsUtil.sendPre();
            }catch (NullFromDBException e1){
                SystemController.logger.info("短信定时器出现null值："+e1.getMessage());
                //关闭定时器
                SMSConstants.openSys=0;
                if (SMSUtil.smsTimer.get("timer") != null) {
                    SMSUtil.smsTimer.get("timer").shutdown();
                    SMSUtil.smsTimer.remove("timer");
                }
            }catch (Exception e){
                SystemController.logger.info("短信定时器任务算法出错："+e.getMessage());
                //关闭定时器
                SMSConstants.openSys=0;
                if (SMSUtil.smsTimer.get("timer") != null) {
                    SMSUtil.smsTimer.get("timer").shutdown();
                    SMSUtil.smsTimer.remove("timer");
                }
            }
        }else{
            //关闭
            SMSConstants.openSys=openSys;
            if (SMSUtil.smsTimer.get("timer") != null) {
                SMSUtil.smsTimer.get("timer").shutdown();
                SMSUtil.smsTimer.remove("timer");
                SystemController.logger.info("短信定时任务关闭");
            }
            SystemController.logger.info("总开关关闭");
        }
    }

    /**
     * 产生新的预警消息， 启动短信任务
     */
    @Override
    public void smsSwitch() {
        try {
            smsUtil.sendPre();
        }catch (NullFromDBException e1){
            SystemController.logger.info("短信定时器出现null值："+e1.getMessage());
            //关闭定时器
            SMSConstants.openSys=0;
            if (SMSUtil.smsTimer.get("timer") != null) {
                SMSUtil.smsTimer.get("timer").shutdown();
                SMSUtil.smsTimer.remove("timer");
            }
        }catch (Exception e){
            SystemController.logger.info("短信定时器任务算法出错："+e.getMessage());
            //关闭定时器
            SMSConstants.openSys=0;
            if (SMSUtil.smsTimer.get("timer") != null) {
                SMSUtil.smsTimer.get("timer").shutdown();
                SMSUtil.smsTimer.remove("timer");
            }
        }
    }


    @Override
    public SysSetDTO getSMSSwitch() {
        SysSetDTO sysSetDTO=new SysSetDTO();
        sysSetDTO.setOpenSys(SMSConstants.openSys);
//        sysSetDTO.setSmsTime(SMSConstants.smsTime);
//        sysSetDTO.setAccessBatabaseTime(StaticVal.accessDatabaseTime);
        return sysSetDTO;
    }

    @Override
    public List<SmsSendEntity> datagridSmsSendEntity(SmsSendEntity smsSendEntity) {
        return smsDao.datagridSmsSendEntity(smsSendEntity);
    }

    @Override
    public List<SmsOrder> datagridSmsOrder() {
        return smsDao.datagridSmsOrder();
    }

    //    @Override
//    public void setSendSMSTime(int time) {
//        SMSConstants.smsTime=time;
//    }
//
//    @Override
//    public void setAccessBatabaseTime(int time) {
//        StaticVal.accessDatabaseTime=time;
//    }


    @Override
    public void deletePhone(SmsSendEntity smsSendEntity) {
        smsDao.deletePhone(smsSendEntity);
    }

    @Override
    public void addSmsOrder(SmsOrder smsOrder) {
        smsDao.addSmsOrder(smsOrder);
    }

    @Override
    public void deleteSmsOrder(SmsOrder smsOrder) {
        smsDao.deleteSmsOrder(smsOrder);
    }

    @Override
    public void editPhone(SmsSendEntity smsSendEntity) {
        smsDao.editPhone(smsSendEntity);
    }

    @Override
    public void editSmsOrder(SmsOrder smsOrder) {
        smsDao.editSmsOrder(smsOrder);
    }

    @Override
    public void addPhone(SmsSendEntity smsSendEntity) {
        smsDao.addPhone(smsSendEntity);
    }

    @Override
    public Sms_template getSmsTemplate() {
        Sms_template sms_template=new Sms_template();
//        sms_template.setAPP_KEY(SMSConstants.APP_KEY);
        sms_template.setEXTEND(SMSConstants.EXTEND);
//        sms_template.setSECRET(SMSConstants.SECRET);
        sms_template.setSMS_SIGN(SMSConstants.SMS_SIGN);
        sms_template.setSMS_TEMPLATE_CODE(SMSConstants.SMS_TEMPLATE_CODE);
        sms_template.setSMS_TYPE(SMSConstants.SMS_TYPE);
        return sms_template;
    }

    @Override
    public void editTemplate(String name,String data) {
        switch (name){
            case "AppKey":
//                SMSConstants.APP_KEY=data;
                break;
            case "短信签名":
                SMSConstants.SMS_SIGN=data;
                break;
            case "公共回传参数":
                SMSConstants.EXTEND=data;
                break;
            case "短信类型":
                SMSConstants.SMS_TYPE=data;
                break;
            case "短信模板ID":
                SMSConstants.SMS_TEMPLATE_CODE=data;
                break;
            case "App Secret":
//                SMSConstants.SECRET=data;
                break;

        }
    }
}
