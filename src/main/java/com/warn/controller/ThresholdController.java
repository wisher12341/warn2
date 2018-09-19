package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.Result;
import com.warn.entity.Threshold;
import com.warn.entity.Threshold_light;
import com.warn.entity.Threshold_out;
import com.warn.entity.Threshold_wendu;
import com.warn.service.ThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/4/12.
 */
@Controller
@RequestMapping("/threshold")
public class ThresholdController {

    @Autowired
    ThresholdService thresholdService;

    /**
     * 跳转至页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "threshold/list";
    }


    /**
     * 根据老人id获得其各房间的行为阈值
     * @param oid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getThreshold",method = RequestMethod.POST)
    public DataGrid getThreshold(Integer oid){
        DataGrid dg = new DataGrid();
        if(oid==null){
            //传一个控制回去  使前端不报rows is null的错误
            dg.setTotal(0L);
            List<Threshold> th=new ArrayList();
            dg.setRows(th);
            return dg;
        }
        List<Threshold> thresholdPlays=thresholdService.getThresholdByOid(oid);
        dg.setTotal((long) thresholdPlays.size());
        dg.setRows(thresholdPlays);
        return dg;
    }

    /**
     * 更新行为阈值 一开始都是默认值 0
     * @param threshold
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateThreshold",method = RequestMethod.POST)
    public Result updateThreshold(Threshold threshold){
        thresholdService.updateThreshold(threshold);
        return new Result(true);
    }

    /**
     * 根据老人id获得其各房间的温度阈值
     * @param oid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getThreshold_w",method = RequestMethod.POST)
    public DataGrid getThreshold_w(Integer oid){
        DataGrid dg = new DataGrid();
        if(oid==null){
            //传一个控制回去  使前端不报rows is null的错误
            dg.setTotal(0L);
            List<Threshold> th=new ArrayList();
            dg.setRows(th);
            return dg;
        }
        List<Threshold_wendu> thresholdPlays=thresholdService.getThreshold_wByOid(oid);
        dg.setTotal((long) thresholdPlays.size());
        dg.setRows(thresholdPlays);
        return dg;
    }

    /**
     * 更新温度阈值 一开始都是默认值 0
     * @param threshold_wendu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateThreshold_w",method = RequestMethod.POST)
    public Result updateThreshold_w(Threshold_wendu threshold_wendu){
        thresholdService.updateThreshold_w(threshold_wendu);
        return new Result(true);
    }


    /**
     * 根据老人id获得其各房间的光强阈值
     * @param oid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getThreshold_l",method = RequestMethod.POST)
    public DataGrid getThreshold_l(Integer oid){
        DataGrid dg = new DataGrid();
        if(oid==null){
            //传一个控制回去  使前端不报rows is null的错误
            dg.setTotal(0L);
            List<Threshold> th=new ArrayList();
            dg.setRows(th);
            return dg;
        }
        List<Threshold_light> thresholdPlays=thresholdService.getThreshold_lByOid(oid);
        dg.setTotal((long) thresholdPlays.size());
        dg.setRows(thresholdPlays);
        return dg;
    }

    /**
     * 更新光强阈值 一开始都是默认值 0
     * @param threshold_light
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateThreshold_l",method = RequestMethod.POST)
    public Result updateThreshold_l(Threshold_light threshold_light){
        thresholdService.updateThreshold_l(threshold_light);
        return new Result(true);
    }


    /**
     * 根据老人id获得其各出门阈值
     * @param oid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getThreshold_d",method = RequestMethod.POST)
    public DataGrid getThreshold_d(Integer oid){
        DataGrid dg = new DataGrid();
        if(oid==null){
            //传一个控制回去  使前端不报rows is null的错误
            dg.setTotal(0L);
            List<Threshold> th=new ArrayList();
            dg.setRows(th);
            return dg;
        }
        List<Threshold_out> thresholdPlays=thresholdService.getThreshold_dByOid(oid);
        dg.setTotal((long) thresholdPlays.size());
        dg.setRows(thresholdPlays);
        return dg;
    }

    /**
     * 更新出门阈值 一开始都是默认值 0
     * @param threshold_out
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateThreshold_d",method = RequestMethod.POST)
    public Result updateThreshold_d(Threshold_out threshold_out){
        thresholdService.updateThreshold_d(threshold_out);
        return new Result(true);
    }

}
