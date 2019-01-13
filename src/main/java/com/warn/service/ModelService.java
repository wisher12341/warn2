package com.warn.service;

import com.warn.dto.ManModelDto;
import com.warn.dto.ManModelDtos;
import com.warn.dto.RoomModelDtos;
import com.warn.entity.model.RoomModel;

import java.util.List;

/**
 * Created by admin on 2017/4/21.
 */
public interface ModelService {
    List<RoomModel> getRoomModelByOid(Integer oid);

//    void updateRoomModel(RoomModel roomModel);

    List<ManModelDto> getManModelByOid(Integer oid);

    void addManModel(ManModelDtos manModelDtos);

    void addRoomModel(RoomModelDtos roomModelDtos);
}
