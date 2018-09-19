package com.warn.service;

import com.warn.entity.OldMan;
import com.warn.dto.PageHelper;

import java.util.List;

/**
 * Created by admin on 2017/4/5.
 */
public interface DataService {
    //获取老人信息总数（或查询到的总数）
    Long getDatagridTotal(OldMan oldMan);
    //获得老人列表
    List<OldMan> datagridUser(PageHelper page,OldMan oldMan);
    //添加老人
    void addOldman(OldMan oldMan, Integer segmentTwo_Ten);
    //修改老人
    void editOldman(OldMan oldMan,Integer segmentTwo_Ten);
    //删除老人
    void deleteOldmanById(Integer oldManId);

    List<OldMan> getAllOldmanID_Name();

    List<OldMan> datagridUserMap(PageHelper page, OldMan oldMan);

    void editOldmanMap(OldMan oldMan);
}
