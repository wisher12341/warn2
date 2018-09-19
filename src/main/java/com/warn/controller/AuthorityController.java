package com.warn.controller;

import com.warn.dto.AuthorityDTO;
import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.entity.Account;
import com.warn.service.AccountService;
import com.warn.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理
 * Created by admin on 2017/4/5.
 */
@Controller
@RequestMapping(value = "/authority")
public class  AuthorityController {

    @Autowired
    AuthorityService authorityService;

    /**
     * 跳转至列表页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "authority/list";
    }




    /**
     * 获得权限信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid() {
        DataGrid dg = new DataGrid();
        dg.setTotal(authorityService.getDatagridTotal());
        List<AuthorityDTO> oldList = authorityService.datagridAuthority();
        dg.setRows(oldList);
        return dg;
    }


    /**
     * 修改权限信息
     * @param authorityDTOS
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editAuthority",method = RequestMethod.POST)
    public Result editAuthority(@RequestBody AuthorityDTO[] authorityDTOS){
        authorityService.editAuthority(authorityDTOS);
        return new Result(true);
    }

    /**
     * 恢复最初权限信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/recoverAuthority",method = RequestMethod.POST)
    public Result recoverAuthority(){
        //菜单还在变
//        authorityService.recoverAuthority();
        return new Result(true);
    }

}
