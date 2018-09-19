package com.warn.service;

import com.warn.entity.Threshold;
import com.warn.entity.Threshold_light;
import com.warn.entity.Threshold_out;
import com.warn.entity.Threshold_wendu;

import java.util.List;

/**
 * Created by admin on 2017/4/12.
 */
public interface ThresholdService {
    List<Threshold> getThresholdByOid(Integer oid);

    void updateThreshold(Threshold threshold);

    List<Threshold_wendu> getThreshold_wByOid(Integer oid);

    void updateThreshold_w(Threshold_wendu threshold_wendu);

    List<Threshold_light> getThreshold_lByOid(Integer oid);

    void updateThreshold_l(Threshold_light threshold_light);

    void updateThreshold_d(Threshold_out threshold_out);

    List<Threshold_out> getThreshold_dByOid(Integer oid);
}
