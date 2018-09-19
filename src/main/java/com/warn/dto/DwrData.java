package com.warn.dto;

import com.warn.entity.EquipDown;
import com.warn.entity.OldMan;
import com.warn.entity.Outdoor;

/**
 * Created by admin on 2017/4/24.
 */
public class DwrData {
    private String type;
    private Urgency urgency;
    private Warn warn;
    private Warn_wendu warn_wendu;
    private Warn_light warn_light;
    private Integer id;//该条预警消息 在历史消息中的id 方便标记为已读  只针对预警  不针对紧急报警
    private Outdoor outdoor;

    private OldMan oldMan;//用于网关故障 和设备故障
    private EquipDown equipDown;//用于设备故障


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Warn getWarn() {
        return warn;
    }

    public void setWarn(Warn warn) {
        this.warn = warn;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public Warn_wendu getWarn_wendu() {
        return warn_wendu;
    }

    public void setWarn_wendu(Warn_wendu warn_wendu) {
        this.warn_wendu = warn_wendu;
    }

    public Warn_light getWarn_light() {
        return warn_light;
    }

    public void setWarn_light(Warn_light warn_light) {
        this.warn_light = warn_light;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Outdoor getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(Outdoor outdoor) {
        this.outdoor = outdoor;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }

    public EquipDown getEquipDown() {
        return equipDown;
    }

    public void setEquipDown(EquipDown equipDown) {
        this.equipDown = equipDown;
    }
}
