package com.jdragon.haoerpdemo.haofangerp.examine.component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间转时间戳
 *
 * @author 金柏宇
 * @version 1.0
 * @date 2020/3/26 20:59
 */
public class DateConvertUtils {
    /**
     *
     * @param date 根据字符串表示的【yyyy-MM-dd HH:mm:ss]】格式的时间进行解析
     * @return 返回指定时间的时间戳
     */
    public static long convertDateTimeToTimeStamp(String date){
        //字符串中若m、H为个位数，必须补0
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //获取时间戳
        long timeStamp=LocalDateTime.parse(date,formatter).toInstant(ZoneOffset.of("+8")).getEpochSecond();
        return timeStamp;
    }

    /**
     *
     * @param date java.util.Date类型
     * @return 转为时间戳
     */
    public static long convertDateTimeToTimeStamp(Date date){
        return date.toInstant().atZone(ZoneId.of("+8")).toEpochSecond();
    }


}
