package com.warn.service;

import com.warn.dto.PageHelper;
import com.warn.entity.Log;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by admin on 2017/5/4.
 */
public interface LogService {
    Long getDatagridTotal(Log log,HttpSession httpSession);

    List<Log> datagridLogData(PageHelper page, Log log, HttpSession session);

    void addLog(Log log, HttpSession session);

    void editLog(Log log, HttpSession session);

    void deleteLogById(Integer logId);
}
