package com.warn.controller;


import com.warn.dto.Count;
import com.warn.dto.Result;
import com.warn.dto.insdep.Tree_insdep;
import com.warn.entity.Role;
import com.warn.entity.User;
import com.warn.mongodb.dao.SensorMogoDao;
import com.warn.mongodb.model.SensorCollection;
import com.warn.service.UserService;
import com.warn.sms.SMSUtil;
import com.warn.util.StaticVal;
import com.warn.util.UserCookieUtil;
import com.warn.util.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Created by admin on 2017/4/4.
 * 处理用户登录、退出
 */
@Controller
public class SystemController {

    @Autowired
    UserService userService;

    public static String absolutePath="";//日志地址

    public static Logger logger;

    public static Logger smslogger;
    /**
     *获取菜单栏
     */
    @ResponseBody
    @RequestMapping(value="/getMenu", method = RequestMethod.POST)
    public List<Tree_insdep> getMenu(HttpSession session){
        return userService.getMenuTree(session);
    }

    /**
     *获取前台用户权限的菜单栏
     */
    @ResponseBody
    @RequestMapping(value="/getUserMenu", method = RequestMethod.POST)
    public List<Tree_insdep> getUserMenu(HttpSession session){
        return userService.getUserMenuTree(session);
    }

    /**
     * 主页
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String indexUsr() {
        return "index";
    }



    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String index() {
        return "main";
    }

    /**
     * 登录认证
     * @param userPost 用户名密码
     * @param autologin 是否一周内自动登录
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request,HttpServletResponse response,
                        User userPost,String autologin){
            Boolean success=userService.checkUser(request, response, userPost, autologin);
            return "redirect:/index";
    }

    /**
     *注销
     */
    @ResponseBody
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public Result logout(HttpSession session,HttpServletResponse response){
        session.removeAttribute(Const.SESSION_USER);
        session.removeAttribute("username");
        session.removeAttribute("role");
//        UserCookieUtil.clearCookie(response);
        return new Result(true);
    }


    /**
     *获取用户角色
     */
    @ResponseBody
    @RequestMapping(value="/getRole", method = RequestMethod.GET)
    public Result getRole(HttpSession session){
        Role role=userService.getRole(session);
        return new Result(true,role);
    }

    /**
     *获取老人、房间、设备数量
     */
    @ResponseBody
    @RequestMapping(value="/getCount", method = RequestMethod.GET)
    public Result getCount(){
        Count count=userService.getCount();
        return new Result(true,count);
    }

    /**
     *获取日志地址
     */
    @ResponseBody
    @RequestMapping(value="/getLogPath", method = RequestMethod.GET)
    public Result getLogPath(){
        return new Result(true,absolutePath);
    }


    /**
     * 前台  未读的预警消息页面
     * @return
     */
    @RequestMapping(value = "/getUserNoReadWarn",method = RequestMethod.GET)
    public String getUserNoReadWarn(HttpServletRequest request) {
        //将  只获取只读的 信息 存入session中
        request.getSession().setAttribute("noreadW","yes");
//        if(type.equals("front")) {
            return "user/warn";
//        }else{
//            return "history/warn";
//        }
    }
    /**
     * 前台  未读的出门消息页面
     * @return
     */
    @RequestMapping(value = "/getUserNoReadOut",method = RequestMethod.GET)
    public String getUserNoReadOut(HttpServletRequest request) {
        //将  只获取只读的 信息 存入session中
        request.getSession().setAttribute("noreadO","yes");
//        if(type.equals("front")) {
            return "user/out";
//        }else{
//            return "history/warn";
//        }
    }

    /**
     * 前台  未读的故障消息页面
     * @return
     */
    @RequestMapping(value = "/getUserNoReadDown",method = RequestMethod.GET)
    public String getUserNoReadDown(HttpServletRequest request) {
        //将  只获取只读的 信息 存入session中
        request.getSession().setAttribute("noreadDown","yes");
//        if(type.equals("front")) {
        return "user/down";
//        }else{
//            return "history/warn";
//        }
    }




}
