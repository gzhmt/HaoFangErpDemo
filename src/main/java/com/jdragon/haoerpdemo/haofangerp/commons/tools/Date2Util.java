package com.jdragon.haoerpdemo.haofangerp.commons.tools;

import org.assertj.core.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.25 13:43
 * @Description:
 */
public class Date2Util {

    /**
     * @Author: Jdragon
     * @Date: 2020.03.25 下午 2:36
     * @params: [dateStr, format]
     * @return: boolean
     * @Description: 传入日期字符与日期格式，判断今天的日期是否与日期字符相符
     **/
    public static boolean contrastNowDateStr(String dateStr,String format){

        return dateStr.equals(now(format));
    }

    /**
     * @Author: Jdragon
     * @Date: 2020.03.25 下午 2:35
     * @params: [format]
     * @return: java.lang.String
     * @Description: 根据格式获取今日的日期
     **/
    public static String now(String format){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = DateUtil.now();
        return simpleDateFormat.format(date);
    }
}
