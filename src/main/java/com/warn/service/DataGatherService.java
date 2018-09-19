package com.warn.service;

import com.warn.dto.DataGatherDto;
import com.warn.dto.PageHelper;

import java.util.List;

/**
 * Created by netlab606 on 2017/5/25.
 */
public interface DataGatherService {
    Long getDatagridTotal(DataGatherDto dataGatherDto);

    List<DataGatherDto> datagridData(PageHelper page, DataGatherDto dataGatherDto);
}
