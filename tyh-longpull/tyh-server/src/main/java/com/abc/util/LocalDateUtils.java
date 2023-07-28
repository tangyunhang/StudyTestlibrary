package com.abc.util;

import org.apache.commons.lang.time.DateFormatUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description: LocalDateUtils
 * @Author: 青衣醉
 * @Date: 2022/9/6 2:21 下午
 */
public class LocalDateUtils {
    /**
     * 完整时间(默认时间格式) yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULTTIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日(无下划线) yyyyMMdd
     */
    public static final String NOUNDERLINEYMD = "yyyyMMdd";

    /**
     * 年月日(默认格式) yyyy-MM-dd
     */
    public static final String DEFAULTYMD = "yyyy-MM-dd";



    /**
     * 根据自定义格式返回，被格式的时间
     *
     * @param time
     * @param formatterTime
     * @return
     */
    public static LocalDateTime getLocalDateTime(String time, String formatterTime) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(formatterTime));
    }

    public static String getNowDateStr(){
        Date date = new Date ();
       return DateFormatUtils.format (date,DEFAULTYMD);
    }

    public static Date getNowDate(){
        return new Date ();
    }
}
