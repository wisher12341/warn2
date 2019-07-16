package com.warn.service.impl;

import com.warn.dto.SenSorDto;
import com.warn.entity.OldMan;
import com.warn.mongodb.dao.RawDataDao;
import com.warn.dto.PageHelper;

import com.warn.mongodb.model.SensorCollection;
import com.warn.mongodb.model.SensorPointCollection;
import com.warn.mongodb.model.UsersCollection;
import com.warn.service.RawDataService;
import com.warn.transfor.SensorTransfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/4/8.
 */
@Service
public class RawDataServiceImpl implements RawDataService {

    @Autowired
    RawDataDao rawDataDao;
    @Autowired
    SensorTransfor sensorTransfor;

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

        return rawDataDao.getsensorDatagridTotal(sensorCollection,oldMan);
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

        List<SensorCollection> sensorCollections=rawDataDao.datagridSensor(page,sensorCollection,oldMan);
        List<SenSorDto> sensors=sensorTransfor.sensorTransfor(sensorCollections);
        return sensors;
    }


    @Override
    public Long getuserDatagridTotal(UsersCollection usersCollection) {
        return rawDataDao.getuserDatagridTotal(usersCollection);
    }

    @Override
    public List<UsersCollection> datagridUser(PageHelper page, UsersCollection usersCollection) {
        page.setStart((page.getPage() - 1) * page.getRows());
        return rawDataDao.datagridUser(page,usersCollection);
    }

    @Override
    public Long getsensorPointDatagridTotal(SensorPointCollection sensorPointCollection) {
        return rawDataDao.getsensorPointDatagridTotal(sensorPointCollection);
    }

    @Override
    public List<SensorPointCollection> datagridSensorPoint(PageHelper page, SensorPointCollection sensorPointCollection) {
        page.setStart((page.getPage() - 1) * page.getRows());
        return rawDataDao.datagridSensorPoint(page,sensorPointCollection);
    }
}
