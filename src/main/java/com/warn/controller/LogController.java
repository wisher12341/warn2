package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.entity.Log;
import com.warn.entity.Outdoor;
import com.warn.entity.User;
import com.warn.service.LogService;
import com.warn.service.OutHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 日志信息管理
 * Created by admin on 2017/4/27.
 */
@Controller
@RequestMapping("/log")
public class LogController {


    @Autowired
    LogService logService;

    /**
     * 跳转至列表页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "log/list";
    }
    /**
     * 跳转至用户列表页面
     * @return
     */
    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String list_user(){
        return "user/log";
    }
    /**
     * 获得日志信息信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page,Log log,HttpSession session) {
        DataGrid dg = new DataGrid();
        dg.setTotal(logService.getDatagridTotal(log,session));
        List<Log> logs = logService.datagridLogData(page, log,session);
        dg.setRows(logs);
        return dg;
    }

    /**
     * 添加日志信息
     * @param log
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addLog",method = RequestMethod.POST)
    public Result addLog(Log log,HttpSession session){
        logService.addLog(log,session);
        return new Result(true);
    }

    /**
     * 修改日志信息
     * @param log
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editLog",method = RequestMethod.POST)
    public Result editLog(Log log,HttpSession session){
        logService.editLog(log,session);
        return new Result(true);
    }

    /**
     * 删除日志信息
     * @param logId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteLog",method = RequestMethod.POST)
    public Result deleteLog(@RequestParam Integer logId){
        logService.deleteLogById(logId);
        return new Result(true);
    }

}
