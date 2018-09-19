package com.warn.service.impl;

import com.warn.dao.DownHistoryDao;
import com.warn.dao.OutHistoryDao;
import com.warn.dto.PageHelper;
import com.warn.entity.DownData;
import com.warn.entity.Outdoor;
import com.warn.service.DownHistoryService;
import com.warn.service.OutHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */
@Service
public class DownHistoryServiceImpl implements DownHistoryService {

    @Autowired
    DownHistoryDao downHistoryDao;



    @Override
    public long getNoReadSum() {
        return downHistoryDao.getNoReadSum();
    }

    @Override
    public void messageRead(Integer downid) {
        downHistoryDao.messageRead(downid);
    }


    @Override
    public List<DownData> datagridDownData(PageHelper page, DownData downData) {
        page.setStart((page.getPage() - 1) * page.getRows());
        page.setSort("downid");
        page.setOrder("desc");
        return downHistoryDao.datagridDownData(page, downData);
    }

    @Override
    public Long getDatagridTotal(DownData downData) {
        return downHistoryDao.getDatagridTotal(downData);
    }

//    @Override
//    public String getMessageByOdid(Integer odid) {
//        return downHistoryDao.getMessageByOdid(odid);
//    }

    @Override
    public void addDownData(DownData downData) {
        downHistoryDao.addDownData(downData);
    }
}
