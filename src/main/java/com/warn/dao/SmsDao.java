package com.warn.dao;

import com.warn.entity.SmsOrder;
import com.warn.entity.SmsSendEntity;

import java.util.List;

/**
 * Created by netlab606 on 2017/6/10.
 */
public interface SmsDao {
    List<SmsSendEntity> datagridSmsSendEntity(SmsSendEntity smsSendEntity);

    List<SmsOrder> datagridSmsOrder();

    void addPhone(SmsSendEntity smsSendEntity);

    void editSmsOrder(SmsOrder smsOrder);

    void editPhone(SmsSendEntity smsSendEntity);

    void deleteSmsOrder(SmsOrder smsOrder);

    void addSmsOrder(SmsOrder smsOrder);

    void deletePhone(SmsSendEntity smsSendEntity);
}
