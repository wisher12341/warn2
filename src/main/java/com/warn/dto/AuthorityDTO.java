package com.warn.dto;

import com.warn.dto.insdep.Tree_insdep;
import com.warn.entity.Menu;

/**
 * 权限管理的DTO
 * Created by netlab606 on 2017/5/31.
 */
public class AuthorityDTO implements Comparable<AuthorityDTO>{

    private String id;//与menu的id一致  方便排序   1 101 102  2 201 202 3 ......这样的顺序 方便前端

    private Menu menu;
    private Integer superManager=0; //该权限  该角色是否有   1有 0没有
    private Integer manager=0; //默认没有
    private Integer user=0;

    public AuthorityDTO() {
    }

    public AuthorityDTO(String id, Integer superManager, Integer manager, Integer user) {
        this.id = id;
        this.superManager = superManager;
        this.manager = manager;
        this.user = user;
    }

    @Override
    public int compareTo(AuthorityDTO o) {
        //父id为 两位数的情况
        if(o.getId().length()==2||o.getId().length()==4){
            //当前 对象的 父id为一位数的情况
            if(this.getId().length()==1||this.getId().length()==3){
                return -1;
            }
        }
        //反过来
        if(this.getId().length()==2||this.getId().length()==4){
            if(o.getId().length()==1||o.getId().length()==3){
                return 1;
            }
        }
        return this.getId().compareTo(o.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getSuperManager() {
        return superManager;
    }

    public void setSuperManager(Integer superManager) {
        this.superManager = superManager;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
