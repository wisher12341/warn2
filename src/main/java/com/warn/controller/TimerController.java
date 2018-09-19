package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.dto.TimeDto;
import com.warn.entity.OldMan;
import com.warn.entity.Room;
import com.warn.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.util.List;

/**
 * 管理各个老人的定时器
 * Created by admin on 2017/4/30.
 */
@Controller
@RequestMapping(value = "/timer")
public class TimerController {

    @Autowired
    TimerService timerService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "threshold/switch";
    }


    /**
     * 获得预警机制开关表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page,OldMan oldMan) {
        //防止重新启动等等， 已添加的老人没有预警开关
        timerService.addSwitch();
        DataGrid dg = timerService.getDatagrid(page,oldMan);
        return dg;
    }

    /**
     * 开启 关闭 预警机制
     * @param timeDto
     * @param oid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateTimer",method = RequestMethod.POST)
    public Result updateTimer(TimeDto timeDto,@RequestParam("oid")Integer oid){
        timeDto.getOldMan().setOid(oid);
        timerService.updateTimer(timeDto);
        return new Result(true);
    }

}
