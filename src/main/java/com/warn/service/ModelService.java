package com.warn.service;

import com.warn.dto.ManModelDto;
import com.warn.dto.ManModelDtos;
import com.warn.dto.RoomModelDto;
import com.warn.dto.RoomModelDtos;
import com.warn.entity.model.AreaModel;
import com.warn.entity.model.RoomModel;

import java.util.List;

/**
 * Created by admin on 2017/4/21.
 */
public interface ModelService {
    List<RoomModel> getRoomModelByOid(Integer oid);

    List<AreaModel> getAreaModelByRid(Integer rid);

//    void updateRoomModel(RoomModel roomModel);

    List<ManModelDto> getManModelByOid(Integer oid);

    List<RoomModelDto> getRoomModelById(Integer id);

    void addManModel(ManModelDtos manModelDtos);

    void addRoomModel(RoomModelDtos roomModelDtos);
}
