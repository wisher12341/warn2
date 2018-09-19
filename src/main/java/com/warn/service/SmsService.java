package com.warn.service;

import com.warn.dto.Sms_template;
import com.warn.dto.SysSetDTO;
import com.warn.entity.SmsOrder;
import com.warn.entity.SmsSendEntity;

import java.util.List;

/**
 * Created by admin on 2017/4/4.
 */
public interface SmsService {

    void smsSysSwitch(Integer openSys);

    void smsSwitch();

    SysSetDTO getSMSSwitch();


    List<SmsSendEntity> datagridSmsSendEntity(SmsSendEntity smsSendEntity);

    List<SmsOrder> datagridSmsOrder();

    void deletePhone(SmsSendEntity smsSendEntity);

    void addSmsOrder(SmsOrder smsOrder);

    void deleteSmsOrder(SmsOrder smsOrder);

    void editPhone(SmsSendEntity smsSendEntity);

    void editSmsOrder(SmsOrder smsOrder);

    void addPhone(SmsSendEntity smsSendEntity);

    Sms_template getSmsTemplate();

    void editTemplate(String name, String data);
}
