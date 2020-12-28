package com.warn.service.impl;


import com.warn.controller.SystemController;
import com.warn.mongodb.model.SensorCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/spring-*.xml"})
public class SensorServiceImplTest {

    @Autowired
    private SensorServiceImpl sensorService;

    @Test
    public void checkMoveData() throws Exception {
        //刘大爷
        LocalDateTime localDateTime=LocalDateTime.now();
        SensorCollection sensorCollection=new SensorCollection();
        sensorCollection.setId("4200429");
        sensorCollection.setSensorID(0);
        sensorCollection.setGatewayID(20);
        sensorCollection.setDate("2020-12-28");
        sensorCollection.setSensorData(15);
        sensorCollection.setSensorPointID("8");
        sensorCollection.setHour(String.valueOf(localDateTime.getHour()));
        sensorCollection.setMinute(String.valueOf(localDateTime.getMinute()));
        sensorCollection.setSecond(String.valueOf(localDateTime.getSecond()));
        List<SensorCollection> sensorCollections=new ArrayList<>();
        sensorCollections.add(sensorCollection);


        if(SystemController.logger==null){
            SystemController.logger= Logger.getLogger("com.horstmann.corejava");
            FileHandler fh;
            try {
                File f=new File("log.txt");
                if(!f.exists()){
                    f.createNewFile();
                }
                SystemController.absolutePath = f.getAbsolutePath();
//                        System.out.println(SystemController.absolutePath);
                fh = new FileHandler("log.txt",true);
                SystemController.logger.addHandler(fh);//日志输出文件
                fh.setFormatter(new SimpleFormatter());//输出格式
            } catch (SecurityException e) {
                SystemController.logger.log(Level.SEVERE, "安全性错误", e);
            } catch (IOException e) {
                System.out.println("IO异常");
                SystemController.logger.log(Level.SEVERE, "读取文件日志错误", e);
            }
        }

        sensorService.checkMoveData(sensorCollections);

        Thread.sleep(60*60*1000);
    }

}
