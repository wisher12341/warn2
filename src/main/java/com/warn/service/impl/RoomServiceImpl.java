package com.warn.service.impl;

import com.warn.dao.EquipDao;
import com.warn.dao.ModelDao;
import com.warn.dao.RoomDao;
import com.warn.dao.ThresholdDao;
import com.warn.entity.AutoValue;
import com.warn.entity.Room;
import com.warn.dto.PageHelper;
import com.warn.entity.Threshold_area;
import com.warn.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by admin on 2017/4/7.
 */
@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    RoomDao roomDao;
    @Autowired
    EquipDao equipDao;
    @Autowired
    ThresholdDao thresholdDao;
    @Autowired
    ModelDao modelDao;

    public Long getDatagridTotal(Room room) {
        if(room.getCollectId()!=null&&!room.getCollectId().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(room.getCollectId().length()>=4&&isBinary(room.getCollectId())){
                //查询以二进制的方式
                room.setCollectId(Integer.valueOf(room.getCollectId(),2).toString());
            }
        }
        return roomDao.getDatagridTotal(room);
    }

    public Long getDatagridTotal1(Room room){
        if(room.getCollectId()!=null&&!room.getCollectId().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(room.getCollectId().length()>=4&&isBinary(room.getCollectId())){
                //查询以二进制的方式
                room.setCollectId(Integer.valueOf(room.getCollectId(),2).toString());
            }
        }
        return roomDao.getDatagridTotal1(room);
    }

    public List<Room> datagridRoom(PageHelper page, Room room) {
        if(room.getCollectId()!=null&&!room.getCollectId().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(room.getCollectId().length()>=4&&isBinary(room.getCollectId())){
                //查询以二进制的方式
                room.setCollectId(Integer.valueOf(room.getCollectId(),2).toString());
            }
        }
        page.setStart((page.getPage() - 1) * page.getRows());
//        page.setEnd(page.getPage() * page.getRows());
        return roomDao.datagridRoom(page, room);
    }

    public List<Room> datagridArea(PageHelper page, Room room){
        if(room.getCollectId()!=null&&!room.getCollectId().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(room.getCollectId().length()>=4&&isBinary(room.getCollectId())){
                //查询以二进制的方式
                room.setCollectId(Integer.valueOf(room.getCollectId(),2).toString());
            }
        }
        page.setStart((page.getPage()-1) * page.getRows());
        return roomDao.datagridArea(page, room);
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
    public void addRoom(Room room) {
        //获得系统当前时间
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String dateNowStr = sdf.format(d);
        room.setrRegtime(dateNowStr);
        roomDao.addRoom(room);
        //想threshold添加默认记录
        thresholdDao.addThreshold(room.getRid());
        Threshold_area threshold_area = new Threshold_area();
        threshold_area.setRoomId(room.getRid());
        for(int i=1 ;i<=10;i++){
            threshold_area.setArea(i);
            thresholdDao.addThresholdArea(threshold_area);
        }

//        modelDao.addRoomModel(room.getRid());
    }

    public void editRoom(Room room, Integer gatewayTwo_Ten) {
        if(gatewayTwo_Ten.intValue()==2){
            //添加时，输入的设备ID是二进制的， 转换成十进制后，再存入数据库
            room.setCollectId(Integer.valueOf(room.getCollectId(),2).toString());
        }
        roomDao.editRoom(room);
    }

    public void editArea(Room room, Integer gatewayTwo_Ten){
        if(gatewayTwo_Ten.intValue()==2){
            //添加时，输入的设备ID是二进制的， 转换成十进制后，再存入数据库
            room.setCollectId(Integer.valueOf(room.getCollectId(),2).toString());
        }
        roomDao.editArea(room);
    }

    @Transactional
    public void deleteRoomById(Room room) {
        //停止房间光强的定时器
        if(SensorServiceImpl.lightTimer.get(room)!=null){
            SensorServiceImpl.lightTimer.get(room).shutdown();
            SensorServiceImpl.lightTimer.remove(room);
        }
        equipDao.deleteRoom(room.getRid());//先从设备信息上 删除该房间的字段
        //删除相邻房间
        Room roomThis=getRoomById(room.getRid()+"");
        String[] nerRoomNames=roomThis.getNerRoom().split(",");
        for(String name:nerRoomNames){
            if(!name.equals("")) {
                Room r = getRoomByName(name, roomThis.getOldId());//要修改的房间
                String nerRoom = r.getNerRoom().replace(roomThis.getRoomName(), ",");
                String[] nerRooms = nerRoom.split(",");
                String ner = "";
                for (int i = 0; i < nerRooms.length; i++) {
                    if (!nerRoom.equals("") && nerRoom != null) {
                        ner += nerRooms[i] + ",";
                    }
                }
                if (ner.indexOf(",") == 0) {//以,号开头
                    ner = ner.substring(1, ner.length());
                }
                if (ner.length() != 0) {
                    ner = ner.substring(0, ner.length() - 1);
                }
                roomDao.deleteNerRoomById(r.getRid(), ner);
            }
        }
        //改成了级联操作
//        thresholdDao.deleteByRoomId(room.getRid());
//        modelDao.deleteByRoomId(room.getRid());
        modelDao.deleteByoldId(room.getOldId());
        modelDao.deleteAreaModelByRid(room.getRid());
        modelDao.deleteRoomAreaModelByRid(room.getRid());
        roomDao.deleteRoomById(room.getRid());
    }

    private Room getRoomById(String id) {
        return roomDao.getRoomById(Integer.parseInt(id));
    }
    private Room getRoomByName(String name,Integer oldId) {
        return roomDao.getRoomByName(name,oldId);
    }

    public List<Room> getAllRoomByOldManId(Integer oldId) {
        return roomDao.getAllRoomByOldManId(oldId);
    }
    public List<AutoValue> getAreasByRoomId(Integer rid){
        Room room = roomDao.getRoomById(rid);
        List<AutoValue> autoValues = new ArrayList<>();
        if(room.getNumOne() != null && !room.getNumOne().equals("")){
            AutoValue autoValue = new AutoValue();
            autoValue.setInfo(room.getNumOne());
            autoValue.setNum(1);
            autoValues.add(autoValue);
        }
        if(room.getNumTwo() != null && !room.getNumTwo().equals("")){
            AutoValue autoValue1 = new AutoValue();
            autoValue1.setInfo(room.getNumTwo());
            autoValue1.setNum(2);
            autoValues.add(autoValue1);
        }
        if(room.getNumThree() != null && !room.getNumThree().equals("")){
            AutoValue autoValue2 = new AutoValue();
            autoValue2.setInfo(room.getNumThree());
            autoValue2.setNum(3);
            autoValues.add(autoValue2);
        }
        if(room.getNumFour() != null && !room.getNumFour().equals("")){
            AutoValue autoValue3 = new AutoValue();
            autoValue3.setInfo(room.getNumFour());
            autoValue3.setNum(4);
            autoValues.add(autoValue3);
        }
        if(room.getNumFive() != null && !room.getNumFive().equals("")){
            AutoValue autoValue4 = new AutoValue();
            autoValue4.setInfo(room.getNumFive());
            autoValue4.setNum(5);
            autoValues.add(autoValue4);
        }
        if(room.getNumSix() != null && !room.getNumSix().equals("")){
            AutoValue autoValue5 = new AutoValue();
            autoValue5.setInfo(room.getNumSix());
            autoValue5.setNum(6);
            autoValues.add(autoValue5);
        }
        if(room.getNumSeven() != null && !room.getNumSeven().equals("")){
            AutoValue autoValue6 = new AutoValue();
            autoValue6.setInfo(room.getNumSeven());
            autoValue6.setNum(7);
            autoValues.add(autoValue6);
        }
        if(room.getNumEight() != null && !room.getNumEight().equals("")){
            AutoValue autoValue7 = new AutoValue();
            autoValue7.setInfo(room.getNumEight());
            autoValue7.setNum(8);
            autoValues.add(autoValue7);
        }
        if(room.getNumNine() != null && !room.getNumNine().equals("")){
            AutoValue autoValue8 = new AutoValue();
            autoValue8.setInfo(room.getNumNine());
            autoValue8.setNum(9);
            autoValues.add(autoValue8);
        }
        if(room.getNumTen() != null && !room.getNumTen().equals("")){
            AutoValue autoValue9 = new AutoValue();
            autoValue9.setInfo(room.getNumTen());
            autoValue9.setNum(10);
            autoValues.add(autoValue9);
        }
        return autoValues;

    }


}
