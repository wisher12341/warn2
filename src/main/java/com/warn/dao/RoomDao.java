package com.warn.dao;

import com.warn.entity.Equipment;
import com.warn.entity.Room;
import com.warn.dto.PageHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/4/7.
 */
public interface RoomDao {
    Long getDatagridTotal(Room room);

    Long getDatagridTotal1(Room room);

    Long getDatagridTotal2(Room room);

    List<Room> datagridRoom(@Param("page")PageHelper page,@Param("room")Room room);

    List<Room> datagridArea(@Param("page")PageHelper page,@Param("room")Room room);

    List<Room> datagridStatistic(@Param("page")PageHelper page,@Param("room")Room room);

    void addRoom(Room room);

    void editRoom(Room room);

    void editArea(Room room);

    void deleteRoomById(@Param("id")Integer rid);

    void deleteRoomByOldManId(@Param("id")Integer oldManId);

    List<Room> getAllRoomByOldManId(@Param("id")Integer oldId);



    void deleteEquipByEid(@Param("eid")String eid,@Param("oid")Integer oid);

    Room getRoomById(@Param("id")Integer id);

    void deleteNerRoomById(@Param("id")Integer id,@Param("nerRoom") String nerRoom);

    Room getRoomByEquipId(@Param("id")String sensorPointObjID);

    Room getRoomByName(@Param("name")String name,@Param("id") Integer oldId);

    Integer getCount();

    Room getRoomByGateWayId_SensorId(@Param("gid")Integer gatewayID,@Param("sid") String sensorId);

    void addRoomEid(Equipment equipment);
}
