package com.warn.service;

import com.warn.entity.Equipment;
import com.warn.dto.PageHelper;

import java.util.List;

/**
 * Created by admin on 2017/4/7.
 */
public interface EquipmentService {
    Long getDatagridTotal(Equipment equipment);

    List<Equipment> datagridEquip(PageHelper page, Equipment equipment);

    void addEquip(Equipment equipment, Integer gatewayTwo_Ten);

    void editEquip(Equipment equipment,Integer preOid);

    void deleteEquipById(String eid,Integer oid);

    List<Equipment> getAllEquipByOldManId(Integer oldId);
}
