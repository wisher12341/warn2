package com.warn.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.warn.controller.SystemController;
import com.warn.dao.SmsDao;
import com.warn.dao.SystemDao;
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

    //请求地址
    private static String URL = SMSConstants.URL;
    //TOP分配给应用的AppKey
    private static String APP_KEY = SMSConstants.APP_KEY;
    //短信签名AppKey对应的secret值
    private static String SECRET = SMSConstants.SECRET;
    //短信类型，传入值请填写normal
    private static String SMS_TYPE = SMSConstants.SMS_TYPE;
    //阿里大于账户配置的短信签名
    private static String SMS_SIGN = SMSConstants.SMS_SIGN;
    //阿里大于账户配置的短信模板ID
    private static String SMS_TEMPLATE_CODE = SMSConstants.SMS_TEMPLATE_CODE;

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
    public String sendMsg(String phone,SMSParam smsParam) throws Exception {
        //System.out.println("验证码code:"+code);
        //获得第三方阿里云短信通知接口
        TaobaoClient client = new DefaultTaobaoClient(URL, APP_KEY, SECRET);
        //获得短信通知请求头
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        //短信通知类型
        req.setSmsType(SMS_TYPE);
        //短信通知签名
        req.setSmsFreeSignName(SMS_SIGN);
        //短信接收号码:传入号码为11位手机号码不能加0或+86,最多传入200个号码,多个号码以逗号分隔
        req.setRecNum(phone);
        //短信通知参数json格式

        String smsParamJson = JSONObject.toJSONString(smsParam);
        SystemController.logger.info("短信通知参数smsParam:"+smsParam);
        //短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，多个变量之间以逗号隔开
        req.setSmsParamString(smsParamJson);
        //短信模板ID
        req.setSmsTemplateCode(SMS_TEMPLATE_CODE);
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        JSONObject json = JSON.parseObject(rsp.getBody());
        String jsonStr = json.getString("alibaba_aliqin_fc_sms_num_send_response");
        if (jsonStr!=null&&!jsonStr.isEmpty() ) {
            json = JSON.parseObject(jsonStr);
            String result = json.getString("result");
            if (result!=null && !result.isEmpty()) {
                json = JSON.parseObject(result);
                SystemController.logger.info("json:"+json);
                String errorCode = json.getString("err_code");
                if ("0".equals(errorCode)) {
                    //发送成功
                    return "success";
                } else {
                    //发送失败
                    return "false";
                }
            }
        }
        //发送失败
        return "false";
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
                                                    String result = sendMsg(smsSendEntity.getPhone(), smsParam);
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
                                        if(maxOrder==sms.get(warnData)){
                                            warnHistoryDao.updateSMSByWid(warnData.getWdid());
                                        }
                                        break;
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
