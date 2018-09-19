package com.warn.dao;

import com.warn.dto.PageHelper;
import com.warn.entity.Outdoor;
import com.warn.entity.WarnData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出门历史信息
 * Created by admin on 2017/4/27.
 */
public interface OutHistoryDao {

    void addOutDoor(Outdoor outdoor);
    long getNoReadSum();

    void messageRead(@Param("id") Integer odid);

    List<Outdoor> datagridOutData(@Param("page") PageHelper page, @Param("outDoor") Outdoor outdoor);

    Long getDatagridTotal(Outdoor outdoor);

    String getMessageByOdid(@Param("id") Integer odid);
}
