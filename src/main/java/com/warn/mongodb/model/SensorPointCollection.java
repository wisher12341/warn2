package com.warn.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 对应 传感器 信息
 * Created by netlab606 on 2017/6/5.
 */
@Document(collection = "SensorPointCollection")
public class SensorPointCollection  implements Serializable {

    @Id
    private String id;
    private String sensorPointID;
    private String gatewayObjID;
    private String gatewayID;
//    @DBRef
//    private UsersCollection usersCollection;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensorPointID() {
        return sensorPointID;
    }

    public void setSensorPointID(String sensorPointID) {
        this.sensorPointID = sensorPointID;
    }

    public String getGatewayObjID() {
        return gatewayObjID;
    }

    public void setGatewayObjID(String gatewayObjID) {
        this.gatewayObjID = gatewayObjID;
    }

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }
}
