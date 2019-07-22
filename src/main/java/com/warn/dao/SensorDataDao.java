package com.warn.dao;

import com.warn.dto.PageHelper;
import com.warn.entity.SensorData;
import com.warn.mongodb.model.SensorCollection;
import com.warn.sensordata.model.SecSensorCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SensorDataDao {
    List<SensorData> getSensorDatas();

    Long getsensorDatagridTotal(@Param("sensor")SensorData sensorData);

    List<SecSensorCollection> getSensorData(@Param("sensor")SensorData sensorData, @Param("page")PageHelper page);

    List<SensorCollection> findByTime(@Param("gatewayIds") List<Integer> gatewayIds,@Param("startTime") Long startTime,@Param("endTime") Long endTime);
}
