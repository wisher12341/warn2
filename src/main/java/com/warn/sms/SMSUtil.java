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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    public Boolean sendMsg(String phone,SMSParam smsParam) throws Exception {
        SystemController.logger.info("1");
        try {
            SendSmsResponse sendSmsResponse = sendSms(phone, smsParam);
            return sendSmsResponse.getCode().equals("OK");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    public static SendSmsResponse sendSms(String phone,SMSParam smsParam) throws ClientException {
        SystemController.logger.info("2");
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(SMS_SIGN);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(SMS_TEMPLATE_CODE);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(String.valueOf(JSON.toJSON(smsParam)));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }


    /**
     * 算法逻辑：有一个总开关 控制 短信功能的开启，  在定时任务中，如果没有检测到未读且未发短信的记录，则停止定时任务，在产生新的预警时，重新启动定时任务
     * @throws NullFromDBException
     * @throws WarnException
     */
    public void sendPre() throws NullFromDBException,WarnException {
        try {
            if (SMSConstants.openSys == 1 && smsTimer.get("timer") == null) {
                //启动定时任务
                SystemController.logger.info("短信：启动定时任务");
                //所有的手机号
                final List<SmsSendEntity> phones = smsDao.datagridSmsSendEntity(new SmsSendEntity());
                //所有顺序
                final List<SmsOrder> smsOrders=smsDao.datagridSmsOrder();
                //对smsOrder 排序 由大到小
                Collections.sort(smsOrders);

                if (phones.size() == 0) {
                    throw new NullFromDBException("短信：电话列表为空");
                }

                Runnable runnable = new Runnable() {
                    public void run() {
                        List<WarnData> warnDataList = warnHistoryDao.getNoReadNoSmsData();
                        for(WarnData warnData:sms.keySet()){
                            if(!warnDataList.contains(warnData)){
                                sms.remove(warnData);
                            }
                        }

                        if (warnDataList.size() == 0) {
                            SystemController.logger.info("短信：没有找到未读且还没有发送短信的记录，关闭定时任务");
                            if (smsTimer.get("timer") != null) {
                                smsTimer.get("timer").shutdown();
                                smsTimer.remove("timer");
                            }
                        } else {
                            SystemController.logger.info("短信：未读且还没有发送短信的记录数：" + warnDataList.size());
                            for (WarnData warnData : warnDataList) {

                                SystemController.logger.info("短信：未读且还没有发送短信的记录：" + warnData.toString());
                                //未读消息生成的时间
                                String time = warnData.getTimeW();
                                //当前系统时间
                                Date d = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                                String currentTime = sdf.format(d);
                                int value = intervalTime(currentTime, time.substring(11, time.length()));
                                SystemController.logger.info("当前时间间隔："+(value/60));

                                int maxOrder=smsOrders.get(0).getOrderSms();//获得 最大的顺序  (已排序)
                                //已排序  顺序由大到小  先判断顺序大的 是否符合条件
                                for(SmsOrder smsOrder:smsOrders){
                                    SystemController.logger.info(smsOrder.toString());
                                    if(value>smsOrder.getTimeSms()*60&&(sms.get(warnData)==null||sms.get(warnData)<smsOrder.getOrderSms())){
                                        //超过指定时间 没有读
                                        SMSParam smsParam = new SMSParam();
                                        smsParam.setOldMan(warnData.getOid() + "");
                                        smsParam.setOldName(warnData.getOldName());
                                        smsParam.setTime(time.substring(5, time.length()));
                                        smsParam.setWarnType(warnData.getTypeW());
                                        for (SmsSendEntity smsSendEntity : phones) {
                                            if(smsSendEntity.getOrderSms()==smsOrder.getOrderSms()) {
                                                SystemController.logger.info(smsSendEntity.toString());
                                                try {
                                                    Boolean result = sendMsg(smsSendEntity.getPhone(), smsParam);
                                                    SystemController.logger.info("短信：发送结果：" + result);
                                                    sms.put(warnData,smsOrder.getOrderSms());
//                                                    SystemController.logger.info("短信发送成功");
//                                                    warnHistoryDao.updateSMSByWid(warnData.getWdid());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        //待所有的手机的 短信都已发完  将该记录 设置为  短信已发的状态
                                        if(sms.get(warnData)!=null &&maxOrder==sms.get(warnData)){
                                            warnHistoryDao.updateSMSByWid(warnData.getWdid());
                                        }
                                        continue;
                                    }
                                }


                                //之前的 没有手机号 先后顺序的代码
//                                if (value > SMSConstants.smsTime * 60) {
//                                    //超过指定时间 没有读
//                                    SMSParam smsParam = new SMSParam();
//                                    smsParam.setOldMan(warnData.getOid() + "");
//                                    smsParam.setOldName(warnData.getOldName());
//                                    smsParam.setTime(time.substring(5, time.length()));
//                                    smsParam.setWarnType(warnData.getTypeW());
//                                    for (String phone : phones) {
//                                        try {
//                                            String result = sendMsg(phone, smsParam);
//                                            SystemController.logger.info("短信：发送结果：" + result);
//                                            warnHistoryDao.updateSMSByWid(warnData.getWdid());
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
                            }
                        }
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
