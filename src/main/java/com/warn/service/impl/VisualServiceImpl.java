package com.warn.service.impl;

import com.warn.dao.RoomDao;
import com.warn.dao.VisualDao;
import com.warn.dto.visual.OldManLive;
import com.warn.dto.visual.OldManLive_Room;
import com.warn.dto.visual.RoomVisual;
import com.warn.dto.visual.TimeRoom;
import com.warn.entity.Room;
import com.warn.entity.model.RoomModel;
import com.warn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2017/4/13.
 */
@Service
public class VisualServiceImpl implements VisualService {

    @Autowired
    VisualDao visualDao;
    @Autowired
    RoomDao roomDao;

    public OldManLive getLiveVisualByoldId(Integer oldId) {
        OldManLive oldManLive=new OldManLive();
        String oldTime=visualDao.getLiveVisualByoldId(oldId);
        //一开始 整体生活规律模型 是直接手动输入存入数据库的 现在只留房间的模型  整体的根据房间的来算 构造出原来手动输入的字符串形式
//        String oldTime="";
//        List<String> times_room=new ArrayList<String>();
//        List<RoomModel> roomModels=visualDao.getRoomActivityByOldId(oldId);
//        for(RoomModel roomModel:roomModels){
//            for(String active:roomModel.getActive().split("#")){
//                times_room.add(active+"@"+roomModel.getRoom().getRid());
//            }
//            for(String rest:roomModel.getRest().split("#")){
//                times_room.add(rest+"@"+roomModel.getRoom().getRid());
//            }
//        }
//        if(times_room.size()==0){
//            return oldManLive;
//        }
//        Collections.sort(times_room);
//
//        //填充 户外时间
//        if(times_room.size()==1){
//
//        }else {
//            for (int i = 0; i < times_room.size(); i++) {
//                if (i == 0) {
//                    //如果第一个不是以00:00开始
//                    if (!times_room.get(i).split("@")[0].split("-")[0].equals("00:00")) {
//                        oldTime += "00:00-" + times_room.get(i).split("@")[0].split("-")[0] + "@0";
//                    }
//                    //如果前一个的结束和后一个的开始是连着的
//                    if (times_room.get(i + 1).split("@")[0].split("-")[0].compareTo(times_room.get(i).split("@")[0].split("-")[1]) == 0) {
//                        oldTime += times_room.get(i);
//                    }
//                    //如果前一个的结束跟后一个的开始不连续 且时间不重叠
//                    else if (times_room.get(i + 1).split("@")[0].split("-")[0].compareTo(times_room.get(i).split("@")[0].split("-")[1]) > 0) {
//                        oldTime += times_room.get(i);
//                        oldTime += times_room.get(i).split("@")[0].split("-")[1] + "-"+times_room.get(i+1).split("@")[0].split("-")[0]+"@0";
//                    }
//                    //如果前一个和后一个 有时间重叠
//                    else{
//
//                        oldTime+=times_room.get(i).split("@")[0].split("-")[0]+"-"+times_room.get(i+1).split("@")[0].split("-")[0]+"@"+times_room.get(i).split("@")[1];
//
//                        //后一个的结束时间大于前一个的结束时间
//                        if(times_room.get(i + 1).split("@")[0].split("-")[1].compareTo(times_room.get(i).split("@")[0].split("-")[1]) > 0){
//                            oldTime+=times_room.get(i+1).split("@")[0].split("-")[0]+"-"+times_room.get(i).split("@")[0].split("-")[1]+"@"+times_room.get(i).split("@")[1]+"%"+
//                                    times_room.get(i+1).split("@")[1];
////                            oldTime+=
//                        }
//                    }
//
//                } else if(i==times_room.size()-1){
//
//                }else{
//
//                }
//            }
//        }


        String[] oldArr=oldTime.split("#");
        List<Room> rooms=roomDao.getAllRoomByOldManId(oldId);//获得该老人所有房间信息
        for(Room room:rooms){
            oldManLive.getRoomNames().add(room.getRoomName());
        }
        oldManLive.getRoomNames().add("户外");
        for(String old:oldArr){
            OldManLive_Room oldManLive_room=new OldManLive_Room();
            String[] data=old.split("@");
            oldManLive_room.setTime(data[0]);
            oldManLive_room.setValue(computeValue(data[0]));
            String roomName=searchRoomName(data[1], rooms);
            //如果是多个房间，则再为OldManLive的房间 添加组合房间名
            if(roomName.indexOf("&")!=-1){
                oldManLive.getRoomNames().add(roomName);
            }
            oldManLive_room.setRoomName(roomName);
            oldManLive.getOldManLive_rooms().add(oldManLive_room);
        }
        return oldManLive;
    }

