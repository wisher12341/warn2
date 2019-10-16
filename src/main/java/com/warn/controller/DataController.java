package com.warn.controller;

import com.warn.dao.SensorDataDao;
import com.warn.dto.DataGrid;
import com.warn.dto.Result;
import com.warn.entity.OldMan;
import com.warn.dto.PageHelper;
import com.warn.entity.SensorData;
import com.warn.service.DataService;
import com.warn.util.DynamicDataSourceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 老人信息管理
 * Created by admin on 2017/4/5.
 */
@Controller
@RequestMapping(value = "/data")
public class DataController {

    @Autowired
    DataService dataService;

    @Autowired
    SensorDataDao sensorDataDao;

    /**
     * 跳转至管理员列表页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "data/list";
    }

    /**
     * 跳转至用户列表页面
     * @return
     */
    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String list_user(){
        return "user/table_oldman";
    }

    @ResponseBody
    @RequestMapping(value = "/testDao",method = RequestMethod.GET)
    public Result testDao(){

        List<SensorData> sensorData = sensorDataDao.getSensorDatas();
        return new Result(true,sensorData);
    }


    /**
     * 获得老人信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page,OldMan oldMan) {
        DataGrid dg = new DataGrid();
        dg.setTotal(dataService.getDatagridTotal(oldMan));
        List<OldMan> oldList = dataService.datagridUser(page,oldMan);
        dg.setRows(oldList);
        return dg;
    }

    @ResponseBody
    @RequestMapping(value = "/area/datagrid", method = RequestMethod.POST)
    public DataGrid dataGrid(PageHelper page, OldMan oldMan){
        DataGrid dg = new DataGrid();
        dg.setTotal(dataService.getDatagridTotalNG(oldMan));
        List<OldMan> oldList = dataService.datagridNGUser(page,oldMan);
        dg.setRows(oldList);
        return dg;
    }

//    /**
//     * 跳转至老人信息注册页面
//     */
//    @RequestMapping(value = "/add",method = RequestMethod.GET)
//    public String add_oldman(){
//        return "data/add";
//    }

    /**
     * 添加老人信息
     * @param oldMan
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addOldman",method = RequestMethod.POST)
    public Result addOldman(OldMan oldMan,@RequestParam Integer segmentTwo_Ten){
        return dataService.addOldman(oldMan,segmentTwo_Ten);
    }

    /**
     * 修改老人信息
     * @param oldMan
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editOldman",method = RequestMethod.POST)
    public Result editOldman(OldMan oldMan,@RequestParam Integer segmentTwo_TenE){
        dataService.editOldman(oldMan,segmentTwo_TenE);
        return new Result(true);
    }

    /**
     * 删除老人信息
     * @param oldmanId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteOldman",method = RequestMethod.POST)
    public Result deleteOldman(@RequestParam Integer oldmanId){
        dataService.deleteOldmanById(oldmanId);
        return new Result(true);
    }

    /**
     * 获得所有老人的ID、名称信息、网关ID
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllOldmanID_Name",method = RequestMethod.GET)
    public Result getAllOldmanID_Name(){
        List<OldMan> oldManList=dataService.getAllOldmanID_Name();
        return new Result(true,oldManList);
    }

    @ResponseBody
    @RequestMapping(value = "/getOldmanId_NG" , method = RequestMethod.GET)
    public Result getOldmanID_NG(){
        List<OldMan> oldManList = dataService.getOldmanId_NG();
        return new Result(true,oldManList);
    }

}
