package com.warn.service;

import com.warn.dto.PageHelper;
import com.warn.entity.DownData;
import com.warn.entity.Outdoor;

import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */
public interface DownHistoryService {
    long getNoReadSum();

    void messageRead(Integer downid);

    List<DownData> datagridDownData(PageHelper page, DownData downData);

    Long getDatagridTotal(DownData downData);

//    String getMessageByOdid(Integer odid);

    void addDownData(DownData downData);
}
