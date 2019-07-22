package com.warn.sensordata.dao;

import com.warn.dto.PageHelper;
import com.warn.entity.OldMan;
import com.warn.mongodb.model.SensorCollection;
import com.warn.sensordata.model.SecSensorCollection;
import com.warn.sensordata.model.SensorPointCollection;
import com.warn.sensordata.model.UsersCollection;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * Created by admin on 2017/4/8.
 */

public interface RawDataSecDao {

    Long getsensorDatagridTotal(SensorCollection sensorCollection, OldMan oldMan,List<OldMan> oldMen);

    List<SecSensorCollection> datagridSensor(PageHelper page, SensorCollection sensorCollection, OldMan oldMan, List<OldMan> oldMen);

    MongoTemplate getMongoTemplate();

    List<UsersCollection> datagridUser(PageHelper page, UsersCollection usersCollection);

    Long getuserDatagridTotal(UsersCollection usersCollection);

    Long getsensorPointDatagridTotal(SensorPointCollection sensorPointCollection);

    List<SensorPointCollection> datagridSensorPoint(PageHelper page, SensorPointCollection sensorPointCollection);
}
