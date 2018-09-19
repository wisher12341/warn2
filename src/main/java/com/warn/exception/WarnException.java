package com.warn.exception;

/**
 * 所有与预警相关的异常处理
 * Created by netlab606 on 2017/5/17.
 */
public class WarnException extends RuntimeException {
    public WarnException(String message) {
        super(message);
    }

    public WarnException(String message, Throwable cause) {
        super(message, cause);
    }
}
