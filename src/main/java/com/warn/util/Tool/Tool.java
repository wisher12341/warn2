package com.warn.util.Tool;

import com.warn.entity.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Tool {

        public static String longToDate(Long lo){
            Date date = new Date(lo);
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sd.format(date);
        }
    public static String getPositionInfo(int data, Room room) {
        if (data >= 0)
            switch (data) {
                case 0:
                    return "户外";
                case 1:
                    return room.getNumOne();
                case 2:
                    return room.getNumTwo();
                case 3:
                    return room.getNumThree();
                case 4:
                    return room.getNumFour();
                case 5:
                    return room.getNumFive();
                case 6:
                    return room.getNumSix();
                case 7:
                    return room.getNumSeven();
                case 8:
                    return room.getNumEight();
                case 9:
                    return room.getNumNine();
                case 10:
                    return "其他区域";
                default:
                    return "地板";
            }
        return "无人";


    }
    public static String getDate(){//今天
        Date date  = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(date);
    }
    public static String getYesDate(){//昨天
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        date = new Date(date.getTime() - 86400000);
        return sdf.format(date.getTime());
    }

    public static String getYesYesDate(){//前天
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        date = new Date(date.getTime() - 86400000 * 2);
        return sdf.format(date.getTime());
    }

}

