package com.warn.service.impl;

import com.warn.controller.SystemController;
import com.warn.dao.DataDao;
import com.warn.dao.EquipDao;
import com.warn.dao.RoomDao;
import com.warn.dao.UserDao;
import com.warn.dto.Count;
import com.warn.dto.insdep.Tree_insdep;
import com.warn.entity.Menu;
import com.warn.entity.Role;
import com.warn.entity.User;
import com.warn.exception.NullFromDBException;
import com.warn.service.UserService;
import com.warn.sms.SMSConstants;
import com.warn.sms.SMSUtil;
import com.warn.util.UserCookieUtil;
import com.warn.util.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 与系统相关的用户操作
 * Created by admin on 2017/4/4.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;
    @Autowired
    DataDao dataDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    EquipDao equipDao;



    public User findUserByName(String username) {
        return userDao.findUserByName(username);
    }

    @Override
    public Boolean checkUser(HttpServletRequest request, HttpServletResponse response, User userPost, String autologin) {
        User user = this.findUserByName(userPost.getUsername());
        if (user == null) {
            request.getSession().setAttribute("message", "用户名不存在，请重新登录");
            return false;
        }else {
            if (user.getPassword().equals(userPost.getPassword())) {
                if(autologin!=null && autologin.equals("on")){ // 判断是否要添加到cookie中，一周自动登录
                    // 保存用户信息到cookie
                    try {
                        UserCookieUtil.saveCookie(user, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 保存用信息到session
                request.getSession().setAttribute(Const.SESSION_USER, user);
                request.getSession().setAttribute("username", user.getUsername());
                Role role=userDao.getRoleByUserId(user.getId());
                request.getSession().setAttribute("role",role.getName());
//                    return "redirect:" + RequestUtil.retrieveSavedRequest();//跳转至访问页面

                //日志
                if(SystemController.logger==null){
                    SystemController.logger= Logger.getLogger("com.horstmann.corejava");
                    FileHandler fh;
                    try {
                        File f=new File("log.txt");
                        if(!f.exists()){
                            f.createNewFile();
                        }
                        SystemController.absolutePath = f.getAbsolutePath();
//                        System.out.println(SystemController.absolutePath);
                        fh = new FileHandler("log.txt",true);
                        SystemController.logger.addHandler(fh);//日志输出文件
                        fh.setFormatter(new SimpleFormatter());//输出格式
                    } catch (SecurityException e) {
                        SystemController.logger.log(Level.SEVERE, "安全性错误", e);
                    } catch (IOException e) {
                        System.out.println("IO异常");
                        SystemController.logger.log(Level.SEVERE, "读取文件日志错误", e);
                    }
                }
                if(SystemController.smslogger==null){
                    SystemController.smslogger= Logger.getLogger("com.horstmann.corejava");
                    FileHandler fh;
                    try {
                        File f=new File("smslog.txt");
                        if(!f.exists()){
                            f.createNewFile();
                        }
                        SystemController.absolutePath = f.getAbsolutePath();
//                        System.out.println(SystemController.absolutePath);
                        fh = new FileHandler("smslog.txt",true);
                        SystemController.smslogger.addHandler(fh);//日志输出文件
                        fh.setFormatter(new SimpleFormatter());//输出格式
                    } catch (SecurityException e) {
                        SystemController.smslogger.log(Level.SEVERE, "安全性错误", e);
                    } catch (IOException e) {
                        System.out.println("IO异常");
                        SystemController.smslogger.log(Level.SEVERE, "读取文件日志错误", e);
                    }
                }

                return true;
            }else {
                request.getSession().setAttribute("message", "用户名密码错误，请重新登录");
                return false;
            }
        }
    }

//    public List<Menu> getMenuByUserId(int userId) {
//        return userDao.getMenuByUserId(userId);
//    }

    @Override
    public List<Tree_insdep> getMenuTree(HttpSession session) {
        User user = (User) session.getAttribute("USER");
        List<Menu> menuList = null;
        menuList = getMenu1ById(user.getId(),1); //后面的1用来区分  管理人员和超级管理员 的前台和后台权限  1后台  0前台
        List<Tree_insdep> treeList = new ArrayList<Tree_insdep>();
        for (Menu menu : menuList) {
            Tree_insdep node = new Tree_insdep();
            node.setId(menu.getId());
            node.setPid(menu.getParentid());
            node.setText(menu.getName());
            node.setUrl(menu.getUrl());
            if(node.getPid()!=0){
                for(Tree_insdep tree_insdep:treeList){
                    if(tree_insdep.getPid()==0&&tree_insdep.getId()==node.getPid()){
                        tree_insdep.getChildren().add(node);
                    }
                }
            }else {
                treeList.add(node);
            }
        }
        return treeList;
    }

    @Override
    public List<Tree_insdep> getUserMenuTree(HttpSession session){
        User user = (User) session.getAttribute("USER");
        List<Menu> menuList = null;
        menuList = getMenu1ById(user.getId(),0);
        List<Tree_insdep> treeList = new ArrayList<Tree_insdep>();
        for (Menu menu : menuList) {
            Tree_insdep node = new Tree_insdep();
            node.setId(menu.getId());
            node.setPid(menu.getParentid());
            node.setText(menu.getName());
            node.setUrl(menu.getUrl());
            if(node.getPid()!=0){
                for(Tree_insdep tree_insdep:treeList){
                    if(tree_insdep.getPid()==0&&tree_insdep.getId()==node.getPid()){
                        tree_insdep.getChildren().add(node);
                    }
                }
            }else {
                treeList.add(node);
            }
        }
        return treeList;
    }

//    public List<Tree_noUse> getMenuTree(HttpSession session,Integer parentid) {
//        User user = (User) session.getAttribute("USER");
//        List<Menu> menuList = null;
//        // 初次进入 id为null 取出所有pid=0的目录
//        if (parentid == null) {
//            menuList = getMenuById(user.getId(), 0);
//            // 再次进入 得到点击id 取出所有pid=该id的目录
//        } else {
//            menuList = getMenuById(user.getId(), parentid);
//        }
//
//        List<Tree_noUse> treeList = new ArrayList<Tree_noUse>();
//
//        for (Menu menu : menuList) {
//
//            Tree_noUse node = new Tree_noUse();
//            node.setId(menu.getId());
//            node.setPid(menu.getParentid());
//            node.setText(menu.getName());
//            node.setIconCls(menu.getIconCls());
//
//            if (menu.getCountChildrens() > 0) {
//                node.setState("closed");
//            }
//            Map<String, Object> attr = new HashMap<String, Object>();
//            attr.put("url", menu.getUrl());
//            node.setAttributes(attr);
//            treeList.add(node);
//        }
//        return treeList;
//    }

    public Role getRole(HttpSession session) {
        User user =  (User)session.getAttribute(Const.SESSION_USER);
        return userDao.getRoleByUserId(user.getId());
    }

    public List<Menu> getMenuById(Integer userid,Integer parentid) {
        return userDao.getMenuById(userid,parentid);
    }


    public List<Menu> getMenu1ById(Integer userid,Integer flag) {
        return userDao.getMenu1ById(userid,flag);
    }


    @Override
    public Count getCount() {
        Count count=new Count();
        count.setOldManC(dataDao.getCount());
        count.setRoomC(roomDao.getCount());
        count.setEquipC(equipDao.getCount());
        count.setTotal(count.getOldManC()+count.getRoomC()+count.getEquipC());
        return count;
    }
}
