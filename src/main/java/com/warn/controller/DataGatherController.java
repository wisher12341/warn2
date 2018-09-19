package com.warn.controller;

import com.warn.dto.DataGatherDto;
import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.entity.OldMan;
import com.warn.service.DataGatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 老人、房间、设备信息汇总
 * Created by netlab606 on 2017/5/25.
 */
@Controller
@RequestMapping(value = "/gather")
public class DataGatherController {


    @Autowired
    DataGatherService dataGatherService;

    /**
     * 跳转至后台页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "gather/list";
    }


    /**
     * 跳转至前台页面
     * @return
     */
    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String list_user(){
        return "user/gather";
    }


    /**
     * 获得汇总信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page, DataGatherDto dataGatherDto) {
        DataGrid dg = new DataGrid();
        dg.setTotal(dataGatherService.getDatagridTotal(dataGatherDto));
        List<DataGatherDto> dataGatherDtos = dataGatherService.datagridData(page,dataGatherDto);
        dg.setRows(dataGatherDtos);
        return dg;
    }
}
