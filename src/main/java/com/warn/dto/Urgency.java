package com.warn.dto;

import com.warn.entity.Equipment;
import com.warn.entity.OldMan;
import com.warn.entity.Room;

/**
 * 紧急报警 信息
 * Created by admin on 2017/4/24.
 */
public class Urgency {

    private OldMan oldMan;
    private Room room;
    private Equipment equip;

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public Equipment getEquip() {
        return equip;
    }

    public void setEquip(Equipment equip) {
        this.equip = equip;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
