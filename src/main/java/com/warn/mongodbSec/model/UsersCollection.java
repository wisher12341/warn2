package com.warn.mongodbSec.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 对应 人员信息
 * Created by netlab606 on 2017/6/5.
 */
@Document(collection = "UsersCollection")
public class UsersCollection  implements Serializable {

    @Id
    private String id;

    private String name;
    private String password;
    private String address;
    private String userPhone;
    private String guardianPhone;
    private String mac;
    private String gatewayID;

    private String roomInfoArray;


    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public String getRoomInfoArray() {
        return roomInfoArray;
    }

    public void setRoomInfoArray(String roomInfoArray) {
        this.roomInfoArray = roomInfoArray;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }
}
