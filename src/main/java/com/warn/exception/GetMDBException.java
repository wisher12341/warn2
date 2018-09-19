package com.warn.exception;

/**
 * 预警机制算法中 从mongoDB 取数据发生的异常
 * Created by netlab606 on 2017/5/17.
 */
public class GetMDBException extends WarnException {
    public GetMDBException(String message) {
        super(message);
    }

    public GetMDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
