package com.warn.dao;

import com.warn.entity.Menu;
import com.warn.entity.Role;
import com.warn.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/4/4.
 */
public interface UserDao {

    User findUserByName(String username);

//    List<Menu> getMenuByUserId(int userId);

    Role getRoleByUserId(Integer userId);

    List<Menu> getMenuById(@Param("userid")Integer userid,@Param("parentid") Integer parentid);

    List<Menu> getMenu1ById(@Param("userid")Integer userid,@Param("flag")Integer flag);

    List<Menu> getMenuByRoleId(Integer roleId);

    List<Menu> getAllMenu();
}
