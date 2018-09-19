package com.warn.dao;

import com.warn.dto.PageHelper;
import com.warn.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/5/4.
 */
public interface LogDao {
    Long getDatagridTotal(Log log);

    List<Log> datagridLog(@Param("page")PageHelper page,@Param("log") Log log);

    void addLog(Log log);

    void editLog(Log log);

    void deleteLogById(@Param("id")Integer logId);
}
