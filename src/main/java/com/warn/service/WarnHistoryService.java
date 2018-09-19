package com.warn.service;

import com.warn.dto.DwrData;
import com.warn.dto.PageHelper;
import com.warn.entity.WarnData;

import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */
public interface WarnHistoryService {
    void addWarnHistory(DwrData dwrData);
    long getNoReadSum();

    void messageRead(Integer wdid);

    List<WarnData> datagridWarnData(PageHelper page, WarnData warnData);

    Long getDatagridTotal(WarnData warnData);

    String getMessageByWdid(Integer wdid);

    void urgencyRead(Integer wdid);
}
