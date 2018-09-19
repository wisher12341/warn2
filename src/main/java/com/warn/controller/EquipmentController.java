package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.Result;
import com.warn.entity.Equipment;
import com.warn.dto.PageHelper;
import com.warn.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 设备信息管理
 * Created by admin on 2017/4/5.
 */
@Controller
@RequestMapping(value = "/equip")
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    /**
     * 跳转至用户列表页面
     * @return
     */
    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String list_user(){
        return "user/table_equip";
    }

    /**
     * 跳转至列表页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "equip/list";
    }


    /**
     * 跳转至某个老人的用户列表页面
     * @return
     */
    @RequestMapping(value = "/getOldManEquips",method = RequestMethod.GET)
    public String list_user_oldman(HttpServletRequest request, @RequestParam Integer oid, @RequestParam String type){
        //将oid放入session中
        request.getSession().setAttribute("oid",oid);
        if(type.equals("front")) {
            return "user/table_equip";
        }else{
            return "equip/list";
        }
    }

    /**
     * 获得设备信息表格
     * @param equipment 条件查询
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page,Equipment equipment,HttpServletRequest request) {
        if(request.getSession().getAttribute("oid")!=null){
            //是从老人页面跳转过来
            equipment.setOldId((Integer) request.getSession().getAttribute("oid"));
            request.getSession().removeAttribute("oid");
        }
        DataGrid dg = new DataGrid();
        dg.setTotal(equipmentService.getDatagridTotal(equipment));
        List<Equipment> equipmentList = equipmentService.datagridEquip(page, equipment);
        dg.setRows(equipmentList);
        return dg;
    }

    /**
     * 跳转至设备信息注册页面
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add_equip(){
        return "equip/add";
    }

    /**
     * 添加设备信息
     * @param equipment
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addEquip",method = RequestMethod.POST)
    public Result addEquip(Equipment equipment,@RequestParam Integer gatewayTwo_Ten){
        equipmentService.addEquip(equipment,gatewayTwo_Ten);
        return new Result(true);
    }

    /**
     * 修改设备信息
     * @param equipment
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editEquip",method = RequestMethod.POST)
    public Result editEquip(Equipment equipment,Integer preOid){
        equipmentService.editEquip(equipment,preOid);
        return new Result(true);
    }

    /**
     * 删除设备信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteEquip",method = RequestMethod.POST)
    public Result deleteEquip(@RequestParam String eid,@RequestParam Integer oid){
        equipmentService.deleteEquipById(eid,oid);
        return new Result(true);
    }


    /**
     * 获取某老人的所有设备信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allRoom",method = RequestMethod.POST)
    public Result nerRoom(@RequestParam Integer oldId){
        List<Equipment> equipments=equipmentService.getAllEquipByOldManId(oldId);
        return new Result(true,equipments);
    }

}
