package com.warn.dto;

import com.warn.entity.Equipment;
import com.warn.entity.OldMan;
import com.warn.entity.Room;

import java.util.List;

/**
 * 老人、房间、设备信息汇总
 * Created by netlab606 on 2017/5/25.
 */
public class DataGatherDto {
    private OldMan oldMan;
    private List<Room> rooms;
    private List<Equipment> equipments;

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
