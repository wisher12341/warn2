package com.warn.service.impl;

import com.warn.dao.DataDao;
import com.warn.dao.EquipDao;
import com.warn.dao.RoomDao;
import com.warn.dto.DataGatherDto;
import com.warn.dto.PageHelper;
import com.warn.entity.Equipment;
import com.warn.entity.OldMan;
import com.warn.entity.Room;
import com.warn.service.DataGatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netlab606 on 2017/5/25.
 */
@Service
public class DataGatherServiceImpl implements DataGatherService {

    @Autowired
    DataDao dataDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    EquipDao equipDao;

    @Override
    public Long getDatagridTotal(DataGatherDto dataGatherDto) {
        if(dataGatherDto.getOldMan()==null){
            dataGatherDto.setOldMan(new OldMan());
        }
        return dataDao.getDatagridTotal(dataGatherDto.getOldMan());
    }

    @Override
    @Transactional
    public List<DataGatherDto> datagridData(PageHelper page, DataGatherDto dataGatherDto) {
        page.setStart((page.getPage() - 1) * page.getRows());
        List<DataGatherDto> dataGatherDtos=new ArrayList<>();
        if(dataGatherDto.getOldMan()==null){
            dataGatherDto.setOldMan(new OldMan());
        }
        List<OldMan> oldManList=dataDao.datagridUser(page,dataGatherDto.getOldMan());
        for(OldMan oldMan:oldManList){
            DataGatherDto dataGatherDtoGet=new DataGatherDto();
            dataGatherDtoGet.setOldMan(oldMan);
            List<Room> rooms=roomDao.getAllRoomByOldManId(oldMan.getOid());
            List<Equipment> equips=equipDao.getAllEquipByOldManId(oldMan.getOid());
            dataGatherDtoGet.setRooms(rooms);
            dataGatherDtoGet.setEquipments(equips);
            dataGatherDtos.add(dataGatherDtoGet);
        }
        return dataGatherDtos;
    }
}
