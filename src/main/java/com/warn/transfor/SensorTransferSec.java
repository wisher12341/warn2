package com.warn.transfor;

import com.warn.dao.DataDao;
import com.warn.dao.EquipDao;
import com.warn.dao.RoomDao;
import com.warn.dto.SenSorDto;
import com.warn.entity.Equipment;
import com.warn.entity.OldMan;
import com.warn.entity.Room;
import com.warn.mongodb.model.SensorCollection;
import com.warn.sensordata.model.SecSensorCollection;
import com.warn.util.DynamicDataSourceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 传感器信息 dao到dto的转换
 * Created by netlab606 on 2017/5/20.
 */
@Service
public class SensorTransferSec {

    @Autowired
    DataDao dataDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    EquipDao equipDao;

    public List<SenSorDto> sensorTransferSec(List<SecSensorCollection> sensorCollections){
        DynamicDataSourceHolder.setDataSource("defaultDataSource");
        List<SenSorDto> senSorDtos=new ArrayList<>();
        for(SecSensorCollection sensorCollection:sensorCollections){
            SenSorDto senSorDto=new SenSorDto();
            senSorDto.setSensorId(sensorCollection.getSensorPointID());
            senSorDto.setSensorData(sensorCollection.getSensorData());
            //senSorDto.setTime(timeDel(sensorCollection));
            senSorDto.setTime(sensorCollection.getTime());
            senSorDto.setGatewayID(sensorCollection.getGatewayID());
            OldMan oldMan=dataDao.getOldManByGatewayID(sensorCollection.getGatewayID());
            if(oldMan==null){
                oldMan=new OldMan();
            }
            senSorDto.setOldMan(oldMan);
            //根据网关ID（人员ID）和设备ID 得到房间
            Room room=roomDao.getRoomByGateWayId_SensorId(sensorCollection.getGatewayID(),senSorDto.getSensorId());
            if(room==null){
                room=new Room();
            }
            senSorDto.setRoom(room);
            Equipment equipment=equipDao.getEquipmentByGateWayId_SensorId(sensorCollection.getGatewayID(),senSorDto.getSensorId());
            if(equipment==null){
                equipment=new Equipment();
            }
            senSorDto.setEquipment(equipment);
            switch (sensorCollection.getSensorID()){
                case 1:
                    senSorDto.setSensorType("行为");
                    break;
                case 2:
                    senSorDto.setSensorType("温度");
                    break;
                case 3:
                    senSorDto.setSensorType("湿度");
                    break;
                case 4:
                    senSorDto.setSensorType("光强");
                    break;
                case 5:
                    senSorDto.setSensorType("霍尔");
                    break;
                case 6:
                    senSorDto.setSensorType("门磁电量低");
                case 252:
                    senSorDto.setSensorType("WIFI故障");
                    break;
                case 254:
                    senSorDto.setSensorType("请求时间");
                    break;
                case 255:
                    senSorDto.setSensorType("紧急报警");
            }
            senSorDtos.add(senSorDto);
        }
        return senSorDtos;
    }

    public String timeDel(SensorCollection sensorCollection){
        String time="";
        time+=sensorCollection.getYear()+"-";
        time+=((sensorCollection.getMonth().length() == 1) ? "0" + sensorCollection.getMonth() : sensorCollection.getMonth())+"-";
        time+=((sensorCollection.getDay().length() == 1) ? "0" + sensorCollection.getDay() : sensorCollection.getDay())+"  ";
        time+=((sensorCollection.getHour().length() == 1) ? "0" + sensorCollection.getHour() : sensorCollection.getHour())+":";
        time+=((sensorCollection.getMinute().length() == 1) ? "0" + sensorCollection.getMinute() : sensorCollection.getMinute())+":";
        time+=((sensorCollection.getSecond().length() == 1) ? "0" + sensorCollection.getSecond() : sensorCollection.getSecond());
        return time;
    }
}
