package com.warn.dao;

import com.warn.entity.Account;
import com.warn.dto.PageHelper;
import com.warn.entity.Role;
import com.warn.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by netlab606 on 2017/5/22.
 */
public interface AccountDao {
    Long getDatagridTotal();

    List<Account> datagridAccount(@Param("page")PageHelper page);

    void addUser(User user);

    void addUser_Role(@Param("uid") Integer uid,@Param("rid") Integer rid);

    void editUserByUid(User user);

    void editUser_Role(@Param("uid") Integer uid,@Param("rid") Integer rid);

    void deleteUserByUid(@Param("id") Integer uid);

    void deleteUser_RoleByUid(@Param("id") Integer uid);

    Integer getRoleIdByName(String roleName);
}
