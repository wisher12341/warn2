package com.warn.sms;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.warn.controller.SystemController;
import com.warn.dao.SmsDao;
import com.warn.dao.WarnHistoryDao;
import com.warn.entity.SmsOrder;
import com.warn.entity.SmsSendEntity;
import com.warn.entity.WarnData;
import com.warn.exception.NullFromDBException;
import com.warn.exception.WarnException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.warn.sms.SMSConstants.SMS_SIGN;
import static com.warn.sms.SMSConstants.SMS_TEMPLATE_CODE;
import static com.warn.sms.SmsDemo.*;

/**
 * 短信通知工具类
 * Created by netlab606 on 2017/5/28.
 */
@Service
public class SMSUtil {


    @Autowired
    SmsDao smsDao;
    @Autowired
    WarnHistoryDao warnHistoryDao;



    //发送短信的定时任务
    public static Map<String,ScheduledExecutorService> smsTimer=new HashMap<>();//短信的定时任务

    //用于存储 当前该未读记录已发送的级别顺序 已把所有顺序级别的短信都发送完了  以后还是未读的话 不用再发短信了(比如 最高级别是3，当发送了3的短信后，map的该值设置为3，以后该式（3<所有级别）一直不成立，就不发短信了
    // 如果下一次的未读记录中，没有该记录了，则把该键值清楚
    public static Map<WarnData,Integer> sms=new HashMap<>();

    /**
     * @param phone 必填参数，手机号码
     * @param smsParam 模板参数
     * @return
     * @throws Exception
     */
    public Boolean sendMsg(String phone,SMSParam smsParam) {
        try {
            SendSmsResponse sendSmsResponse = SmsDemo.sendSms(phone, smsParam);
            SystemController.smslogger.info("结果："+sendSmsResponse.getCode()+","+sendSmsResponse.getMessage());
            return sendSmsResponse.getCode().equals("OK");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 算法逻辑：有一个总开关 控制 短信功能的开启，  在定时任务中，如果没有检测到未读且未发短信的记录，则停止定时任务，在产生新的预警时，重新启动定时任务
     * @throws NullFromDBException
     * @throws WarnException
     */
    public void sendPre() throws WarnException {
        try {
            if (SMSConstants.openSys == 1 && smsTimer.get("timer") == null) {
                //启动定时任务
                SystemController.smslogger.info("短信：启动定时任务");

                Runnable runnable = () -> {
                    try {
                        List<SmsSendEntity> phones = smsDao.datagridSmsSendEntity(new SmsSendEntity());
                        List<SmsOrder> smsOrders = smsDao.datagridSmsOrder();
                        if (CollectionUtils.isEmpty(phones) || CollectionUtils.isEmpty(smsOrders)) {
                            return;
                        }
                        phones.sort(Comparator.comparingInt(SmsSendEntity::getOrderSms));

                        List<WarnData> warnDataList = warnHistoryDao.getNoReadNoFinishSmsData(phones.get(phones.size() - 1).getOrderSms());
                        if (CollectionUtils.isEmpty(warnDataList)) {
                            return;
                        }
                        Map<Integer, List<SmsSendEntity>> phoneMap = phones.stream().collect(Collectors.groupingBy(SmsSendEntity::getOrderSms));
                        Map<Integer, Integer> orderMap = smsOrders.stream().collect(Collectors.toMap(SmsOrder::getOrderSms, SmsOrder::getTimeSms));
                        Map<String, List<WarnData>> result = new HashMap<>();

                        warnDataList.forEach(warnData -> {
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime createTime = LocalDateTime.parse(warnData.getTimeW(), df);
                            SystemController.smslogger.info("当前时间："+LocalDateTime.now());
                            long value = Duration.between(createTime, LocalDateTime.now()).toMinutes();
                            if (orderMap.get(warnData.getSms() + 1) <= value) {
                                List<String> phoneList = phoneMap.get(warnData.getSms() + 1).stream().map(SmsSendEntity::getPhone).collect(Collectors.toList());
                                phoneList.forEach(phone -> {
                                    if (result.containsKey(phone)) {
                                        result.get(phone).add(warnData);
                                    } else {
                                        List<WarnData> list = new ArrayList<>();
                                        list.add(warnData);
                                        result.put(phone, list);
                                    }
                                });
                            }
                        });
                        SystemController.smslogger.info("短信：发送数据：" + result.toString());
                        if (MapUtils.isNotEmpty(result)) {
                            result.forEach((k, v) -> v.forEach(warnData -> {
                                SMSParam smsParam = new SMSParam();
                                smsParam.setOldMan(warnData.getOid() + "");
                                smsParam.setOldName(warnData.getOldName());
                                smsParam.setTime(warnData.getTimeW());
                                smsParam.setWarnType(warnData.getTypeW());
                                boolean b = sendMsg(k, smsParam);
                                SystemController.smslogger.info("短信：" + k + ",发送结果：" + b);
                            }));
                            List<Integer> wids = warnDataList.stream().map(WarnData::getWdid).collect(Collectors.toList());
                            warnHistoryDao.updateSMSByWids(wids);
                        }
                    }catch (Exception e){
                        SystemController.smslogger.info("短信：报错");
                        e.printStackTrace();
                    }
                };

                ScheduledExecutorService service = Executors
                        .newSingleThreadScheduledExecutor();
                // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
                service.scheduleAtFixedRate(runnable, 1, 60, TimeUnit.SECONDS);
                smsTimer.put("timer",service);

            }else if(SMSConstants.openSys == 1 && smsTimer.get("timer") != null){
                SystemController.logger.info("短信：产生新的预警消息，定时任务未结束");
            }else{
                SystemController.logger.info("短信：短信功能未开");
            }
        }catch (NullFromDBException e1){
            throw e1;
        }catch (Exception e){
            throw new WarnException("light inner error:"+e.getMessage());
        }

    }

    //计算两个时刻直接的时间间隔 单位秒       hh:mm:ss
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
}
