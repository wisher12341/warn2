package com.warn.service;

import com.warn.dto.DwrData;
import com.warn.dto.PageHelper;
import com.warn.entity.Outdoor;
import com.warn.entity.WarnData;

import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */
public interface OutHistoryService {
    long getNoReadSum();

    void messageRead(Integer odid);

    List<Outdoor> datagridOutData(PageHelper page, Outdoor outdoor);

    Long getDatagridTotal(Outdoor outdoor);

    String getMessageByOdid(Integer odid);

    void addOutDoor(Outdoor outdoor);
}
