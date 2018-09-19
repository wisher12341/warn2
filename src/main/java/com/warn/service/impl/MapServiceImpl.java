package com.warn.service.impl;


import com.warn.dao.DataDao;
import com.warn.dao.MapDao;

import com.warn.dto.MarkerSum;
import com.warn.dto.PageHelper;
import com.warn.entity.*;
import com.warn.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by admin on 2017/5/4.
 */
@Service
public class MapServiceImpl implements MapService {

    @Autowired
    MapDao mapDao;
    @Autowired
    DataDao dataDao;

    @Override
    public List<HouseMarker> getHouseMarkers() {
        return mapDao.getHouseMarkers();
    }

    @Override
    public List<LouMarker> getLouMarkers() {
        return mapDao.getLouMarkers();
    }

    @Override
    public OldMan addHouseMarker(HouseMarker houseMarker) {
        mapDao.addHouseMarker(houseMarker);
        OldMan oldMan=dataDao.getOldManByOid(houseMarker.getOid());
        return oldMan;
    }

    @Override
    public List<QuMarker> getQuMarkers() {
        return mapDao.getQuMarkers();
    }

    @Override
    public List<JieDaoMarker> getJieDaoMarkers() {
        return mapDao.getJieDaoMarkers();
    }

    @Override
    public List<JieDaoMarker> getJieDaoMarkersByQid(Integer qid) {
        return mapDao.getJieDaoMarkersByQid(qid);
    }

    @Override
    public List<LouMarker> getLouMarkersByJid(Integer jid) {
        return mapDao.getLouMarkersByJid(jid);
    }

    @Override
    public Integer addStreetMarker(JieDaoMarker jieDaoMarker) {
        mapDao.addStreetMarker(jieDaoMarker);
        return jieDaoMarker.getId();
    }

    @Override
    public MarkerSum getSums() {
        MarkerSum markerSum=new MarkerSum();
        Integer sum=0;
        Integer greenSum=0;
        Integer yellowSum=0;
        Integer redSum=0;
        List<QuMarker> quMarkerList=mapDao.getSums_qu();
        for(QuMarker quMarker:quMarkerList){
            sum+=quMarker.getSum();
            greenSum+=quMarker.getGreenSum();
            yellowSum+=quMarker.getYellowSum();
            redSum+=quMarker.getRedSum();
            quMarker.setJieDaoMarkerList(mapDao.getSums_jiedao(quMarker.getId()));
//            for(JieDaoMarker jieDaoMarker:quMarker.getJieDaoMarkerList()){
//                jieDaoMarker.setHouseMarkerList(mapDao.getSums_house(jieDaoMarker.getId()));
//            }
        }
        markerSum.setGreenSum(greenSum);
        markerSum.setRedSum(redSum);
        markerSum.setYellowSum(yellowSum);
        markerSum.setSum(sum);
        markerSum.setQuMarkerList(quMarkerList);
        return markerSum;
    }

    @Override
    public void addLouMarker(LouMarker louMarker) {
        louMarker.setyR(louMarker.getyG());
        louMarker.setyY(louMarker.getyG());

        louMarker.setxR(String.format("%.6f",Double.valueOf(louMarker.getxG())+0.000103d));
        louMarker.setxY(String.format("%.6f",Double.valueOf(louMarker.getxR())+0.000103d));

        mapDao.addLouMarker(louMarker);
    }

    @Override
    public Long getLouMarkersTotal(LouMarker louMarker) {
        return mapDao.getLouMarkersTotal(louMarker);
    }

    @Override
    public List<LouMarker> getLouMarkersManager(PageHelper page, LouMarker louMarker) {
        page.setStart((page.getPage() - 1) * page.getRows());
        return mapDao.getLouMarkersManager(page,louMarker);
    }

    @Override
    public Long getQuMarkersTotal(QuMarker quMarker) {
        return mapDao.getQuMarkersTotal(quMarker);
    }

    @Override
    public List<QuMarker> getQuMarkersManager(PageHelper page, QuMarker quMarker) {
        page.setStart((page.getPage() - 1) * page.getRows());
        return mapDao.getQuMarkersManager(page,quMarker);
    }

    @Override
    public Long getJieDaoMarkersTotal(JieDaoMarker jieDaoMarker) {
        return mapDao.getJieDaoMarkersTotal(jieDaoMarker);
    }

    @Override
    public List<JieDaoMarker> getJieDaoMarkersManager(PageHelper page, JieDaoMarker jieDaoMarker) {
        page.setStart((page.getPage() - 1) * page.getRows());
        return mapDao.getJieDaoMarkersManager(page,jieDaoMarker);
    }
}
