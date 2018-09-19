package com.warn.sms;

/**
 * 短信通知模板变量
 * Created by netlab606 on 2017/5/28.
 */
public class SMSParam {
    private String oldMan;
    private String oldName;
    private String warnType;
    private String time;

    public String getOldMan() {
        return oldMan;
    }

    public void setOldMan(String oldMan) {
        this.oldMan = oldMan;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getWarnType() {
        return warnType;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
