package com.warn.service;

import com.warn.entity.*;

import java.util.List;

/**
 * Created by admin on 2017/4/12.
 */
public interface ThresholdService {
    List<Threshold> getThresholdByOid(Integer oid);

    List<Threshold_area> getThresholdByRid(Integer rid);

    void updateThreshold(Threshold threshold);

    void updateThresholdArea(Threshold_area threshold_area);

    List<Threshold_wendu> getThreshold_wByOid(Integer oid);

    void updateThreshold_w(Threshold_wendu threshold_wendu);

    List<Threshold_light> getThreshold_lByOid(Integer oid);

    void updateThreshold_l(Threshold_light threshold_light);

    void updateThreshold_d(Threshold_out threshold_out);

    List<Threshold_out> getThreshold_dByOid(Integer oid);
}
