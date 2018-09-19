package com.warn.dao;

import com.warn.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/4/12.
 */
public interface ThresholdDao {
    List<Threshold> getThresholdByRooms(List<Room> rooms);

    void addThreshold(Integer rid);

    void updateThreshold(Threshold threshold);

    void deleteByRoomId(Integer rid);

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
