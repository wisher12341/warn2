package com.warn.dto;

/**
 * 老人、房间、设备数量
 * Created by admin on 2017/5/4.
 */
public class Count {
    private Integer oldManC;
    private Integer equipC;
    private Integer roomC;
    private Integer total;

    public Integer getOldManC() {
        return oldManC;
    }

    public void setOldManC(Integer oldManC) {
        this.oldManC = oldManC;
    }

    public Integer getEquipC() {
        return equipC;
    }

    public void setEquipC(Integer equipC) {
        this.equipC = equipC;
    }

    public Integer getRoomC() {
        return roomC;
    }

    public void setRoomC(Integer roomC) {
        this.roomC = roomC;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
