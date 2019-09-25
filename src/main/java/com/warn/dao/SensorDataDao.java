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

    Long getsensorDatagridTotal(@Param("sensor")SensorData sensorData,@Param("startId") Integer startId,@Param("endId") Integer endId,@Param("selectId")Integer selectId);//二代现在的数据

    Long getsensorDatagridTotal_old(@Param("sensor")SensorData sensorData,@Param("startId") Integer startId,@Param("endId") Integer endId,@Param("selectId")Integer selectId);//二代迁移数据

    Long getsensorDatagridTotal_fg(@Param("sensor")SensorData sensorData,@Param("startId") Integer startId,@Param("endId") Integer endId,@Param("selectId")Integer selectId);//一代的现在的数据

    List<SecSensorCollection> getSensorData(@Param("sensor")SensorData sensorData, @Param("page")PageHelper page,@Param("startId") Integer startId,@Param("endId") Integer endId,@Param("selectId")Integer selectId);//二代现在

    List<SecSensorCollection> getSensorData_old(@Param("sensor")SensorData sensorData, @Param("page")PageHelper page,@Param("startId") Integer startId,@Param("endId") Integer endId,@Param("selectId")Integer selectId);//二代迁移

    List<SecSensorCollection> getSensorData_fg(@Param("sensor")SensorData sensorData, @Param("page")PageHelper page,@Param("startId") Integer startId,@Param("endId") Integer endId,@Param("selectId")Integer selectId);//一代数据

    List<SensorCollection> findByTime(@Param("gatewayIds") List<Integer> gatewayIds,@Param("startTime") Long startTime,@Param("endTime") Long endTime);//二代数据

    List<SensorCollection> findByTime_fg(@Param("gatewayIds") List<Integer> gatewayIds,@Param("startTime") Long startTime,@Param("endTime") Long endTime);//一代数据

    List<SensorCollection> findToStatisticBeta(@Param("sensor")SensorData sensorData, @Param("startTime")Long startTime,@Param("endTime")Long endTime);

    List<SensorCollection> findToStatisticBetaFt(@Param("sensor")SensorData sensorData, @Param("startTime")Long startTime,@Param("endTime")Long endTime);//43专用

    SensorCollection getDateEndRecord(String date);//获得前一天的最后一条记录

    void addDateRecord(SensorCollection sensorCollection);

    SensorCollection getDateEndRecord_fg(String date);

    void addDateRecord_fg(SensorCollection sensorCollection);

    void addDateRecordOA(List<SensorCollection> sensorCollections);

    void migrateDate(List<SensorData> sensorDatas);

    List<SensorCollection> findByTimeOld(@Param("gatewayId") Integer gatewayId,@Param("startTime") Long startTime,@Param("endTime") Long endTime);

    SensorCollection getDateGateway(SensorCollection sensorCollection);

    List<SensorCollection> getDateEndGateway(String date);

    List<SensorCollection> getDateRecord_old(String date);

    List<SensorCollection> getDateRecordOA();

    void addDateGateway(SensorCollection sensorCollection);

    List<SensorCollection> getDateRecord(String date);//获得日日期和索引的关系

    List<SensorCollection> getDateRecord_fg(String date);
}
