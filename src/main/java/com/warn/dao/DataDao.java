package com.warn.dao;

import com.warn.entity.*;
import com.warn.dto.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/4/4.
 */
public interface DataDao {

    Long getDatagridTotal(@Param("oldMan")OldMan oldMan);

    List<OldMan> datagridUser(@Param("page")PageHelper page,@Param("oldMan")OldMan oldMan);//两个参数的话 要加Param

    Integer addOldman(OldMan oldMan);

    void editOldman(OldMan oldMan);

    void deleteOldmanById(@Param("id")Integer oldManId);

    void addRelatives(Relatives relatives);

    void editRelatives(Relatives relatives);

    void deleteRelativesByOldManId(@Param("id")Integer oldManId);

    OldMan getOldManByEquipId(@Param("id")String sensorPointObjID);


    OldMan getOldManByOid(@Param("id")Integer oid);

    List<OldMan> getAllOldMan();

    Integer getCount();

    OldMan getOldManByGatewayID(Integer gatewayID);

    List<OldMan> getOldManBySegment(String segment);

    void editOldManStatus(OldMan oldMan);

    List<OldMan> datagridUserMap(@Param("page")PageHelper page,@Param("oldMan") OldMan oldMan);

    void editOldmanMap(OldMan oldMan);
}
