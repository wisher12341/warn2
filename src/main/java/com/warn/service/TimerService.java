package com.warn.service;

import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.dto.TimeDto;
import com.warn.entity.OldMan;
import com.warn.entity.Room;

import java.util.List;

/**
 * Created by admin on 2017/4/30.
 */
public interface TimerService {
//    Long getDatagridTotal(OldMan oldMan);
//
//    List<Room> datagridUser(PageHelper page, OldMan oldMan);

    void updateTimer(TimeDto timeDto);

    DataGrid getDatagrid(PageHelper page, OldMan oldMan);

    void addSwitch();
}
