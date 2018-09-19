package com.warn.dao;

import com.warn.entity.Equipment;
import com.warn.dto.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/4/7.
 */
public interface EquipDao {
    //从设备信息上 删除该房间的字段
    void deleteRoom(@Param("id")Integer rid);

    Long getDatagridTotal(Equipment equipment);

    List<Equipment> datagridEquip(@Param("page")PageHelper page,@Param("equipment")Equipment equipment);

    void addEquip(Equipment equipment);

    void editEquip(@Param("equip") Equipment equipment,@Param("preOid") Integer preOid);

    void deleteEquipById(@Param("eid")String eid,@Param("oid")Integer oid);

    void deleteOldMan(@Param("id")Integer oldId);

    List<Equipment> getAllEquipByOldManId(@Param("id")Integer oldId);

    Equipment getEquipmentByEid(@Param("id")String eid);

    Integer getCount();

    Equipment getEquipmentByGateWayId_SensorId(@Param("gid")Integer gatewayID,@Param("sid") String sensorId);
}
