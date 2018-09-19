package com.warn.dao;

import com.warn.entity.model.RoomModel;

import java.util.List;

/**
 * Created by admin on 2017/4/13.
 */
public interface VisualDao {
    List<RoomModel> getRoomActivityByOldId(Integer oldId);

    String getLiveVisualByoldId(Integer oldId);
}
