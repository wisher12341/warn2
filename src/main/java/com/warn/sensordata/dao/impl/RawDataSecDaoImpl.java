package com.warn.sensordata.dao.impl;

import com.warn.dao.DataDao;
import com.warn.dao.EquipDao;
import com.warn.dao.SensorDataDao;
import com.warn.dto.PageHelper;
import com.warn.entity.OldMan;
import com.warn.entity.SensorData;
import com.warn.sensordata.dao.RawDataSecDao;
import com.warn.mongodb.model.SensorCollection;
import com.warn.sensordata.model.SecSensorCollection;
import com.warn.sensordata.model.SensorPointCollection;
import com.warn.sensordata.model.UsersCollection;
import com.warn.util.DynamicDataSourceHolder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/5/6.
 */
@Repository
public class RawDataSecDaoImpl implements RawDataSecDao {


    @Autowired
    @Resource(name = "secondaryMongoTemplate")
    private MongoTemplate mongoTemplate;
    @Autowired
    EquipDao equipDao;
    @Autowired
    DataDao dataDao;
    @Autowired
    SensorDataDao sensorDataDao;

    @Override
    public Long getsensorDatagridTotal(SensorCollection sensorCollection, OldMan oldMan,List<OldMan> oldMen) {
        SensorData sensorData = new SensorData();
        List<Integer> gateways=new ArrayList<>();
        if(oldMan!=null&&oldMan.getGatewayID()!=null&&!oldMan.getGatewayID().equals("")){
//            List<Equipment> equipments=equipDao.getAllEquipByOldManId(oldMan.getOid());
//            List<Integer> eids=new ArrayList<>();
//            for(Equipment equipment:equipments){
//                eids.add(Integer.parseInt(equipment.getEid()));
//            }
            gateways.add(Integer.parseInt(oldMan.getGatewayID()));
//            in(eids).and("gatewayID").is(oldMan.getOid());
        }
        if(oldMan.getOid() !=null)
            gateways.add(Integer.parseInt(oldMan.getGatewayID()));
        if(oldMen.size() != 0){
            for(OldMan oldManSearch:oldMen) {
                gateways.add(Integer.parseInt(oldManSearch.getGatewayID()));
            }
        }
        if(sensorCollection.getSensorID()!=null)
            sensorData.setSensorID(sensorCollection.getSensorID());
        if(sensorCollection.getSensorPointID()!=null&&!sensorCollection.getSensorPointID().equals(""))
            sensorData.setSensorPointID(Integer.parseInt(sensorCollection.getSensorPointID()));
        if(sensorCollection.getTime()!=null&&!sensorCollection.getTime().equals(""))
            sensorData.setDate(sensorCollection.getTime());
//        query.with(new Sort(Sort.Direction.DESC, "_id"));
//        List<SensorCollection> sensorCollections = getMongoTemplate().find(query.limit(2000), SensorCollection.class);
//        return (long)sensorCollections.size();
        sensorData.setGatewayIDs(gateways);
        DynamicDataSourceHolder.setDataSource("sensorDataSource");
        return sensorDataDao.getsensorDatagridTotal(sensorData);
}

    @Override
    public List<SecSensorCollection> datagridSensor(PageHelper page, SensorCollection sensorCollection, OldMan oldMan, List<OldMan> oldMen) {
        SensorData sensorData = new SensorData();
        List<Integer> gateways=new ArrayList<>();
        if(oldMan!=null&&oldMan.getGatewayID()!=null&&!oldMan.getGatewayID().equals(""))
            gateways.add(Integer.parseInt(oldMan.getGatewayID()));
//            List<Equipment> equipments=equipDao.getAllEquipByOldManId(oldMan.getOid());
//            List<Integer> eids=new ArrayList<>();
//            for(Equipment equipment:equipments){
//                eids.add(Integer.parseInt(equipment.getEid()));
//            }
//            criteria=Criteria.where("sensorPointID").in(eids);
        if(oldMan.getOid()!=null){
            gateways.add(Integer.parseInt(oldMan.getGatewayID()));
        }
        if(oldMen.size() != 0){
            for(OldMan oldManSearch:oldMen) {
                gateways.add(Integer.parseInt(oldManSearch.getGatewayID()));
            }
        }
        if(sensorCollection.getSensorID()!=null){
            sensorData.setSensorID(sensorCollection.getSensorID());
        }
        if(sensorCollection.getSensorPointID()!=null&&!sensorCollection.getSensorPointID().equals("")){
            sensorData.setSensorPointID(Integer.parseInt(sensorCollection.getSensorPointID()));
        }
        if(sensorCollection.getTime()!=null&&!sensorCollection.getTime().equals("")){
            sensorData.setDate(sensorCollection.getTime());
//            String[] t=sensorCollection.getTime().split("-");
//            Integer year=Integer.parseInt(t[0]);
//            Integer month=Integer.parseInt(t[1]);
//            Integer day=Integer.parseInt(t[2]);
//            if(criteria==null){
//                criteria=Criteria.where("year").is(year).and("month").is(month).and("day").is(day);
//            }else{
//                criteria=criteria.and("year").is(year).and("month").is(month).and("day").is(day);;
//            }
        }
        sensorData.setGatewayIDs(gateways);
        page.setSort("id");
        page.setOrder("desc");
        DynamicDataSourceHolder.setDataSource("sensorDataSource");
        return sensorDataDao.getSensorData(sensorData,page);
//        query.with(new Sort(Sort.Direction.DESC, "_id"));
//        return getMongoTemplate().find(query.skip(page.getStart()).limit(page.getRows()), SensorCollection.class);
    }


