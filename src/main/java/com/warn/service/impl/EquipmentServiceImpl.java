package com.warn.service.impl;

import com.warn.dao.DataDao;
import com.warn.dao.EquipDao;
import com.warn.dao.RoomDao;
import com.warn.entity.Equipment;
import com.warn.dto.PageHelper;
import com.warn.entity.OldMan;
import com.warn.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/4/7.
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipDao equipDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    DataDao dataDao;

    public Long getDatagridTotal(Equipment equipment) {
        if(equipment.getEid()!=null&&!equipment.getEid().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(equipment.getEid().length()>=4&&isBinary(equipment.getEid())){
                //查询以二进制的方式
                equipment.setEid(Integer.valueOf(equipment.getEid(),2).toString());
            }
        }
        if(equipment.getGatewayID()!=null&&!equipment.getGatewayID().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(equipment.getGatewayID().length()>=4&&isBinary(equipment.getGatewayID())){
                //查询以二进制的方式
                equipment.setGatewayID(Integer.valueOf(equipment.getGatewayID(),2).toString());
            }
            OldMan oldMan=dataDao.getOldManByGatewayID(Integer.parseInt(equipment.getGatewayID()));
            equipment.setOldId(oldMan.getOid());
        }
        return equipDao.getDatagridTotal(equipment);
    }

    public List<Equipment> datagridEquip(PageHelper page, Equipment equipment) {
        if(equipment.getEid()!=null&&!equipment.getEid().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(equipment.getEid().length()>=4&&isBinary(equipment.getEid())){
                //查询以二进制的方式
                equipment.setEid(Integer.valueOf(equipment.getEid(),2).toString());
            }
        }
        if(equipment.getGatewayID()!=null&&!equipment.getGatewayID().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(equipment.getGatewayID().length()>=4&&isBinary(equipment.getGatewayID())){
                //查询以二进制的方式
                equipment.setGatewayID(Integer.valueOf(equipment.getGatewayID(),2).toString());
            }
            OldMan oldMan=dataDao.getOldManByGatewayID(Integer.parseInt(equipment.getGatewayID()));
            equipment.setOldId(oldMan.getOid());
        }
        page.setStart((page.getPage() - 1) * page.getRows());
        return equipDao.datagridEquip(page, equipment);
    }

    //简单判断是不是二进制数
    private boolean isBinary(String collectId) {
        if(collectId.contains("2")||collectId.contains("3")||collectId.contains("4")||collectId.contains("5")||collectId.contains("6")||collectId.contains("7")||collectId.contains("8")
                ||collectId.contains("9")){
            return false;
        }
        return true;
    }

    @Transactional
    public void addEquip(Equipment equipment, Integer gatewayTwo_Ten) {
        if(gatewayTwo_Ten.intValue()==2){
            //添加时，输入的网关是二进制的， 转换成十进制后，再存入数据库
            equipment.setEid(Integer.valueOf(equipment.getEid(),2).toString());
        }
        //获得系统当前时间
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        equipment.seteRegtime(dateNowStr);
        equipDao.addEquip(equipment);
        //在对应房间 填充 设备ID
        roomDao.addRoomEid(equipment);
    }

    @Transactional
    public void editEquip(Equipment equipment,Integer preOid) {
        equipDao.editEquip(equipment,preOid);
        roomDao.addRoomEid(equipment);
    }

    @Transactional
    public void deleteEquipById(String eid,Integer oid) {
        roomDao.deleteEquipByEid(eid,oid);
        equipDao.deleteEquipById(eid,oid);
    }

    public List<Equipment> getAllEquipByOldManId(Integer oldId) {
        return equipDao.getAllEquipByOldManId(oldId);
    }
}
