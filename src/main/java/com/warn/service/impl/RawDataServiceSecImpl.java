package com.warn.service.impl;

import com.warn.dao.DataDao;
import com.warn.dto.SenSorDto;
import com.warn.entity.OldMan;
import com.warn.sensordata.dao.RawDataSecDao;
import com.warn.dto.PageHelper;

import com.warn.mongodb.model.SensorCollection;
import com.warn.sensordata.model.SecSensorCollection;
import com.warn.sensordata.model.SensorPointCollection;
import com.warn.sensordata.model.UsersCollection;
import com.warn.service.RawDataServiceSec;
import com.warn.transfor.SensorTransferSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/8.
 */
@Service
public class RawDataServiceSecImpl implements RawDataServiceSec {

    @Autowired
    RawDataSecDao rawDataSecDao;
    @Autowired
    SensorTransferSec sensorTransfor;
    @Autowired
    DataDao dataDao;

    public Long getsensorDatagridTotal(SenSorDto senSorDto) {
        SensorCollection sensorCollection=new SensorCollection();
        OldMan oldMan=senSorDto.getOldMan();
        if(senSorDto.getSensorType()!=null&&!senSorDto.getSensorType().equals("")) {
            switch (senSorDto.getSensorType()){
                case "行为":
                    sensorCollection.setSensorID(1);
                    break;
                case "温度":
                    sensorCollection.setSensorID(2);
                    break;
                case "湿度":
                    sensorCollection.setSensorID(3);
                    break;
                case "光强":
                    sensorCollection.setSensorID(4);
                    break;
                case "霍尔":
                    sensorCollection.setSensorID(5);
                    break;
                case "WIFI故障":
                    sensorCollection.setSensorID(252);
                    break;
                case "请求时间":
                    sensorCollection.setSensorID(254);
                    break;
                case "紧急报警":
                    sensorCollection.setSensorID(255);
            }
        }
        sensorCollection.setSensorData(senSorDto.getSensorData());
        sensorCollection.setTime(senSorDto.getTime());

        if(oldMan!=null&&oldMan.getGatewayID()!=null&&!oldMan.getGatewayID().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(oldMan.getGatewayID().length()>=4&&isBinary(oldMan.getGatewayID())){
                //查询以二进制的方式
                oldMan.setGatewayID(Integer.valueOf(oldMan.getGatewayID(),2).toString());
            }
        }
        if(oldMan!=null&&oldMan.getSegment()!=null&&!oldMan.getSegment().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(oldMan.getSegment().length()>=4&&isBinary(oldMan.getSegment())){
                //查询以二进制的方式
                oldMan.setSegment(Integer.valueOf(oldMan.getSegment(),2).toString());
            }
        }
        if(senSorDto.getSensorId()!=null&&!senSorDto.getSensorId().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(senSorDto.getSensorId().length()>=4&&isBinary(senSorDto.getSensorId())){
                //查询以二进制的方式
                sensorCollection.setSensorPointID(Integer.valueOf(senSorDto.getSensorId(),2).toString());
            }else{
                sensorCollection.setSensorPointID(senSorDto.getSensorId());
            }
        }
        OldMan oldManSearch = new OldMan();
        List<OldMan> oldManSearchs = new ArrayList<>();
        if(oldMan!=null&&oldMan.getOid()!=null)
            oldManSearch=dataDao.getOldManByOid(oldMan.getOid());
        if(oldMan!=null&&oldMan.getSegment()!=null&&!oldMan.getSegment().equals(""))
            oldManSearchs =dataDao.getOldManBySegment(oldMan.getSegment());
        return rawDataSecDao.getsensorDatagridTotal(sensorCollection,oldManSearch,oldManSearchs);
    }


    //简单判断是不是二进制数
    private boolean isBinary(String collectId) {
        if(collectId.contains("2")||collectId.contains("3")||collectId.contains("4")||collectId.contains("5")||collectId.contains("6")||collectId.contains("7")||collectId.contains("8")
                ||collectId.contains("9")){
            return false;
        }
        return true;
    }