    @Override
    public List<UsersCollection> datagridUser(PageHelper page, UsersCollection usersCollection) {
        Query query=null;
        Criteria criteria = null;
        if(usersCollection.getName()!=null&&!usersCollection.getName().equals("")){
            criteria=Criteria.where("name").is(usersCollection.getName());
        }
        if(usersCollection.getGatewayID()!=null&&!usersCollection.getGatewayID().equals("")){
            if(criteria==null){
                criteria=Criteria.where("gatewayID").is(Integer.parseInt(usersCollection.getGatewayID()));
            }else{
                criteria=criteria.and("gatewayID").is(Integer.parseInt(usersCollection.getGatewayID()));
            }
        }
        if(criteria==null){
            query=new Query();
        }else{
            query=new Query(criteria);
        }
        return getMongoTemplate().find(query.skip(page.getStart()).limit(page.getRows()),UsersCollection.class);
    }

    @Override
    public Long getuserDatagridTotal(UsersCollection usersCollection) {
        Query query=null;
        Criteria criteria = null;
        if(usersCollection.getName()!=null&&!usersCollection.getName().equals("")){
            criteria=Criteria.where("name").is(usersCollection.getName());
        }
        if(usersCollection.getGatewayID()!=null&&!usersCollection.getGatewayID().equals("")){
            if(criteria==null){
                criteria=Criteria.where("gatewayID").is(Integer.parseInt(usersCollection.getGatewayID()));
            }else{
                criteria=criteria.and("gatewayID").is(Integer.parseInt(usersCollection.getGatewayID()));
            }
        }
        if(criteria==null){
            query=new Query();
        }else{
            query=new Query(criteria);
        }
        return getMongoTemplate().count(query,UsersCollection.class);
    }

    @Override
    public Long getsensorPointDatagridTotal(SensorPointCollection sensorPointCollection) {
        Query query=null;
        Criteria criteria = null;

//        if(sensorPointCollection.getSensorPointID()!=null&&!sensorPointCollection.getSensorPointID().equals("")){
//            criteria=Criteria.where("name").is(Integer.parseInt(sensorPointCollection.getSensorPointID()));
//        }
//        if(sensorPointCollection.getGatewayID()!=null&&!sensorPointCollection.getGatewayID().equals("")){
//            if(criteria==null){
//                criteria=Criteria.where("gatewayID").is(Integer.parseInt(sensorPointCollection.getGatewayID()));
//            }else{
//                criteria=criteria.and("gatewayID").is(Integer.parseInt(sensorPointCollection.getGatewayID()));
//            }
//        }
//        if(criteria==null){
//            query=new Query();
//        }else{
//            query=new Query(criteria);
//        }
        //只有设备ID
        if(sensorPointCollection.getSensorPointID()!=null&&!sensorPointCollection.getSensorPointID().equals("")&&(sensorPointCollection.getGatewayID()==null||sensorPointCollection.getGatewayID().equals(""))){
            criteria=Criteria.where("sensorPointID").is(Integer.parseInt(sensorPointCollection.getSensorPointID()));
            query=new Query(criteria);
            return getMongoTemplate().count(query,SensorPointCollection.class);
        }
        //只有网关ID
        if(sensorPointCollection.getGatewayID()!=null&&!sensorPointCollection.getGatewayID().equals("")&&(sensorPointCollection.getSensorPointID()==null||sensorPointCollection.getSensorPointID().equals(""))){
            criteria=Criteria.where("gatewayID").is(Integer.parseInt(sensorPointCollection.getGatewayID()));
            query=new Query(criteria);
            UsersCollection usersCollection=getMongoTemplate().findOne(query,UsersCollection.class);
            if(usersCollection!=null){
                //objectID类型的 不能以字符串的类型进行查询
                criteria=Criteria.where("gatewayObjID").is(new ObjectId(usersCollection.getId()));
                query=new Query(criteria);
                return getMongoTemplate().count(query,SensorPointCollection.class);
            }else{
                return 0L;
            }
        }
        //都有
        if(sensorPointCollection.getSensorPointID()!=null&&!sensorPointCollection.getSensorPointID().equals("")&&sensorPointCollection.getGatewayID()!=null&&!sensorPointCollection.getGatewayID().equals("")){
            criteria=Criteria.where("gatewayID").is(Integer.parseInt(sensorPointCollection.getGatewayID()));
            query=new Query(criteria);
            UsersCollection usersCollection=getMongoTemplate().findOne(query,UsersCollection.class);
            if(usersCollection!=null){
                criteria=Criteria.where("gatewayObjID").is(new ObjectId(usersCollection.getId())).and("sensorPointID").is(Integer.parseInt(sensorPointCollection.getSensorPointID()));
                query=new Query(criteria);
                return getMongoTemplate().count(query,SensorPointCollection.class);
            }else{
                return 0L;
            }
        }
        query=new Query();
        return getMongoTemplate().count(query,SensorPointCollection.class);
    }