    //根据房间ID查找房间名
    public String searchRoomName(String roomId,List<Room> rooms){
        //先判断是否有多个房间
        if(roomId.indexOf("%")!=-1){
            String[] roomIds=roomId.split("%");
            String roomName="";
            for(String id:roomIds){
                if(id.charAt(0)=='0'){
                    roomName+="户外&";
                }
                for(Room room:rooms){
                    String a=id.split("\\$")[0];
                    if(room.getRid()==Integer.parseInt(a)){
                        roomName+=room.getRoomName()+"&";
                    }
                }
            }
            return roomName.substring(0,roomName.length()-1);
        }else{
            if(roomId.equals("0")){
                return "户外";
            }
            for(Room room:rooms){
                String a=roomId.split("\\$")[0];
                if(room.getRid()==Integer.parseInt(a)){
                    return room.getRoomName();
                }
            }
            return "";
        }
    }

    public List<RoomVisual> getRoomVisualByoldId(Integer oldId) {
        //先获得数据库记录信息
        List<RoomModel> room_activities=visualDao.getRoomActivityByOldId(oldId);
        List<RoomVisual> roomVisuals=new ArrayList<RoomVisual>();
        //对数据库记录信息进行处理，处理成前端需要的格式
        for(RoomModel room_model :room_activities){
            RoomVisual roomVisual=new RoomVisual();
            roomVisual.setRoomId(room_model.getRoom().getRid());//房间ID
            roomVisual.setRoomName(room_model.getRoom().getRoomName());//房间名
            //获得房间活动字段
            String activityStr= room_model.getActive();
            if(activityStr!=null&&!activityStr.equals("")) {
                String[] activityArr = activityStr.split("#");
                //活动时间段
                for (String activity : activityArr) {
                    TimeRoom timeRoomA = new TimeRoom();
                    timeRoomA.setTime(activity);
                    timeRoomA.setType("活动时间");
                    timeRoomA.setValue(computeValue(activity));
                    roomVisual.getTime().add(timeRoomA);
                }
            }
            //获得房间休息字段,处理方式同上
            String restStr= room_model.getRest();
            if(restStr!=null&&!restStr.equals("")) {
                String[] restArr = restStr.split("#");
                //休息时间段
                for (String rest : restArr) {
                    TimeRoom timeRoomR = new TimeRoom();
                    timeRoomR.setTime(rest);
                    timeRoomR.setType("休息时间");
                    timeRoomR.setValue(computeValue(rest));
                    roomVisual.getTime().add(timeRoomR);
                }
            }
//            填充不在的时间段,  先将活动和休息的时间段合并，然后分析空隙时间段
            String allTime=(!activityStr.equals("")?activityStr+"#":"")+restStr;
            String[] allArr=allTime.split("#");
            sortTime(allArr);//排序
            dealBlank(allArr, roomVisual);//分析空闲时间
            Collections.sort(roomVisual.getTime());
            roomVisuals.add(roomVisual);
        }
        return roomVisuals;
    }

