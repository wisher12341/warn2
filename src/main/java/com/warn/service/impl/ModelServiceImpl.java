package com.warn.service.impl;

import com.warn.dao.ModelDao;
import com.warn.dao.RoomDao;
import com.warn.dto.ManModelDto;
import com.warn.dto.ManModelDtos;
import com.warn.dto.RoomModelDto;
import com.warn.dto.RoomModelDtos;
import com.warn.entity.Room;
import com.warn.entity.model.AreaModel;
import com.warn.entity.model.ManModel;
import com.warn.entity.model.RoomModel;
import com.warn.entity.model.roomAreaModel;
import com.warn.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/21.
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelDao modelDao;
    @Autowired
    RoomDao roomDao;

    @Override
    public List<RoomModel> getRoomModelByOid(Integer oid) {
        List<Room> rooms = roomDao.getAllRoomByOldManId(oid);
        List<RoomModel> roomModels = new ArrayList();
        if (rooms.size() > 0) {
            roomModels = modelDao.getRoomModelByRoomIds(rooms);
            for (RoomModel roomModel : roomModels) {
                roomModel.setActive(roomModel.getActive().replaceAll("#", "<br>"));
                roomModel.setRest(roomModel.getRest().replaceAll("#", "<br>"));
            }
        }
        return roomModels;
    }
    @Override
    public List<AreaModel> getAreaModelByRid(Integer rid){
        Room room = roomDao.getRoomById(rid);
        List<AreaModel> areaModels = new ArrayList<>();
        if(room != null){
            areaModels = modelDao.getAreaModelByRid(rid);
            for(AreaModel areaModel:areaModels){
                areaModel.setAreaInfo(getPositionInfo(areaModel.getArea(),room));
                areaModel.setAreaActiveTime(areaModel.getAreaActiveTime().replaceAll("#","<br>"));
                areaModel.setAreaRestTime(areaModel.getAreaRestTime().replaceAll("#","<br>"));
            }
        }
        return areaModels;
    }
