package com.warn.exception;

/**
 * 预警机制的算法中  从mysql数据库取数据的异常
 * Created by netlab606 on 2017/5/17.
 */
public class NullFromDBException extends WarnException{
    public NullFromDBException(String message) {
        super(message);
    }

    public NullFromDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
