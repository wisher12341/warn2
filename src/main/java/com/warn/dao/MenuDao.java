package com.warn.dao;

import com.warn.entity.Menu;
import org.apache.ibatis.annotations.Param;

/**
 * Created by admin on 2017/4/7.
 */
public interface MenuDao {
    void deleteMenuById(Integer id);

    void editMenu(@Param("menu") Menu menu,@Param("preId") Integer preId);

    void addMenu(Menu menu);

    void recoverMenu();
}
