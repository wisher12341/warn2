package com.warn.controller;

import com.warn.dto.*;
import com.warn.entity.model.AreaModel;
import com.warn.entity.model.RoomModel;
import com.warn.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 房间生活规律模型 相关操作
 * Created by admin on 2017/4/20.
 */
@Controller
@RequestMapping("model")
public class ModelController {

    @Autowired
    ModelService modelService;

    /**
     * 跳转至页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "model/list";
    }

    @RequestMapping(value="/next", method = RequestMethod.GET)
    public String list_next() {return "model/next_list";}
    /**
     * 根据老人id获得其各房间的规律模型
     * @param oid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRoomModel",method = RequestMethod.POST)
    public DataGrid getRoomModel(Integer oid){
        DataGrid dg = new DataGrid();
        if(oid==null){
            //传一个控制回去  使前端不报rows is null的错误
            dg.setTotal(0L);
            List<RoomModel> th=new ArrayList();
            dg.setRows(th);
            return dg;
        }
        List<RoomModel> roomModels=modelService.getRoomModelByOid(oid);
        dg.setTotal((long) roomModels.size());
        dg.setRows(roomModels);
        return dg;
    }
    /**
     * 根据房间id
     */
    @ResponseBody
    @RequestMapping(value = "/getAreaModel", method = RequestMethod.POST)
    public DataGrid getAreaModel(Integer rid){
        DataGrid dg = new DataGrid();
        if(rid==null){
            //传一个控制回去  使前端不报rows is null的错误
            dg.setTotal(0L);
            List<RoomModel> th=new ArrayList();
            dg.setRows(th);
            return dg;
        }
        List<AreaModel> areaModels = modelService.getAreaModelByRid(rid);
        dg.setTotal((long) areaModels.size());
        dg.setRows(areaModels);
        return dg;
    }
//    /**
//     * 更新房间模型
//     * @param roomModel
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "updateRoomModel",method = RequestMethod.POST)
//    public Result updateRoomModel(RoomModel roomModel){
//        modelService.updateRoomModel(roomModel);
//        return new Result(true);
//    }

    /**
     * 根据老人id获得其生活规律规律模型
     * @param oid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManModel",method = RequestMethod.POST)
    public DataGrid getManModel(Integer oid){
        DataGrid dg = new DataGrid();
        if(oid==null){
            //传一个控制回去  使前端不报rows is null的错误
            dg.setTotal(0L);
            List<RoomModel> th=new ArrayList();
            dg.setRows(th);
            return dg;
        }
        List<ManModelDto> manModelDtos=modelService.getManModelByOid(oid);
        dg.setTotal((long) manModelDtos.size());
        dg.setRows(manModelDtos);
        return dg;
    }
    /**
     * 根据房间id获得房间内具体区域模型
     */
    @ResponseBody
    @RequestMapping(value = "/getAreaRoomModel", method = RequestMethod.POST)
    public DataGrid getAreaRoomModel(Integer rid){//rid在这里指房间id
        DataGrid dg = new DataGrid();
        if(rid == null){
            dg.setTotal(0L);
            List<RoomModel> th=new ArrayList();
            dg.setRows(th);
            return dg;
        }
        List<RoomModelDto> roomModelDtos = modelService.getRoomModelById(rid);
        dg.setTotal((long)roomModelDtos.size());
        dg.setRows(roomModelDtos);
        return dg;
    }

    /**
     * 添加老人生活规律模型
     * @param manModelDtos
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addManModel",method = RequestMethod.POST)
    public Result addManModel(ManModelDtos manModelDtos){
        modelService.addManModel(manModelDtos);
        return new Result(true);
    }

    @ResponseBody
    @RequestMapping(value = "/addRoomModel", method = RequestMethod.POST)
    public Result addRoomModel(RoomModelDtos roomModelDtos){
        modelService.addRoomModel(roomModelDtos);
        return new Result(true);
    }
}
