package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.Result;
import com.warn.entity.Room;
import com.warn.dto.PageHelper;
import com.warn.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老人信息管理
 * Created by admin on 2017/4/5.
 */
@Controller
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    /**
     * 跳转至列表页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "room/list";
    }

    /**
     * 跳转至用户列表页面
     * @return
     */
    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String list_user(){
        return "user/table_room";
    }

    /**
     * 跳转至某个老人的用户列表页面
     * @return
     */
    @RequestMapping(value = "/getOldManRooms",method = RequestMethod.GET)
    public String list_user_oldman(HttpServletRequest request,@RequestParam Integer oid, @RequestParam String type){
        //将oid放入session中
        request.getSession().setAttribute("oid",oid);
        if(type.equals("front")) {
            return "user/table_room";
        }else{
            return "room/list";
        }
    }


    /**
     * 获得房间信息表格
     * @param room 条件查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page,Room room,HttpServletRequest request) {
        if(request.getSession().getAttribute("oid")!=null){
            //是从老人页面跳转过来
            room.setOldId((Integer) request.getSession().getAttribute("oid"));
            request.getSession().removeAttribute("oid");
        }
        DataGrid dg = new DataGrid();
        dg.setTotal(roomService.getDatagridTotal(room));
        List<Room> roomList = roomService.datagridRoom(page,room);
        dg.setRows(roomList);
        return dg;
    }

//    /**
//     * 跳转至房间信息注册页面
//     */
//    @RequestMapping(value = "/add",method = RequestMethod.GET)
//    public String add_room(){
//        return "room/add";
//    }

    /**
     * 添加房间信息
     * @param room
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRoom",method = RequestMethod.POST)
    public Result addRoomn(Room room){
        roomService.addRoom(room);
        return new Result(true);
    }

    /**
     * 修改房间信息
     * @param room
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editRoom",method = RequestMethod.POST)
    public Result editRoom(Room room,@RequestParam Integer gatewayTwo_Ten){
        roomService.editRoom(room,gatewayTwo_Ten);
        return new Result(true);
    }

    /**
     * 删除老人房间信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteRoom",method = RequestMethod.POST)
    public Result deleteRoom(Room room){
        roomService.deleteRoomById(room);
        return new Result(true);
    }

//    /**
//     * 获取相邻房间信息
//     * @param
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "/nerRoom",method = RequestMethod.POST)
//    public Result nerRoom(Room room){
//        List<Room> rooms=roomService.getNerRoom(room);
//        return new Result(true,rooms);
//    }

    /**
     * 获取某老人的所有房间信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allRoom",method = RequestMethod.POST)
    public Result nerRoom(@RequestParam Integer oldId){
        List<Room> rooms=roomService.getAllRoomByOldManId(oldId);
        return new Result(true,rooms);
    }

}
