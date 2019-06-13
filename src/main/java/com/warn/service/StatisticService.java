package com.warn.service;

import com.warn.dto.visual.AreaVisual;
import com.warn.dto.visual.AreaVisualList;

import java.util.List;

public interface StatisticService {
    void getStatisticData(Integer gateWayId);

    List<AreaVisual> getStatisticArea(Integer oid,Integer rid,String time);

    List<AreaVisualList> getStatisticAreaList(Integer oid,Integer rid);

    void checkStatistic(Integer oid,Integer rid);
}
