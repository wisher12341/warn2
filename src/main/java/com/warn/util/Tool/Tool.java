package com.warn.util.Tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {

        public static String longToDate(Long lo){
            Date date = new Date(lo);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sd.format(date);
        }
    }

