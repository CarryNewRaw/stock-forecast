package com.ls.stockforecast.utils;

import java.util.Calendar;

/**
 * Created by CharloYao on 2017/8/14.
 */
public final class CalendarUtils {

    private CalendarUtils() {
    }

    /**
     * 清除时间；时、分、秒归零
     * @param calendar
     * @return
     */
    public static Calendar clearTime(Calendar calendar) {
        if (calendar == null) return calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar;
    }

    /**
     * 获得当前日期；时、分、秒归零
     * @return
     */
    public static Calendar getCurrentDateWithZeroTime(){
        Calendar calendar = Calendar.getInstance();
        calendar = clearTime(calendar);
        return calendar;
    }
}
