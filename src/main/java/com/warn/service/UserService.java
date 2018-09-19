package com.warn.service;

import com.warn.dto.Count;
import com.warn.dto.insdep.Tree_insdep;
import com.warn.entity.Menu;
import com.warn.entity.Role;
import com.warn.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by admin on 2017/4/4.
 */
public interface UserService {

    //根据姓名查询用户
    public User findUserByName(String username);
    //检查账号密码
    Boolean checkUser(HttpServletRequest request, HttpServletResponse response, User userPost, String autologinch);
    //获取用户角色
    Role getRole(HttpSession session);

    List<Menu> getMenu1ById(Integer id,Integer flag);

    List<Tree_insdep> getMenuTree(HttpSession session);

    Count getCount();

    List<Tree_insdep> getUserMenuTree(HttpSession session);

}
