package com.warn.controller;


import com.warn.dto.DataGrid;
import com.warn.dto.Result;
import com.warn.dto.Sms_template;
import com.warn.dto.SysSetDTO;
import com.warn.entity.SmsOrder;
import com.warn.entity.SmsSendEntity;
import com.warn.service.SmsService;
import com.warn.sms.SMSConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by admin on 2017/5/30.
 *  短信
 */
@Controller
@RequestMapping(value = "/sms")
public class SmsController {

    @Autowired
    SmsService smsService;


    /**
     * 跳转至设置界面
     * @return
     */
    @RequestMapping(value = "/set",method = RequestMethod.GET)
    public String set(){
        return "sms/set";
    }

    /**
     * 跳转至模板界面
     * @return
     */
    @RequestMapping(value = "/template",method = RequestMethod.GET)
    public String template(Model model){
        Sms_template sms_template=smsService.getSmsTemplate();
        model.addAttribute("constants",sms_template);
        return "sms/template";
    }

    /**
     * 修改模板参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editTemplate",method = RequestMethod.POST)
    public Result editTemplate(@RequestParam String name,@RequestParam String data){
        smsService.editTemplate(name,data);
        return new Result(true);
    }

    /**
     * 接收短信的手机信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(SmsSendEntity smsSendEntity) {
        DataGrid dg = new DataGrid();
        List<SmsSendEntity> smsSendEntities = smsService.datagridSmsSendEntity(smsSendEntity);
        dg.setRows(smsSendEntities);
        return dg;
    }
    /**
     * 短信级别的信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagridOrder", method = RequestMethod.POST)
    public DataGrid datagridOrder() {
        DataGrid dg = new DataGrid();
        List<SmsOrder> smsOrders = smsService.datagridSmsOrder();
        dg.setRows(smsOrders);
        return dg;
    }


    /**
     * 获得短信总开关
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getSMSSwitch", method = RequestMethod.GET)
    public Result getSMSSwitch() {
        SysSetDTO sysSetDTO=smsService.getSMSSwitch();
        return new Result(true,sysSetDTO);
    }

    /**
     * 更改短信的总开关  系统管理人员控制
     * @param openSys  1打开  0关闭   默认关闭
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/smsSysSwitch",method = RequestMethod.POST)
    public Result smsSysSwitch(@RequestParam Integer openSys){
        smsService.smsSysSwitch(openSys);
        return new Result(true);
    }


    /**
     * 添加级别信息
     * @param smsOrder
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addSmsOrder",method = RequestMethod.POST)
    public Result addSmsOrder(SmsOrder smsOrder){
        smsService.addSmsOrder(smsOrder);
        return new Result(true);
    }

    /**
     * 添加手机信息
     * @param smsSendEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addSmsSendEntity",method = RequestMethod.POST)
    public Result addSmsSendEntity(SmsSendEntity smsSendEntity){
        smsService.addPhone(smsSendEntity);
        return new Result(true);
    }

    /**
     * 修改级别信息
     * @param smsOrder
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editSmsOrder",method = RequestMethod.POST)
    public Result editSmsOrder(SmsOrder smsOrder){
        smsService.editSmsOrder(smsOrder);
        return new Result(true);
    }

    /**
     * 修改手机信息
     * @param smsSendEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editSmsSendEntity",method = RequestMethod.POST)
    public Result editSmsSendEntity(SmsSendEntity smsSendEntity){
        smsService.editPhone(smsSendEntity);
        return new Result(true);
    }

    /**
     * 删除级别信息
     * @param smsOrder
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteSmsOrder",method = RequestMethod.POST)
    public Result deleteSmsOrder(SmsOrder smsOrder){
        smsService.deleteSmsOrder(smsOrder);
        return new Result(true);
    }

    /**
     * 删除手机信息
     * @param smsSendEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteSmsSendEntity",method = RequestMethod.POST)
    public Result deleteSmsSendEntity(SmsSendEntity smsSendEntity){
        smsService.deletePhone(smsSendEntity);
        return new Result(true);
    }

}
