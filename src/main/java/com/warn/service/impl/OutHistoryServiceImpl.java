package com.warn.service.impl;

import com.warn.dao.OutHistoryDao;
import com.warn.dao.ThresholdDao;
import com.warn.dao.WarnHistoryDao;
import com.warn.dto.DwrData;
import com.warn.dto.PageHelper;
import com.warn.entity.Outdoor;
import com.warn.entity.WarnData;
import com.warn.service.OutHistoryService;
import com.warn.service.WarnHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/4/27.
 */
@Service
public class OutHistoryServiceImpl implements OutHistoryService {

    @Autowired
    OutHistoryDao outHistoryDao;



    @Override
    public long getNoReadSum() {
        return outHistoryDao.getNoReadSum();
    }

    @Override
    public void messageRead(Integer odid) {
        outHistoryDao.messageRead(odid);
    }


    @Override
    public List<Outdoor> datagridOutData(PageHelper page, Outdoor outdoor) {
        page.setStart((page.getPage() - 1) * page.getRows());
        page.setSort("odid");
        page.setOrder("desc");
        return outHistoryDao.datagridOutData(page, outdoor);
    }

    @Override
    public Long getDatagridTotal(Outdoor outdoor) {
        return outHistoryDao.getDatagridTotal(outdoor);
    }

    @Override
    public String getMessageByOdid(Integer odid) {
        return outHistoryDao.getMessageByOdid(odid);
    }

    @Override
    public void addOutDoor(Outdoor outdoor) {
        outHistoryDao.addOutDoor(outdoor);
    }
}