//    @Override
//    public void updateRoomModel(RoomModel roomModel) {
//        modelDao.updateRooModel(roomModel);
//    }


    @Override
    public List<ManModelDto> getManModelByOid(Integer oid) {
        ManModel manModel = modelDao.getManModelByOid(oid);
        List<ManModelDto> manModelDtos = new ArrayList<>();
        if (manModel == null) {
            return manModelDtos;
        }
        for (String str : manModel.getLive().split("#")) {
            ManModelDto manModelDto = new ManModelDto();
            manModelDto.setMid(manModel.getMid());
            manModelDto.setTimes(str.split("@")[0]);
            String st = "";
            for (String s : str.split("@")[1].split("%")) {
                if (s.split("\\$")[0].equals("0")) {
                    st += "户外,";
                } else {
                    st += roomDao.getRoomById(Integer.parseInt(s.split("\\$")[0])).getRoomName() + ",";
                }
            }
            st = st.substring(0, st.length() - 1);
            manModelDto.setTimeRoom(st);
            manModelDtos.add(manModelDto);
        }
        return manModelDtos;
    }

    @Override
    public List<RoomModelDto> getRoomModelById(Integer id){
        roomAreaModel rAreaModel = modelDao.getRoomAreaModelByRid(id);
        List<RoomModelDto> roomModelDtos = new ArrayList<>();
        if(rAreaModel == null)
            return roomModelDtos;
        for(String str : rAreaModel.getRoomLive().split("#")){
            RoomModelDto roomModelDto = new RoomModelDto();
            roomModelDto.setId(rAreaModel.getId());
            roomModelDto.setTimes(str.split("@")[0]);
            String st = "";
            for(String s : str.split("@")[1].split("%")){
                if(s.split("\\$")[0].equals("0"))
                    st+="户外";
                else
                    st += getPositionInfo(Integer.parseInt(s.split("\\$")[0]),roomDao.getRoomById(id)) + ",";
            }
            st = st.substring(0, st.length() - 1);
            roomModelDto.setTimeArea(st);
            roomModelDtos.add(roomModelDto);
        }
        return roomModelDtos;
    }

    @Transactional
    @Override
    public void addManModel(ManModelDtos manModelDtos) {
        ManModel manModel1 = modelDao.getManModelByOid(manModelDtos.getOid());
        List<Room> rooms = roomDao.getAllRoomByOldManId(manModelDtos.getOid());
        //删除原来的数据
        if (manModel1 != null) {
            modelDao.deleteByoldId(manModelDtos.getOid());
            modelDao.deleteByRoomIds(rooms);
        }
        //生活规律
        ManModel manModel = new ManModel();
        manModel.getOldMan().setOid(manModelDtos.getOid());
        String live = "";
        int i = 0;
        for (String time : manModelDtos.getTimes()) {
            live += time + "@";
            for (String room_type : manModelDtos.getTimeRooms().get(i++)) {
                live += room_type + "%";
            }
            live = live.substring(0, live.length() - 1);
            live += "#";
        }
        live = live.substring(0, live.length() - 1);
        manModel.setLive(live);
        modelDao.addManModel(manModel);
        //房间规律
        for (Room room : rooms) {
            RoomModel roomModel = new RoomModel();
            roomModel.setRoom(room);
            String active = "";
            String rest = "";
            int j = 0;//计数
            for (String[] room_type : manModelDtos.getTimeRooms()) {
                for (String r : room_type) {
                    String rid = r;
                    String type = "0";
                    if (r.indexOf("$") != -1) {
                        rid = r.split("\\$")[0];
                        type = r.split("\\$")[1];
                    }
                    //该时间段内 时间匹配
                    if (Integer.parseInt(rid) == room.getRid()) {
                        //活动
                        if (type.equals("1")) {
                            active += manModelDtos.getTimes().get(j) + "#";
                        } else {//休息
                            rest += manModelDtos.getTimes().get(j) + "#";
                        }
                    }
                }
                j++;
            }
            if (!active.equals("")) {
                active = active.substring(0, active.length() - 1);
            }
            if (!rest.equals("")) {
                rest = rest.substring(0, rest.length() - 1);
            }
            roomModel.setRest(rest);
            roomModel.setActive(active);
            modelDao.addRoomModel(roomModel);
        }
    }

    @Transactional
    @Override
    public void addRoomModel(RoomModelDtos roomModelDtos) {
        roomAreaModel areaModel = modelDao.getRoomAreaModelByRid(roomModelDtos.getOid());
        if (areaModel != null) {
            modelDao.deleteRoomAreaModelByRid(roomModelDtos.getOid());
            modelDao.deleteAreaModelByRid(roomModelDtos.getOid());
        }
        roomAreaModel rAreaModel = new roomAreaModel();
        rAreaModel.getRoom().setRid(roomModelDtos.getOid());
        String live = "";
        int i = 0;
        for (String time : roomModelDtos.getTimes()) {
            live += time + "@";
            for (String area_type : roomModelDtos.getTimeRooms().get(i++)) {
                live += area_type + "%";
            }
            live = live.substring(0, live.length() - 1);
            live += "#";
        }
        live = live.substring(0, live.length() - 1);
        rAreaModel.setRoomLive(live);
        modelDao.addRoomAreaModel(rAreaModel);
        Room room = roomDao.getRoomById(roomModelDtos.getOid());
        for (int num = 1; num <= 9; num++) {
            AreaModel areaModel1 = new AreaModel();
            areaModel1.setArea(0);
            String active = "";
            String rest = "";
            int j = 0;//计数
            for (String[] area_type : roomModelDtos.getTimeRooms()) {
                for (String r : area_type) {
                    String area = r;
                    String type = "0";
                    if (r.indexOf("$") != -1) {
                        area = r.split("\\$")[0];
                        type = r.split("\\$")[1];
                    }
                    //该时间段内 时间匹配
                    if (Integer.parseInt(area) == num) {
                        //活动
                        areaModel1.setArea(num);
                        if (type.equals("1")) {
                            active += roomModelDtos.getTimes().get(j) + "#";
                        } else {//休息
                            rest += roomModelDtos.getTimes().get(j) + "#";
                        }

                    }
                }
                j++;
            }
            if (!active.equals("")) {
                active = active.substring(0, active.length() - 1);
            }
            if (!rest.equals("")) {
                rest = rest.substring(0, rest.length() - 1);
            }
            if(areaModel1.getArea() != 0)
            {
                areaModel1.setRoomId(roomModelDtos.getOid());
                areaModel1.setAreaInfo(getPositionInfo(num,room));
                areaModel1.setAreaActiveTime(active);
                areaModel1.setAreaRestTime(rest);
                modelDao.addAreaModel(areaModel1);
            }
        }
    }

    public String getPositionInfo(int data, Room room) {
        if (data != 0)
            switch (data) {
                case 1:
                    return room.getNumOne();
                case 2:
                    return room.getNumTwo();
                case 3:
                    return room.getNumThree();
                case 4:
                    return room.getNumFour();
                case 5:
                    return room.getNumFive();
                case 6:
                    return room.getNumSix();
                case 7:
                    return room.getNumSeven();
                case 8:
                    return room.getNumEight();
                case 9:
                    return room.getNumNine();
                default:
                    return "地板";
            }
        return "无人";


    }
}