    //计算该时间段占改天的比例 求出比例后乘以1000方便前端
    public String computeValue(String time){
        String[] arr=time.split("-");
        String start=arr[0];//开始时间
        String end=arr[1];//结束时间
        String[] startArr=start.split(":");
        String[] endArr=end.split(":");
        Integer value=(Integer.parseInt(endArr[0])-Integer.parseInt(startArr[0]))*60+(Integer.parseInt(endArr[1])-Integer.parseInt(startArr[1]));
        return value.toString();
    }
    //活动、休息时间段排序
    public void sortTime(String[] arr){
        Arrays.sort(arr);//可以直接用工具 从第一个字符比较    不用自定义
    }
    //分析空闲时间
    public void dealBlank(String[] allArr, RoomVisual roomVisual) {
        if(allArr[0].equals("")){
            TimeRoom timeRoomN = new TimeRoom();
            String nTime="00:00-24:00";
            timeRoomN.setTime(nTime);
            timeRoomN.setValue((60*24)+"");
            timeRoomN.setType("不在房间");
            roomVisual.getTime().add(timeRoomN);
            return;
        }
        for(int i=0;i<allArr.length;i++) {
            //既不是第一条 也不是最后一条
            if(i!=allArr.length-1&&i!=0) {
                //计算空闲时间段  后一个活动时间的开始减去前一个活动时间的结束
                Integer value = (Integer.parseInt(allArr[i+1].split("-")[0].split(":")[0]) - Integer.parseInt(allArr[i].split("-")[1].split(":")[0]))*60+
                        (Integer.parseInt(allArr[i+1].split("-")[0].split(":")[1]) - Integer.parseInt(allArr[i].split("-")[1].split(":")[1]));
                if (value != 0) {
                    TimeRoom timeRoomN = new TimeRoom();
                    String nTime=allArr[i].split("-")[1]+"-"+allArr[i+1].split("-")[0];
                    timeRoomN.setTime(nTime);
                    timeRoomN.setValue(value.toString());
                    timeRoomN.setType("不在房间");
                    roomVisual.getTime().add(timeRoomN);
                }
            }
            //第一条记录
            else if(i==0){
                //0点开始的空闲时间段
                Integer value=(Integer.parseInt(allArr[i].split("-")[0].split(":")[0]))*60+Integer.parseInt(allArr[i].split("-")[0].split(":")[1]);
                if(value!=0){
                    TimeRoom timeRoomN = new TimeRoom();
                    String nTime="00:00-"+allArr[i].split("-")[0];
                    timeRoomN.setTime(nTime);
                    timeRoomN.setValue(value.toString());
                    timeRoomN.setType("不在房间");
                    roomVisual.getTime().add(timeRoomN);
                }
                //有多条记录
                if(allArr.length>1){
                    //两活动时间段 间隔的空闲时间
                    Integer value1 = (Integer.parseInt(allArr[i+1].split("-")[0].split(":")[0]) - Integer.parseInt(allArr[i].split("-")[1].split(":")[0]))*60+
                            (Integer.parseInt(allArr[i+1].split("-")[0].split(":")[1]) - Integer.parseInt(allArr[i].split("-")[1].split(":")[1]));
                    if (value1 != 0) {
                        TimeRoom timeRoomN = new TimeRoom();
                        String nTime=allArr[i].split("-")[1]+"-"+allArr[i+1].split("-")[0];
                        timeRoomN.setTime(nTime);
                        timeRoomN.setValue(value1.toString());
                        timeRoomN.setType("不在房间");
                        roomVisual.getTime().add(timeRoomN);
                    }
                }
                //只有一条记录
                else{
                    //到24点结束的空闲时间段
                    Integer value2=(24-Integer.parseInt(allArr[i].split("-")[1].split(":")[0]))*60-Integer.parseInt(allArr[i].split("-")[1].split(":")[1]);
                    if(value2!=0){
                        TimeRoom timeRoomN = new TimeRoom();
                        String nTime=allArr[i].split("-")[1]+"-24:00";
                        timeRoomN.setTime(nTime);
                        timeRoomN.setValue(value2.toString());
                        timeRoomN.setType("不在房间");
                        roomVisual.getTime().add(timeRoomN);
                    }
                }
            }
            //最后一条记录 默认是多条记录
            else{
                Integer valueEnd = (24-Integer.parseInt(allArr[i].split("-")[1].split(":")[0]))*60-Integer.parseInt(allArr[i].split("-")[1].split(":")[1]);
                if (valueEnd != 0) {
                    TimeRoom timeRoomN = new TimeRoom();
                    String nTime=allArr[i].split("-")[1]+"-24:00";
                    timeRoomN.setTime(nTime);
                    timeRoomN.setValue(valueEnd.toString());
                    timeRoomN.setType("不在房间");
                    roomVisual.getTime().add(timeRoomN);
                }
            }
        }
    }
}
