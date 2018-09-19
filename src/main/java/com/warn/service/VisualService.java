package com.warn.service;

import com.warn.dto.visual.OldManLive;
import com.warn.dto.visual.RoomVisual;

import java.util.List;

/**
 * Created by admin on 2017/4/13.
 */
public interface VisualService {
    List<RoomVisual> getRoomVisualByoldId(Integer oldId);

    OldManLive getLiveVisualByoldId(Integer oldId);
}
