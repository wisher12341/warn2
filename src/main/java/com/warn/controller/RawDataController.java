package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.SenSorDto;

import com.warn.mongodb.model.SensorPointCollection;
import com.warn.mongodb.model.UsersCollection;
import com.warn.service.RawDataService;
import com.warn.service.RawDataServiceSec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 原始数据管理
 * Created by admin on 2017/4/8.
 */
@Controller
@RequestMapping(value = "/raw")
public class RawDataController {

    @Autowired
    RawDataService rawDataService;

    @Autowired
    RawDataServiceSec rawDataServiceSec;


    /**
     * 跳转至传感器列表页面
     * @return
     */
    @RequestMapping(value = "/sensor",method = RequestMethod.GET)
    public String sensor_list(){
        return "raw/sensor_list";
    }
    /**
     * 获得传感器表格
     * @param senSorDto 条件查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/sensor/datagrid", method = RequestMethod.POST)
    public DataGrid sensordatagrid(PageHelper page, SenSorDto senSorDto) {
        DataGrid dg = new DataGrid();
        dg.setTotal(rawDataService.getsensorDatagridTotal(senSorDto));
        List<SenSorDto> sensorCollections = rawDataService.datagridSensor(page,senSorDto);
        dg.setRows(sensorCollections);
        return dg;
    }

    @RequestMapping(value = "/sensorSec",method = RequestMethod.GET)
    public String sensorSec_list(){
        return "raw/sensorSec_list";
    }
    /**
     * 获得传感器表格
     * @param senSorDto 条件查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/sensorSec/datagrid", method = RequestMethod.POST)
    public DataGrid sensorSecdatagrid(PageHelper page, SenSorDto senSorDto) {
        DataGrid dg = new DataGrid();
        dg.setTotal(rawDataServiceSec.getsensorDatagridTotal(senSorDto));
        List<SenSorDto> sensorCollections = rawDataServiceSec.datagridSensor(page,senSorDto);
        dg.setRows(sensorCollections);
        return dg;
    }


    /**
     * 跳转至user列表页面
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String user_list(){
        return "raw/user_list_noUse";
    }
    /**
     * 获得user表格
     * @param usersCollection 条件查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/user/datagrid", method = RequestMethod.POST)
    public DataGrid userdatagrid(PageHelper page, UsersCollection usersCollection) {
        DataGrid dg = new DataGrid();
        dg.setTotal(rawDataService.getuserDatagridTotal(usersCollection));
        List<UsersCollection> usersCollections = rawDataService.datagridUser(page,usersCollection);
        dg.setRows(usersCollections);
        return dg;
    }


    /**
     * 跳转至sensorPoint列表页面
     * @return
     */
    @RequestMapping(value = "/sensorPoint",method = RequestMethod.GET)
    public String sensorPoint_list(){
        return "raw/sensorPoint_list";
    }
    /**
     * 获得sensorPoint表格
     * @param sensorPointCollection 条件查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/sensorPoint/datagrid", method = RequestMethod.POST)
    public DataGrid sensorPointdatagrid(PageHelper page, SensorPointCollection sensorPointCollection) {
        DataGrid dg = new DataGrid();
        dg.setTotal(rawDataService.getsensorPointDatagridTotal(sensorPointCollection));
        List<SensorPointCollection> sensorPointCollections = rawDataService.datagridSensorPoint(page,sensorPointCollection);
        dg.setRows(sensorPointCollections);
        return dg;
    }
}
