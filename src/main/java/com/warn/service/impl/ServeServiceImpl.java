package com.warn.service.impl;

import com.warn.dao.LogDao;
import com.warn.dao.MapDao;
import com.warn.dto.PageHelper;
import com.warn.entity.*;
import com.warn.service.LogService;
import com.warn.service.SensorService;
import com.warn.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by admin on 2017/5/4.
 */
@Service
public class ServeServiceImpl implements ServeService {


    @Autowired
    SensorService sensorService;

    @Override
    public void beginServe(Serve serve) {
        //地图更新
//        HouseMarker houseMarker=new HouseMarker();
//        houseMarker.setOid(serve.getOid());
//        houseMarker.setStyleIndex(9); //黄色
//        houseMarker.setDetail("服务人员："+serve.getSid()+"&nbsp;&nbsp;服务内容："+serve.getDetai());
//        sensorService.mapUpdate(houseMarker);
        OldMan oldMan=new OldMan(serve.getOid());
        oldMan.setStatus(1);
        sensorService.mapUpdate(oldMan);
    }

    @Override
    public void endServe(Serve serve) {
        //地图更新
//        HouseMarker houseMarker=new HouseMarker();
//        houseMarker.setOid(serve.getOid());
//        houseMarker.setStyleIndex(6); //绿色
//        houseMarker.setDetail("");
//        sensorService.mapUpdate(houseMarker);
        OldMan oldMan=new OldMan(serve.getOid());
        oldMan.setStatus(0);
        sensorService.mapUpdate(oldMan);
    }
}
