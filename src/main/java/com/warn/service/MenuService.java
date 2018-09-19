package com.warn.service;

import com.warn.entity.Menu;

/**
 * Created by admin on 2017/4/5.
 */
public interface MenuService {
    void deleteMenuById(Integer id);

    void editMenu(Menu menu, Integer preId);

    void addMenu(Menu menu);

    void recoverMenu();
}
