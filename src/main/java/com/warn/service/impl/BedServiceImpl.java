package com.warn.service.impl;

import com.warn.dao.BedDao;
import com.warn.dao.DataDao;
import com.warn.dto.PageHelper;
import com.warn.entity.Bed;
import com.warn.entity.OldMan;
import com.warn.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BedServiceImpl implements BedService {
    @Autowired
    BedDao bedDao;
    @Autowired
    DataDao dataDao;
    public List<Bed> dataGridBed(PageHelper page,Bed bed){
         List<Bed> beds = new ArrayList<>();
         page.setStart((page.getPage() - 1) * page.getRows());
         beds = bedDao.datagridBed(page,bed);
         for(Bed bed1:beds){
             if(bed1.getOid() == 0)
                 bed1.setStatus("无人");
             else
                 bed1.setStatus("有人");
             if(bed1.getGravityId().equals("") || bed1.getGravityId() == null)
                 bed1.setGravityId("未安装设备");
             if(bed1.getVoiceId().equals("") || bed1.getVoiceId() == null)
                 bed1.setVoiceId("未安装设备");

         }
         return beds;
    }

    public Long dataGridBedOldTotal(Bed bed){
        return bedDao.datagridBedOldTotal(bed);
    }

    public List<Bed> dataGridBedOld(PageHelper page,Bed bed){
        List<Bed> beds = new ArrayList<>();
        page.setStart((page.getPage() - 1) * page.getRows());
        beds = bedDao.datagridBedOld(page,bed);
        for(Bed bed1:beds){
            OldMan oldMan = new OldMan();
            if(bed1.getOid() != null) {
                oldMan = dataDao.getOldManByOid(bed1.getOid());
                bed1.setOldMan(oldMan);
            }

        }
        return beds;
    }
}
