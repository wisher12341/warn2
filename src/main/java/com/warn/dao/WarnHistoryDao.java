package com.warn.dao;

import com.warn.dto.PageHelper;
import com.warn.entity.SmsOrder;
import com.warn.entity.WarnData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预警历史信息
 * Created by admin on 2017/4/27.
 */
public interface WarnHistoryDao {


    void addWarnHistory(WarnData warnData);

    long getNoReadSum();

    void messageRead(@Param("id")Integer wdid);

    List<WarnData> datagridWarnData(@Param("page")PageHelper page, @Param("warnData")WarnData warnData);

    Long getDatagridTotal(WarnData warnData);

    String getMessageByWdid(@Param("id")Integer wdid);

    List<WarnData> getNoReadNoSmsData();

    void updateSMSByWid(@Param("id")Integer wdid);

    List<WarnData> getNoReadNoFinishSmsData(@Param("smsOrder")Integer smsOrder);

    void updateSMSByWids(List<Integer> wids);
}
