package com.warn.service;


import com.warn.dto.MarkerSum;
import com.warn.dto.PageHelper;
import com.warn.entity.*;

import java.util.List;

/**
 * Created by admin on 2017/5/4.
 */
public interface MapService {

    List<HouseMarker> getHouseMarkers();

    OldMan addHouseMarker(HouseMarker houseMarker);

    List<QuMarker> getQuMarkers();

    List<JieDaoMarker> getJieDaoMarkers();

    Integer addStreetMarker(JieDaoMarker jieDaoMarker);

    MarkerSum getSums();

    List<LouMarker> getLouMarkers();

    void addLouMarker(LouMarker louMarker);

    List<JieDaoMarker> getJieDaoMarkersByQid(Integer qid);

    List<LouMarker> getLouMarkersByJid(Integer jid);

    Long getLouMarkersTotal(LouMarker louMarker);

    List<LouMarker> getLouMarkersManager(PageHelper page, LouMarker louMarker);

    Long getQuMarkersTotal(QuMarker quMarker);

    List<QuMarker> getQuMarkersManager(PageHelper page, QuMarker quMarker);

    Long getJieDaoMarkersTotal(JieDaoMarker jieDaoMarker);

    List<JieDaoMarker> getJieDaoMarkersManager(PageHelper page, JieDaoMarker jieDaoMarker);
}
