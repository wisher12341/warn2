package com.warn.service.impl;

import com.warn.dao.LogDao;
import com.warn.dto.PageHelper;
import com.warn.entity.Log;
import com.warn.entity.User;
import com.warn.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by admin on 2017/5/4.
 */
@Service
public class LogServiceImpl implements LogService{

    @Autowired
    LogDao logDao;

    @Override
    public Long getDatagridTotal(Log log,HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("USER");
        log.setUserId(user.getId());
        return logDao.getDatagridTotal(log);
    }

    @Override
    public List<Log> datagridLogData(PageHelper page, Log log, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("USER");
        log.setUserId(user.getId());
        page.setStart((page.getPage() - 1) * page.getRows());
        page.setSort("logId");
        page.setOrder("desc");
        return logDao.datagridLog(page, log);
    }

    @Override
    public void addLog(Log log, HttpSession session) {
        User user = (User) session.getAttribute("USER");
        log.setUserId(user.getId());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String dateNowStr = sdf.format(d);
        log.setLogTime(dateNowStr);
        logDao.addLog(log);
    }

    @Override
    public void editLog(Log log, HttpSession session) {
        User user = (User) session.getAttribute("USER");
        log.setUserId(user.getId());
        logDao.editLog(log);
    }

    @Override
    public void deleteLogById(Integer logId) {
        logDao.deleteLogById(logId);
    }
}
