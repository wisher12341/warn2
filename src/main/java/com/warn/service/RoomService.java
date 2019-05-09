package com.warn.service;

import com.warn.entity.AutoValue;
import com.warn.entity.Room;
import com.warn.dto.PageHelper;

import java.util.List;

/**
 * Created by admin on 2017/4/7.
 */
public interface RoomService {
    //获得特定查询条件（或无条件）的总记录数
    public Long getDatagridTotal(Room room);
    //获得特定查询条件（或无条件）的记录 列表
    public List<Room> datagridRoom(PageHelper page, Room room);

    Long getDatagridTotal1(Room room);

    List<Room> datagridArea(PageHelper page, Room room);


    void addRoom(Room room);

    void editRoom(Room room, Integer gatewayTwo_Ten);

    void editArea(Room room, Integer gatewayTwo_Ten);

    void deleteRoomById(Room room);

    List<Room> getAllRoomByOldManId(Integer oldId);

    List<AutoValue> getAreasByRoomId(Integer rid);
}
