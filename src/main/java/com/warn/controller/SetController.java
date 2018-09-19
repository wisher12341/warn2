package com.warn.controller;


import com.warn.dto.DataGrid;
import com.warn.dto.Result;
import com.warn.dto.SysSetDTO;
import com.warn.service.SetService;
import com.warn.sms.SMSConstants;
import com.warn.util.StaticVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2017/5/30.
 * 系统 配置参数 设置
 */
@Controller
@RequestMapping(value = "/set")
public class SetController {

    @Autowired
    SetService setService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "set/list";
    }



    /**
     * 获得所有系统参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getOpenSys", method = RequestMethod.GET)
    public Result getSysAllSet() {
        SysSetDTO sysSetDTO=setService.getSysAllSet();
        return new Result(true,sysSetDTO);
    }

//    /**
//     * 发送短信的总开关  系统管理人员控制
//     * @param openSys  1打开  0关闭   默认关闭
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/smsSysSwitch",method = RequestMethod.POST)
//    public Result smsSysSwitch(@RequestParam Integer openSys){
//        setService.smsSysSwitch(openSys);
//        return new Result(true);
//    }

//    /**
//     * 更改 未读预警消息多长时间后发短信
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/setSendSMSTime",method = RequestMethod.POST)
//    public Result setSendSMSTime(@RequestParam("time") int time){
//        setService. setSendSMSTime(time);
//        return new Result(true);
//    }


    /**
     * 设置 访问传感器数据库的时间间隔
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setAccessBatabaseTime",method = RequestMethod.POST)
    public Result setAccessBatabaseTime(@RequestParam("time") int time){
        setService.setAccessBatabaseTime(time);
        return new Result(true);
    }

    /**
     * 设置 故障的时间
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setDown",method = RequestMethod.POST)
    public Result setGatewayDown(@RequestParam("time") int time,@RequestParam("name") String name){
        setService.setDown(time,name);
        return new Result(true);
    }


    /**
     * 预警自动 总开关  系统管理人员控制
     * @param timerOpen  1打开  0关闭   默认关闭
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/timerOpen",method = RequestMethod.POST)
    public Result timerOpen(@RequestParam("timerOpen") Integer timerOpen){
        setService.timerOpen(timerOpen);
        return new Result(true);
    }

}
