package com.warn.service.impl;

import com.warn.dao.RoomDao;
import com.warn.dao.ThresholdDao;
import com.warn.entity.*;
import com.warn.service.ThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/4/12.
 */
@Service
public class ThresholdServiceImpl implements ThresholdService{

    @Autowired
    RoomDao roomDao;
    @Autowired
    ThresholdDao thresholdDao;

    public List<Threshold> getThresholdByOid(Integer oid) {
        List<Room> rooms=roomDao.getAllRoomByOldManId(oid);
        List<Threshold> thresholds=thresholdDao.getThresholdByRooms(rooms);
        return thresholds;
    }

    public void updateThreshold(Threshold threshold) {
        thresholdDao.updateThreshold(threshold);
    }

    @Override
    public List<Threshold_wendu> getThreshold_wByOid(Integer oid) {
        List<Room> rooms=roomDao.getAllRoomByOldManId(oid);
        List<Threshold_wendu> thresholds=thresholdDao.getThreshold_wByRooms(rooms);
        return thresholds;
    }

    @Override
    public void updateThreshold_w(Threshold_wendu threshold_wendu) {
        thresholdDao.updateThreshold_w(threshold_wendu);
    }

    @Override
    public List<Threshold_light> getThreshold_lByOid(Integer oid) {
        List<Room> rooms=roomDao.getAllRoomByOldManId(oid);
        List<Threshold_light> thresholds=thresholdDao.getThreshold_lByRooms(rooms);
        return thresholds;
    }

    @Override
    public void updateThreshold_l(Threshold_light threshold_light) {
        thresholdDao.updateThreshold_l(threshold_light);
    }

    @Override
    public List<Threshold_out> getThreshold_dByOid(Integer oid) {
        List<Threshold_out> thresholds=thresholdDao.getThreshold_dByOid(oid);
        return thresholds;
    }

    @Override
    public void updateThreshold_d(Threshold_out threshold_out) {
        thresholdDao.updateThreshold_d(threshold_out);
    }

}
