package com.warn.service;

import com.warn.dto.PageHelper;
import com.warn.entity.Bed;

import java.util.List;

public interface BedService {
    List<Bed> dataGridBed(PageHelper page,Bed bed);

    Long dataGridBedOldTotal(Bed bed);

    List<Bed> dataGridBedOld(PageHelper page,Bed bed);
}
