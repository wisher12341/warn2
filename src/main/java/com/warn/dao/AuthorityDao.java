package com.warn.dao;

import com.warn.dto.AuthorityDTO;
import com.warn.dto.PageHelper;
import com.warn.entity.Account;
import com.warn.entity.Menu;
import com.warn.entity.Role_Menu;
import com.warn.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by netlab606 on 2017/5/22.
 */
public interface AuthorityDao {

    Long getDatagridTotal();

    void editAuthority(List<Role_Menu> role_menus);

    void clearAuthority();

    void recoverAuthority();
}
