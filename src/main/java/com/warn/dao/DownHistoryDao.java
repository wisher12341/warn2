package com.warn.dao;

import com.warn.dto.PageHelper;
import com.warn.entity.DownData;
import com.warn.entity.Outdoor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 故障历史信息
 * Created by admin on 2017/4/27.
 */
public interface DownHistoryDao {

    void addDownData(DownData downData);
    long getNoReadSum();

    void messageRead(@Param("id") Integer downId);

    List<DownData> datagridDownData(@Param("page") PageHelper page, @Param("downData") DownData downData);

    Long getDatagridTotal(DownData downData);
}
