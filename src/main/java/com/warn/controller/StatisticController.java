package com.warn.controller;

import com.sun.org.glassfish.external.statistics.Statistic;
import com.warn.dao.DataDao;
import com.warn.dao.RoomDao;
import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.dto.visual.AreaVisual;
import com.warn.dto.visual.AreaVisualList;
import com.warn.entity.AreaStatistic;
import com.warn.entity.OldMan;
import com.warn.entity.OldRoom;
import com.warn.entity.Room;
import com.warn.service.RoomService;
import com.warn.service.StatisticService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/Statistic")
public class StatisticController {
    @Autowired
    StatisticService statisticService;
    @Autowired
    RoomService roomService;
    @Autowired
    DataDao dataDao;
    @Autowired
    RoomDao roomDao;

    @RequestMapping(value = "/areaData",method  = RequestMethod.GET)
    @ResponseBody
    public Result getAreaData(Integer gatewatId,Integer sensorPointId){
        statisticService.getStatisticData(gatewatId);
        return new Result(true);
    }

    @RequestMapping(value = "/datagrid", method = RequestMethod.POST)
    @ResponseBody
    public DataGrid datagrid(PageHelper page, Room room) {
        DataGrid dg = new DataGrid();
        page.setStart((page.getPage()-1) * page.getRows());
        List<Room> roomList = roomDao.datagridStatistic(page,room);
        List<OldRoom> oldRooms = new ArrayList<>();
        for(Room room1:roomList){
            OldRoom oldRoom = new OldRoom();
            BeanUtils.copyProperties(room1,oldRoom);
            OldMan oldMan = dataDao.getOldManByOid(room1.getOldId());
            oldRoom.setOldName(oldMan.getOldName());
            oldRooms.add(oldRoom);
        }
        dg.setTotal(roomDao.getDatagridTotal2(room));
        dg.setRows(oldRooms);
        return dg;
    }

    @RequestMapping(value = "/areaVisual",method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaVisual(Integer oid,Integer rid){
        List<AreaVisual> areaVisuals = statisticService.getStatisticArea(oid,rid);
        return new Result(true,areaVisuals);
    }

    @RequestMapping(value = "/areaLine",method = RequestMethod.POST)
    @ResponseBody
    public Result getAreaLine(Integer oid, Integer rid){
        List<AreaVisualList> areaVisualLists = statisticService.getStatisticAreaList(oid,rid);
        return new Result(true,areaVisualLists);
    }


}
