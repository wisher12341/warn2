package com.warn.dto.insdep;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取左侧菜单栏
 * Created by admin on 2017/4/21.
 */
public class Tree_insdep {
    private int id;				//节点的ID
    private String text;		//节点显示的文字
    private String url;			//给一个节点追加的自定义属性 url
    private List<Tree_insdep> children=new ArrayList<>();		//定义了一些子节点的节点数组
    private int pid;					//定义该节点的父节点


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Tree_insdep> getChildren() {
        return children;
    }

    public void setChildren(List<Tree_insdep> children) {
        this.children = children;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
