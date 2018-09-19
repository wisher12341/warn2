package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.entity.Outdoor;
import com.warn.entity.WarnData;
import com.warn.service.OutHistoryService;
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
 * 出门历史信息管理
 * Created by admin on 2017/4/27.
 */
@Controller
@RequestMapping("/outHistory")
public class OutHistoryController {


    @Autowired
    OutHistoryService outHistoryService;

    //获取 未读消息的数量
    @RequestMapping(value = "/getNoReadSum",method = RequestMethod.GET)
    @ResponseBody
    public Result getNoReadSum(){
        long total=outHistoryService.getNoReadSum();
        return new Result(true,total);
    }

    //标记 消息已读
    @RequestMapping(value = "/messageRead",method = RequestMethod.POST)
    @ResponseBody
    public Result messageRead(@RequestParam("odid")Integer odid){
        outHistoryService.messageRead(odid);
        return new Result(true);
    }


    /**
     * 跳转至列表页面
     * @return
     */
    @RequestMapping(value = "/out",method = RequestMethod.GET)
    public String list(){
        return "history/out";
    }

    /**
     * 跳转至用户列表页面
     * @return
     */
    @RequestMapping(value = "/user/out",method = RequestMethod.GET)
    public String list_user(){
        return "user/out";
    }

    /**
     * 获得出门历史信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page,Outdoor outdoor,HttpServletRequest request) {
        if(request.getSession().getAttribute("noreadO")!=null&&request.getSession().getAttribute("noreadO").equals("yes")){
            //是从右上角的未读消息跳转过来
            outdoor.setReadD("否");
            request.getSession().removeAttribute("noreadO");
        }
        DataGrid dg = new DataGrid();
        dg.setTotal(outHistoryService.getDatagridTotal(outdoor));
        List<Outdoor> outDatas = outHistoryService.datagridOutData(page,outdoor);
        dg.setRows(outDatas);
        return dg;
    }

    /**
     * 根据id获得某一条出门消息的详细内容
     * @param odid
     * @return
     */
    @RequestMapping(value = "/getMessage",method = RequestMethod.POST)
    @ResponseBody
    public Result getMessage(@RequestParam("odid")Integer odid){
        String data=outHistoryService.getMessageByOdid(odid);
        outHistoryService.messageRead(odid);
        return new Result(true,data);
    }

}
