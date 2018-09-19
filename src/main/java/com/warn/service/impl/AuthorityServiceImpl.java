package com.warn.service.impl;

import com.warn.dao.AccountDao;
import com.warn.dao.AuthorityDao;
import com.warn.dao.UserDao;
import com.warn.dto.AuthorityDTO;
import com.warn.dto.PageHelper;
import com.warn.dto.insdep.Tree_insdep;
import com.warn.entity.*;
import com.warn.service.AccountService;
import com.warn.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by netlab606 on 2017/5/22.
 */
@Service
public class AuthorityServiceImpl implements AuthorityService{

    @Autowired
    AuthorityDao authorityDao;
    @Autowired
    UserDao userDao;

    @Override
    public Long getDatagridTotal() {
        return authorityDao.getDatagridTotal();
    }

    /**
     * 先获取各个角色的权限， 根据超级管理人员的权限 来判断其他角色有无该权限
     * @return
     */
    @Override
    public List<AuthorityDTO> datagridAuthority() {
        List<AuthorityDTO> authorityDTOList=new ArrayList<>();
        List<Menu> allMenusList=userDao.getAllMenu();
        List<Menu> superMenuList = userDao.getMenuByRoleId(3);
        List<Menu> managerMenuList = userDao.getMenuByRoleId(1);
        List<Menu> userMenuList = userDao.getMenuByRoleId(2);
        for(Menu menu:allMenusList){
            AuthorityDTO authorityDTO=new AuthorityDTO();
            authorityDTO.setMenu(menu);
            authorityDTO.setId(menu.getId()+"");
            if(menu.getId()==801){

            }
            if(superMenuList.contains(menu)){
                authorityDTO.setSuperManager(1);
            }
            if(managerMenuList.contains(menu)){
                authorityDTO.setManager(1);
            }
            if(userMenuList.contains(menu)){
                authorityDTO.setUser(1);
            }

            authorityDTOList.add(authorityDTO);
        }
        Collections.sort(authorityDTOList);
        return authorityDTOList;
    }

    @Transactional
    @Override
    public void editAuthority(AuthorityDTO[] authorityDTOS) {
        List<Role_Menu> role_menus=new ArrayList<>();
        //先清空表中的数据
        authorityDao.clearAuthority();
        int id=1;//数据表的主键id
        for (AuthorityDTO authorityDTO:authorityDTOS){
            //超级管理人员
            if(authorityDTO.getSuperManager() ==1){
                Role_Menu role_menu=new Role_Menu();
                role_menu.setId(id++);
                role_menu.setRoleId(3);
                role_menu.setMenuId(Integer.parseInt(authorityDTO.getId()));
                //超级管理人员  设置 前台的功能
                if(authorityDTO.getId().equals("1")||authorityDTO.getId().equals("2")||authorityDTO.getId().equals("101")||authorityDTO.getId().equals("102")||authorityDTO.getId().equals("103")||
                authorityDTO.getId().equals("1103")||authorityDTO.getId().equals("201")||authorityDTO.getId().equals("602")||authorityDTO.getId().equals("1104")||authorityDTO.getId().equals("104")||
                        authorityDTO.getId().equals("1106")){
                    role_menu.setFlagBack("0"); //0   前台
                }else if(authorityDTO.getId().equals("6")||authorityDTO.getId().equals("11")){
                    role_menu.setFlagBack("01");  //父菜单  又在前台又在后台
                }else if(authorityDTO.getId().length()==3&&(authorityDTO.getId().substring(0,1).equals("1")||authorityDTO.getId().substring(0,1).equals("2"))){
                    //id 为三位数 且第一个数为1或者2的子菜单
                    role_menu.setFlagBack("0");
                }else{
                    role_menu.setFlagBack("1");  //1  后台
                }
                role_menus.add(role_menu);
            }
            //管理人员
            if(authorityDTO.getManager() ==1){
                Role_Menu role_menu=new Role_Menu();
                role_menu.setId(id++);
                role_menu.setRoleId(1);
                role_menu.setMenuId(Integer.parseInt(authorityDTO.getId()));
                //管理人员  设置 前台的功能
                if(authorityDTO.getId().equals("1")||authorityDTO.getId().equals("2")||authorityDTO.getId().equals("101")||authorityDTO.getId().equals("102")||authorityDTO.getId().equals("103")||
                        authorityDTO.getId().equals("1103")||authorityDTO.getId().equals("201")||authorityDTO.getId().equals("602")||authorityDTO.getId().equals("1104")||authorityDTO.getId().equals("104")||
                        authorityDTO.getId().equals("1106")){
                    role_menu.setFlagBack("0");
                }else if(authorityDTO.getId().equals("6")||authorityDTO.getId().equals("11")){
                    role_menu.setFlagBack("01");
                }else if(authorityDTO.getId().length()==3&&(authorityDTO.getId().substring(0,1).equals("1")||authorityDTO.getId().substring(0,1).equals("2"))){
                    //id 为三位数 且第一个数为1或者2的子菜单
                    role_menu.setFlagBack("0");
                }else{
                    role_menu.setFlagBack("1");
                }
                role_menus.add(role_menu);
            }
            //前台人员
            if(authorityDTO.getUser() ==1){
                Role_Menu role_menu=new Role_Menu();
                role_menu.setId(id++);
                role_menu.setRoleId(2);
                role_menu.setMenuId(Integer.parseInt(authorityDTO.getId()));
                role_menu.setFlagBack("0");
                role_menus.add(role_menu);
            }
        }
        authorityDao.editAuthority(role_menus);
    }

    @Override
    public void recoverAuthority() {
        authorityDao.recoverAuthority();
    }
}
