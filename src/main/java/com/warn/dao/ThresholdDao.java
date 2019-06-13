package com.warn.dao;

import com.warn.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/4/12.
 */
public interface ThresholdDao {
    List<Threshold> getThresholdByRooms(List<Room> rooms);

    List<Threshold_area> getThresholdByRid(Integer rid);

    List<Threshold_statistic> getThresholdSByRid(Integer rid);

    void addThreshold(Integer rid);

    void addThresholdArea(Threshold_area threshold_area);

    void addThresholdStatistic(List<Threshold_statistic> threshold_statistic);

    void updateThreshold(Threshold threshold);

    void updateThresholdArea(Threshold_area threshold_area);

    void updateThresholdStatistic(Threshold_statistic threshold_statistic);

    void deleteByRoomId(Integer rid);

    Threshold_area getThresholdAreaByRidAndNum(Threshold_area threshold_area);

    Threshold_statistic getThresholdStatisticByRidAndNum(Threshold_statistic threshold_statistic);

    Threshold getThresholdByRoomId(@Param("id")Integer rid);

    List<Threshold_wendu> getWenDuThresholdByOid(@Param("id")Integer oid);

    List<Threshold_light> getLightThresholdByOid(@Param("id")Integer oid);

    List<Threshold_wendu> getThreshold_wByRooms(List<Room> rooms);

    void updateThreshold_w(Threshold_wendu threshold_wendu);

    List<Threshold_light> getThreshold_lByRooms(List<Room> rooms);

    void updateThreshold_l(Threshold_light threshold_light);

    Threshold_out getDoorThresholdByOid(@Param("id")Integer oid);

    void addDoorThresholdByOid(@Param("id")Integer oid);

    List<Threshold_out> getThreshold_dByOid(@Param("id")Integer oid);

    void updateThreshold_d(Threshold_out threshold_out);

    void deleteDoorThresholdByOid(@Param("id")Integer oldManId);

}
