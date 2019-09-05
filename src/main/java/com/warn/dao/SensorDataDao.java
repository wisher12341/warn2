package com.warn.dao;

import com.sun.tracing.dtrace.ProviderAttributes;
import com.warn.dto.PageHelper;
import com.warn.entity.SensorData;
import com.warn.mongodb.model.SensorCollection;
import com.warn.sensordata.model.SecSensorCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SensorDataDao {
    List<SensorData> getSensorDatas();

    Long getsensorDatagridTotal(@Param("sensor")SensorData sensorData,@Param("startId") Integer startId,@Param("endId") Integer endId,@Param("selectId")Integer selectId);

    List<SecSensorCollection> getSensorData(@Param("sensor")SensorData sensorData, @Param("page")PageHelper page,@Param("startId") Integer startId,@Param("endId") Integer endId,@Param("selectId")Integer selectId);

    List<SensorCollection> findByTime(@Param("gatewayIds") List<Integer> gatewayIds,@Param("startTime") Long startTime,@Param("endTime") Long endTime);

    List<SensorCollection> findToStatisticBeta(@Param("sensor")SensorData sensorData, @Param("startTime")Long startTime,@Param("endTime")Long endTime);

    List<SensorCollection> findToStatisticBetaFt(@Param("sensor")SensorData sensorData, @Param("startTime")Long startTime,@Param("endTime")Long endTime);//43专用

    SensorCollection getDateEndRecord(String date);

    void addDateRecord(SensorCollection sensorCollection);

    void migrateDate(List<SensorData> sensorDatas);

    List<SensorCollection> findByTimeOld(@Param("gatewayId") Integer gatewayId,@Param("startTime") Long startTime,@Param("endTime") Long endTime);

    SensorCollection getDateGateway(SensorCollection sensorCollection);

    List<SensorCollection> getDateEndGateway(String date);

    void addDateGateway(SensorCollection sensorCollection);

    List<SensorCollection> getDateRecord(String date);

}
