package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.entity.Log;
import com.warn.entity.Serve;
import com.warn.service.LogService;
import com.warn.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 老人报警时 派上去家里服务 同时，更改地图的该老人的状态  对外开放接口
 * 黄灯   服务包括  购买的日常服务 以及预警查看服务  都需要专人 通知系统 谁去服务哪个老人
 * Created by admin on 2017/7/14.
 */
@Controller
@RequestMapping("/serve")
public class ServeController {


    @Autowired
    ServeService serveService;

    /**
     * 派人 上门服务
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/beginServe",method = RequestMethod.GET)
    public Result beginServe(Serve serve){
        serveService.beginServe(serve);
        return new Result(true);
    }

    /**
     * 服务结束
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/endServe",method = RequestMethod.GET)
    public Result endServe(Serve serve){
        serveService.endServe(serve);
        return new Result(true);
    }

}
