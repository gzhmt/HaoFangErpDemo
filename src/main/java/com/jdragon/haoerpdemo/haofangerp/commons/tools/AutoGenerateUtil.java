package com.jdragon.haoerpdemo.haofangerp.commons.tools;

import java.text.MessageFormat;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.25 22:04
 * @Description: 自动生成单号
 */
public class AutoGenerateUtil {
    /**
     * 日期格式
     */
    private static String dateFormat = "yyyyMMdd";
    /**
     * 单号格式
     */
    private static String increaseFormat = "{0}-{1}-{2}";

    /**
     * 通过最后单号创建自增单号
     * @param str 最后单号
     * @return 单号
     */
    public static String createIncreaseOdd(String str){
        /*
          |-检测历史计划最后创建的计划，没有则直接使用 {xx}-{今日日期}-{0001}，如果有则进入条件
               |-将单号按 - 号分隔，分隔之后传日期部分进行对比，返回结果
                   |-如果今日创建过单号，则根据上次生成的第三部分+1来生成
                   |-如果今日没有创建过单号，则使用0001
         */
        String[] split = str.split("-");
        boolean lastPlanCreateIsToday = Date2Util.contrastNowDateStr(split[1], dateFormat);
        String newPlanProductionThreePartStr;/*{SC}-{20200325}-{0001}这个变量为最后部分的0001的生成*/
        if (lastPlanCreateIsToday) {
            int newPlanProductionThreePart = Integer.parseInt(split[2]) + 1;
            newPlanProductionThreePartStr = String.format("%04d", newPlanProductionThreePart);
        } else {
            newPlanProductionThreePartStr = String.format("%04d", 1);
        }
        //使用{}-{}-{}格式占位符来生成生产单号
        return MessageFormat.format(increaseFormat,split[0] ,Date2Util.now(dateFormat), newPlanProductionThreePartStr);
    }

    /**
     * 创建首次单号
     * @param type 单号前缀
     * @return 单号
     */
    public static String createTodayFirstOdd(String type){
        return MessageFormat.format(increaseFormat,type,Date2Util.now(dateFormat), String.format("%04d", 1));
    }
}
