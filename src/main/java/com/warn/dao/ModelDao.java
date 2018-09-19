package com.warn.dao;

import com.warn.entity.Room;
import com.warn.entity.model.ManModel;
import com.warn.entity.model.RoomModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/4/20.
 */
public interface ModelDao {

    RoomModel getRoomModelByRoomId(@Param("id")Integer rid);

    List<RoomModel> getRoomModelByRoomIds(List<Room> rooms);

    void updateRooModel(RoomModel roomModel);

//    void addRoomModel(@Param("id")Integer rid);

    void deleteByRoomId(@Param("id")Integer rid);

    ManModel getManModelByOid(@Param("id")Integer oid);

    void deleteByoldId(@Param("id")Integer oldManId);

    void addManModel(ManModel manModel);

    void addRoomModel(RoomModel roomModel);

    void deleteByRoomIds(List<Room> rooms);
}
