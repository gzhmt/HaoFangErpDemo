package com.jdragon.haoerpdemo.haofangerp.commons.tools;

import java.text.MessageFormat;

/**
 * @Author: Jdragon
 * @email: 1061917196@qq.com
 * @Date: 2020.03.25 22:04
 * @Description:
 */
public class AutoGenerateUtil {

    private static String dateFormat = "yyyyMMdd";

    private static String increaseFormat = "{0}-{1}-{2}";

    /**
     * @Author: Jdragon
     * @Date: 2020.03.25 下午 10:28
     * @params: [str]
     * @return: java.lang.String
     * @Description: 用来根据最后单号来生成自增后的单号
     **/
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
     * @Author: Jdragon
     * @Date: 2020.03.25 下午 10:28
     * @params: [type]
     * @return: java.lang.String
     * @Description: 根据类型来生成第一次创建的单号
     **/
    public static String createTodayFirstOdd(String type){

        return MessageFormat.format(increaseFormat,type,Date2Util.now(dateFormat), String.format("%04d", 1));
    }
}
