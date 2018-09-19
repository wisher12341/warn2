package com.warn.controller;

import com.warn.dto.Result;
import com.warn.dto.visual.OldManLive;
import com.warn.dto.visual.RoomVisual;
import com.warn.service.VisualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 可视化处理
 * Created by admin on 2017/4/13.
 */
@Controller
@RequestMapping("/visual")
public class VisualController {

    @Autowired
    VisualService visualService;

    @RequestMapping(value = "/",method = RequestMethod.GET )
    public String visual(){
        return "user/play";
    }

    /**
     * 根据老人id获得  该老人 所有房间活动模型
     */
    @ResponseBody
    @RequestMapping(value = "/getRoomVisual",method = RequestMethod.POST)
    public Result getRoomVisualByoldId(@RequestParam Integer oldId){
        List<RoomVisual> roomVisuals=visualService.getRoomVisualByoldId(oldId);
        return new Result(true,roomVisuals);
    }

    /**
     * 根据老人id获得 该老人生活规律模型
     * @param oldId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getLiveVisual",method = RequestMethod.POST)
    public Result getLiveVisualByoldId(@RequestParam Integer oldId){
        OldManLive oldManLive=visualService.getLiveVisualByoldId(oldId);
        return new Result(true,oldManLive);
    }

}
