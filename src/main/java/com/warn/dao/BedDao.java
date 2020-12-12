package com.warn.dao;

import com.warn.dto.PageHelper;
import com.warn.entity.Bed;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BedDao {
    List<Bed> datagridBed(@Param("page")PageHelper page,@Param("bed")Bed bed);

    Long datagridBedOldTotal(@Param("bed") Bed bed);

    List<Bed> datagridBedOld(@Param("page")PageHelper page,@Param("bed") Bed bed);
}
