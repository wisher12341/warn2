package com.warn.controller;

import com.warn.entity.Account;
import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.Result;
import com.warn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 账号信息管理
 * Created by admin on 2017/4/5.
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    /**
     * 跳转至管理员列表页面
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "account/list";
    }




    /**
     * 获得账号信息表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page) {
        DataGrid dg = new DataGrid();
        dg.setTotal(accountService.getDatagridTotal());
        List<Account> oldList = accountService.datagridAccount(page);
        dg.setRows(oldList);
        return dg;
    }

    /**
     * 添加账户信息
     * @param account
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addAccount",method = RequestMethod.POST)
    public Result addOldman(Account account){
        accountService.addAccount(account);
        return new Result(true);
    }

    /**
     * 修改账户信息
     * @param account
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editAccount",method = RequestMethod.POST)
    public Result editAccount(Account account){
        accountService.editAccount(account);
        return new Result(true);
    }

    /**
     * 删除账户信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAccount",method = RequestMethod.POST)
    public Result deleteAccount(@RequestParam("id") Integer id){
        accountService.deleteAccount(id);
        return new Result(true);
    }

}
