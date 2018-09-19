package com.warn.dto;

import com.warn.entity.OldMan;
import com.warn.entity.Room;
import com.warn.entity.Threshold_wendu;

/**
 * Created by admin on 2017/4/26.
 */
public class Warn_wendu {

    private Threshold_wendu threshold_wendu;
    private Integer wendu;//当前温度
    private OldMan oldMan;



    public Integer getWendu() {
        return wendu;
    }

    public void setWendu(Integer wendu) {
        this.wendu = wendu;
    }

    public Threshold_wendu getThreshold_wendu() {
        return threshold_wendu;
    }

    public void setThreshold_wendu(Threshold_wendu threshold_wendu) {
        this.threshold_wendu = threshold_wendu;
    }

    public OldMan getOldMan() {
        return oldMan;
    }

    public void setOldMan(OldMan oldMan) {
        this.oldMan = oldMan;
    }
}