    @Override
    public List<SensorPointCollection> datagridSensorPoint(PageHelper page, SensorPointCollection sensorPointCollection) {
        Query query=null;
        Criteria criteria = null;
//        if(criteria==null){
//            query=new Query();
//        }else{
//            query=new Query(criteria);
//        }
//        List<SensorPointCollection> sensorPointCollections=getMongoTemplate().find(query.skip(page.getStart()).limit(page.getRows()),SensorPointCollection.class);
//        for(SensorPointCollection sensorPointCollection1:sensorPointCollections){
//            Criteria criteria1=Criteria.where("id").is(sensorPointCollection1.getGatewayObjID());
//            Query query1=new Query(criteria1);
//            UsersCollection usersCollection=getMongoTemplate().findOne(query1,UsersCollection.class);
//            if(usersCollection!=null) {
//                sensorPointCollection1.setGatewayID(usersCollection.getGatewayID());
//            }
//        }
//        return sensorPointCollections;

        //只有设备ID
        if(sensorPointCollection.getSensorPointID()!=null&&!sensorPointCollection.getSensorPointID().equals("")&&(sensorPointCollection.getGatewayID()==null||sensorPointCollection.getGatewayID().equals(""))){
            criteria=Criteria.where("sensorPointID").is(Integer.parseInt(sensorPointCollection.getSensorPointID()));
            query=new Query(criteria);
            List<SensorPointCollection> sensorPointCollections=getMongoTemplate().find(query.skip(page.getStart()).limit(page.getRows()),SensorPointCollection.class);
            for(SensorPointCollection sensorPointCollection1:sensorPointCollections){
                Criteria criteria1=Criteria.where("id").is(sensorPointCollection1.getGatewayObjID());
                Query query1=new Query(criteria1);
                UsersCollection usersCollection=getMongoTemplate().findOne(query1,UsersCollection.class);
                if(usersCollection!=null) {
                   sensorPointCollection1.setGatewayID(usersCollection.getGatewayID());
                }
           }
           return sensorPointCollections;
        }
        //只有网关ID
        if(sensorPointCollection.getGatewayID()!=null&&!sensorPointCollection.getGatewayID().equals("")&&(sensorPointCollection.getSensorPointID()==null||sensorPointCollection.getSensorPointID().equals(""))){
            criteria=Criteria.where("gatewayID").is(Integer.parseInt(sensorPointCollection.getGatewayID()));
            query=new Query(criteria);
            UsersCollection usersCollection=getMongoTemplate().findOne(query,UsersCollection.class);
            if(usersCollection!=null){
                criteria=Criteria.where("gatewayObjID").is(new ObjectId(usersCollection.getId()));
                query=new Query(criteria);
                List<SensorPointCollection> sensorPointCollections=getMongoTemplate().find(query.skip(page.getStart()).limit(page.getRows()),SensorPointCollection.class);
                for(SensorPointCollection sensorPointCollection1:sensorPointCollections){
                    sensorPointCollection1.setGatewayID(sensorPointCollection.getGatewayID());
                }
                return sensorPointCollections;
            }else{
                return null;
            }
        }
        //都有
        if(sensorPointCollection.getSensorPointID()!=null&&!sensorPointCollection.getSensorPointID().equals("")&&sensorPointCollection.getGatewayID()!=null&&!sensorPointCollection.getGatewayID().equals("")){
            criteria=Criteria.where("gatewayID").is(Integer.parseInt(sensorPointCollection.getGatewayID()));
            query=new Query(criteria);
            UsersCollection usersCollection=getMongoTemplate().findOne(query,UsersCollection.class);
            if(usersCollection!=null){
                criteria=Criteria.where("gatewayObjID").is(new ObjectId(usersCollection.getId())).and("sensorPointID").is(Integer.parseInt(sensorPointCollection.getSensorPointID()));
                query=new Query(criteria);
                //最多一个
                List<SensorPointCollection> sensorPointCollections=getMongoTemplate().find(query,SensorPointCollection.class);
                sensorPointCollections.get(0).setGatewayID(sensorPointCollection.getGatewayID());
                return sensorPointCollections;
            }else{
                return null;
            }
        }
        query=new Query();
        List<SensorPointCollection> sensorPointCollections=getMongoTemplate().find(query.skip(page.getStart()).limit(page.getRows()),SensorPointCollection.class);
        for(SensorPointCollection sensorPointCollection1:sensorPointCollections){
            Criteria criteria1=Criteria.where("id").is(sensorPointCollection1.getGatewayObjID());
            Query query1=new Query(criteria1);
            UsersCollection usersCollection=getMongoTemplate().findOne(query1,UsersCollection.class);
            if(usersCollection!=null) {
                sensorPointCollection1.setGatewayID(usersCollection.getGatewayID());
            }
        }
        return sensorPointCollections;
    }


    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }



}