    public List<SenSorDto> datagridSensor(PageHelper page,SenSorDto senSorDto) {
        page.setStart((page.getPage() - 1) * page.getRows());
        SensorCollection sensorCollection=new SensorCollection();
        OldMan oldMan=senSorDto.getOldMan();
        if(senSorDto.getSensorType()!=null&&!senSorDto.getSensorType().equals("")) {
            switch (senSorDto.getSensorType()){
                case "行为":
                    sensorCollection.setSensorID(1);
                    break;
                case "温度":
                    sensorCollection.setSensorID(2);
                    break;
                case "湿度":
                    sensorCollection.setSensorID(3);
                    break;
                case "光强":
                    sensorCollection.setSensorID(4);
                    break;
                case "霍尔":
                    sensorCollection.setSensorID(5);
                    break;
                case "门磁电量低":
                    sensorCollection.setSensorID(6);
                case "WIFI故障":
                    sensorCollection.setSensorID(252);
                    break;
                case "请求时间":
                    sensorCollection.setSensorID(254);
                    break;
                case "紧急报警":
                    sensorCollection.setSensorID(255);
            }
        }
        sensorCollection.setSensorData(senSorDto.getSensorData());
        sensorCollection.setTime(senSorDto.getTime());

        if(oldMan!=null&&oldMan.getGatewayID()!=null&&!oldMan.getGatewayID().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(oldMan.getGatewayID().length()>=4&&isBinary(oldMan.getGatewayID())){
                //查询以二进制的方式
                oldMan.setGatewayID(Integer.valueOf(oldMan.getGatewayID(),2).toString());
            }
        }
        if(oldMan!=null&&oldMan.getSegment()!=null&&!oldMan.getSegment().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(oldMan.getSegment().length()>=4&&isBinary(oldMan.getSegment())){
                //查询以二进制的方式
                oldMan.setSegment(Integer.valueOf(oldMan.getSegment(),2).toString());
            }
        }
        if(senSorDto.getSensorId()!=null&&!senSorDto.getSensorId().equals("")){
            //简单判断 用户输入的是二进制还是十进制   十进制1-16  长度>=4且只有0和1 则判断为二进制
            if(senSorDto.getSensorId().length()>=4&&isBinary(senSorDto.getSensorId())){
                //查询以二进制的方式
                sensorCollection.setSensorPointID(Integer.valueOf(senSorDto.getSensorId(),2).toString());
            }else{
                sensorCollection.setSensorPointID(senSorDto.getSensorId());
            }
        }
        OldMan oldManSearch = new OldMan();
        List<OldMan> oldManSearchs = new ArrayList<>();
        if(oldMan!=null&&oldMan.getOid()!=null)
            oldManSearch=dataDao.getOldManByOid(oldMan.getOid());
        if(oldMan!=null&&oldMan.getSegment()!=null&&!oldMan.getSegment().equals(""))
            oldManSearchs =dataDao.getOldManBySegment(oldMan.getSegment());
        List<SecSensorCollection> sensorCollections= rawDataSecDao.datagridSensor(page,sensorCollection,oldManSearch,oldManSearchs);
        List<SenSorDto> sensors=sensorTransfor.sensorTransferSec(sensorCollections);
        return sensors;
    }


    @Override
    public Long getuserDatagridTotal(UsersCollection usersCollection) {
        return rawDataSecDao.getuserDatagridTotal(usersCollection);
    }

    @Override
    public List<UsersCollection> datagridUser(PageHelper page, UsersCollection usersCollection) {
        page.setStart((page.getPage() - 1) * page.getRows());
        return rawDataSecDao.datagridUser(page,usersCollection);
    }

    @Override
    public Long getsensorPointDatagridTotal(SensorPointCollection sensorPointCollection) {
        return rawDataSecDao.getsensorPointDatagridTotal(sensorPointCollection);
    }

    @Override
    public List<SensorPointCollection> datagridSensorPoint(PageHelper page, SensorPointCollection sensorPointCollection) {
        page.setStart((page.getPage() - 1) * page.getRows());
        return rawDataSecDao.datagridSensorPoint(page,sensorPointCollection);
    }
}
