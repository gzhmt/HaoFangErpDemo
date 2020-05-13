package com.jdragon.haoerpdemo.haofangerp.commons.constant;

/**
 * 常量类
 * @author zhu
 * @version 1.0
 * @date 2020/5/12 下午9:31
 * @Description
 */
public class Constants {

    /**
     * 匹配密码的正则表达式:8～16位，必须同时包含至少一个字母，一个数字，一个特殊字符(包括@$!%*#?&_.)
     */
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&_.])[A-Za-z\\d$@$!%*#?&_.]{8,16}$";
}
