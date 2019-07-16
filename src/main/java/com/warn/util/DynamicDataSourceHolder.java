package com.warn.util;

public class DynamicDataSourceHolder {
    private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<>();

    public static String getDataSource(){
        return (String)THREAD_DATA_SOURCE.get();
    }

    public static void setDataSource(String dataSource){
        THREAD_DATA_SOURCE.set(dataSource);
    }

    public static void clearDataSource(){
        THREAD_DATA_SOURCE.remove();
    }
}
