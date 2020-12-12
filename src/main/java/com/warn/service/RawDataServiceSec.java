package com.warn.service;

import com.warn.dto.PageHelper;
import com.warn.dto.SenSorDto;
import com.warn.sensordata.model.SensorPointCollection;
import com.warn.sensordata.model.UsersCollection;


import java.util.List;

/**
 * Created by admin on 2017/4/8.
 */
public interface RawDataServiceSec {


    Long getsensorDatagridTotal(SenSorDto senSorDto);

    List<SenSorDto> datagridSensor(PageHelper page,SenSorDto senSorDto);

    Long getuserDatagridTotal(UsersCollection usersCollection);

    List<UsersCollection> datagridUser(PageHelper page, UsersCollection usersCollection);

    Long getsensorPointDatagridTotal(SensorPointCollection sensorPointCollection);

    List<SensorPointCollection> datagridSensorPoint(PageHelper page, SensorPointCollection sensorPointCollection);
}
