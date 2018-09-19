package com.warn.dao;


import com.warn.dto.PageHelper;
import com.warn.entity.HouseMarker;
import com.warn.entity.JieDaoMarker;
import com.warn.entity.LouMarker;
import com.warn.entity.QuMarker;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * Created by admin on 2017/5/4.
 */
public interface MapDao {

    List<HouseMarker> getHouseMarkers();

    void addHouseMarker(HouseMarker houseMarker);

    List<QuMarker> getQuMarkers();

    List<JieDaoMarker> getJieDaoMarkers();

    void addStreetMarker(JieDaoMarker jieDaoMarker);

    List<QuMarker> getSums();
    

    List<QuMarker> getSums_qu();

    List<JieDaoMarker> getSums_jiedao(Integer id);

    List<HouseMarker> getSums_house(Integer id);

    void editHouseMarker(HouseMarker houseMarker);

    HouseMarker getHouseMarkerByOid(Integer oid);

    List<LouMarker> getLouMarkers();

    void addLouMarker(LouMarker louMarker);

    List<JieDaoMarker> getJieDaoMarkersByQid(@Param("id") Integer qid);

    List<LouMarker> getLouMarkersByJid(@Param("id")Integer jid);

    Long getLouMarkersTotal(LouMarker louMarker);

    List<LouMarker> getLouMarkersManager(@Param("page") PageHelper page, @Param("lou") LouMarker louMarker);

    Long getQuMarkersTotal(QuMarker quMarker);

    List<QuMarker> getQuMarkersManager(@Param("page")PageHelper page,@Param("qu") QuMarker quMarker);

    Long getJieDaoMarkersTotal(JieDaoMarker jieDaoMarker);

    List<JieDaoMarker> getJieDaoMarkersManager(@Param("page")PageHelper page,@Param("jie") JieDaoMarker jieDaoMarker);
}
