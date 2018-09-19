package com.warn.service.impl;

import com.warn.dao.MenuDao;
import com.warn.entity.Menu;
import com.warn.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/4/5.
 */
@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuDao menuDao;

    @Override
    public void recoverMenu() {
        menuDao.recoverMenu();
    }

    @Override
    public void deleteMenuById(Integer id) {
        menuDao.deleteMenuById(id);
    }

    @Override
    public void editMenu(Menu menu, Integer preId) {
        menuDao.editMenu(menu,preId);
    }

    @Override
    public void addMenu(Menu menu) {
        if(menu.getParentid()==null){
            menu.setParentid(0);
        }
        menuDao.addMenu(menu);
    }
}
