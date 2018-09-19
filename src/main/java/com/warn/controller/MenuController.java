package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.Result;
import com.warn.entity.Menu;
import com.warn.entity.OldMan;
import com.warn.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 * Created by admin on 2017/4/5.
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 跳转至列表页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "menu/list";
    }


    /**
     * 恢复最初菜单信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recoverMenu",method = RequestMethod.POST)
    public Result recoverMenu(){
        menuService.recoverMenu();
        return new Result(true);
    }

    /**
     * 添加菜单信息
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMenu",method = RequestMethod.POST)
    public Result addMenu(Menu menu){
        menuService.addMenu(menu);
        return new Result(true);
    }

    /**
     * 修改菜单信息
     * @param menu
     * @param preId 修改之前的 菜单ID  存在菜单ID也被修改的情况
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editMenu",method = RequestMethod.POST)
    public Result editMenu(Menu menu,@RequestParam Integer preId){
        menuService.editMenu(menu,preId);
        return new Result(true);
    }

    /**
     * 删除菜单信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteMenu",method = RequestMethod.POST)
    public Result deleteMneu(@RequestParam Integer id){
        menuService.deleteMenuById(id);
        return new Result(true);
    }


}
