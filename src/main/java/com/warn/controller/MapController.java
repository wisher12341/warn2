package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.MarkerSum;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.entity.*;
import com.warn.service.DataService;
import com.warn.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 地图信息管理
 * Created by admin on 2017/4/27.
 */
@Controller
@RequestMapping("/map")
public class MapController {


    @Autowired
    MapService mapService;
    @Autowired
    DataService dataService;

    /**
     * 跳转至列表页面
     * @return
     */
    @RequestMapping(value = "/play",method = RequestMethod.GET)
    public String list(){
        return "user/map";
    }

    /**
     * 获得房屋标注
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getHouseMarkers", method = RequestMethod.GET)
    public Result getHouseMarkers() {
        List<HouseMarker> houseMarkerList = mapService.getHouseMarkers();
        return new Result(true, houseMarkerList);
    }

    /**
     * 添加房屋标注
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/addHouseMarker", method = RequestMethod.POST)
    public Result addHouseMarker(HouseMarker houseMarker) {
        OldMan oldMan=mapService.addHouseMarker(houseMarker);
        return new Result(true,oldMan);
    }

    /**
     * 添加楼标注
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/addLouMarker", method = RequestMethod.POST)
    public Result addLouMarker(LouMarker louMarker) {
        mapService.addLouMarker(louMarker);
        return new Result(true);
    }

    /**
     * 获得楼标注 GET
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getLouMarkers", method = RequestMethod.GET)
    public Result getLouMarkers() {
        List<LouMarker> louMarkerList = mapService.getLouMarkers();
        return new Result(true, louMarkerList);
    }

    /**
     * 获得楼标注  POST
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getLouMarkers", method = RequestMethod.POST)
    public DataGrid getLouMarkers_post(LouMarker louMarker,PageHelper page) {
        DataGrid dg = new DataGrid();
        dg.setTotal(mapService.getLouMarkersTotal(louMarker));
        List<LouMarker> louMarkerList = mapService.getLouMarkersManager(page,louMarker);
        dg.setRows(louMarkerList);
        return dg;
    }
    /**
     * 获得区标注  POST
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getQuMarkers", method = RequestMethod.POST)
    public DataGrid getQuMarkers_post(QuMarker quMarker,PageHelper page) {
        DataGrid dg = new DataGrid();
        dg.setTotal(mapService.getQuMarkersTotal(quMarker));
        List<QuMarker> quMarkerList = mapService.getQuMarkersManager(page,quMarker);
        dg.setRows(quMarkerList);
        return dg;
    }
    /**
     * 获得街道标注  POST
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getJieDaoMarkers", method = RequestMethod.POST)
    public DataGrid getJieDaoMarkers_post(JieDaoMarker jieDaoMarker,PageHelper page) {
        DataGrid dg = new DataGrid();
        dg.setTotal(mapService.getJieDaoMarkersTotal(jieDaoMarker));
        List<JieDaoMarker> jieDaoMarkerList = mapService.getJieDaoMarkersManager(page,jieDaoMarker);
        dg.setRows(jieDaoMarkerList);
        return dg;
    }

    /**
     * 获得区标注
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getQuMarkers", method = RequestMethod.GET)
    public Result getQuMarkers() {
        List<QuMarker> quMarkerList = mapService.getQuMarkers();
        return new Result(true, quMarkerList);
    }

    /**
     * 获得某个区的所有街道标注
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/{qid}/getJieDaoMarkers", method = RequestMethod.GET)
    public Result getJieDaoMarkers(@PathVariable("qid") Integer qid) {
        List<JieDaoMarker> jieDaoMarkers = mapService.getJieDaoMarkersByQid(qid);
        return new Result(true, jieDaoMarkers);
    }

    /**
     * 获得某个街道的所有楼标注
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/{jid}/getLouMarkers", method = RequestMethod.GET)
    public Result getLouMarkers(@PathVariable("jid") Integer jid) {
        List<LouMarker> louMarkers = mapService.getLouMarkersByJid(jid);
        return new Result(true, louMarkers);
    }

    /**
     * 获得统计信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getSums", method = RequestMethod.GET)
    public Result getSums() {
        MarkerSum markerSum = mapService.getSums();
        return new Result(true, markerSum);
    }
    /**
     * 获得街道标注
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getJieDaoMarkers", method = RequestMethod.GET)
    public Result getJieDaoMarkers() {
        List<JieDaoMarker> jieDaoMarkerList = mapService.getJieDaoMarkers();
        return new Result(true, jieDaoMarkerList);
    }

    /**
     * 添加街道标注
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/addStreetMarker", method = RequestMethod.POST)
    public Result addStreetMarker(JieDaoMarker jieDaoMarker) {
        Integer jid=mapService.addStreetMarker(jieDaoMarker);
        return new Result(true,jid);
    }

    /**
     * 人员绑定界面
     * @return
     */
    @RequestMapping(value="/oldman", method = RequestMethod.GET)
    public String old() {
        return "map/oldman";
    }

    /**
     * 获得老人信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/oldman/data", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page, OldMan oldMan) {
        DataGrid dg = new DataGrid();
        dg.setTotal(dataService.getDatagridTotal(oldMan));
        List<OldMan> oldList = dataService.datagridUserMap(page,oldMan);
        dg.setRows(oldList);
        return dg;
    }

    /**
     * 修改人员的 地图信息
     * @param oldMan
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editOldmanMap",method = RequestMethod.POST)
    public Result editOldmanMap(OldMan oldMan){
        dataService.editOldmanMap(oldMan);
        return new Result(true);
    }

    /**
     * 楼管理界面
     * @return
     */
    @RequestMapping(value="/manager/lou", method = RequestMethod.GET)
    public String lou() {
        return "map/lou";
    }
    /**
     * 街道管理界面
     * @return
     */
    @RequestMapping(value="/manager/street", method = RequestMethod.GET)
    public String street() {
        return "map/street";
    }
    /**
     * 区管理界面
     * @return
     */
    @RequestMapping(value="/manager/district", method = RequestMethod.GET)
    public String district() {
        return "map/district";
    }
}
