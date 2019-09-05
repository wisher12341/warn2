package com.warn.dao;

import com.warn.entity.AreaStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatisticDao {
    List<AreaStatistic> getStatisticByDate(@Param("date") String date, @Param("oid")int oid);

    List<AreaStatistic> getStatisticInfo(@Param("date") String date, @Param("oid")int oid);

    List<AreaStatistic> getStatisitcInfos(@Param("dates") List<String> dates, @Param("oid") int oid,@Param("rid") int rid);

    void addAreaStatistic(AreaStatistic areaStatistic);

    void updateAreaStatistic(AreaStatistic areaStatistic);

    void updateNormal(AreaStatistic areaStatistic);
}
