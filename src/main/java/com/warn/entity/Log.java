package com.warn.entity;

/**
 * Created by admin on 2017/5/4.
 */
public class Log {

    private Integer logId;
    private Integer userId;
    private String logData;
    private String logTime;

    public Integer getLogId() {
        return logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getLogData() {
        return logData;
    }

    public void setLogData(String logData) {
        this.logData = logData;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
}
