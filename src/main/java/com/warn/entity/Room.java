package com.warn.entity;

import java.util.List;

/**
 * Created by admin on 2017/4/5.
 */
//房间信息
public class Room {

    private Integer rid;
    private String roomName;
    private String collectId;//采集点 即设备ID
    private String nerRoom;//相邻房间ID，以 , 隔开
    private Integer oldId;//对应老人的ID
    private String rRegtime;//注册时间
    private String numOne;
    private String numTwo;
    private String numThree;
    private String numFour;
    private String numFive;
    private String numSix;
    private String numSeven;
    private String numEight;
    private String numNine;

    public String getNerRoom() {
        return nerRoom;
    }

    public void setNerRoom(String nerRoom) {
        this.nerRoom = nerRoom;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getrRegtime() {
        return rRegtime;
    }

    public void setrRegtime(String rRegtime) {
        this.rRegtime = rRegtime;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getNumOne() {
        return numOne;
    }

    public void setNumOne(String numOne) {
        this.numOne = numOne;
    }

    public String getNumTwo() {
        return numTwo;
    }

    public void setNumTwo(String numTwo) {
        this.numTwo = numTwo;
    }

    public String getNumThree() {
        return numThree;
    }

    public void setNumThree(String numThree) {
        this.numThree = numThree;
    }

    public String getNumFour() {
        return numFour;
    }

    public void setNumFour(String numFour) {
        this.numFour = numFour;
    }

    public String getNumFive() {
        return numFive;
    }

    public void setNumFive(String numFive) {
        this.numFive = numFive;
    }

    public String getNumSix() {
        return numSix;
    }

    public void setNumSix(String numSix) {
        this.numSix = numSix;
    }

    public String getNumSeven() {
        return numSeven;
    }

    public void setNumSeven(String numSeven) {
        this.numSeven = numSeven;
    }

    public String getNumEight() {
        return numEight;
    }

    public void setNumEight(String numEight) {
        this.numEight = numEight;
    }

    public String getNumNine() {
        return numNine;
    }

    public void setNumNine(String numNine) {
        this.numNine = numNine;
    }

    //重写equals hashcode 不然获得不了以oldman为键的map的值
    @Override
    public boolean equals(Object obj) {
        Room room= (Room) obj;
        return room.getRid()==rid;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + rid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "rid=" + rid +
                ", roomName='" + roomName + '\'' +
                ", collectId='" + collectId + '\'' +
                ", nerRoom='" + nerRoom + '\'' +
                ", oldId=" + oldId +
                ", rRegtime='" + rRegtime + '\'' +
                '}';
    }
}
