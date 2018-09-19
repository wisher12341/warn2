package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.entity.OldMan;
import com.warn.entity.WarnData;
import com.warn.service.WarnHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 预警历史信息管理
 * Created by admin on 2017/4/27.
 */
@Controller
@RequestMapping("/warnHistory")
public class WarnHistoryController {


    @Autowired
    WarnHistoryService warnHistoryService;

    //获取 未读消息的数量
    @RequestMapping(value = "/getNoReadSum",method = RequestMethod.GET)
    @ResponseBody
    public Result getNoReadSum(){
        long total=warnHistoryService.getNoReadSum();
        return new Result(true,total);
    }

    //标记 消息已读
    @RequestMapping(value = "/messageRead",method = RequestMethod.POST)
    @ResponseBody
    public Result messageRead(@RequestParam("wdid")Integer wdid){
        warnHistoryService.messageRead(wdid);
        return new Result(true);
    }


    /**
     * 跳转至列表页面
     * @return
     */
    @RequestMapping(value = "/warn",method = RequestMethod.GET)
    public String list(){
        return "history/warn";
    }


    /**
     * 跳转至用户列表页面
     * @return
     */
    @RequestMapping(value = "/user/warn",method = RequestMethod.GET)
    public String list_user(){
        return "user/warn";
    }
    /**
     * 获得预警历史信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page,WarnData warnData,HttpServletRequest request) {
        if(request.getSession().getAttribute("noreadW")!=null&&request.getSession().getAttribute("noreadW").equals("yes")){
            //是从右上角的未读消息跳转过来
            warnData.setReadW("否");
            request.getSession().removeAttribute("noreadW");
        }
        DataGrid dg = new DataGrid();
        dg.setTotal(warnHistoryService.getDatagridTotal(warnData));
        List<WarnData> warnDatas = warnHistoryService.datagridWarnData(page, warnData);
        dg.setRows(warnDatas);
        return dg;
    }

    /**
     * 根据id获得某一条预警消息的详细内容
     * @param wdid
     * @return
     */
    @RequestMapping(value = "/getMessage",method = RequestMethod.POST)
    @ResponseBody
    public Result getMessage(@RequestParam("wdid")Integer wdid){
        String data=warnHistoryService.getMessageByWdid(wdid);
        warnHistoryService.messageRead(wdid);
        return new Result(true,data);
    }


    /**
     * 根据id 将紧急报警  设置为已读
     * @param wdid
     * @return
     */
    @RequestMapping(value = "/urgencyRead",method = RequestMethod.POST)
    @ResponseBody
    public Result urgencyRead(@RequestParam("wdid")Integer wdid){
        warnHistoryService.urgencyRead(wdid);
        return new Result(true);
    }

}
