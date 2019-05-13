package com.ls.stockforecast.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yaokai on 16/4/27.
 */
public class DateUtils {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_PATTERN_MONTH = "yyyy-MM";
    public static final String DATE_TIME_PATTERN_MONTH_ = "yyyyMM";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_ = "yyyyMMdd";
    public static final String DATE_MONTH = "mm";

    public static final String DATE_PATTERN_OPEN_API = "yyyy-MM-dd'T'HH:mm:ss'Z'";


    public static String getCurrentDate(){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(new Date());
    }

    public static String getCurrentDate(String format){
        SimpleDateFormat ft = new SimpleDateFormat(format);
        return ft.format(new Date());
    }

    public static String getDateStr(Date date){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.format(date);
    }

    public static String getFormatDateStr(Date date, String format) {
        SimpleDateFormat ft = new SimpleDateFormat(format);
        return ft.format(date);
    }

    public static String getDateStr(Date date, String format) {
        SimpleDateFormat ft = new SimpleDateFormat(format);
        return ft.format(date);
    }

    public static String getDateOpenApiStr(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat(DATE_PATTERN_OPEN_API);
        return ft.format(date);
    }

    public static Date getDateOpenApiStr(String date) throws Exception {
        SimpleDateFormat ft = new SimpleDateFormat(DATE_PATTERN_OPEN_API);
        return ft.parse(date);
    }

    public static Date getFormatDate(String time, String format) {
        return getFormatDate(time, format, false);
    }

    public static Date getFormatDate(String time, String format, boolean lenient) {
        SimpleDateFormat ft = new SimpleDateFormat(format);
        try {
            ft.setLenient(lenient);
            Date date = ft.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getTodayStartTime(){
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return new Date(todayStart.getTime().getTime());
    }

    /**
     * 获取当前时间UTC时间戳，精确到秒
     * @return
     */
    public static Long getCurrentUTC() {
        Date now = new Date();
        long time = now.getTime();
        return time/1000;
    }

    /**
     * 将date类型转换成UTC时间戳，精确到秒
     * @param date
     * @return
     */
    public static Long getUTC(Date date) {
        long time = date.getTime();
        return time/1000;
    }

    /**
     * @param begin
     * @param end
     * @return 时间差(分钟)
     */
    public static Long diffMinute(Date begin, Date end){
        long between = 0;
        try {
            between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (between > 0) {
            long min = between / (60 * 1000);
            return min + 1;
        }else {
            return 0l;
        }
    }

    public static double getHoursBetweenDates(Date start,Date end){
    	DecimalFormat df = new DecimalFormat("###.00");
    	long seconds = end.getTime() - start.getTime();
    	double hours = seconds / 3600.0;
    	hours = Double.parseDouble(df.format(hours));
    	return hours;
    }
    
    /**
     * 获取某月的第一天和最后一天
     * @param date
     * @return
     */
    public static Map<String,Object> getMinMaxDate(Date date){
    	SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int maxDay = cal.getActualMaximum(Calendar.DATE);
    	
    	String month = sdfMonth.format(cal.getTime());
    	String first = month + "01000000";
    	String last = month + maxDay + "235959";
    	Map<String,Object> map = new HashMap<>();
    	try {
			Date start = sdfDay.parse(first);
			Date end = sdfDay.parse(last);
			map.put("start", start);
			map.put("end", end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
    }
    
    /**
     * 毫秒转xx天xx小时xx分。。。
     * @param ms
     * @return
     */
    public static String formatTime(Integer ms) {  
        Integer ss = 1000;  
        Integer mi = ss * 60;  
        Integer hh = mi * 60;  
        Integer dd = hh * 24;  
      
        Integer day = ms / dd;  
        Integer hour = (ms - day * dd) / hh;  
        Integer minute = (ms - day * dd - hour * hh) / mi;  
        Integer second = (ms - day * dd - hour * hh - minute * mi) / ss;  
        Integer milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  
          
        StringBuffer sb = new StringBuffer();  
        if(day > 0) {  
            sb.append(day+"天");  
        }  
        if(hour > 0) {  
            sb.append(hour+"小时");  
        }  
        if(minute > 0) {  
            sb.append(minute+"分");  
        }  
        if(second > 0) {  
            sb.append(second+"秒");  
        }  
        if(milliSecond > 0) {  
            sb.append(milliSecond+"毫秒");  
        }  
        return sb.toString();  
    } 
    
    public static Date getDateFromLong(Long time){
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(time);
    	return cal.getTime();
    }
    
    /**
     * 两个时间段是否有重叠,参数时间都不为null
     * @param start
     * @param end
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isDateOverlap(Date start,Date end,Date startDate,Date endDate){
    	if(startDate.getTime() > end.getTime() 
    			|| endDate.getTime() < start.getTime()){
    		return false;
    	}
    	return true;
    }
}
