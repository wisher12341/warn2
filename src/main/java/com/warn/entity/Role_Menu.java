package com.warn.entity;

/**
 * 角色功能关联
 * Created by netlab606 on 2017/6/1.
 */
public class Role_Menu {

    private Integer id;
    private Integer roleId;
    private Integer menuId;
    private String flagBack;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getFlagBack() {
        return flagBack;
    }

    public void setFlagBack(String flagBack) {
        this.flagBack = flagBack;
    }

    @Override
    public String toString() {
        return "Role_Menu{" +
                "roleId=" + roleId +
                ", menuId=" + menuId +
                ", flagBack='" + flagBack + '\'' +
                '}';
    }
}
