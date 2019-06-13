package com.warn.service.impl;

import com.warn.dao.RoomDao;
import com.warn.dao.ThresholdDao;
import com.warn.entity.*;
import com.warn.service.CommonService;
import com.warn.service.ThresholdService;
import com.warn.util.Tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/4/12.
 */
@Service
public class ThresholdServiceImpl implements ThresholdService{
    @Autowired
    CommonService commonService;

    @Autowired
    RoomDao roomDao;
    @Autowired
    ThresholdDao thresholdDao;

    public List<Threshold> getThresholdByOid(Integer oid) {
        List<Room> rooms=roomDao.getAllRoomByOldManId(oid);
        List<Threshold> thresholds=thresholdDao.getThresholdByRooms(rooms);
        return thresholds;
    }

    public List<Threshold_area> getThresholdByRid(Integer rid){
        Room room = roomDao.getRoomById(rid);
        List<Threshold_area> threshold_areas = thresholdDao.getThresholdByRid(rid);
        for(Threshold_area threshold_area:threshold_areas){
            threshold_area.setAreaInfo(commonService.getPositionInfo(threshold_area.getArea(),room));
        }
        return threshold_areas;
    }

    @Override
    @Transactional
    public List<Threshold_statistic> getThresholdSByRid(Integer rid){
        Room room = roomDao.getRoomById(rid);
        List<Threshold_statistic> threshold_statistics = thresholdDao.getThresholdSByRid(rid);
        if(threshold_statistics.size() == 0){
            for(int i = 0; i<=10 ; i++){
                Threshold_statistic threshold_statistic = new Threshold_statistic();
                threshold_statistic.setArea(i);
                threshold_statistic.setRoomId(rid);
                threshold_statistics.add(threshold_statistic);
            }
            thresholdDao.addThresholdStatistic(threshold_statistics);
        }
        else {
            for (Threshold_statistic threshold_statistic : threshold_statistics)
                threshold_statistic.setAreaInfo(Tool.getPositionInfo(threshold_statistic.getArea(), room));
        }
        return threshold_statistics;
    }

    public void updateThreshold(Threshold threshold) {
        thresholdDao.updateThreshold(threshold);
    }

    public void updateThresholdArea(Threshold_area threshold_area){
        thresholdDao.updateThresholdArea(threshold_area);
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
